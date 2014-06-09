/**
 * Manages a representation of the connect 4 board
 * @author Santosh Thoduka
 *
 */
public class Board implements Cloneable {
	
	char [][] cells;
	int height;
	int width;
	
	/**
	 * Initialize a 7x7 board
	 */
	public Board() {
		cells = new char[7][7];
		height = 7;
		width = 7;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < height; j++) {
				cells[i][j] = '.';
			}
		}
	}
	/**
	 * Initialize a height x 7 board
	 * @param height number of rows in the board
	 */
	public Board(int height) {
		cells = new char[height][7];
		this.height = height;
		this.width = 7;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = '.';
			}
		}
	}
	/**
	 * Constructor used for copying
	 * @param cells cells of the board
	 * @param height height of the board
	 * @param width width of the board
	 */
	public Board(char [][] cells, int height, int width) {
		this.cells = cells;
		this.height = height;
		this.width = width;
	}
	/**
	 * Check to see if a symbol can be dropped in the specified column
	 * @param column column in which to drop
	 * @return true if symbol can be dropped in the column
	 */
	public boolean checkDropValidity(int column) {
		if (column > 6 || column < 0) {
			return false;			
		}
		if (cells[height-1][column] != '.') {
			return false;
		}
		return true;
	}
	
	/**
	 * Drops a symbol in the specified column
	 * @param symbol symbol/disk to be dropped
	 * @param column column in which to be dropped
	 * @return true if symbol was successfully dropped
	 */
	public boolean dropDisk(char symbol, int column) {
		if (!checkDropValidity(column)) {
			return false;
		}
		int i = 0;
		while (cells[i][column] != '.') {
			i++;			
		}
		cells[i][column] = symbol;
		return true;
	}
	/**
	 * Print the current board
	 */
	public void print() {
		// row 7  col 1 .... col7
		// ..
		// row 1
		for (int i = height-1;i >= 0; i--) {
			for(int j = 0; j < width; j++) {
				System.out.print(cells[i][j] + " ");
			}
			System.out.println();
		}
		for(int j = 0; j < 2*width; j++) {
			System.out.print("_");			
		}
		System.out.println();
		for(int j = 0; j < width; j++) {
			System.out.print((j+1) + " ");
		}
		System.out.println();
	}
	
	/**
	 * Check if the board is in a state where one player has won
	 * @param symbol symbol for whom to check victory
	 * @return true if 'symbol' has won
	 */
	public boolean isWon(char symbol) {
		// check row-wise
		for (int i=0; i < height; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i][j+1] == symbol && cells[i][j+2] == symbol &&
						cells[i][j+3] == symbol) {
						return true;
					}						
				}
			}
		}		
		// check column wise
		for (int j=0; j < width; j++) {
			for (int i=0; i < height - 3; i++) {
				if (cells[i][j] == symbol) {
					if (cells[i+1][j] == symbol && cells[i+2][j] == symbol &&
						cells[i+3][j] == symbol) {
						return true;
					}						
				}
			}
		}
		// forward diagonal
		for (int i=0; i < height-3; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i+1][j+1] == symbol && cells[i+2][j+2] == symbol &&
						cells[i+3][j+3] == symbol) {
						return true;
					}						
				}
			}
		}
		
		// backward diagonal
		for (int i=height-1; i >= 3; i--) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i-1][j+1] == symbol && cells[i-2][j+2] == symbol &&
						cells[i-3][j+3] == symbol) {
						return true;
					}						
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Return a count of the number of rows,columns and diagonals where there are 
	 * n symbols within 4 positions. The n symbols should not be separated by an
	 * opponent symbol but can be separated by empty spaces
	 * @param symbol symbol to check
	 * @param opponent_symbol opponent symbol
	 * @param n number of symbols that should exist in the 4 position space
	 * @return number of such rows, columns and diagonals
	 */
	public int nInARow(char symbol, char opponent_symbol, int n) {
		int count = 0;	
		// check row-wise
		boolean stop = false;
		for (int i=0; i < height; i++) {
			for (int j=0; j < width - 3; j++) {				
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i][j+k] == opponent_symbol) {
							//stop = true;
							s_count = 0;
							break;
						}
						if (cells[i][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}				
			}
		}
		stop = false;
		// check row-wise backwards
		for (int i=0; i < height; i++) {
			for (int j=3; j < width; j++) {				
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i][j-k] == opponent_symbol) {
							//stop = true;
							s_count = 0;
							break;
						}
						if (cells[i][j-k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}				
			}			
		}
		stop = false;

		// check column wise (no need to check backwards, since you can only build the column
		// upwards)
		for (int j=0; j < width; j++) {
			for (int i=0; i < height - 3; i++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i+k][j] == opponent_symbol) {
							//stop = true;
							s_count = 0;
							break;
						}
						if (cells[i+k][j] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		stop = false;
		// forward diagonal
		for (int i=0; i < height-3; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i+k][j+k] == opponent_symbol) {
						//	stop = true;
							s_count = 0;
							break;
						}
						if (cells[i+k][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		stop = false;
		// backward diagonal
		for (int i=height-1; i >= 3; i--) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;					
					for(int k=1;k<4;k++) {
						if (cells[i-k][j+k] == opponent_symbol) {
						//	stop = true;
							s_count = 0;
							break;
						}
						if (cells[i-k][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		return count;
	}	
	
	/**
	 * Set a value in the board
	 * @param i row
	 * @param j column
	 * @param c value
	 */
	public void setValue(int i, int j, char c) {
		cells[i][j] = c;	
	}

	@Override
	public Board clone() {
	    try {
			return (Board)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Copy the entire board and return a copy
	 * @return copied board
	 */
	public Board copy() {
		char [][] newcells = new char[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++ ) {
				newcells[i][j] = cells[i][j];
			}				
		}
		Board b = new Board(newcells,height,width);
		return b;
	}
	
	/** testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Board b = new Board(6);
		b.setValue(2, 3, 'X');
		b.setValue(3, 3, 'X');
		b.setValue(4, 3, 'X');
		b.setValue(5, 3, '0');
		System.out.println(b.nInARow('X', '0', 3));
		System.out.println(b.isWon('X'));
		b.print();
	}

}
