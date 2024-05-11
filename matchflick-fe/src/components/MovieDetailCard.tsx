import {Button, Card, styled, Typography} from '@mui/material';
import Box from "@mui/material/Box";
import {FC} from "react";
import {Movie} from "../utils/api/matchflickApi.ts";

const DescriptionWrapper = styled(Box)({
  display: "flex",
  justifyContent: "space-between",
  marginBottom: "10px",
  marginTop: "20px",
  marginLeft: "20px",
  marginRight: "20px",
  fontSize: "10px",
});

const BoldText = styled(Typography)({
  fontWeight: "bold",
  fontSize: "10px",
});

const RightAlignedTypography = styled(Typography)({
  textAlign: "right",
});

const OverlappingSessionCard = styled(Card)(({theme}) => ({
  position: "absolute",
  width: "30%",
  borderRadius: "30px",
  zIndex: 1,
  height: "60vh",
  boxSizing: "border-box",
  overflow: "auto",
  [theme.breakpoints.down("sm")]: {
    width: "80%", // Adjust the width for small screens
    height: "80vh", // Adjust the height for small screens
    maxHeight: "80vh" // Add maxHeight to enable scrolling on smaller screens
  }
}));


interface MovieDetailsProps {
  movie: Movie | null;
  handleClose: () => void;
}

export const MovieDetailsCard: FC<MovieDetailsProps> = ({movie, handleClose}) => {
  const formatDate = (dateString: Date | undefined) => {
    if (!dateString) return "";

    const date = new Date(dateString);
    const options: Intl.DateTimeFormatOptions = {year: 'numeric', month: 'long', day: 'numeric'};
    return date.toLocaleDateString('en-US', options);
  };


  const details = [
    {label: 'Description', value: movie?.description},
    {label: 'Release date', value: movie?.publicationDate && formatDate(movie?.publicationDate)},
    {label: 'Rating', value: movie?.rating},
    {label: 'Duration', value: movie?.duration},
    {label: 'Creator', value: movie?.creator},
    {label: 'Director', value: 'NaN'},
    {label: 'Genre', value: 'NaN'},
    {label: 'Trailer',
      value: movie &&
          <a href={movie.trailerLink} target="_blank" rel="noopener noreferrer">Watch Trailer</a>
    },
    {label: 'Languages', value: ''},
    {label: 'Country of origin', value: movie?.countryOfOrigin},
  ];

  return (
      <OverlappingSessionCard>
        <Typography
            sx={{textAlign: "center", fontWeight: "bold", marginBottom: "10px", marginTop: "8%"}}>
          {movie?.name}
        </Typography>
        {details.map((detail, index) => (
            <DescriptionWrapper key={index}>
              <BoldText>{detail.label}</BoldText>
              <RightAlignedTypography>{detail.value}</RightAlignedTypography>
            </DescriptionWrapper>
        ))}
        <Button
            sx={{position: "absolute", top: "10px", right: "10px", width: "30px", height: "30px"}}
            onClick={handleClose}>X</Button>
      </OverlappingSessionCard>
  );
};