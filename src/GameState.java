public class GameState {
	Cell[] boxState;
	int playerX, playerY, worth;
	char pullDirection;
	GameState prevGameState = null;
	int[][] roomMatrix = new int[Main.board.length][Main.board[0].length];
	char[][] current_board;

	public GameState(Cell[] boxState, int x, int y, GameState prevGameState,
			char direction, char[][] board) {
		this.boxState = boxState;
		playerX = x;
		playerY = y;
		pullDirection = direction;
		this.prevGameState = prevGameState;
		current_board = board;
		generateWorth();
	}

	public int getX() {
		return playerX;
	}

	public int getY() {
		return playerY;
	}

	public Cell[] getBoxState() {
		return boxState;
	}

	public void generateWorth() {
		worth = 0;
	}

	public int getWroth() {
		return worth;
	}
	
	public char[][] board(){
		return current_board;
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
	
	public void setInRoom(int x, int y){
		roomMatrix[x][y] = 1;
	}

}
