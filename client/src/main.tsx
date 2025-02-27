import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import Splash from './Splash.tsx';
import Games from './Games.tsx';
import Game from './components/Game/Game.tsx';
import { WebSocketProvider } from './services/WebSocketContext.tsx';

export const App = () => (
  <Router>
    <WebSocketProvider>
      <Routes>
        <Route path="/" element={<Splash />} />
        <Route path="/games" element={<Games />} />
        <Route path="/games/:gameId" element={<Game />} />
      </Routes>
    </WebSocketProvider>
  </Router>
);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
