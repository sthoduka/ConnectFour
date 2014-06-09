import java.util.ArrayList;

/**
 * An implementation of the Player class used to implement
 * the Minimax algorithm with alpha beta pruning
 * @author Santosh Thoduka
 *
 */

public class AlphaBetaPlayer extends Player {
	/**
	 * Initialize the Player with the symbol to play with
	 * @param symbol
	 */
	public AlphaBetaPlayer(char symbol) {
		super(symbol);
	}
	/**
	 * Makes a move using the minimax algorithm with alpha beta pruning
	 * @return Board after move has been played
	 */
	public Board makeMove(Board board, ObjectCounter ctr) {
		BoardNode bn = new BoardNode(board, 0, symbol, symbol, true,ctr);
		ArrayList<BoardNode> nodes = bn.getChildren();
		int maximum_eval = Integer.MIN_VALUE;
		int max_index = 0;
		for (int i = 0; i < nodes.size(); i++) {
			int evaluation = nodes.get(i).evaluate(Integer.MIN_VALUE);
			if (evaluation > maximum_eval) {
				maximum_eval = evaluation;
				max_index = i;
			}
		}
		board = nodes.get(max_index).getBoard();
		return board;
	}
}
