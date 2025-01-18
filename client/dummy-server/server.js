import { WebSocketServer } from 'ws';

const wss = new WebSocketServer({ port: 3000 });

console.log('WebSocket server started on ws://localhost:3000');

wss.on('connection', (ws) => {
    console.log('Client connected');
    
    // Send welcome message
    ws.send(JSON.stringify({
        type: 'welcome',
        message: 'Connected to WebSocket server'
    }));

    // Broadcast new connection to all clients
    wss.clients.forEach((client) => {
        if (client !== ws) {
            client.send(JSON.stringify({
                type: 'system',
                message: 'New player joined'
            }));
        }
    });

    // Handle messages
    ws.on('message', (data) => {
        try {
            const message = JSON.parse(data);
            console.log('Received:', message);

            // Echo the message back with a type
            ws.send(JSON.stringify({
                type: 'echo',
                originalMessage: message,
                timestamp: new Date().toISOString()
            }));
        } catch (error) {
            console.error('Error processing message:', error);
        }
    });

    // Handle client disconnect
    ws.on('close', () => {
        console.log('Client disconnected');
        wss.clients.forEach((client) => {
            client.send(JSON.stringify({
                type: 'system',
                message: 'A player left'
            }));
        });
    });
});
