package pieces;

import game.BlankSpace;
import game.ChessBoard;
import game.Square;

public class King extends Square{
	
	public King(String color) {
		super(color, "king");
		if(color == "white") {
			setSymbol("WKi");
		}else {
			setSymbol("BKi");
		}
		setCastling(true);
	}
	
	@Override
	public boolean checkMove(int[] from, int[] to, String plyColor, boolean afterMoveCheck) {
		// TODO Auto-generated method stub
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
						return true;
					}else if(toSquare.getType() == "blank") {
						setCastling(false);
						return true;
					}
				}
			}
		}
		if(toX == fromX + 2) {
			if(this.getCastling() && ChessBoard.board[fromY][7].getCastling()) {
				for(int i = fromX+1; i < 7; i++) {
					if(ChessBoard.board[fromY][i].getType() != "blank") {
						return false;
					}
				}
				ChessBoard.board[fromY][5] = ChessBoard.board[fromY][7];
				ChessBoard.board[fromY][7] = new BlankSpace();
				return true;
			}
		}else if(toX == fromX - 2) {
			if(this.getCastling() && ChessBoard.board[fromY][0].getCastling()) {
				for(int i = fromX-1; i > 0; i--) {
					if(ChessBoard.board[fromY][i].getType() != "blank") {
						return false;
					}
				}
				ChessBoard.board[fromY][3] = ChessBoard.board[fromY][0];
				ChessBoard.board[fromY][0] = new BlankSpace();
				return true;
			}
		}
		
		return false; 
	}

}
