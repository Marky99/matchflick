import styled from "@mui/material/styles/styled";
import Box from "@mui/material/Box";
import {Button, Theme, Typography} from "@mui/material";

export const StyledBackground = styled(Box)(() => ({
  height: "100vh",
  width: "100vw",
  backgroundImage: "linear-gradient(to bottom right, #565DFF, #343899)",
}));

export const Flexbox = styled(Box)(() => ({
  display: "flex",
}));

interface StyledButtonProp {
  marginLeft?: string;
  theme?: Theme
}

export const StyledButton = styled(Button)<StyledButtonProp>(({marginLeft, theme}) => ({
  width: "20%",
  height: "5vh",
  backgroundColor: "white",
  marginTop: "15%",
  marginLeft: marginLeft,
  borderRadius: "15px",
  color: "black",
  fontWeight: "bold",
  alignSelf: "flex-end",
  marginBottom: "2%",
  [theme.breakpoints.down("sm")]: {
    width: "40%",
    marginTop: "50%",
  }
}));

export const StyledMainText = styled(Typography)(() => ({
  fontSize: "40px",
  color: "white",
  fontWeight: "bold",
  textAlign: "center",
  marginTop: "2%",
}));