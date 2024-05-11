import {css, GlobalStyles as MGlobalStyles} from '@mui/material';
import {FC} from 'react';

const styles = css`
  html,
  body {
    font-family: 'Atyp Text', serif;
    font-size: 16px;
    font-weight: 400;
    margin: 0;
    height: 100%;
    width: 100%;
  }

  a {
    color: #816826;
  }
`;

export const GlobalStyles: FC = () => {
  return <MGlobalStyles styles={styles}/>;
};
