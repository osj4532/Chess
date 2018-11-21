package pieces;

import game.ChessBoard;
import game.Square;

public class King extends Piece{

	private boolean castlingCheck = true;
	
	public King(String colorIn) {
		super(colorIn, "king");
		if(color == "white") {
			symbol = "WKi";
		}else {
			symbol = "BKi";
		}
	}
	public boolean getCastlingCheck() {
		return castlingCheck;
	}
	@Override
	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing) {
		// TODO Auto-generated method stub
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		Square toSquare = ChessBoard.board[moveToY][moveToX];
		
		//8방향을 모두 검사하고 가려는 곳이 조건에 맞으면 
		for(int distanceY = -1; distanceY <= 1; distanceY++) {
			for(int distanceX = -1; distanceX <= 1; distanceX++) {
				if(moveToX == moveFromX + distanceX && moveToY == moveFromY + distanceY) {
					if(toSquare.getType() != "blank" && toSquare.getColor() != plyColor) {
						castlingCheck = true;
						return true;
					}else if(toSquare.getType() == "blank") {
						castlingCheck = true;
						return true;
					}
				}
			}
		}
		//if(castlingCheck == false && moveToX == moveFromX + 2 || moveToX == moveFromX - 2)
		//	return true;
		return false; 
	}

}
