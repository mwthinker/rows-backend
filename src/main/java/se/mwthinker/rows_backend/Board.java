package se.mwthinker.rows_backend;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

			int count = max(
					countInARowHorizontal(cell),
					countInARowVertical(cell),
					countInARowDiagonal1(cell),
					countInARowDiagonal2(cell)
			);
			gameOver = count >= maxNumberInARow;
			return true;
		}

		return false;
	}

	private int countInARowHorizontal(Cell cell) {
		if (cell.state() == CellState.EMPTY) {
			return 0;
		}

		int x = cell.x();
		int y = cell.y();
		CellState state = cell.state();
		int count = 1;
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x + i, y, state))) {
				count++;
			} else {
				break;
			}
		}
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x - i, y, state))) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private int countInARowVertical(Cell cell) {
		if (cell.state() == CellState.EMPTY) {
			return 0;
		}

		int x = cell.x();
		int y = cell.y();
		CellState state = cell.state();
		int count = 1;
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x, y + i, state))) {
				count++;
			} else {
				break;
			}
		}
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x, y - i, state))) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private int countInARowDiagonal1(Cell cell) {
		if (cell.state() == CellState.EMPTY) {
			return 0;
		}

		int x = cell.x();
		int y = cell.y();
		CellState state = cell.state();
		int count = 1;
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x + i, y + i, state))) {
				count++;
			} else {
				break;
			}
		}
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x - i, y - i, state))) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private int countInARowDiagonal2(Cell cell) {
		if (cell.state() == CellState.EMPTY) {
			return 0;
		}

		int x = cell.x();
		int y = cell.y();
		CellState state = cell.state();
		int count = 1;
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x + i, y - i, state))) {
				count++;
			} else {
				break;
			}
		}
		for (int i = 1; i < maxNumberInARow; i++) {
			if (cells.contains(new Cell(x - i, y + i, state))) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private static int max(Integer... vals) {
		return Collections.max(List.of(vals));
	}

}
