public class GameState implements Comparable<GameState>{
	Cell[] boxState;
	Cell player;
	int worth;
	char pullDirection;
	GameState prevGameState = null;
	int[][] roomMatrix = new int[Main.board.length][Main.board[0].length];
	char[][] current_board;

	public GameState(Cell[] boxState, int x, int y, GameState prevGameState,
			char direction, char[][] board) {
		this.boxState = boxState;
		player = new Cell(x, y);
		pullDirection = direction;
		this.prevGameState = prevGameState;
		current_board = board;
		generateWorth();
	}

	public int getX() {
		return player.getX();
	}

	public int getY() {
		return player.getY();
	}
	
	public Cell getPlayerPos(){
		return player;
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
	
	public boolean isInRoom(int x, int y){
		if(roomMatrix[x][y] == 1){
			return true;
		}
		return false;
	}
	
	public Cell getBox(int x, int y){
		for(int i = 0; i < boxState.length; i++){
			if(boxState[i].getX() == x && boxState[i].getY() == y){
				return boxState[i];
			}
		}
		return null;
	}

	@Override
	public int compareTo(GameState gs) {
		return worth - gs.worth;
	}

}
