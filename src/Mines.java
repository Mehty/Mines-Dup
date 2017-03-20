import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Mines extends JPanel {
    Board board;
    JButton[] tiles;
    int X, Y, num_mines;
    GridLayout MinesLayout;
    
    static final String GAME_TITLE = "Mine Sweeper";
    
    static final Color GRAY = new Color(170, 170, 170);
    static final Color LIGHT_GRAY = new Color(220, 220, 220);
    
    static final int TILE_SIZE = 50;


    public Mines() {
		X = 8;
		Y = 8;
		num_mines = 10;
		
		board = new Board(X, Y, num_mines);
	
		
		tiles = new JButton[X*Y];
		MinesLayout = new GridLayout(X, Y);
	
		for (int i = 0; i < Y; ++i) {
			for (int j = 0; j < X; ++j) {
				int index = i * X + j;
			    tiles[index] = createTile(j, i);
			    tiles[index].setText(" ");
			    // TODO: RM this
			   // if (board.isMine(j, i)) {
			    //	tiles[index].setText("M");
			  //  }
			    add(tiles[index]);
			}
		}
	/*
		board.printBoard();
		System.out.printf("%b\n", board.is_revealed(0, 0));
		board.reveal(0, 0);
		board.printBoard();
		board.reveal(3, 3);
		board.printBoard();
		board.reveal(2, 2);
		board.printBoard();
	*/
    }
    
    public static void main(String args[]) {
		try {
		    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	
		createAndShowGUI();
    }
    
    private JButton getTile(int x, int y) {
    	return tiles[y * X + x];
    }

    public JButton createTile(final int x, final int y) {
    	final JButton b = new JButton();
    	
    	b.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
    	b.setForeground(Color.BLACK);
    	b.setBackground(GRAY);
    	b.setFocusPainted(false);

    	b.addMouseListener(new MouseAdapter() {
    		public void mousePressed(MouseEvent e) {
	    		// JButton bt = Mines.this.getTile(x, y);
	    		if (SwingUtilities.isLeftMouseButton(e)) {
	    			board.reveal(x, y);
	    			Mines.this.drawBoard();
	    		/* why === returns false?
	    		System.out.printf("r: %d, c: %d, %b, %b\n",
	    				x, y, b == bt, b.equals(bt));
	    		*/
	    			if (board.hasWon()) JOptionPane.showMessageDialog(null, "You Won!");
	    			if (board.hasLost()) JOptionPane.showMessageDialog(null, "You Lost!");
	    		} else if (SwingUtilities.isRightMouseButton(e)) {
	    			board.flag(x,  y);
	    			Mines.this.drawBoard();
	    			if (board.hasWon()) JOptionPane.showMessageDialog(null, "You Won!");
	    			if (board.hasLost()) JOptionPane.showMessageDialog(null, "You Lost!");
	    		}
	    	}
    	});
    	return b;
    }
    
    public void drawBoard() {
		for (int y = 0; y < Y; ++y) {
		    for (int x = 0; x < X; ++x) {
		    	int index = y * X + x;
		    	char ch = board.board[y * X + x];
		    	if (ch == '0') {
		    		tiles[index].setBackground(LIGHT_GRAY);
		    	} else {
		    		tiles[index].setText(Character.toString(ch));
		    	}
		    	
		    	//board.printBoard();
		    }
		}
    }
    
    public void addComponentsToPane(final Container pane) {
		pane.setLayout(MinesLayout);
    }

    private static void createAndShowGUI() {
		JFrame frame = new JFrame(GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Mines MinesPane = new Mines();
		//MinesPane.setOpaque(true);
		frame.setContentPane(MinesPane);
	
		MinesPane.addComponentsToPane(frame.getContentPane());
	
		frame.pack();
		frame.setVisible(true);
    }
}





















