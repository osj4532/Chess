package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Square {
	private int x, y = 0;
	private ArrayList<ArrayList<Integer>> attackPath;
	private ArrayList<ArrayList<Integer>> movePath;
	private ArrayList<ArrayList<Integer>> checkPath;
	
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
		setValue(5);
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
		if(this.getColor() == "white") {	
			//북
			for(int i = y-1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				if(ChessBoard.board[i][x].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				 
				if(ChessBoard.board[i][x].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				 
				if(ChessBoard.board[y][i].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				 
				if(ChessBoard.board[y][i].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
		}else if(this.getColor() == "black") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				
				if(ChessBoard.board[i][x].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				
				if(ChessBoard.board[i][x].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				if(ChessBoard.board[y][i].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				if(ChessBoard.board[y][i].getColor() != "none") {
					break;
				}
				movePath.add(tmp);
			}
		}
		return movePath;
	}
	
	public ArrayList<ArrayList<Integer>> checkPathCheck() {
		checkPath = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> me = new ArrayList<Integer>(Arrays.asList(y, x));
		checkPath.add(me);
		if(this.getColor() == "white") {	
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				checkPath.add(tmp);		
				if(ChessBoard.board[i][x].getSymbol() == "BKi") {
					return checkPath;
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				checkPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() == "BKi") {
					return checkPath;
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				checkPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() == "BKi") {
					return checkPath;
				}
			}
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				checkPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() == "BKi") {
					return checkPath;
				}
			}
		}else if(this.getColor() == "black") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				checkPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() == "WKi") {
					return checkPath;
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				checkPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() == "WKi") {
					return checkPath;
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				checkPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() == "WKi") {
					return checkPath;
				}
			}
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				checkPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() == "WKi") {
					return checkPath;
				}
			}
		}
		return null;
	}
	
	public ArrayList<ArrayList<Integer>> aiPath(){
		ArrayList<ArrayList<Integer>> aiPath = new ArrayList<ArrayList<Integer>>();
		
		//북
		for(int i = y - 1; i >= 0; i--) {
			ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
			if(ChessBoard.board[i][x].getColor() == getColor())  break;
			if(ChessBoard.board[i][x].getColor() != getColor()) {
				aiPath.add(tmp);
				if(ChessBoard.board[i][x].getColor() != "none") break;
			}
		}
		//남				
		for(int i = y + 1; i <= 7; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
			if(ChessBoard.board[i][x].getColor() == getColor())  break;
			if(ChessBoard.board[i][x].getColor() != getColor()) {
				aiPath.add(tmp);
				if(ChessBoard.board[i][x].getColor() != "none") break;
			}
		}
		//서
		for(int i = x - 1; i >= 0; i--) {
			ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
			if(ChessBoard.board[y][i].getColor() == getColor())  break;
			if(ChessBoard.board[y][i].getColor() != getColor()) {
				aiPath.add(tmp);
				if(ChessBoard.board[y][i].getColor() != "none") break;
			}
		}
		//동
		for(int i = x + 1; i <= 7; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
			if(ChessBoard.board[y][i].getColor() == getColor())  break;
			if(ChessBoard.board[y][i].getColor() != getColor()) {
				aiPath.add(tmp);
				if(ChessBoard.board[y][i].getColor() != "none") break;
			}
		}
		
		int kY = -1, kX = -1;
        if(this.getColor() == "white"){
            kY = ChessBoard.wKYX.get(0); kX = ChessBoard.wKYX.get(1);
        }else{
            kY = ChessBoard.bKYX.get(0); kX = ChessBoard.bKYX.get(1);
        }

        if(ChessBoard.board[kY][kX].isCheck()){
            ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
            if(aiPath.contains(ChessBoard.checker)){
                aiPath.clear();
                aiPath.add(new ArrayList<Integer>(Arrays.asList(ChessBoard.checker[0],ChessBoard.checker[1])));
            }else{
                for(int i = 0; i < ChessBoard.checkerRange.size(); i++){
                    if(aiPath.contains(ChessBoard.checkerRange.get(i))){
                        tmp.add(ChessBoard.checkerRange.get(i));
                    }
                }
            }
            aiPath.clear();
            aiPath.addAll(tmp);
        }
		
		return aiPath;
	}
}