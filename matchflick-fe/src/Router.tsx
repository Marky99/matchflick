import {FC} from "react";
import {Route, Routes} from "react-router-dom";
import {Login} from "./screens/Login.tsx";
import {GenreSelect} from "./screens/GenreSelect.tsx";
import {Dashboard} from "./screens/Dashboard.tsx";
import {Session} from "./screens/Session.tsx";
import {ActiveSession} from "./screens/ActiveSession.tsx";
import {ACCESS_TOKEN} from "./utils/constants.ts";

export const Router: FC = () => {

  return (
      <Routes>
        <Route path="/login" element={<Login/>}/>
        <Route path="/genre-select" element={<GenreSelect/>}/>
        <Route path="/dashboard" element={<Dashboard/>}/>
        <Route path="/session" element={<Session/>}/>
        <Route path="/active-session" element={<ActiveSession/>}/>
        <Route path="*"
               element={localStorage.getItem(ACCESS_TOKEN) != null ? <Dashboard/> : <Login/>}/>
      </Routes>
  )
}