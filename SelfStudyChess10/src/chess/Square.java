package chess;

import java.util.ArrayList;

public abstract class Square {
	// white, black, none
	private String color;
	// |   | 
	// WPa, WRo, WKn, WBi, WQu, WKi
	// BPa, BRo, BKn, BBi, BQu, BKi
	private String symbol;
	// pawn, rook, knight, bishop, queen, king, none
	private String type;
	// pawn rule
	private boolean enpasant = false;
	// king, rook rule
	private boolean castling = false;
	// king rule
	private boolean isCheck = false;
	private int x, y;
	private int value;
	
	public void setValue(int v) {
		this.value = v;
	}
	
	public int getValue() {
		return value;
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

	public Square(String color,String type) {
		this.color = color;
		this.type = type;
	}
	
	public abstract ArrayList<ArrayList<Integer>> attackPathCheck();
	
	public abstract ArrayList<ArrayList<Integer>> movePathCheck();
	
	public abstract ArrayList<ArrayList<Integer>> checkPathCheck();
	
	public abstract ArrayList<ArrayList<Integer>> aiPath();

	public abstract boolean moveCheck(int[] from, int[] to, String color, boolean afterMoveCheck);
	
	public String getColor() {
		return color;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getType() {
		return type;
	}
	
	public void setEnpasant(boolean enpasant) {
		this.enpasant = enpasant;
	}
	
	public boolean getEnpasant() {
		return enpasant;
	}

	public boolean getCastling() {
		return castling;
	}

	public void setCastling(boolean castling) {
		this.castling = castling;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	
}
