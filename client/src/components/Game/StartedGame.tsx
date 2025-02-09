import { useState, useEffect } from 'react';
import Board from '../Board';
import GameEndResult from '../GameEndResult';

//import { useParams } from 'react-router-dom';


function StartedGame() {
  //const { gameId } = useParams();  
  const [gridSize, setGridSize] = useState(25);
  const [cellSize, setCellSize] = useState(25);
  //const [fontSize, setFontSize] = useState(20);
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
    //setFontSize(calculatedCellSize / 2);
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

      count+= countMarksInLine(1, row, col, dx, dy);  //Check positive direction
      count+= countMarksInLine(-1, row, col, dx, dy); //Check negative direction

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

  const countMarksInLine = (directionFactor: number, row: number, col: number, dx: number, dy: number) => {
    let count = 0;
    for (let i = 1; i < 5; i++) {
      const newRow = row + (i*directionFactor) * dx;
      const newCol = col + (i*directionFactor) * dy;
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
    return count;
  };

  const resetGame = () => {
    setBoard(Array(gridSize).fill(null).map(() => Array(gridSize).fill('')));
    setCurrentPlayer('X');
    setWinner(null);
  };

  return (
    <div className="flex w-full h-screen flex-col items-center bg-slate-800 text-gray-100 font-black">
      <GameEndResult winner={winner} resetGame={resetGame} />
      <Board board={board} cellSize={cellSize} gridSize={gridSize} handleClick={handleClick}  />
    </div>
  );
}

export default StartedGame;
