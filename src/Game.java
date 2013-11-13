import java.util.PriorityQueue;

public class Game {

	public static PriorityQueue<GameState> initateGameQueue(GameState game_state) {
		PriorityQueue<GameState> game_queue = new PriorityQueue<GameState>();
		Cell[] box_state = game_state.getBoxState();
		for (int i = 0; i < box_state.length; i++) {
			Cell cell = box_state[i];
			if (isPossMove(cell.getX() + 1, cell.getY(), game_state.board(),
					'R')) {
				game_queue.add(createGameState(cell.getX()+1, cell.getY(), game_state.board(), box_state, 'R', i, game_state));
				
			}
			if (isPossMove(cell.getX() - 1, cell.getY(), game_state.board(),
					'L')) {
				game_queue.add(createGameState(cell.getX()-1, cell.getY(), game_state.board(), box_state, 'L', i, game_state));

			}
			if (isPossMove(cell.getX(), cell.getY() + 1, game_state.board(),
					'D')) {
				game_queue.add(createGameState(cell.getX(), cell.getY()+1, game_state.board(), box_state, 'D', i, game_state));

			}
			if (isPossMove(cell.getX(), cell.getY() - 1, game_state.board(),
					'U')) {
				game_queue.add(createGameState(cell.getX(), cell.getY()-1, game_state.board(), box_state, 'U', i, game_state));

			}
		}
		
		return game_queue;
	}
	
	public static GameState createGameState(int x, int y, char[][] board, Cell[] boxList, char dir, int i, GameState game_state){
		Cell[] new_list = new Cell[boxList.length];
		char[][] new_board = new char[board.length][board[0].length];
		new_list = boxList.clone();
		for(int j = 0; j < board.length; j++){
			new_board[j] = board[j].clone();
		}
		new_list[i] = new Cell(x, y);
		new_board = updateBoard(new_list[i], boxList[i], new_board);
		switch(dir){
		case 'R':
			return new GameState(new_list, new_list[i].getX()+1, new_list[i].getY(), game_state, dir, new_board);
		case 'L':
			return new GameState(new_list, new_list[i].getX()-1, new_list[i].getY(), game_state, dir, new_board);
		case 'D':
			return new GameState(new_list, new_list[i].getX(), new_list[i].getY()+1, game_state, dir, new_board);
		case 'U':
			return new GameState(new_list, new_list[i].getX(), new_list[i].getY()-1, game_state, dir, new_board);
		}
		return null;
		
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
						|| board[y][x] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'L':
				if (board[y][x - 1] == Symbol.FREE_SPACE.getChar()
						|| board[y][x] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'U':
				if (board[y - 1][x] == Symbol.FREE_SPACE.getChar()
						|| board[y][x] == Symbol.GOAL.getChar())
					return true;
				break;
			case 'D':
				if (board[y + 1][x] == Symbol.FREE_SPACE.getChar()
						|| board[y][x] == Symbol.GOAL.getChar())
					return true;
				break;
			}
		}
		return false;
	}
}
