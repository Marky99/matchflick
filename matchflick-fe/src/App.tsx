import {Router} from "./Router.tsx";
import {BrowserRouter} from "react-router-dom";
import {FC, Suspense} from "react";
import {Providers} from "./components/layout/providers";

export const App: FC = () => (
    <Suspense>
      <Providers>
        <BrowserRouter>
          <Router/>
        </BrowserRouter>
      </Providers>
    </Suspense>
);
