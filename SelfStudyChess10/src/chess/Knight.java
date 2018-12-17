package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Square{

	private int x, y;
	private ArrayList<ArrayList<Integer>> attackPath;
	private ArrayList<ArrayList<Integer>> movePath;
	
	public Knight(String color, int y, int x) {
		super(color, "knight");
		
		if(color == "white"){
			setSymbol("WKn");
			
		}
		else{
			setSymbol("BKn");
		}
		this.x = x;
		this.y = y;
		setValue(3);
	}

	public boolean moveCheck(int[] from, int[] to, String plyColor, boolean afterMoveCheck) {
		
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];
		
		Square toSquare = ChessBoard.board[toY][toX];
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
			return false;
		}
		
		boolean locationPass = false; 
		
		for(int displaceX = -2; displaceX <= 2; displaceX++){
		
			if(displaceX != 0){
				if(toX == fromX + displaceX){
					
					if(Math.abs(displaceX) == 1){ //x가 1번이면 y는 2번
						for(int displaceY = -2; displaceY <= 2; displaceY += 4){
							if(toY == fromY + displaceY){
								locationPass = true;
							}
						}
					}
					else{ //y가 1번이면 x는 두번
						for(int displaceY = -1; displaceY <= 1; displaceY += 2){
							if(toY == fromY + displaceY){
								locationPass = true;
							}
						}
					}
				}
			}
		}
		if(locationPass){
			if((toSquare.getType() == "none") || (toSquare.getColor() != plyColor)){
				y = toY;
				x = toX;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ArrayList<ArrayList<Integer>> attackPathCheck() {
		attackPath = new ArrayList<ArrayList<Integer>>();
		//→↗
		//→↘
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x+2 > 7) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x+2));
				attackPath.add(tmp);
			}
		}
		//←↖
		//←↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x-2 < 0) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x-2));
				attackPath.add(tmp);
			}
		}
		//↑↗
		//↑↖
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y-2 < 0) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y-2,x+i));
				attackPath.add(tmp);
			}
		}
		//↓↘
		//↓↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y+2 > 7) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+2,x+i));
				attackPath.add(tmp);
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
		//→↗
		//→↘
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x+2 > 7) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x+2));
				if(ChessBoard.board[y+i][x+2].getColor() == "none")
					movePath.add(tmp);
			}
		}
		//←↖
		//←↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x-2 < 0) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x-2));
				if(ChessBoard.board[y+i][x-2].getColor() == "none")
					movePath.add(tmp);
				
			}
		}
		//↑↗
		//↑↖
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y-2 < 0) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y-2,x+i));
				if(ChessBoard.board[y-2][x+i].getColor() == "none")
					movePath.add(tmp);
			}
		}
		//↓↘
		//↓↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y+2 > 7) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+2,x+i));
				if(ChessBoard.board[y+2][x+i].getColor() == "none")
					movePath.add(tmp);
			}
		}
		return movePath;
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> checkPathCheck() {
		ArrayList<ArrayList<Integer>> checkPath = new ArrayList<ArrayList<Integer>>();
		//→↗
		//→↘
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x+2 > 7) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x+2));
				if(ChessBoard.board[y+i][x+2].getColor() != getColor()) {
					if(ChessBoard.board[y+i][x+2].getType() == "king") {
						checkPath.add(tmp);
					}
				}
					
			}
		}
		//←↖
		//←↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x-2 < 0) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x-2));
				if(ChessBoard.board[y+i][x-2].getColor() != getColor()) {
					if(ChessBoard.board[y+i][x-2].getType() == "king") {
						checkPath.add(tmp);
					}
				}
				
			}
		}
		//↑↗
		//↑↖
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y-2 < 0) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y-2,x+i));
				if(ChessBoard.board[y-2][x+i].getColor() != getColor()) {
					if(ChessBoard.board[y-2][x+i].getType() == "king") {
						checkPath.add(tmp);
					}
				}
			}
		}
		//↓↘
		//↓↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y+2 > 7) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+2,x+i));
				if(ChessBoard.board[y+2][x+i].getColor() != getColor()) {
					if(ChessBoard.board[y+2][x+i].getType() == "king") {
						checkPath.add(tmp);
					}
				}
			}
		}
		return checkPath;
	}
	
	public ArrayList<ArrayList<Integer>> aiPath(){
		ArrayList<ArrayList<Integer>> aiPath = new ArrayList<ArrayList<Integer>>();
		//→↗
		//→↘
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x+2 > 7) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x+2));
				if(ChessBoard.board[y+i][x+2].getColor() != getColor())
					aiPath.add(tmp);
			}
		}
		//←↖
		//←↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(x-2 < 0) break;
				if(y+i > 7 || y+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+i,x-2));
				if(ChessBoard.board[y+i][x-2].getColor() != getColor())
					aiPath.add(tmp);
				
			}
		}
		//↑↗
		//↑↖
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y-2 < 0) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y-2,x+i));
				if(ChessBoard.board[y-2][x+i].getColor() != getColor())
					aiPath.add(tmp);
			}
		}
		//↓↘
		//↓↙
		for(int i = -1; i<2; i++) {
			if(Math.abs(i) == 1) {
				if(y+2 > 7) break;
				if(x+i > 7 || x+i < 0) continue;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y+2,x+i));
				if(ChessBoard.board[y+2][x+i].getColor() != getColor())
					aiPath.add(tmp);
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
