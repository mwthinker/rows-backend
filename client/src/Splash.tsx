import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { webSocket } from 'rxjs/webSocket';
import { catchError, retry } from 'rxjs/operators';

export function Splash() {
  const navigate = useNavigate();
  const [isConnected, setIsConnected] = useState(false);
  const [hasError, setHasError] = useState(false);

  useEffect(() => {
    const socket$ = webSocket('ws://localhost:3000');

    const subscription = socket$.pipe(
      retry(3),
      catchError(() => {
        setIsConnected(false);
        setHasError(true);
        return [];
      })
    ).subscribe({
      next: () => {
        setIsConnected(true);
        setHasError(false);
      },
      error: () => {
        setIsConnected(false);
        setHasError(true);
      },
    });

    return () => subscription.unsubscribe();
  }, []);

  useEffect(() => {
    if (isConnected) {
      navigate('/games');
    }
  }, [isConnected, navigate]);

  return (
    <div className="flex flex-col items-center justify-center w-full h-screen text-white">
      <h1 className="text-4xl font-bold mb-6 text-blue-500">5 in a Row!</h1>
      <div className="text-lg flex flex-col items-center gap-4">
        {hasError ? (
          <>
            <div className="text-red-500">Cannot connect to server</div>
            <div className="text-8xl mt-4">😵</div>
            <div className="text-sm text-gray-400 mt-4">
              (Is the server running on port 3000?)
            </div>
          </>
        ) : isConnected ? (
          <span>Connected! Redirecting...</span>
        ) : (
          <span>Connecting to server...</span>
        )}
      </div>
    </div>
  );
}

export default Splash;
