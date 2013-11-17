import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;


public class Board {
	public static int num_of_boxes;
	public static Cell startPlayer;
	public static int boardSize = 0;
	public static ArrayList<Cell> boxCoords = new ArrayList<Cell>();
	public static ArrayList<Cell> goalCoords = new ArrayList<Cell>();
	
	public static char[][] createBoard() throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		Vector<String> board_vect = new Vector<String>();
		for (int i = 0; br.ready(); i++) {
//		for(int i = 0; i < 13; i++){
			line = br.readLine();
			if(line == "\n"){
				break;
			}
			if(line.length() > boardSize){
				boardSize = line.length();
			}
			board_vect.add(line);
		}
		char[][] board = new char[board_vect.size()][boardSize];
		
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
					startPlayer = new Cell(j, i);
					board[i][j] = Symbol.FREE_SPACE.getChar();
				}else if(board[i][j] == Symbol.PLAYER_ON_GOAL.getChar()){
					startPlayer = new Cell(j, i);
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
		System.out.print(" ");
		for(int i = 0; i < board[0].length; i++) {
			System.out.print(i);
		}
		System.out.println();
		for(int i = 0; i < board.length; i++){
			System.out.print(i);
			for(int j = 0; j < board[0].length; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	public static boolean isSolution(GameState gs){
		Cell[] box_state = gs.getBoxes();
		boolean boxOnGoal;
		for(int i = 0; i < goalCoords.size(); i++){
			boxOnGoal = false;
			Cell goal = goalCoords.get(i);
			for(int j = 0; j < box_state.length; j++){
				if(goal.getX() == box_state[j].getX() && goal.getY() == box_state[j].getY()){
					boxOnGoal = true;
				}
			}
			if(!boxOnGoal){
				return false;
			}
		}
		if(gs.roomMatrix[startPlayer.getY()][startPlayer.getY()] != 1){
			return false;
		}
		return true;
	}
}

