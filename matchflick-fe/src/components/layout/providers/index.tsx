import {FC} from 'react';
import {ThemeProvider} from '@mui/material';
import {GlobalStyles} from '../GlobalStyles';
import {SnackbarProvider} from 'notistack';
import {SNACKBAR_AUTO_HIDE_DURATION, SNACKBAR_MAX_SNACK,} from '../../../utils/constants.ts'
import {theme} from "../../../theme/Theme.tsx";

interface Props {
  children: React.ReactNode;
}

export const Providers: FC<Props> = ({children}) => {
  return (
      <>
        <GlobalStyles/>
        <ThemeProvider theme={theme}>
          <SnackbarProvider
              anchorOrigin={{vertical: 'bottom', horizontal: 'right'}}
              maxSnack={SNACKBAR_MAX_SNACK}
              autoHideDuration={SNACKBAR_AUTO_HIDE_DURATION}
              classes={{containerAnchorOriginBottomRight: 'z-alert'}}
          >
            {children}
          </SnackbarProvider>
        </ThemeProvider>
      </>
  );
};
