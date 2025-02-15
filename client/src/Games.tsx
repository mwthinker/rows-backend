import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useWebSocket } from './WebSocketContext';

const C2S_GET_ROOMS = {
  type: "C2S_GET_ROOMS"
}

const C2S_CREATE_GAME = {
  type: "C2S_CREATE_GAME"
}

type Game = {
  gameId: string;
  players: any[];
  started?: boolean;
}

export function Games() {
  const navigate = useNavigate();

  const [games, setGames] = useState<Game[]>([]);
  const { socket, isConnected } = useWebSocket();

  const handleRequestRooms = () => {
    if (!socket || !isConnected) return;
    socket.next(C2S_GET_ROOMS);
  };

  const handleCreateGame = () => {
    if (!socket || !isConnected) return;
    socket.next(C2S_CREATE_GAME);
  };

  useEffect(() => {
    if (!socket || !isConnected) return;

    const subscription = socket.subscribe({
      next: (message: any) => {
        if (message.type === 'S2C_ROOMS') {
          setGames(message.games || []);
          return;
        }

        if (message.type === 'S2C_CREATED_GAME') {
          // Navigate to the newly created game
          navigate(`/games/${message.gameId}`);
          return;
        }

        if (message.type === 'S2C_CREATE_GAME') {
          alert("Game was created! Now we would redirect into the lobby. For now, refresh page to see the new record.");
          return;
        }

        throw new Error(`Unexpected message: ${message.type}`);
      }
    });

    handleRequestRooms();

    return () => subscription.unsubscribe();
  }, [socket, isConnected]);

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen text-white text-gray-500">
      <h1 className="text-4xl font-bold mb-6 text-blue-500">Available Games</h1>
      <table className="table-auto border-collapse border border-gray-500">
        <thead>
          <tr className="bg-blue-500">
            <th className="border border-gray-500 px-4 py-2">Game ID</th>
            <th className="border border-gray-500 px-4 py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {games.map((game) => (
            <tr key={game.gameId}>
              <td className="border border-gray-500 text-blue-600 px-4 py-2 text-center">{game.gameId}</td>
              <td className="border border-gray-500 px-4 py-2 text-center">
                <button
                  className="cursor-pointer px-4 py-2 bg-blue-500 rounded text-white hover:bg-gray-600"
                  onClick={() => {
                    navigate(`/games/${game.gameId}`);
                  }}
                >
                  Join Game
                </button>
              </td>
            </tr>
          ))}
          {games.length === 0 && (
            <tr>
              <td className="border border-gray-500 text-gray-500 px-4 py-2 text-center" colSpan={2}>No games available 😔</td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="flex gap-4">   
        <button
          className="mt-6 px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600"
          onClick={() => navigate('/')}
        >
          Back
        </button>
        <button
          className="mt-6 px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600"
          onClick={handleCreateGame}
        >
          Create Game
        </button>           
      </div>
    </div>
  );
}

export default Games;
