import { useState, useEffect } from 'react';
//import { useParams } from 'react-router-dom';



function Game() {
  //const { gameId } = useParams();  
  const [gridSize, setGridSize] = useState(25);
  const [cellSize, setCellSize] = useState(25);
  const [fontSize, setFontSize] = useState(20);
  const [board, setBoard] = useState<string[][]>([]);
  const [currentPlayer, setCurrentPlayer] = useState<'X' | 'O'>('X');
  const [winner, setWinner] = useState<string | null>(null);

  // Dynamically calculate grid size based on screen dimensions
  useEffect(() => {
    const smallerSide = Math.max(window.innerWidth, window.innerHeight);
    const calculatedGridSize = Math.floor(smallerSide / 30); // Adjust divisor to change the grid density
    const calculatedCellSize = Math.floor(smallerSide / calculatedGridSize);

    setGridSize(calculatedGridSize);
    setCellSize(calculatedCellSize);
    setFontSize(calculatedCellSize / 2);
    setBoard(Array(calculatedGridSize).fill(null).map(() => Array(calculatedGridSize).fill('')));
  }, []);

  // Check for a winner
  const checkWinner = (row: number, col: number) => {
    const directions = [
      [1, 0], // horizontal
      [0, 1], // vertical
      [1, 1], // diagonal top-left to bottom-right
      [1, -1], // diagonal top-right to bottom-left
    ];

    for (const [dx, dy] of directions) {
      let count = 1;

      // Check in the positive direction
      for (let i = 1; i < 5; i++) {
        const newRow = row + i * dx;
        const newCol = col + i * dy;
        if (
          newRow < 0 ||
          newRow >= gridSize ||
          newCol < 0 ||
          newCol >= gridSize ||
          board[newRow][newCol] !== currentPlayer
        )
          break;
        count++;
      }

      // Check in the negative direction
      for (let i = 1; i < 5; i++) {
        const newRow = row - i * dx;
        const newCol = col - i * dy;
        if (
          newRow < 0 ||
          newRow >= gridSize ||
          newCol < 0 ||
          newCol >= gridSize ||
          board[newRow][newCol] !== currentPlayer
        )
          break;
        count++;
      }

      if (count >= 5) {
        return true;
      }
    }

    return false;
  };

  const handleClick = (row: number, col: number) => {
    if (board[row][col] || winner) return;

    const newBoard = board.map((r) => [...r]);
    newBoard[row][col] = currentPlayer;
    setBoard(newBoard);

    if (checkWinner(row, col)) {
      setWinner(currentPlayer);
      return;
    }

    setCurrentPlayer(currentPlayer === 'X' ? 'O' : 'X');
  };

  const resetGame = () => {
    setBoard(Array(gridSize).fill(null).map(() => Array(gridSize).fill('')));
    setCurrentPlayer('X');
    setWinner(null);
  };

  return (
    <div className="flex w-full h-screen flex-col items-center bg-slate-800 text-gray-100 font-black">
      {winner && <h2>Player {winner} Wins!</h2>}
      {winner && (
        <button onClick={resetGame} style={{ marginBottom: '20px' }}>
          New Game
        </button>
      )}
      <div
        style={{
          display: 'grid',
          gridTemplateColumns: `repeat(${gridSize}, ${cellSize}px)`,
          gridTemplateRows: `repeat(${gridSize}, ${cellSize}px)`,
          gap: '0',
        }}
      >
        {board.map((row, rowIndex) =>
          row.map((cell, colIndex) => (
            <button
              key={`${rowIndex}-${colIndex}`}
              onClick={() => handleClick(rowIndex, colIndex)}
              style={{
                width: `${cellSize}px`,
                height: `${cellSize}px`,
                border: '1px solid #d1d5db',
                backgroundColor: '#ffffff',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                fontSize: `${fontSize}px`,
                cursor: 'pointer',
              }}
              className={`
                flex items-center justify-center
                font-black
                ${cell === 'X' ? 'text-red-500' : 'text-blue-500'}
                `}
            >
              {cell}
            </button>
          ))
        )}
      </div>
    </div>
  );
}

export default Game;
