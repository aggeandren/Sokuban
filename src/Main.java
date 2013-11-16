import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * Tankar och funderingar.
 * Läs kommentarerna i Board.
 * @author August
 *	
 */

public class Main {
	public static char[][] board;
	public static PriorityQueue<GameState> game_queue;
	public static ArrayList<Cell> boxes;
	public static void main(String[] args) throws IOException{
		
		board = Board.createBoard();
		Board.printBoard(board);
		GameState game_state = new GameState(Board.getBoxArray(), -1, -1, null, 'Z', board);
		game_queue = Game.initateGameQueue(game_state);
		findPath();
	}
	
	public static void findPath(){
		GameState current_game;
		while(!game_queue.isEmpty()){
			current_game = game_queue.poll();
			if(Board.isSolution(current_game)){
				printPath(current_game);
				break;
			}
			makeNextMoves(current_game);
		}
	}
	
	public static void printPath(GameState gs){
		System.out.println("Done!");
		Board.printBoard(gs.board());
	}
	
	public static void makeNextMoves(GameState gs){
		ArrayList<Cell> reachableBoxes = floodRoom(gs);
		for(Cell box : reachableBoxes){
			createPossibleGameStates(box, gs);
		}
	}
	
	public static void createPossibleGameStates(Cell box, GameState gs){
		GameState new_gs;
		if(isDragableLeft(box, gs)){
			new_gs = Game.createGameState(box.getX()-1, box.getY(), box, gs, 'L');
//			if(transportCheck){
//				game_queue.add(new_gs);
//			}
			game_queue.add(new_gs);
		}
		if(isDragableUp(box, gs)){
			new_gs = Game.createGameState(box.getX(), box.getY()-1, box, gs, 'U');
//			if(transportCheck){
//				game_queue.add(new_gs);
//			}
			game_queue.add(new_gs);
		}
		if(isDragableRight(box, gs)){
			new_gs = Game.createGameState(box.getX()+1, box.getY(), box, gs, 'R');
//			if(transportCheck){
//				game_queue.add(new_gs);
//			}
			game_queue.add(new_gs);
		}
		if(isDragableDown(box, gs)){
			new_gs = Game.createGameState(box.getX(), box.getY()+1, box, gs, 'D');
//			if(transportCheck){
//				game_queue.add(new_gs);
//			}
			game_queue.add(new_gs);
		}
	}
	
	public static ArrayList<Cell> floodRoom(GameState gs){
		Cell curr_cell = gs.getPlayerPos();
		boxes = new ArrayList<Cell>();
		int x = curr_cell.getX();
		int y = curr_cell.getY();
		char[][] board = gs.board();
		Queue<Cell> cellsToVisit = new LinkedList<Cell>();
		gs.setInRoom(x, y);
		do{
			if(isFreeUp(curr_cell, board, gs)){
				
			}
			if(isFreeUp(curr_cell, board, gs)){
				
			}
			if(isFreeUp(curr_cell, board, gs)){
				
			}
			if(isFreeUp(curr_cell, board, gs)){
				
			}
		}while()
	}
	
	public static boolean isDragableLeft(Cell box, GameState gs){
		int x = box.getX()-1;
		int y = box.getY();
		if(gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'L')){
			return true;
		}
		return false;
		
	}
	public static boolean isDragableUp(Cell box, GameState gs){
		int x = box.getX()-1;
		int y = box.getY();
		if(gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'U')){
			return true;
		}
		return false;
		
	}
	public static boolean isDragableRight(Cell box, GameState gs){
		int x = box.getX()-1;
		int y = box.getY();
		if(gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'R')){
			return true;
		}
		return false;		
	}
	public static boolean isDragableDown(Cell box, GameState gs){
		int x = box.getX()-1;
		int y = box.getY();
		if(gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'D')){
			return true;
		}
		return false;
	}
	
	public static boolean isFreeLeft(Cell cell, char[][] board, GameState gs){
		int x = cell.getX()-1;
		int y = cell.getY();
		if(board[x][y] == Symbol.GOAL.getChar() || board[x][y] == Symbol.FREE_SPACE.getChar()){
			return true;
		}if(board[x][y] == Symbol.BOX.getChar() || board[x][y] == Symbol.BOX_ON_GOAL.getChar()){
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}
	public static boolean isFreeUp(Cell cell, char[][] board, GameState gs){
		int x = cell.getX();
		int y = cell.getY()-1;
		if(board[x][y] == Symbol.GOAL.getChar() || board[x][y] == Symbol.FREE_SPACE.getChar()){
			return true;
		}if(board[x][y] == Symbol.BOX.getChar() || board[x][y] == Symbol.BOX_ON_GOAL.getChar()){
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}
	public static boolean isFreeRight(Cell cell, char[][] board, GameState gs){
		int x = cell.getX()+1;
		int y = cell.getY();
		if(board[x][y] == Symbol.GOAL.getChar() || board[x][y] == Symbol.FREE_SPACE.getChar()){
			return true;
		}if(board[x][y] == Symbol.BOX.getChar() || board[x][y] == Symbol.BOX_ON_GOAL.getChar()){
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}
	public static boolean isFreeoDown(Cell cell, char[][] board, GameState gs){
		int x = cell.getX();
		int y = cell.getY()+1;
		if(board[x][y] == Symbol.GOAL.getChar() || board[x][y] == Symbol.FREE_SPACE.getChar()){
			return true;
		}if(board[x][y] == Symbol.BOX.getChar() || board[x][y] == Symbol.BOX_ON_GOAL.getChar()){
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}
}
