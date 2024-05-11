import {FC, useEffect, useState} from "react";
import {Header} from "../components/Header.tsx";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  Table,
  TableBody,
  TableCell,
  TableRow,
  TextField,
  Typography
} from "@mui/material";
import Box from "@mui/material/Box";
import styled from "@mui/material/styles/styled";
import {
  Flexbox,
  StyledBackground,
  StyledButton,
  StyledMainText
} from "../components/styles/common.tsx";
import {useNavigate} from "react-router-dom";
import {
  GenericNameDescResponse,
  GenreResponse,
  getGenres,
  getLastReview,
  getMyLastMatches,
  getMyReviews,
  Match,
  postLastReview,
  Review
} from "../utils/api/matchflickApi.ts";
import {USERNAME} from "../utils/constants.ts";

interface StyledBoxProp {
  width: string;
}

const StyledBox = styled(Box)<StyledBoxProp>(({width}) => ({
  backgroundColor: "white",
  borderRadius: "30px",
  width: width,
  height: "30vh",
  margin: "2%",
}));

const StyledHeaderText = styled(Typography)(() => ({
  fontWeight: "bold",
  textAlign: "center",
  marginTop: "2%"
}));

const StyledOverlay = styled(Dialog)(({theme}) => ({
  '& .MuiPaper-root': {
    borderRadius: "30px",
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2),
    width: '40%',
  },
}));

const StyledTitle = styled(DialogTitle)({
  textAlign: 'center',
});

const StyledTextInput = styled(TextField)(() => ({
  width: '100%',
  marginBottom: "5%",
  marginTop: "2%"
}));

const StyledButtonContainer = styled('div')(({theme}) => ({
  display: 'flex',
  justifyContent: 'space-between',
  marginTop: theme.spacing(2),
}));

type ResponseType = GenreResponse[] | { title: string, score: number }[] | Match[];

export const Dashboard: FC = () => {
  const navigate = useNavigate();

  const [genres, setGenres] = useState<GenreResponse[]>();
  const [reviews, setReviews] = useState<{ title: string, score: number }[]>();
  const [lastMatches, setLastMatches] = useState<Match[]>();
  const [lastReview, setLastReview] = useState<GenericNameDescResponse>()
  const [title, setTitle] = useState("")
  const [score, setScore] = useState(0)
  const [description, setDescription] = useState("")
  const [overlay, setOverlay] = useState(false)

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  }

  const handleSkip = () => {
    setOverlay(false)
  }

  const handleConfirm = async () => {
    await postLastReview(lastReview!.id, {title, score, description})
    setOverlay(false)
  }

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleScoreChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    const newScore = parseInt(value, 10);

    if (!isNaN(newScore) || value === '') {
      setScore(value === '' ? 1 : (newScore > 10 ? parseInt(value[value.length - 1], 10) : (newScore === 0 ? 1 : newScore)));
    }
  };

  const handleDescriptionChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value);
  };

  useEffect(() => {
    (async () => {
      try {
        const genresResponse: GenreResponse[] = await getGenres({page: 0, size: 3});
        const reviewsResponse: Review[] = await getMyReviews({page: 0, size: 3});
        const lastMatchesResponse: Match[] = await getMyLastMatches();

        setGenres(genresResponse);
        setReviews(reviewsResponse.map(r => ({title: r.title, score: r.score})));
        setLastMatches(lastMatchesResponse);

        const lastReview: GenericNameDescResponse = await getLastReview();
        console.log(lastReview);
        if (lastReview) {
          setLastReview(lastReview);
          setOverlay(true);
        } else {
          setOverlay(false);
        }
      } catch (error: any) {
        if (error.response && error.response.status === 404) {
          console.log("IN")
          setOverlay(false);
        } else {
          console.error("Error:", error);
        }
      }
    })();
  }, [overlay]);

  const renderTable = (responses: ResponseType | undefined) => {
    if (!responses || responses.length === 0) {
      return (
          <Typography sx={{textAlign: "center"}}>No Data yet</Typography>
      )
    }

    return (
        <Box style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "80%"
        }}>
          <Table sx={{width: "80%"}}>
            <TableBody>
              {responses.map((response, index) => (
                  <TableRow key={index}>
                    {Object.values(response).map((value, columnIndex) => (
                        <TableCell key={columnIndex}>
                          {typeof value === 'number' ? value.toFixed(2) : value}
                        </TableCell>
                    ))}
                  </TableRow>
              ))}
            </TableBody>
          </Table>
        </Box>
    );
  };

  const ReviewModal = () => {
    return (
        lastReview != null && (
            <StyledOverlay open={overlay}>
              <StyledTitle>Leave a review on your last selection?</StyledTitle>
              <StyledTitle>{lastReview.name}</StyledTitle>
              <DialogContent>
                <StyledTextInput
                    label="Title of a review"
                    id="name"
                    variant="outlined"
                    value={title}
                    onChange={handleTitleChange}/>
                <StyledTextInput
                    label="7/10"
                    id="score"
                    variant="outlined"
                    value={score}
                    onChange={handleScoreChange}
                    inputProps={{
                      pattern: "[1-9]|10|[1-9][0-9]?",
                      title: "Score must be a number from 1 to 10"
                    }}/>
                <StyledTextInput
                    label="Review"
                    id="review"
                    variant="outlined"
                    multiline
                    rows={10}
                    value={description}
                    onChange={handleDescriptionChange}/>
                <StyledButtonContainer>
                  <StyledButton variant="outlined" color="error"
                                onClick={handleSkip}>Skip</StyledButton>
                  <StyledButton variant="contained" color="primary"
                                onClick={handleConfirm}>Confirm</StyledButton>
                </StyledButtonContainer>
              </DialogContent>
            </StyledOverlay>
        )
    );
  };

  return (
      <StyledBackground>
        <Header/>
        <StyledMainText>
          Hello {localStorage.getItem(USERNAME)}
        </StyledMainText>
        <Flexbox>
          <StyledBox width="30%">
            <StyledHeaderText>My reviews</StyledHeaderText>
            {renderTable(reviews)}
          </StyledBox>
          <StyledBox width="30%">
            <StyledHeaderText>Last matches</StyledHeaderText>
            {renderTable(lastMatches)}
          </StyledBox>
          <StyledBox width="30%">
            <StyledHeaderText>Top friends</StyledHeaderText>
            {renderTable(undefined)}
          </StyledBox>
        </Flexbox>
        <Flexbox>
          <StyledBox width={"65%"}>
            <StyledHeaderText>Most liked genres</StyledHeaderText>
            {renderTable(genres)}
          </StyledBox>
          <StyledButton onClick={handleLogout}>Logout</StyledButton>
        </Flexbox>
        {ReviewModal()}
      </StyledBackground>
  );
};
