import {FC, useEffect, useState} from "react";
import {
  Flexbox,
  StyledBackground,
  StyledButton,
  StyledMainText
} from "../components/styles/common.tsx";
import {styled, TextField, Typography} from "@mui/material";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import {endSession, getCurrentActiveSession, startSession} from "../utils/api/matchflickApi.ts";


const SessionText = styled(Typography)(({theme}) => ({
  fontWeight: "bold",
  marginBottom: 1,
  textAlign: "center",
  marginTop: "5%",
  fontSize: "20px",
  [theme.breakpoints.down("sm")]: {
    width: "80%",
    marginTop: "50%",
  }
}));

const InputText = styled(TextField)(({theme}) => ({
  margin: "auto",
  bgcolor: "white",
  borderRadius: "10px",
  textAlign: "right",
  width: "20%",
  [theme.breakpoints.down("sm")]: {
    width: "80%",
    marginTop: "30%",
  }
}));

const SessionCard = styled(Typography)(({theme}) => ({
  width: "30%",
  height: "25vh",
  alignItems: "center",
  justifyContent: "center",
  borderRadius: "20px",
  backgroundColor: "white",
  [theme.breakpoints.down("sm")]: {
    width: "80%",
    marginTop: "50%",
  }
}));

const SessionBox = styled(Box)(({theme}) => ({
  marginTop: "10%",
  display: "flex",
  alignItems: "center",
  flexDirection: "column",
  [theme.breakpoints.down("sm")]: {
    width: "80%",
    marginTop: "50%",
  }
}));

export const Session: FC = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [sessionName, setSessionName] = useState("");


  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handleBack = () => {
    navigate("/dashboard")
  };

  const handleDecline = async () => {
    await endSession(sessionName)

    navigate("/dashboard")
  };

  const handleStartSession = async () => {
    await fetchData();
    if (sessionName === "") {
      try {
        await startSession(username);
      } catch (error: any) {
        if (error.response && error.response.status === 404) {
          console.log("username not found");
          return;
        } else {
          console.error("Error:", error);
        }
      }
    }
    navigate("/active-session")
  }

  const handleStartActiveSession = async () => {
    navigate("/active-session")
  }

  const fetchData = async () => {
    try {
      const response = await getCurrentActiveSession();
      setSessionName(response.username);
    } catch (error: any) {
      if (error.response && error.response.status === 404) {
        console.log("Session not found");
      } else {
        console.error("Error:", error);
      }
    }
  };

  useEffect(() => {
    (async () => {
      await fetchData()
    })()
  }, [])

  return (
      <Flexbox>
        <StyledBackground>
          {sessionName === "" ? (
                  <>
                    <StyledMainText>Find user to start a session</StyledMainText>
                    <Box sx={{display: "flex", height: "20vh", alignItems: "center"}}>
                      <InputText
                          label="Username"
                          value={username}
                          onChange={handleUsernameChange}
                          sx={{
                            margin: "auto",
                            bgcolor: "white",
                            borderRadius: "10px",
                            textAlign: "right",
                            width: "20%"
                          }}
                      />
                    </Box>
                    <Box sx={{display: "flex", justifyContent: "center"}}>
                      <StyledButton onClick={handleBack}>Back</StyledButton>
                      <StyledButton marginLeft="5%" onClick={handleStartSession}>Start a
                        session</StyledButton>
                    </Box>
                  </>
              ) :
              (<SessionBox>
                <SessionCard>
                  <SessionText>User</SessionText>
                  <SessionText variant="h6">{sessionName}</SessionText>
                  <SessionText variant="h6">Wants to start a session</SessionText>
                </SessionCard>
                <Box sx={{width: "80%", display: "flex", justifyContent: "center", mt: 2}}>
                  <StyledButton onClick={handleDecline}>Decline</StyledButton>
                  <StyledButton marginLeft="8%" onClick={handleStartActiveSession}>Start a
                    session</StyledButton>
                </Box>
              </SessionBox>)}
        </StyledBackground>
      </Flexbox>
  );
}