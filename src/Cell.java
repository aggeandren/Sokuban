public class Cell implements Comparable<Cell>{
	private int x, y, moveCost, totalCost;
	private Cell parent;
	private char direction;

	public Cell(int x, int y) {
		this.setCoords(x, y);
	}
	
	public Cell(Cell cell) {
		setCoords(cell.getX(), cell.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX() {
		return x;
	}

	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setMoveCost(int moveCost){
		this.moveCost = moveCost;
	}
	
	public int getMoveCost(){
		return moveCost;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int compareTo(Cell cell) {
		return totalCost - cell.totalCost;
	}

	public Cell getParent() {
		return parent;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
}
