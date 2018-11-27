package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Square {
	private int x, y = 0;
	private ArrayList<ArrayList<Integer>> attackPath;

	public Rook(String color, int y, int x) {
		super(color, "rook");
		if (color == "white") {
			setSymbol("WRo");
		} else {
			setSymbol("BRo");
		}
		this.x = x;
		this.y = y;
		setCastling(true);

	}

	@Override
	public boolean moveCheck(int[] from, int[] to, String plyColor, boolean afterMoveCheck) {
		
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];

		String direction;
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
			System.out.println("왕이 체크상태입니다.");
			return false;
		}

		if (fromY == toY) {
			if (fromX > toX) {
				direction = "left";
			} else {
				direction = "right";
			}
		} else if (fromX == toX) {
			if (fromY > toY) {
				direction = "top";
			} else {
				direction = "bottom";
			}
		} else {
			return false;
		}

		Square testSquare;

		if (direction == "right" || direction == "left") {
			int displaceMax = Math.abs(toX - fromX);

			for (int displace = 1; displace <= displaceMax; displace++) {
				if (direction == "right") {
					testSquare = ChessBoard.board[fromY][fromX + displace];
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}
				} else {
					testSquare = ChessBoard.board[fromY][fromX - displace];
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}
				}
			}
		} else if (direction == "top" || direction == "bottom") {
			int displaceMax = Math.abs(toY - fromY);
			for (int displace = 1; displace <= displaceMax; displace++) {
				if (direction == "top") {
					testSquare = ChessBoard.board[fromY - displace][fromX];
					if ((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					} else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}
				}else {
					testSquare = ChessBoard.board[fromY + displace][fromX];
					if((testSquare.getType() != "none") && (displace != displaceMax)) {
						return false;
					}else if((testSquare.getType()=="none" || testSquare.getColor() != plyColor)&&(displace == displaceMax)) {
						setCastling(false);
						y = toY;
						x = toX;
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public ArrayList<ArrayList<Integer>> attackPathCheck() {
			attackPath = new ArrayList<ArrayList<Integer>>();
				if(this.getColor() == "white") {	
					//북
					for(int i = y - 1; i >= 0; i--) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
						attackPath.add(tmp);
						if(ChessBoard.board[i][x].getSymbol() != "BKi") {	
							if(ChessBoard.board[i][x].getType() != "none") {
								break;
							}
						}
					}
					//남				
					for(int i = y + 1; i <= 7; i++) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
						attackPath.add(tmp);
						if(ChessBoard.board[i][x].getSymbol() != "BKi") {	
							if(ChessBoard.board[i][x].getType() != "none") {
								break;
							}
						}
					}
					//서
					for(int i = x - 1; i >= 0; i--) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
						attackPath.add(tmp);
						if(ChessBoard.board[y][i].getSymbol() != "BKi") {	
							if(ChessBoard.board[y][i].getType() != "none") {
								break;
							}
						}
					}
					//동
					for(int i = x + 1; i <= 7; i++) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
						attackPath.add(tmp);
						if(ChessBoard.board[y][i].getSymbol() != "BKi") {	
							if(ChessBoard.board[y][i].getType() != "none") {
								break;
							}
						}
					}
				}else if(this.getColor() == "black") {
					//북
					for(int i = y - 1; i >= 0; i--) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
						attackPath.add(tmp);
						if(ChessBoard.board[i][x].getSymbol() != "WKi") {	
							if(ChessBoard.board[i][x].getType() != "none") {
								break;
							}
						}
					}
					//남				
					for(int i = y + 1; i <= 7; i++) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
						attackPath.add(tmp);
						if(ChessBoard.board[i][x].getSymbol() != "WKi") {	
							if(ChessBoard.board[i][x].getType() != "none") {
								break;
							}
						}
					}
					//서
					for(int i = x - 1; i >= 0; i--) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
						attackPath.add(tmp);
						if(ChessBoard.board[y][i].getSymbol() != "WKi") {	
							if(ChessBoard.board[y][i].getType() != "none") {
								break;
							}
						}
					}
					//동
					for(int i = x + 1; i <= 7; i++) {
						ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
						attackPath.add(tmp);
						if(ChessBoard.board[y][i].getSymbol() != "WKi") {	
							if(ChessBoard.board[y][i].getType() != "none") {
								break;
							}
						}
					}
				}
				return attackPath;
	}

	@Override
	public ArrayList<ArrayList<Integer>> movePathCheck() {
		// TODO Auto-generated method stub
		return null;
	}
}