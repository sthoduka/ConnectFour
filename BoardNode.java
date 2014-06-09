import java.util.ArrayList;

/**
 * BoardNode - used to store possible states of the game while running
 * the Minimax (and Alpha Beta pruning) algorithm
 * @author Santosh Thoduka
 *
 */
public class BoardNode {
	private Board board;
	private int depth;
	private char symbol_to_play;
	private char symbol_opponent;
	private char head_symbol;
	private char head_opponent;	
	private boolean pruning_enabled;
	private ObjectCounter ctr;
	
	/**
	 * Initialize the board node
	 * @param board current board
	 * @param depth depth of the board in the min/max tree
	 * @param symbol symbol for the current player in the min/max tree
	 * @param head_symbol symbol for the computer
	 * @param pruning_enabled whether or not to use alpha-beta pruning
	 * @param ctr object counter
	 */
	public BoardNode(Board board, int depth, char symbol, char head_symbol, boolean pruning_enabled, ObjectCounter ctr) {
		this.board = board;
		this.depth = depth;
		this.head_symbol = head_symbol;
		if (head_symbol == 'X')
			head_opponent = '0';
		else
			head_opponent = 'X';
		this.symbol_to_play = symbol;
		if(symbol_to_play == 'X') {
			symbol_opponent = '0';
		} else {
			symbol_opponent = 'X';
		}
		this.pruning_enabled = pruning_enabled;
		this.ctr = ctr;
		ctr.increment();
	}
	
	/**
	 * Evaluates utility of the current board position for the computer
	 * @param current_parent_evaluation current evaluation of parent node - used for pruning
	 * @return utility of the current board node
	 */
	public int evaluate(int current_parent_evaluation) {
		// winning state for computer
		if (board.isWon(head_symbol)) {
			return Integer.MAX_VALUE;
		}
		// losing state for computer
		if (board.isWon(head_opponent)) {
			return Integer.MIN_VALUE;
		}
		// maximum depth of tree
		int threshold = 5;
		// if we've reached the depth, do a heuristic evaluation
		if (depth == threshold) {
			return heuristicEvaluation();
		}
		// get children/neighbours of current node
		ArrayList<BoardNode> children = getChildren();		
		
		int evaluation =0;
		// We're at a "Max" node in the min-max tree
		if (depth % 2 == 0) {
			evaluation = Integer.MIN_VALUE;
			for (BoardNode child : children) {
				int value = child.evaluate(evaluation);
				if (value > evaluation) {
					evaluation = value;
				} 
				if (pruning_enabled && evaluation > current_parent_evaluation)
					break;
			}
		} else { // We're at a "Min" node in the min-max tree
			evaluation = Integer.MAX_VALUE;
			for (BoardNode child : children) {
				int value = child.evaluate(evaluation);
				if (value < evaluation) {
					evaluation = value;
				}
				if (pruning_enabled && evaluation < current_parent_evaluation)
					break;
			}
		}		
		return evaluation;
	}
	
	/**
	 * The main idea for the heuristic functions was adapted from here:
	 * https://www.cs.purdue.edu/homes/cs190m/fall08/projects/project4/#ai
	 * Estimates the utility of the current position of the node
	 * It is based on number of "three-in-a-row" and two-in-a-row" situations
	 * for the computer and opponent
	 * @return utility of current board configuration for the computer
	 */
	private int heuristicEvaluation() {
		if (board.isWon(head_symbol)) {
			return Integer.MAX_VALUE;
		}
		if (board.isWon(head_opponent)) {
			return Integer.MIN_VALUE;
		}
		// three-in-a-row and two-in-a-row situation for computer and opponent
		// weighted sum of all four is the evaluation
		int threepos = 100*board.nInARow(head_symbol, head_opponent,3);
		int threeneg = -100*board.nInARow(head_opponent, head_symbol,3);
		int twopos = 10*board.nInARow(head_symbol, head_opponent,2);
		int twoneg =  -10*board.nInARow(head_opponent, head_symbol,2);
		
		return threepos+threeneg+twopos+twoneg;
	}
	
	/**
	 * Get all possible board positions from current board position
	 * @return children
	 */
	public ArrayList<BoardNode> getChildren() {
		ArrayList<BoardNode> children = new ArrayList<BoardNode>();
		for (int i = 0; i < 7; i++) {
			if (board.checkDropValidity(i)) {
				Board child = board.copy();
				child.dropDisk(symbol_to_play, i);			
				BoardNode childNode = new BoardNode(child, depth+1,symbol_opponent, head_symbol, pruning_enabled, ctr);
				children.add(childNode);
			}
		}
		return children;
	}
	
	/**
	 * Returns the board for the node
	 * @return
	 */
	public Board getBoard() {		
		return board;
	}
}
