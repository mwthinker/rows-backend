import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import Splash from './Splash.tsx';
import Games from './Games.tsx';
import Game from './Game.tsx';

const App = () => (
  <Router>
    <Routes>
      <Route path="/" element={<Splash />} />
      <Route path="/games" element={<Games />} />
      <Route path="/games/:gameId" element={<Game />} />
    </Routes>
  </Router>
);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
