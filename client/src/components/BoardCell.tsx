interface propTypes {
  cell: string,
  rowIndex: number,
  colIndex: number,
  handleClick: CallableFunction
}

function BoardCell({cell, rowIndex, colIndex, handleClick}: propTypes) {
  return (
    <button
      onClick={() => handleClick(rowIndex, colIndex)}
      className={`
      bg-white flex items-center justify-center cursor-pointer border border-gray-100
      ${cell === 'X' ? 'filled text-red-600' : 'filled text-blue-600'}
      `}
    >{cell}</button>
  )
}

export default BoardCell;