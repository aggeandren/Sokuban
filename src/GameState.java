import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GameState implements Comparable<GameState> {
	Cell[] boxes;
	Cell player;
	int worth;
	char pullDirection;
	GameState prevGameState = null;
	int[][] roomMatrix = new int[Main.board.length][Main.board[0].length];
	char[][] currentBoard;
	HashSet<Cell> possBoxes = new HashSet<Cell>();

	public GameState(Cell[] boxState, int x, int y, GameState prevGameState,
			char direction, char[][] board) {
		this.boxes = boxState;
		player = new Cell(x, y);
		pullDirection = direction;
		this.prevGameState = prevGameState;
		currentBoard = board;
		generateWorth();
	}

	public GameState(GameState gs, Cell box, char direction) {
		prevGameState = gs;
		boxes = new Cell[gs.boxes.length];
		int movedBoxIndex = 0;
		for (int i = 0; i < boxes.length; i++) {
			if (box.equals(gs.boxes[i])) {
				movedBoxIndex = i;
				switch (direction) {
				case 'U':
					boxes[i] = new Cell(box.getX(), box.getY() - 1);
					player = new Cell(box.getX(), box.getY() - 2);
					break;
				case 'R':
					boxes[i] = new Cell(box.getX() + 1, box.getY());
					player = new Cell(box.getX() + 2, box.getY());
					break;
				case 'D':
					boxes[i] = new Cell(box.getX(), box.getY() + 1);
					player = new Cell(box.getX(), box.getY() + 2);
					break;
				case 'L':
					boxes[i] = new Cell(box.getX() - 1, box.getY());
					player = new Cell(box.getX() - 2, box.getY());
					break;

				}
			} else {
				boxes[i] = new Cell(gs.boxes[i]);
			}
		}
		pullDirection = direction;
		currentBoard = new char[gs.currentBoard.length][gs.currentBoard[0].length];
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				currentBoard[i][j] = gs.currentBoard[i][j];
			}
		}

		updateBoard(boxes[movedBoxIndex], gs.boxes[movedBoxIndex]);

		generateWorth();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(boxes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (!Arrays.equals(boxes, other.boxes))
			return false;
		if(other.roomMatrix[this.getY()][this.getX()] != 1){
			return false;
		}
		return true;
	}
	
	public void setPossBoxes(HashSet<Cell> pbArrayList){
		possBoxes = pbArrayList;
	}
	
	public HashSet getPossBoxes(){
		return possBoxes;
	}

	public void updateBoard(Cell newBox, Cell oldBox) {
		if (currentBoard[newBox.getY()][newBox.getX()] == Symbol.GOAL.getChar()) {
			currentBoard[newBox.getY()][newBox.getX()] = Symbol.BOX_ON_GOAL
					.getChar();
		} else if (currentBoard[newBox.getY()][newBox.getX()] == Symbol.FREE_SPACE
				.getChar()) {
			currentBoard[newBox.getY()][newBox.getX()] = Symbol.BOX.getChar();
		}

		if (currentBoard[oldBox.getY()][oldBox.getX()] == Symbol.BOX_ON_GOAL
				.getChar()) {
			currentBoard[oldBox.getY()][oldBox.getX()] = Symbol.GOAL.getChar();
		} else if (currentBoard[oldBox.getY()][oldBox.getX()] == Symbol.BOX
				.getChar()) {
			currentBoard[oldBox.getY()][oldBox.getX()] = Symbol.FREE_SPACE
					.getChar();
		}
	}

	public int getX() {
		return player.getX();
	}

	public int getY() {
		return player.getY();
	}

	public Cell getPlayerPos() {
		return player;
	}

	public Cell[] getBoxes() {
		return boxes;
	}

	public void generateWorth() {
		worth = Heuristic.getHeuristicValue(this);
	}

	public int getWroth() {
		return worth;
	}

	public char[][] board() {
		return currentBoard;
	}

	public char getPushDirection() {
		switch (pullDirection) {
		case 'L':
			return 'R';
		case 'R':
			return 'L';
		case 'D':
			return 'U';
		case 'U':
			return 'D';
		}
		return 'E';
	}

	public void setInRoom(int x, int y) {
		roomMatrix[y][x] = 1;
	}

	public boolean isInRoom(int x, int y) {
		if (roomMatrix[y][x] == 1) {
			return true;
		}
		return false;
	}

	public Cell getBox(int x, int y) {
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i].getX() == x && boxes[i].getY() == y) {
				return boxes[i];
			}
		}
		return null;
	}

	@Override
	public int compareTo(GameState gs) {
		return worth - gs.worth;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String boxPos = "";
		for(int i = 0; i < boxes.length; i++){
			boxPos += "{" + boxes[i].getX() + "," + boxes[i].getY() + "}";
		}
		return boxPos;
	}

}
