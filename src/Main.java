import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import sun.security.ssl.Debug;

public class Main {
	public static char[][] board;
	public static PriorityQueue<GameState> game_queue = new PriorityQueue<GameState>();
	public static HashSet<Cell> boxes;
	public static HashSet<GameState> transpositionTable = new HashSet<GameState>();

	public static void main(String[] args) throws IOException {

		board = Board.createBoard();
//		Board.printBoard(board);
		GameState game_state = new GameState(Board.getBoxArray(), -1, -1, null,
				'Z', board);
		Game.initateGameQueue(game_state);
		findPath();
	}

	public static void findPath() {
		GameState current_game;
		while (!game_queue.isEmpty()) {
			current_game = game_queue.poll();
//			Board.printBoard(current_game.currentBoard);
			makeNextMoves(current_game);
			if (Board.isSolution(current_game)) {
				printPath(current_game);
				break;
			}
		}
		
		System.out.println("no path");
		System.exit(0);
	}

	public static void printPath(GameState gs) {
		Cell src = Board.startPlayer;
		Cell dest = null;
		StringBuilder path = new StringBuilder();
		GameState gameState = gs;
		while(gameState.prevGameState != null){
//			Board.printBoard(gameState.board());
			dest = gameState.getPlayerPos();
			path.append(PlayerPathSearch.playerAStar(src, dest, gameState));
			path.append(gameState.getPushDirection());
			src = getNextSource(gameState);
			gameState = gameState.prevGameState;
		}
		System.out.print(path.toString());
		System.exit(0);
		
	}
	
	private static Cell getNextSource(GameState gs) {
		int x = gs.getX();
		int y = gs.getY();
		switch(gs.getPushDirection()) {
		case 'U':
			y--;
			break;
		case 'R':
			x++;
			break;
		case 'D':
			y++;
			break;
		case 'L':
			x--;
			break;
		}
		return new Cell(x, y);
	}

	public static void makeNextMoves(GameState gs) {
		HashSet<Cell> reachableBoxes = gs.getPossBoxes();
		for (Cell box : reachableBoxes) {
			createPossibleGameStates(box, gs);
		}
	}

	public static void createPossibleGameStates(Cell box, GameState gs) {
		GameState newGs;
		if (box == null) {
			return;
		}
		if (isDragableLeft(box, gs)) {
			newGs = Game.createGameState(box, 'L', gs);
			addGameState(newGs);
		}
		if (isDragableUp(box, gs)) {
			newGs = Game.createGameState(box, 'U', gs);
			addGameState(newGs);
		}
		if (isDragableRight(box, gs)) {
			newGs = Game.createGameState(box, 'R', gs);
			addGameState(newGs);
		}
		if (isDragableDown(box, gs)) {
			newGs = Game.createGameState(box, 'D', gs);
			addGameState(newGs);
		}
	}
	
	public static void addGameState(GameState newGs){
		if (!transpositionTable.contains(newGs)) {
			newGs.setPossBoxes(floodRoom(newGs));
			transpositionTable.add(newGs);
			game_queue.add(newGs);
		}
	}

	public static HashSet<Cell> floodRoom(GameState gs) {
		Cell curr_cell = gs.getPlayerPos();
		boxes = new HashSet<Cell>();
		int x = curr_cell.getX();
		int y = curr_cell.getY();
		char[][] board = gs.board();
		Queue<Cell> cellsToVisit = new LinkedList<Cell>();
		gs.setInRoom(x, y);
		cellsToVisit.add(curr_cell);
		while (!cellsToVisit.isEmpty()) {
			curr_cell = cellsToVisit.poll();
			x = curr_cell.getX();
			y = curr_cell.getY();
			if (isFreeLeft(curr_cell, board, gs)) {
				cellsToVisit.add(new Cell(x - 1, y));
				gs.setInRoom(x - 1, y);
			}
			if (isFreeUp(curr_cell, board, gs)) {
				cellsToVisit.add(new Cell(x, y - 1));
				gs.setInRoom(x, y - 1);
			}
			if (isFreeRight(curr_cell, board, gs)) {
				cellsToVisit.add(new Cell(x + 1, y));
				gs.setInRoom(x + 1, y);
			}
			if (isFreeDown(curr_cell, board, gs)) {
				cellsToVisit.add(new Cell(x, y + 1));
				gs.setInRoom(x, y + 1);
			}
		}
		return boxes;
	}

	public static boolean isDragableLeft(Cell box, GameState gs) {
		int x = box.getX() - 1;
		int y = box.getY();
		if (gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'L')) {
			return true;
		}
		return false;

	}

	public static boolean isDragableUp(Cell box, GameState gs) {
		int x = box.getX();
		int y = box.getY() - 1;
		if (gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'U')) {
			return true;
		}
		return false;

	}

	public static boolean isDragableRight(Cell box, GameState gs) {
		int x = box.getX() + 1;
		int y = box.getY();
		if (gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'R')) {
			return true;
		}
		return false;
	}

	public static boolean isDragableDown(Cell box, GameState gs) {
		int x = box.getX();
		int y = box.getY() + 1;
		if (gs.isInRoom(x, y) && Game.isPossMove(x, y, gs.board(), 'D')) {
			return true;
		}
		return false;
	}

	public static boolean isFreeLeft(Cell cell, char[][] board, GameState gs) {
		int x = cell.getX() - 1;
		int y = cell.getY();
		if ((board[y][x] == Symbol.GOAL.getChar() || board[y][x] == Symbol.FREE_SPACE
				.getChar()) && !gs.isInRoom(x, y)) {
			return true;
		}
		if (board[y][x] == Symbol.BOX.getChar()
				|| board[y][x] == Symbol.BOX_ON_GOAL.getChar()) {
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}

	public static boolean isFreeUp(Cell cell, char[][] board, GameState gs) {
		int x = cell.getX();
		int y = cell.getY() - 1;
		if ((board[y][x] == Symbol.GOAL.getChar() || board[y][x] == Symbol.FREE_SPACE
				.getChar()) && !gs.isInRoom(x, y)) {
			return true;
		}
		if (board[y][x] == Symbol.BOX.getChar()
				|| board[y][x] == Symbol.BOX_ON_GOAL.getChar()) {
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}

	public static boolean isFreeRight(Cell cell, char[][] board, GameState gs) {
		int x = cell.getX() + 1;
		int y = cell.getY();
		if ((board[y][x] == Symbol.GOAL.getChar() || board[y][x] == Symbol.FREE_SPACE
				.getChar()) && !gs.isInRoom(x, y)) {
			return true;
		}
		if (board[y][x] == Symbol.BOX.getChar()
				|| board[y][x] == Symbol.BOX_ON_GOAL.getChar()) {
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}

	public static boolean isFreeDown(Cell cell, char[][] board, GameState gs) {
		int x = cell.getX();
		int y = cell.getY() + 1;
		if ((board[y][x] == Symbol.GOAL.getChar() || board[y][x] == Symbol.FREE_SPACE
				.getChar()) && !gs.isInRoom(x, y)) {
			return true;
		}
		if (board[y][x] == Symbol.BOX.getChar()
				|| board[y][x] == Symbol.BOX_ON_GOAL.getChar()) {
			boxes.add(gs.getBox(x, y));
		}
		return false;
	}
}
