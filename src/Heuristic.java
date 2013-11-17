
public class Heuristic {
	
	public static int getHeuristicValue(GameState gs) {
		int value = 0;
		for(Cell box : gs.boxes) {
			int smallestDistance = Integer.MAX_VALUE;
			int distance = 0; 
			for(Cell goal : Board.goalCoords) {
				distance = manhattanDistance(box, goal);
				if(distance < smallestDistance) {
					smallestDistance = distance;
				}
			}
			if(smallestDistance == 0) {
				value -= 10;
			} else {
				value += smallestDistance;
			}
		}
		return value;
	}
	
	public static int manhattanDistance(Cell a, Cell b) {
		return (Math.abs((b.getX() - a.getX())) + Math.abs((b.getY() - a.getY())));
	}

}
