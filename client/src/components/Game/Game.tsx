import Lobby from './Lobby';
import { useParams } from 'react-router-dom';

function Game() {
  const { gameId } = useParams();
  if(gameId === undefined) return null;
  
  // TODO if (game.started) return <StartedGame />;
  
  return <Lobby gameId={gameId} />;
}

export default Game;
