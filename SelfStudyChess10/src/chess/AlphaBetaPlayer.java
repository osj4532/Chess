package chess;

import java.util.ArrayList;
import java.util.Arrays;
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
		ArrayList<ArrayList<Integer>> random = new ArrayList<ArrayList<Integer>>();
		
		moves[1][0] = move.get(1);
		moves[1][1] = move.get(0);
		
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(b.board[i][j].getColor() == color) {
					if(b.board[i][j].aiPath().contains(move)) {
						ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(j,i));
						random.add(temp);
					}
				}
			}
		}
		Random rad = new Random();
		int de = rad.nextInt(random.size());
		moves[0][0] = random.get(de).get(0);
		moves[0][1] = random.get(de).get(1);
		
		for(int i = 0; i < 2; i++) {
			for(int j = 1; j >= 0; j--)
				System.out.print(moves[i][j]);
			System.out.print("\n");
		}
		return moves;
	}

}
