package game;

public class BlankSpace extends Square{
	
	
	public BlankSpace() {
		super("blank");
		color = "null";
		symbol = "   ";
	}

	@Override
	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing) {
		return false;
	}
	
	
}
