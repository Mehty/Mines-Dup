import java.util.concurrent.ThreadLocalRandom;

public class Board {
    static final char FLAGGED = 'f';
    static final char UNREVEALED = ' ';
    static final char MINE = '*';
    static final String MINE_COUNT = "012345678";
    
    int width, height;
    int num_mines;
    Posn[] mines;
    
    public char[] board;
    

    public Board(int width, int height, int num_mines) {
		this.width = width;
		this.height = height;
		this.num_mines = num_mines;
		
		mines = new Posn[num_mines];
		for (int i = 0; i < num_mines; ++i ){
			int x = randomInt(0, width - 1);
			int y = randomInt(0, height - 1);
			mines[i] = new Posn(x, y);
		}
		
	
	    board = new char[width * height];
		
		for (int i = 0; i < board.length; ++i) {
		    board[i] = UNREVEALED;
		}
    }

    public boolean flag(int x, int y) {
		if (is_revealed(x, y)) {
		    return false;
		} else {
		    int index = y * width + x;
		    board[index] = FLAGGED;
		    return true;
		}
    }

    private int mineCount(int x, int y) {
    	int count = 0;
    	for (int i = y - 1; i <= y + 1; ++i) {
    		for (int j = x - 1; j <= x + 1; ++j) {
    			if (isOnBoard(j, i) && isMine(j, i)) {
    				++count;
    			}
    		}
    	}
    	return count; 
    }
    
    private boolean isOnBoard(int x, int y) {
    	if (x >= 0 && x < width && y >= 0 && y < height) {
    		return true;
    	}
    	return false;
    }

    public boolean isMine(int x, int y) {
    	for (Posn m : mines) {
    		if (m.x == x && m.y == y) {
    			return true;
    		}
    	}
		return false;
    }
    

    public boolean reveal(int x, int y) {
		if (!isOnBoard(x, y) || is_revealed(x, y)) {
		    return false;
		} else {
		    int index = boardIndex(x, y);
		    if (isMine(x, y)) {
		    	board[index] = MINE;
		    } else {
		    	int theMineCount = mineCount(x, y);
		    	System.out.printf("%d %d\n", x, y);
		    	board[index] = MINE_COUNT.charAt(theMineCount);
		    	if (theMineCount == 0) {
		    		for (int i = y - 1; i <= y + 1; ++i) {
		    			for (int j = x - 1; j <= x + 1; ++j) {
		    				reveal(j, i);
		    			}
		    		}
		    	}
		    }
			return true;		    
		}
    }

    public boolean is_revealed(int x, int y) {
		char tile_value = board[boardIndex(x, y)];
		if (tile_value == UNREVEALED || tile_value == FLAGGED) {
		    return false;
		}
		return true;
    }
    
    public boolean hasWon() {
    	for (int i = 0; i < board.length; i++) {
    		int x = i % width;
    		int y = i / width;
    		if ((is_revealed(x, y) && isMine(x, y)) ||
    			(!is_revealed(x, y) && !isMine(x, y))) 
    				return false;
    	}
    	return true;
    }
    
    public boolean hasLost() {
    	for (int i = 0; i < board.length; i++) {
    		if (board[i] == MINE) return true;
    	}
    	return false;
    }
    
  
    private int boardIndex(int x, int y) {
    	return y * width + x;
    }

    public void printBoard() {
		for (int y = 0; y < height; ++y) {
		    for (int x = 0; x < width; ++x) {
			System.out.printf("%c", board[width * y + x]);
		    }
		    System.out.printf("\n");
		}
    }
    
    private int randomInt(int min, int max) {
    	return ThreadLocalRandom.current().nextInt((max - min + 1)) + min;
    }
}
