import {FC, useState} from "react";
import {Button, Grid, Paper, styled, TextField, Typography} from '@mui/material';
import {getGenres, login} from "../utils/api/matchflickApi.ts"
import {useNavigate} from "react-router-dom";

const StyledContainer = styled(Grid)({
  height: "100vh",
  margin: 0,
  padding: 0,
});

const StyledLeftGrid = styled(Grid)(() => ({
  color: "black",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
}));

const StyledRightGrid = styled(Grid)(({theme}) => ({
  backgroundImage: "linear-gradient(to bottom right, #565DFF, #343899)",
  height: "100%",
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
  [theme.breakpoints.down("md")]: {
    display: "none",
  },
}));

const StyledPaper = styled(Paper)({
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  width: "60%",
});

const StyledButton = styled(Button)({
  borderRadius: 20,
  marginTop: "20px",
  backgroundImage: "linear-gradient(to right, #89CFF0, blue)",
  "&:hover": {
    backgroundImage: "none",
  },
});

export const Login: FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleLogin = async () => {
    setLoading(true);
    localStorage.clear();
    try {
      await login({username, password});
      const response = await getGenres({page: 0, size: 1});
      const isEmptyGenres = response.length === 0;
      navigate(isEmptyGenres ? "/genre-select" : "/dashboard");
    } catch (error) {
      console.error("Login failed:", error); // Handle login error
    } finally {
      setLoading(false);
    }
  };

  return (
      <StyledContainer container>
        <StyledLeftGrid item xs={12} sm={6}>
          <StyledPaper elevation={0}>
            <Typography variant="h5" gutterBottom>
              Login
            </Typography>
            <TextField
                label="Username"
                margin="normal"
                variant="outlined"
                fullWidth
                value={username}
                onChange={handleUsernameChange}/>
            <TextField
                label="Password"
                type="password"
                margin="normal"
                variant="outlined"
                fullWidth
                value={password}
                onChange={handlePasswordChange}/>
            <StyledButton variant="contained" fullWidth onClick={handleLogin} disabled={loading}>
              {loading ? "Logging in..." : "Login"} {/* Display loading state or button label based on loading state */}
            </StyledButton>
          </StyledPaper>
        </StyledLeftGrid>

        <StyledRightGrid item sm={6}>
          <Typography variant="h3" gutterBottom color="white">
            MatchFlick
          </Typography>
        </StyledRightGrid>
      </StyledContainer>
  );
}