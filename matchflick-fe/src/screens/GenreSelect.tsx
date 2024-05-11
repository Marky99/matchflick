import {FC, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Button, Grid, styled, Typography} from "@mui/material";
import {assignGenre, getAllGenres} from "../utils/api/matchflickApi.ts";

interface Genre {
  id: string;
  name: string;
  description: string;
}

const GradientBackground = styled(Grid)(() => ({
  backgroundImage: 'linear-gradient(to bottom, #565DFF, #343899)',
  height: '100vh', // Set height to cover the entire viewport
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  padding: '2rem',
}));

const StyledButton = styled(Button)(({selected}: { selected: boolean }) => ({
  backgroundColor: selected ? '#00008B' : 'transparent',
  color: "white",
  border: "2px solid white", // Enclose the value in quotes
  borderRadius: 20,
  margin: "0.5rem", // Enclose the value in quotes
}));

export const GenreSelect: FC = () => {
  const navigate = useNavigate();
  const [selectedGenres, setSelectedGenres] = useState<string[]>([]);
  const [genres, setGenres] = useState<Genre[]>([]);

  useEffect(() => {
    (async () => {
      const response = await getAllGenres({page: 0, size: 30});
      setGenres(response);
    })()
  }, [])

  const toggleGenre = (id: string) => {
    setSelectedGenres(prevGenres => {
      if (prevGenres.includes(id)) {
        return prevGenres.filter(genreId => genreId !== id);
      } else {
        return [...prevGenres, id];
      }
    });
  };

  const handleContinue = async () => {
    if (selectedGenres.length > 0) {
      await assignGenre(selectedGenres);
      navigate('/dashboard');
    } else {
      // No genre is selected, display an error message or handle it accordingly
      alert('Please select at least one genre.');
    }
  };

  return (
      <GradientBackground container>
        <Grid item>
          <Typography variant="h2" color="white" gutterBottom>
            What genres do you like?
          </Typography>
        </Grid>
        <Grid item container justifyContent="center">
          {genres.map(genre => (
              <StyledButton
                  key={genre.id}
                  variant="outlined"
                  selected={selectedGenres.includes(genre.id)}
                  onClick={() => toggleGenre(genre.id)}
              >
                {genre.name}
              </StyledButton>
          ))}
        </Grid>
        <Grid item container justifyContent="center" marginTop="2rem">
          <StyledButton
              variant={'outlined'}
              onClick={() => navigate('/dashboard')}
              selected={false}>
            Skip
          </StyledButton>
          <StyledButton
              variant={'outlined'}
              onClick={handleContinue}
              selected={false}>
            Continue
          </StyledButton>
        </Grid>
      </GradientBackground>
  );
}