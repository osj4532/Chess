package piece;

public class Rook extends Square {

	public Rook(String color) {
		super(color, "rook");
		if (color == "white") {
			setSymbol("WRo");
		} else {
			setSymbol("BRo");
		}
		setCastling(true);

	}

	@Override
	public boolean moveCheck(int[] moveFromReq, int[] moveToReq, String plyColor, boolean afterMoveCheck) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];

		String direction;
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
			System.out.println("왕이 체크상태입니다.");
			return false;
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
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						return true;
					}
				} else {
					testSquare = ChessBoard.board[moveFromY][moveFromX - displace];
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						return true;
					}
				}
			}
		} else if (direction == "top" || direction == "bottom") {
			int displaceMax = Math.abs(moveToY - moveFromY);
			for (int displace = 1; displace <= displaceMax; displace++) {
				if (direction == "top") {
					testSquare = ChessBoard.board[moveFromY - displace][moveFromX];
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						return true;
					}
				}else {
					testSquare = ChessBoard.board[moveFromY + displace][moveFromX];
					if((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					}else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						return true;
					}
				}
			}
		}

		return false;
	}

}
