import BoardCell from './BoardCell';

interface propTypes {
  board: string[][], 
  gridSize: number, 
  cellSize: number, 
  handleClick: CallableFunction
}

function Board({board, gridSize, cellSize, handleClick}: propTypes) {
  return (
    <div className={'grid gap-0'}
      style={{
        gridTemplateColumns: `repeat(${gridSize}, ${cellSize}px)`,
        gridTemplateRows: `repeat(${gridSize}, ${cellSize}px)`,
      }}
    >
      {board.map((row, rowIndex) =>
        row.map((cell, colIndex) => (
          <BoardCell key={`${rowIndex}-${colIndex}`} cell={cell} colIndex={colIndex} rowIndex={rowIndex} handleClick={handleClick} />
        ))
      )}
    </div>
  );
}

export default Board;