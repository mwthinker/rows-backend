import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useWebSocket } from '../../WebSocketContext';

type Game = {
  gameId: string;
  players: any[];
  started?: boolean;
}

export function Lobby({ gameId }: { gameId: string }) {
  const navigate = useNavigate();
  const { socket, isConnected } = useWebSocket();
  const [game, setGame] = useState<Game | null>(null);

  useEffect(() => {
    if (!socket || !isConnected || !gameId) return;

    const subscription = socket.subscribe({
      next: (message: any) => {
        console.log("Got a message!", message);
        if (message.type === 'S2C_BOARD') {
          setGame(message.state);
          
          // If game has started, redirect to game
          if (message.state.started) {
            navigate(`/games/${gameId}`);
          }
        }
      }
    });

    const GET_BOARD = {
      type: 'C2S_GET_BOARD',
      gameId
    }

    console.log({ GET_BOARD })

    // Request initial lobby state
    socket.next(GET_BOARD);



    return () => subscription.unsubscribe();
  }, [socket, isConnected, gameId, navigate]);

  const handleStartGame = () => {
    if (!socket || !isConnected || !gameId) return;
    socket.next({
      type: 'C2S_START_GAME',
      gameId
    });
  };

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen text-white">
      <h1 className="text-xl font-bold mb-6 text-blue-500">Game Lobby: {gameId}</h1>

      <pre>{JSON.stringify(game, null, 2)}</pre>
      
      {game && (
        <div className="text-gray-500">
          <h2 className="text-2xl mb-4">Players</h2>
          <div className="flex flex-col gap-2 mb-6">
            {game.players.map(player => (
              <div key={player.id} className="flex items-center gap-2">
                <span className="text-blue-500">{player.symbol}</span>
                <span>{player.id}</span>
              </div>
            ))}
            {game.players.length < 2 && (
              <div className="text-gray-400 italic">Waiting for opponent...</div>
            )}
          </div>

          <button
            className="px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600 disabled:opacity-50 disabled:cursor-not-allowed"
            onClick={handleStartGame}
            disabled={game.players.length !== 2}
          >
            Start Game {game.players.length !== 2 ? `(${game.players.length}/2 players)` : ''}
          </button>
        </div>
      )}

      <button
        className="mt-6 px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600"
        onClick={() => navigate('/games')}
      >
        Back to Games
      </button>
    </div>
  );
}

export default Lobby;