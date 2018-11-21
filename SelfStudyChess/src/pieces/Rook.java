package pieces;

import game.ChessBoard;
import game.Square;

public class Rook extends Piece {

	private boolean castlingCheck = false;
	
	public Rook(String colorIn) {
		super(colorIn, "rook");
		if (color == "white") {
			symbol = "WRo";
		} else {
			symbol = "BRo";
		}

	}

	public boolean getCastlingCheck() {
		return castlingCheck;
	}
	
	@Override
	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing) {
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];

		String direction;

		Square toSquare = ChessBoard.board[moveToY][moveToX];

		if (!testKing) {
			if (toSquare.getType() == "king") {
				return false;
			}
		}

		if (moveFromY == moveToY) {
			if (moveFromX > moveToX) {
				direction = "left";
			} else {
				direction = "right";
			}
		} else if (moveFromX == moveToX) {
			if (moveFromY > moveToY) {
				direction = "top";
			} else {
				direction = "bottom";
			}
		} else {
			return false;
		}

		Square testSquare;

		if (direction == "right" || direction == "left") {
			int displaceMax = Math.abs(moveToX - moveFromX);

			for (int displace = 1; displace <= displaceMax; displace++) {
				if (direction == "right") {
					testSquare = ChessBoard.board[moveFromY][moveFromX + displace];
					if ((testSquare.getType() != "blank") && (displace != displaceMax)) {
						return false;
					} else if ((testSquare.getType() == "blank") && (displace == displaceMax)
							|| (testSquare.getColor() != plyColor)) {
						castlingCheck = true;
						return true;
					}
				} else {
					testSquare = ChessBoard.board[moveFromY][moveFromX - displace];
					if ((testSquare.getType() != "blank") && (displace != displaceMax)) {
						return false;
					} else if ((testSquare.getType() == "blank") && (displace == displaceMax)
							|| (testSquare.getColor() != plyColor)) {
						castlingCheck = true;
						return true;
					}
				}
			}
		} else if (direction == "top" || direction == "bottom") {
			int displaceMax = Math.abs(moveToY - moveFromY);
			for (int displace = 1; displace <= displaceMax; displace++) {
				if (direction == "top") {
					testSquare = ChessBoard.board[moveFromY - displace][moveFromX];
					if ((testSquare.getType() != "blank") && (displace != displaceMax)) {
						return false;
					} else if ((testSquare.getType() == "blank") && (displace == displaceMax) || (testSquare.getColor() != plyColor)) {
						castlingCheck = true;
						return true;
					}
				}else {
					testSquare = ChessBoard.board[moveFromY + displace][moveFromX];
					if((testSquare.getType() != "blank") && (displace != displaceMax)) {
						return false;
					}else if((testSquare.getType()=="blank")&&(displace == displaceMax)||(testSquare.getColor() != plyColor)) {
						castlingCheck = true;
						return true;
					}
				}
			}
		}

		return false;
	}

}
