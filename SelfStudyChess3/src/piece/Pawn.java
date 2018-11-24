package piece;

import java.util.ArrayList;

public class Pawn extends Square{

	private int x, y;
	private boolean forwardTwo = true;
	private ArrayList<ArrayList> attackPath;
	
	public Pawn(String color, int y, int x) {
		
		super(color, "pawn");
		if(color == "white") {
			setSymbol("WPa");
		}else {
			setSymbol("BPa");
		}
		this.x = x;
		this.y = y;
	}
	
	public void promotion(int[] from, int[] to) {
		int toY = to[1];
		int fromX = from[0];
		int fromY = from[1];
		
		if(getColor() == "white") {
			if(toY == 0) {
				ChessBoard.board[fromY][fromX] = new Rook("white", y, x);
			}
		}else {
			if(toY == 7) {
				ChessBoard.board[fromY][fromX] = new Rook("black", y, x);
			}
		}
	}
	public ArrayList<ArrayList> attackPathCheck() {
		attackPath = new ArrayList<ArrayList>();
		if(this.getColor() == "white") {
			for(int i = -1; i < 2; i++) {
				ArrayList<Integer> attackTmp = new ArrayList<Integer>();
				if(Math.abs(i) == 1) {
					attackTmp.add(y-1);
					attackTmp.add(x+i);
					attackPath.add(attackTmp);
				}
				
			}
		}else if(this.getColor() == "black") {
			for(int i = -1; i < 1; i++) {
				ArrayList<Integer> attackTmp = new ArrayList<Integer>();
				if(Math.abs(i) == 1) {
					attackTmp.add(y+1);
					attackTmp.add(x+i);
					attackPath.add(attackTmp);
				}
			}
		}
		return attackPath;
	}
	
	// X ← →
	// Y ↑ ↓
	// [Y, X]
	@Override
	public boolean moveCheck(int[] from, int[] to, String color, boolean afterMoveCheck) {
		
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];
		
		Square fromSquare = ChessBoard.board[fromY][fromX];
		Square toSquare = ChessBoard.board[toY][toX];
		
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
			System.out.println("왕이 체크상태입니다.");
			return false;
		}
		
		// 흰색일 경우 전진 규칙
		if(color == "white") {
			// 제일 마지막으로 2칸 움직인 말만 앙파상 가능하도록 나머지는 전부 false
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(ChessBoard.board[i][j].getSymbol()=="WPa")
						ChessBoard.board[i][j].setEnpasant(false);
				}
			}
			// 한칸 전진
			if((fromX == toX) && (fromY - 1 == toY)) {
				if(toSquare.getType() == "none") {
					forwardTwo = false;
					promotion(from, to);
					x = toX;
					y = toY;
					return true;
				}
			}
			// 두칸 전진
			else if((forwardTwo) && (fromX == toX) && (fromY - 2 == toY)) {
				if(toSquare.getType() == "none") {
					forwardTwo = false;
					fromSquare.setEnpasant(true);
					x = toX;
					y = toY;
					return true;
				}
			}
			// 앙파상 or 죽이기
			else if((fromX - 1 == toX) || (fromX + 1 == toX) &&
					(fromY - 1 == toY)) {
				//앙파상
				if(toSquare.getType() == "none") {
					if(ChessBoard.board[fromY][toX].getEnpasant() == true){
						ChessBoard.board[fromY][toX] = new BlankSpace();
						x = toX;
						y = toY;
						return true;
					}
				}
				//죽이기
				else {
					if(toSquare.getColor() == "black") {
						promotion(from, to);
						forwardTwo = false;
						x = toX;
						y = toY;
						return true;
					}
				}
			}
		}// 흰색 전진 끝
		// 검정색일 경우 전진 규칙
		else{
			// 제일 마지막으로 2칸 움직인 말만 앙파상 가능하도록 나머지는 전부 false
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(ChessBoard.board[i][j].getSymbol()=="BPa")
						ChessBoard.board[i][j].setEnpasant(false);
				}
			}
			// 한칸 전진
			if((fromX == toX) && (fromY + 1 == toY)) {
				if(toSquare.getType() == "none") {
					forwardTwo = false;
					promotion(from, to);
					x = toX;
					y = toY;
					return true;
				}
			}
			// 두칸 전진
			else if((forwardTwo) && (fromX == toX) && (fromY + 2 == toY)) {
				if(toSquare.getType() == "none") {
					forwardTwo = false;
					fromSquare.setEnpasant(true);
					x = toX;
					y = toY;
					return true;
				}
			}
			// 앙파상 or 죽이기
			else if((fromX - 1 == toX) || (fromX + 1 == toX) &&
					(fromY + 1 == toY)) {
				//앙파상
				if(toSquare.getType() == "none") {
					if(ChessBoard.board[fromY][toX].getEnpasant() == true){
						ChessBoard.board[fromY][toX] = new BlankSpace();
						x = toX;
						y = toY;
						return true;
					}
				}
				//죽이기
				else {
					if(toSquare.getColor() == "white") {
						forwardTwo = false;
						promotion(from, to);
						x = toX;
						y = toY;
						return true;
					}
				}
			}
		}// 검정색 전진 끝
		
		return false;
	}
}
