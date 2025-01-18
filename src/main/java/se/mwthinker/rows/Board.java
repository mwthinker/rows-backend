package se.mwthinker.rows;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Board {
	private final int maxNumberInARow;
	private final Set<Cell> cells = new HashSet<>();
	private boolean gameOver = false;

	public static void main(String[] args) {
		Board board = new Board(3);
		board.printBoard();
	}

	public Board(int maxNumberInARow) {
		this.maxNumberInARow = maxNumberInARow;
	}

	public void printBoard() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (cells.contains(new Cell(i, j, CellState.X))) {
					System.out.print("X");
				} else if (cells.contains(new Cell(i, j, CellState.O))) {
					System.out.print("O");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean put(int x, int y, CellState state) {
		if (gameOver) {
			return false;
		}

		if (!cells.contains(new Cell(x, y, CellState.X)) || !cells.contains(new Cell(x, y, CellState.O))) {
			Cell cell = new Cell(x, y, state);
			cells.add(cell);
			gameOver = isMaxInARow(cell);
		}

		return gameOver;
	}

	private boolean isMaxInARow(Cell cell) {
		if (cell.state() == CellState.EMPTY) {
			return false;
		}

		return Stream.of(new Direction(1, 0),
						new Direction(0, 1),
						new Direction(1, 1),
						new Direction(1, -1))
				.anyMatch(dir -> countInRow(cell, dir) >= maxNumberInARow);
	}

	private int countInRow(Cell cell, Direction dir) {
		int x = cell.x();
		int y = cell.y();
		CellState state = cell.state();

		int count = 1;
		for (int i = 1; i < maxNumberInARow; ++i) {
			if (cells.contains(new Cell(x + i * dir.dx, y + i * dir.dy, state))) {
				count++;
			} else {
				break;
			}
		}
		for (int i = 1; i < maxNumberInARow; ++i) {
			if (cells.contains(new Cell(x - i * dir.dx, y - i * dir.dy, state))) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private record Direction(int dx, int dy) {}

}
