package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Square{
	
	private int x, y = 0;
	private ArrayList<ArrayList<Integer>> attackPath;
	private ArrayList<ArrayList<Integer>> movePath;
	public King(String color, int y, int x) {
		super(color, "king");
		if(color == "white") {
			setSymbol("WKi");
		}else {
			setSymbol("BKi");
		}
		setCastling(true);
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean moveCheck(int[] from, int[] to, String plyColor, boolean testKing) {
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];
		
		Square toSquare = ChessBoard.board[toY][toX];
		
		//8방향을 모두 검사하고 가려는 곳이 조건에 맞으면 
		for(int distanceY = -1; distanceY <= 1; distanceY++) {
			for(int distanceX = -1; distanceX <= 1; distanceX++) {
				if(toX == fromX + distanceX && toY == fromY + distanceY) {
					if(toSquare.getType() != "blank" && toSquare.getColor() != plyColor) {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}else if(toSquare.getType() == "blank") {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}
				}
			}
		}
		if(toX == fromX + 2) {
			if(this.getCastling() && ChessBoard.board[fromY][7].getCastling()) {
				for(int i = fromX+1; i < 7; i++) {
					if(ChessBoard.board[fromY][i].getType() != "none") {
						return false;
					}
				}
				ChessBoard.board[fromY][5] = ChessBoard.board[fromY][7];
				ChessBoard.board[fromY][7] = new BlankSpace();
				y = toY;
				x = toX;
				return true;
			}
		}else if(toX == fromX - 2) {
			if(this.getCastling() && ChessBoard.board[fromY][0].getCastling()) {
				for(int i = fromX-1; i > 0; i--) {
					if(ChessBoard.board[fromY][i].getType() != "none") {
						return false;
					}
				}
				ChessBoard.board[fromY][3] = ChessBoard.board[fromY][0];
				ChessBoard.board[fromY][0] = new BlankSpace();
				y = toY;
				x = toX;
				return true;
			}
		}
		
		return false; 
	}

	@Override
	public ArrayList<ArrayList<Integer>> attackPathCheck() {
		attackPath = new ArrayList<ArrayList<Integer>>();
		
		for(int i = y - 1; i < y + 2; i++) {
			for(int j = x - 1; j < x + 2; j++) {
				if(i > 7 || i < 0 || j > 7 || j < 0) continue;
				if(i == y && j == x) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
			}
		}
		return attackPath;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public ArrayList<ArrayList<Integer>> movePathCheck() {
		movePath = new ArrayList<ArrayList<Integer>>();
		for(int i = y - 1; i < y + 2; i++) {
			for(int j = x - 1; j < x + 2; j++) {
				if(i > 7 || i < 0 || j > 7 || j < 0) continue;
				if(i == y && j == x) continue;
				if(ChessBoard.board[i][j].getColor() != this.getColor()) {
					ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
					movePath.add(tmp);
				}
			}
		}
		return movePath;
	}
	
	public ArrayList<ArrayList<Integer>> checkPathCheck() {
		return null;
	}

}
