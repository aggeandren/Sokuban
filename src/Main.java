import java.io.IOException;


/**
 * Tankar och funderingar.
 * L�s kommentarerna i Board.
 * @author August
 *	
 */

public class Main {
	public static char[][] board;
	public static void main(String[] args) throws IOException{
		
		board = Board.createBoard();
		Board.printBoard(board);
	}
	
}
