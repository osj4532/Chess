package piece;

public abstract class Square {
	// white, black, none
	private String color;
	// |   | 
	// WPa, WRo, WKn, WBi, WQu, WKi
	// BPa, BRo, BKn, BBi, BQu, BKi
	private String symbol;
	// pawn, rook, knight, bishop, queen, king, none
	private String type;
	private boolean enpasant = false;
	private boolean castling = false;
	private boolean isCheck = false;
	
	public Square(String color,String type) {
		this.color = color;
		this.type = type;
	}
	
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
