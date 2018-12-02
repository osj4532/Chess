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
				if(ChessBoard.board[y+i][x+2].getColor() != this.getColor())
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
				if(ChessBoard.board[y+i][x-2].getColor() != this.getColor())
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
				if(ChessBoard.board[y-2][x+i].getColor() != this.getColor())
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
				if(ChessBoard.board[y+2][x+i].getColor() != this.getColor())
					movePath.add(tmp);
			}
		}
		return movePath;
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> checkPathCheck() {
		return null;
	}
}
