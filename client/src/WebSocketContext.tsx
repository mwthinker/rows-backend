import { createContext, useContext, ReactNode, useEffect, useState } from 'react';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { catchError, retry } from 'rxjs/operators';

interface WebSocketContextType {
  socket: WebSocketSubject<any> | null;
  isConnected: boolean;
  hasError: boolean;
}

const WebSocketContext = createContext<WebSocketContextType>({ socket: null, isConnected: false, hasError: false });

export const WebSocketProvider = ({ children }: { children: ReactNode }) => {
  const [socket, setSocket] = useState<WebSocketSubject<any> | null>(null);
  const [isConnected, setIsConnected] = useState(false);
  const [hasError, setHasError] = useState(false);

  useEffect(() => {
    const socket$ = webSocket('ws://localhost:3000/ws');
    setSocket(socket$);

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

    return () => {
      subscription.unsubscribe();
      socket$.complete();
    };
  }, []);

  return (
    <WebSocketContext.Provider value={{ socket, isConnected, hasError }}>
      {children}
    </WebSocketContext.Provider>
  );
};

export const useWebSocket = () => useContext(WebSocketContext);
