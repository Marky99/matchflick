import {createTheme} from "@mui/material";

export const theme = createTheme({
  palette: {
    primary: {
      main: "#007bff",
    },
    secondary: {
      main: "#6c757d",
    },
  },
  typography: {
    fontFamily: "Roboto, sans-serif",
  },
  spacing: 0,
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 960,
      lg: 1280,
      xl: 1920,
    },
  },
});