import { useNavigate } from 'react-router-dom';

export function Games() {
  const navigate = useNavigate();

  const games = [1, 2, 3]; // Example list of games

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen text-white text-gray-500">
      <h1 className="text-4xl font-bold mb-6 text-blue-500">Available Games</h1>
      <table className="table-auto border-collapse border border-gray-500">
        <thead>
          <tr>
            <th className="border border-gray-500 px-4 py-2">Game ID</th>
            <th className="border border-gray-500 px-4 py-2">Action</th>
          </tr>
        </thead>
        <tbody>
          {games.map((gameId) => (
            <tr key={gameId}>
              <td className="border border-gray-500 px-4 py-2 text-center">{gameId}</td>
              <td className="border border-gray-500 px-4 py-2 text-center">
                <button
                  className="cursor-pointer px-4 py-2 bg-blue-500 rounded text-white hover:bg-gray-600"
                  onClick={() => navigate(`/games/${gameId}`)}
                >
                  Join Game
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button
        className="mt-6 px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600"
        onClick={() => navigate('/')}
      >
        Back
      </button>
    </div>
  );
}

export default Games;
