import java.util.Arrays;
import java.util.PriorityQueue;

public class Game {

	public static void initateGameQueue(GameState gameState) {
		PriorityQueue<GameState> gameQueue = new PriorityQueue<GameState>();
		Cell[] boxes = gameState.getBoxes();
		GameState gs;
		for (int i = 0; i < boxes.length; i++) {
			Cell box = boxes[i];
			if (isPossMove(box.getX() + 1, box.getY(), gameState.board(),
					'R')) {
				gs = createGameState(box, 'R', gameState);
				Main.addGameState(gs);
				
			}
			if (isPossMove(box.getX() - 1, box.getY(), gameState.board(),
					'L')) {
				gs = createGameState(box, 'L', gameState);
				Main.addGameState(gs);

			}
			if (isPossMove(box.getX(), box.getY() + 1, gameState.board(),
					'D')) {
				gs = createGameState(box, 'D', gameState);
				Main.addGameState(gs);

			}
			if (isPossMove(box.getX(), box.getY() - 1, gameState.board(),
					'U')) {
				gs = createGameState(box, 'U', gameState);
				Main.addGameState(gs);

			}
		}
	}
	
	public static GameState createGameState(Cell box, char dir, GameState gameState){
		return new GameState(gameState, box, dir);
		
	}
	
	public static char[][] updateBoard(Cell new_box, Cell old_box, char[][] board){
		if(board[new_box.getY()][new_box.getX()] == Symbol.GOAL.getChar()){
			board[new_box.getY()][new_box.getX()] = Symbol.BOX_ON_GOAL.getChar();
		}else if(board[new_box.getY()][new_box.getX()] == Symbol.FREE_SPACE.getChar()){
			board[new_box.getY()][new_box.getX()] = Symbol.BOX.getChar();
		}
		
		if(board[old_box.getY()][old_box.getX()] == Symbol.BOX_ON_GOAL.getChar()){
			board[old_box.getY()][old_box.getX()] = Symbol.GOAL.getChar();
		}else if(board[old_box.getY()][old_box.getX()] == Symbol.BOX.getChar()){
			board[old_box.getY()][old_box.getX()] = Symbol.FREE_SPACE.getChar();
		}
		
		return board;
	}

	public static boolean isPossMove(int x, int y, char[][] board, char dir) {
		if (board[y][x] == Symbol.FREE_SPACE.getChar()
				|| board[y][x] == Symbol.GOAL.getChar()) {
			switch (dir) {
			case 'R':
				if (board[y][x + 1] == Symbol.FREE_SPACE.getChar()
						|| board[y][x+1] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'L':
				if (board[y][x - 1] == Symbol.FREE_SPACE.getChar()
						|| board[y][x-1] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'U':
				if (board[y - 1][x] == Symbol.FREE_SPACE.getChar()
						|| board[y-1][x] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'D':
				if (board[y + 1][x] == Symbol.FREE_SPACE.getChar()
						|| board[y+1][x] == Symbol.GOAL.getChar())
					return true;
				break;
			}
		}
		return false;
	}
	
}
