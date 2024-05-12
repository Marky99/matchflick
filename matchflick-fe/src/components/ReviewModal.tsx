import React, {FC} from 'react';
import {Dialog, DialogContent, DialogTitle, TextField} from "@mui/material";
import styled from "@mui/material/styles/styled";
import {StyledButton} from "./styles/common.tsx";

interface ReviewModalProps {
  lastReview: any;
  overlay: boolean;
  title: string;
  score: number;
  description: string;
  handleTitleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleScoreChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleDescriptionChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleSkip: () => void;
  handleConfirm: () => void;
}

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

export const ReviewModal: FC<ReviewModalProps> = ({
                                                    lastReview,
                                                    overlay,
                                                    title,
                                                    score,
                                                    description,
                                                    handleTitleChange,
                                                    handleScoreChange,
                                                    handleDescriptionChange,
                                                    handleSkip,
                                                    handleConfirm
                                                  }) => {
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
                  onChange={handleTitleChange}
              />
              <StyledTextInput
                  label="7/10"
                  id="score"
                  variant="outlined"
                  value={score}
                  onChange={handleScoreChange}
                  inputProps={{
                    pattern: "[1-9]|10|[1-9][0-9]?",
                    title: "Score must be a number from 1 to 10"
                  }}
              />
              <StyledTextInput
                  label="Review"
                  id="review"
                  variant="outlined"
                  multiline
                  rows={10}
                  value={description}
                  onChange={handleDescriptionChange}
              />
              <StyledButtonContainer>
                <StyledButton variant="outlined" color="error" onClick={handleSkip}>
                  Skip
                </StyledButton>
                <StyledButton variant="contained" color="primary" onClick={handleConfirm}>
                  Confirm
                </StyledButton>
              </StyledButtonContainer>
            </DialogContent>
          </StyledOverlay>
      )
  );
};
