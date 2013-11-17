import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class PlayerPathSearch {
	public static Cell destination;
	public static Cell source;
	public static HashSet<Cell> explored;
	public static GameState gameState;

	public static String playerAStar(Cell src, Cell dest, GameState gs){
		explored = new HashSet<Cell>();
		gameState = gs;
		PriorityQueue<Cell> frontier = new PriorityQueue<Cell>();
		source = src;
		destination = dest;
		frontier.add(source);
		while(!frontier.isEmpty()){
			Cell currCell = frontier.poll();
			if(currCell.equals(destination)){
				return trackBack(currCell);
			}
			explored.add(currCell);
			addCellToFrontier(frontier, currCell, 'U');
			addCellToFrontier(frontier, currCell, 'R');
			addCellToFrontier(frontier, currCell, 'D');
			addCellToFrontier(frontier, currCell, 'L');
		}
		System.out.println("     ERROR      ");
		return "";
	}

	private static String trackBack(Cell goal) {
		Cell cell = goal;
		Stack<Character> pathStack = new Stack<Character>();
		while(!cell.equals(source)) {
			pathStack.push(cell.getDirection());
			cell = cell.getParent();
		}
		return printPath(pathStack);
	}

	private static String printPath(Stack<Character> pathStack) {
		StringBuilder sb = new StringBuilder();
		while(!pathStack.empty()) {
			sb.append(pathStack.pop());
		}
		return sb.toString();
	}

	private static void addCellToFrontier(PriorityQueue<Cell> frontier,
			Cell currCell, char direction) {
		int x = currCell.getX();
		int y = currCell.getY();
		int childX = x;
		int childY = y;
		switch (direction) {
		case 'U':
			childY--;
			break;
		case 'R':
			childX++;
			break;
		case 'D':
			childY++;
			break;
		case 'L':
			childX--;
			break;
		}
		if (gameState.currentBoard[childY][childX] == Symbol.FREE_SPACE
				.getChar()
				|| gameState.currentBoard[childY][childX] == Symbol.GOAL
						.getChar()) {
			Cell child = new Cell(childX, childY);
			child.setParent(currCell);
			child.setMoveCost(currCell.getMoveCost() + 1);
			child.setDirection(direction);
			int totalCost = child.getMoveCost() + Heuristic.manhattanDistance(child, destination);
			child.setTotalCost(totalCost);
			if(!explored.contains(child)) {
				frontier.add(child);
			}
		}
	}

	public static int calcNodeVal(Cell cell) {
		return Heuristic.manhattanDistance(cell, destination);
	}

}
