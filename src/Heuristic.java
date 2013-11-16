
public class Heuristic {
	
	public static int getHeuristicValue(GameState gs) {
		int value = 0;
		for(Cell box : gs.boxState) {
			int smallestDistance = Integer.MAX_VALUE;
			int distance = 0; 
			for(Cell goal : gs.goals) {
				distance = manhattanDistance(box, goal);
				if(distance < smallestDistance) {
					smallestDistance = distance;
				}
			}
			value += smallestDistance;
		}
		return value;
	}
	
	public static int manhattanDistance(Cell a, Cell b) {
		return (b.getX() - a.getX()) + (b.getY() - a.getY());
	}

}
