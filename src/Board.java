import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;


public class Board {
	public static int num_of_boxes;
	public static Cell player;
	
	/** Borde vi göra egna objekt eller försöka lösa det i en simplare lösning då vi egentligen bara är 
	 * intresserad av x och y coordinaterna?. För boxCoords och goalCoords det vill säga.
	 * **/
	
	public static ArrayList<Cell> boxCoords = new ArrayList<Cell>();
	public static ArrayList<Cell> goalCoords = new ArrayList<Cell>();
	
	public static char[][] createBoard() throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		Vector<String> board_vect = new Vector<String>();
//		for (int i = 0; br.ready(); i++) {
		for(int i = 0; i < 6; i++){
			line = br.readLine();
			if(line == "\n"){
				break;
			}
			board_vect.add(line);
		}
		char[][] board = new char[board_vect.size()][board_vect.get(0).length()];
		
		for(int i = 0; i < board_vect.size(); i++){
			for(int j = 0; j < board_vect.get(i).length(); j++){
				board[i][j] = board_vect.get(i).charAt(j);
				if(board[i][j] == Symbol.BOX.getChar()){
					goalCoords.add(new Cell(j, i));
					board[i][j] = Symbol.GOAL.getChar();
				}else if(board[i][j] == Symbol.GOAL.getChar()){
					boxCoords.add(new Cell(j, i));
					board[i][j] = Symbol.BOX.getChar();
				}else if(board[i][j] == Symbol.BOX_ON_GOAL.getChar()){
					goalCoords.add(new Cell(j, i));
					boxCoords.add(new Cell(j, i));
				}else if(board[i][j] == Symbol.PLAYER.getChar()){
					player = new Cell(j, i);
					board[i][j] = Symbol.FREE_SPACE.getChar();
				}else if(board[i][j] == Symbol.PLAYER_ON_GOAL.getChar()){
					player = new Cell(j, i);
					board[i][j] = Symbol.GOAL.getChar();
					
				}
			}
		}
		num_of_boxes = boxCoords.size();
		return board;
	}

	public static Cell[] getBoxArray(){
		Cell[] cell = new Cell[boxCoords.size()];
		for(int i = 0; i < boxCoords.size(); i++){
			cell[i] = boxCoords.get(i);
		}
		return cell;
	}
	
	public static void printBoard(char[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	public static boolean isSolution(GameState gs){
		Cell[] box_state = gs.getBoxState();
		boolean boxOnGoal;
		for(int i = 0; i < goalCoords.size(); i++){
			boxOnGoal = false;
			Cell goal = goalCoords.get(i);
			for(int j = 0; j < box_state.length; j++){
				if(!goal.equals(box_state[j])){
					boxOnGoal = true;
				}
			}
			if(!boxOnGoal){
				return false;
			}
		}
		return true;
	}
}

