package game;

public abstract class Square {
	
	// -1은 조각이 보드 밖으로 나감
	protected String symbol;
	public String color;// black, white, blank
	public String type;
	
	public Square(String typeIn) {
		type = typeIn;
	}
	
	String getSymbol() {
		return symbol;
	}
	public String getColor() {
		return color;
	}
	public String getType() {
		return type;
	}
	
	public abstract boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing);
	
}
