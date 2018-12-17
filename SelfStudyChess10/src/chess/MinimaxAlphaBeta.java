package chess;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MinimaxAlphaBeta {

	boolean color;
	int maxDepth;
	Random rand;
	/**
	 * 
	 */
	public MinimaxAlphaBeta(String color, int maxDepth) {
		if(color == "white")
			this.color = true;
		else if(color == "black")
			this.color = false;
		this.maxDepth = maxDepth;
		rand = new Random();
	}
	
	private float maxValue(ChessBoard b, ArrayList<ArrayList<Integer>> state, float alpha, float beta, int depth) {
		if(depth > maxDepth)
			return eval1(b, state, color);
		ArrayList<ArrayList<Integer>> moves = null;
		if(color) {
			moves = b.allW;
		}
		else if(!color) {
			moves = b.allB;
		}
		
		if(moves.size() == 0) // TODO add draw
			return Float.NEGATIVE_INFINITY;
		
		for(int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			float tmp = minValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if(tmp > alpha) {
				alpha = tmp;
			}
			
			if(beta <= alpha)
				break;
		}
		
		return alpha;
	}
	
	private float minValue(ChessBoard b, ArrayList<ArrayList<Integer>> state, float alpha, float beta, int depth) {
		if(depth > maxDepth)
			return eval1(b, state, color);
		
		ArrayList<ArrayList<Integer>> moves = null;
		if(color) {
			moves = b.allW;
		}
		else if(!color) {
			moves = b.allB;
		}
		if(moves.size() == 0) // TODO add draw
			return Float.POSITIVE_INFINITY;
		
		for(int i = 0; i < moves.size(); i++) {
			state.add(moves.get(i));
			float tmp = maxValue(b, state, alpha, beta, depth + 1);
			state.remove(state.lastIndexOf(moves.get(i)));
			if(tmp < beta) {
				beta = tmp;
			}
			
			if(beta <= alpha)
				break;
		}
		
		return beta;
	}
	
	public ArrayList<Integer> decision(final ChessBoard b) {
		// get maximum move
		
		// 색깔별 움직일수 있는 모든 배열 가져오기
		ArrayList<ArrayList<Integer>> moves = null;
		if(color) {
			moves = b.allW;
		}else {
			moves = b.allB;
		}
		
		if(moves.size() == 0)
			return null;
 		
		
		Vector<Future<Float>> costs = new Vector<Future<Float>>(moves.size());
		costs.setSize(moves.size());
		
 		ExecutorService exec = Executors.newFixedThreadPool(moves.size());
 		try {
 		    for (int i = 0; i < moves.size(); i++) {
 		    	ArrayList<Integer> move = moves.get(i);
 		        Future<Float> result = exec.submit(new Callable<Float>() {

 		            @Override
 		            public Float call() {
 		            	ArrayList<ArrayList<Integer>> state = new ArrayList<ArrayList<Integer>>();
 		            	state.add(move);
 		            	
 		            	float tmp = minValue(b, state, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 1);
 		            	return tmp;
 		            }
 		        });
 		        costs.set(i, result);
 		    }
 		} finally {
 		    exec.shutdown();
 		}

 		// max
 		int maxi = -1;
		float max = Float.NEGATIVE_INFINITY;
 		for(int i = 0; i < moves.size(); i++) {
 			float cost;
			try {
				cost = costs.get(i).get();
			} catch (Exception e) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
				}
				continue;
			}
 			if(cost >= max) {
 				if(Math.abs(cost-max) < 0.1) // add a little random element
 					if(rand.nextBoolean())
 						continue;

 				max = cost;
 				maxi = i;
 			}
 		}
 		
 		return moves.get(maxi);
	}
	
	private float eval1(ChessBoard b, ArrayList<ArrayList<Integer>> moves, boolean currentColor) {
		Square[][] squares = b.board;
		
		ArrayList<ArrayList<Integer>> almoves = null;
		ArrayList<Integer> king = null;
		if(color) {
			almoves = b.allW;
			king = b.wKYX;
		}else {
			almoves = b.allB;
			king = b.bKYX;
		}
		
		
		if(almoves.size() == 0) {
			if(squares[king.get(0)][king.get(1)].isCheck())
				return (currentColor == this.color) ?  Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
			else
				return Float.NEGATIVE_INFINITY; // we don't like draws
		}
		
		int whiteScore = 0;
		int blackScore = 0;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				if(squares[i][j].getType() != "none")
					if(squares[i][j].getColor() == "white")
						whiteScore += squares[i][j].getValue();
					else
						blackScore += squares[i][j].getValue();
			}
		
		
		if(color == true)
			return whiteScore - blackScore;
		else
			return blackScore - whiteScore;
	}

}
