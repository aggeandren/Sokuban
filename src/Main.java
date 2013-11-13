import java.io.IOException;
import java.util.PriorityQueue;


/**
 * Tankar och funderingar.
 * Läs kommentarerna i Board.
 * @author August
 *	
 */

public class Main {
	public static char[][] board;
	public static PriorityQueue<GameState> game_queue;
	public static void main(String[] args) throws IOException{
		
		board = Board.createBoard();
		Board.printBoard(board);
		GameState game_state = new GameState(Board.getBoxArray(), -1, -1, null, 'Z', board);
		game_queue = Game.initateGameQueue(game_state);
		GameState gs = game_queue.peek();
		Board.printBoard(gs.board());
		Board.printBoard(board);
		int i = 1;
		
	}
}
