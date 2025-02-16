import { useNavigate } from 'react-router-dom';
import { useWebSocket } from './services/WebSocketContext';

export function Splash() {
  const navigate = useNavigate();
  const { isConnected, hasError } = useWebSocket();

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen text-white">
      <h1 className="text-4xl font-bold mb-6 text-blue-500">5 in a Row!</h1>
      <div className="text-lg flex flex-col items-center gap-4">
        {hasError && (
          <>
            <div className="text-red-500">Cannot connect to server</div>
            <div className="text-8xl mt-4">😵</div>
            <div className="text-sm text-gray-400 mt-4">
              (Is the server running on port 3000?)
            </div>
          </>
        )}
        {isConnected && <div>
          <div>Is connected!</div>
          <button
            className="mt-6 px-6 py-3 bg-blue-500 rounded text-white hover:bg-gray-600"
            onClick={() => navigate('/games')}
          >
            Show games
          </button>          
        </div>}
      </div>
    </div>
  );
}

export default Splash;
