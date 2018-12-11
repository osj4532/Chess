package chess;

import java.util.ArrayList;
import java.util.Random;

public class AlphaBetaPlayer extends Player {
	MinimaxAlphaBeta minimax;
	private String color;
	public AlphaBetaPlayer(String color, int maxDepth) {
		super("AI", color);
		this.color = color;
		minimax = new MinimaxAlphaBeta(color, maxDepth);
	}

	
	public int[][] getMove(ChessBoard b){
		int[][] moves = new int[2][2];
				
		
		
		ArrayList<Integer> move = minimax.decision(b);
		moves[1][0] = move.get(1);
		moves[1][1] = move.get(0);
		
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(b.board[i][j].getColor() == color) {
					if(b.board[i][j].attackPathCheck().contains(move)) {
						moves[0][0] = j;
						moves[0][1] = i;
					}
				}
			}
		}
		
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 2; j++)
				System.out.println(moves[i][j]);
		return moves;
	}

}
