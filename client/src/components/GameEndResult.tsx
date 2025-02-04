interface propTypes {
    winner: string | null,
    resetGame: CallableFunction
}

function GameEndResult({winner, resetGame}: propTypes) {

    return <>
      {winner && (
        <div className={`absolute bg-black p-5 w-full text-center content-center h-[35svh] m-auto top-0 bottom-0`}>
          <h2>Player {winner} Wins!</h2>
          <button className={`button mb-5`} onClick={() => {resetGame()}}>
            New Game
          </button>
        </div>
      )}
    </>
    
}

export default GameEndResult;