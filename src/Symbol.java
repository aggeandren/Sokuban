public enum Symbol {
    FREE_SPACE(' '),
    WALL('#'),
    GOAL('.'),
    PLAYER('@'),
    PLAYER_ON_GOAL('+'),
    BOX('$'),
    BOX_ON_GOAL('*');

    private final char symbol;

    private Symbol(char s) {
        symbol = s;
    }

    public char getChar() {
        return symbol;
    }

    public static Symbol fromChar(char c) {
    	if(c == Symbol.BOX.getChar()) {
    		return Symbol.FREE_SPACE;
    	} else if(c == Symbol.BOX_ON_GOAL.getChar()) {
    		return Symbol.GOAL;
    	}
        for (Symbol symbol : Symbol.values()) {
            if (c == symbol.getChar()) {
                return symbol;
            }
        }
        return null;
    }
}