import {FC, useEffect, useState} from "react";
import {Flexbox, StyledBackground, StyledButton} from "../components/styles/common.tsx";
import {Button, Card, styled, Typography} from "@mui/material";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import {
  endSession,
  getCurrentActiveSession,
  getMovie,
  isMatch,
  makeDecision,
  Movie
} from "../utils/api/matchflickApi.ts";
import {MovieDetailsCard} from "../components/MovieDetailCard.tsx";

const SessionCard = styled(Card)(({theme}) => ({
  width: "30%",
  height: "25vh",
  alignItems: "center",
  justifyContent: "center",
  borderRadius: "20px",
  backgroundColor: "white",
  position: "relative",
  [theme.breakpoints.down("sm")]: {
    width: "80%",
    marginTop: "50%",
  }
}));

const SessionBox = styled(Box)({
  marginTop: "10%",
  display: "flex",
  alignItems: "center",
  flexDirection: "column",
});

const QuestionMarkButton = styled(Button)({
  position: "absolute",
  bottom: "15px",
  right: "20px",
  borderRadius: "30px",
  backgroundColor: "#d3d3d3",
  textAlign: "center",
  color: "black"
});

// Define the arrow characters
const RightArrow = "\u2192"; // Right arrow
const LeftArrow = "\u2190";  // Left arrow

const ArrowButton = styled(Button)({
  width: "20%",
  height: "30px",
  fontSize: "20px",
  backgroundColor: "white",
  color: "black",
  textAlign: "center",
  margin: "20px",
  borderRadius: "20px"
});

export const ActiveSession: FC = () => {
  const navigate = useNavigate()
  const [swipe, setSwipe] = useState(false);
  const [movie, setMovie] = useState<Movie | null>(null);
  const [open, setOpen] = useState(false);
  const [match, setMatch] = useState("")

  useEffect(() => {
    (async () => {
      try {
        const matchResponse = await handleMatch();
        setMatch(matchResponse)
      } catch (error: any) {
        if (error.response && error.response.status === 404) {
          const response: Movie = await getMovie();
          setMovie(response);
        } else {
          console.error("Error:", error);
        }
      }
    })()
  }, [swipe])

  const handleOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }

  const handleSwipe = async (isSwipe: boolean) => {
    await makeDecision(movie!.id, isSwipe);
    setSwipe(!swipe)
  }

  const handleMatch = async () => {
    return await isMatch();
  }

  const handleEndSession = async () => {
    try {
      const response = await getCurrentActiveSession();
      await endSession(response.username);
    } catch (error) {
      console.error("Error occurred while ending session:", error);
    }

    navigate("/dashboard");
  }

  return (
      <Flexbox>
        <StyledBackground>
          <SessionBox>
            {match ? (
                <>
                  <SessionCard>
                    <Typography sx={{
                      fontWeight: "bold",
                      textAlign: "center",
                      marginTop: "10%"
                    }}>{match}</Typography>
                    <Typography sx={{fontWeight: "bold", textAlign: "center", marginTop: "10%"}}>It's
                      a
                      match!</Typography>
                  </SessionCard>
                  <Box display={"flex"} justifyContent={"center"}>
                    <StyledButton sx={{width: "100%"}} onClick={handleEndSession}>End
                      Session</StyledButton>
                  </Box>
                </>
            ) : (
                <SessionCard>
                  {movie && (
                      <Typography sx={{
                        textAlign: "center",
                        fontWeight: "bold",
                        marginTop: "20%"
                      }}>{movie.name}</Typography>
                  )}
                  <QuestionMarkButton onClick={handleOpen}>?</QuestionMarkButton>
                </SessionCard>
            )}
            {open && (
                <MovieDetailsCard movie={movie} handleClose={handleClose}/>
            )}
          </SessionBox>
          {!match && (
              <Box sx={{display: "flex", justifyContent: "center", marginTop: "5%"}}>
                <ArrowButton onClick={() => handleSwipe(false)}>{LeftArrow}</ArrowButton>
                <ArrowButton onClick={() => handleSwipe(true)}>{RightArrow}</ArrowButton>
              </Box>
          )}
        </StyledBackground>
      </Flexbox>
  );
}