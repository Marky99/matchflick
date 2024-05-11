import * as React from "react";
import {FC} from "react";
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import {AppBar, IconButton, Toolbar} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import {useNavigate} from "react-router-dom";

const routes = [
  {name: "Profile", route: "dashboard"},
  {name: "Start session", route: "session"}
];

export const Header: FC = () => {
  const navigate = useNavigate();
  const [open, setOpen] = React.useState(false);

  const toggleDrawer = (newOpen: boolean) => () => {
    setOpen(newOpen);
  };

  const handleNavigation = (path: string) => {
    navigate(path);
    setOpen(false); // Close the drawer after navigation
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  }

  const DrawerList = (
      <Box sx={{width: 250}} role="presentation" onClick={toggleDrawer(false)}>
        <List>
          {routes.map((r) => (
              <ListItem key={r.name} disablePadding onClick={() => handleNavigation(`/${r.route}`)}>
                <ListItemButton>
                  <ListItemText primary={r.name}/>
                </ListItemButton>
              </ListItem>
          ))}
        </List>
        <Box sx={{backgroundColor: "white"}}>
          <List sx={{position: "absolute", bottom: "0", width: "100%", marginBottom: "10%"}}>
            <ListItem disablePadding onClick={handleLogout}>
              <ListItemButton>
                <ListItemText primary="Log out"/>
              </ListItemButton>
            </ListItem>
          </List>
        </Box>
      </Box>
  );

  return (
      <div>
        <AppBar position="static" sx={{backgroundColor: 'white', borderBottom: '1px solid #ccc'}}>
          <Toolbar sx={{justifyContent: 'space-between'}}>
            <Box sx={{color: "black", marginLeft: "2%"}}>
              LOGO
            </Box>
            <IconButton
                edge="end"
                sx={{color: "black", marginRight: "2%"}}
                aria-label="menu"
                onClick={toggleDrawer(true)}
            >
              <MenuIcon/>
            </IconButton>
          </Toolbar>
        </AppBar>
        <Drawer open={open} anchor="right" onClose={toggleDrawer(false)}>
          {DrawerList}
        </Drawer>
      </div>
  );
}