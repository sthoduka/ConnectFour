/**
 * Abstract class to represent a connect 4 player
 * @author Santosh Thoduka
 *
 */
public abstract class Player {
	protected char symbol;
	protected char symbol_opp;
	public Player(char symbol) {
		this.symbol = symbol;
		if (this.symbol == 'X')
			symbol_opp = '0';
		else 
			symbol_opp = 'X';
	}
	public abstract Board makeMove(Board board, ObjectCounter ctr);
}
