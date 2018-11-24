package pieces;

import game.BlankSpace;
import game.ChessBoard;
import game.Square;

public class Pawn extends Square{

	private boolean forwardTwo = true;
	
	public Pawn(String color) {
		super(color, "pawn");
		if(color == "white") {
			setSymbol("WPa");
		}else {
			setSymbol("BPa");
		}
	}
	
	public void promotion(int[] from, int[] to) {
		int toY = to[1];
		int fromX = from[0];
		int fromY = from[1];
		
		if(getColor() == "white") {
			if(toY == 0) {
				ChessBoard.board[fromY][fromX] = new Queen("white");
			}
		}else {
			if(toY == 7) {
				ChessBoard.board[fromY][fromX] = new Queen("black");
			}
		}
	}
	// X ← →
	// Y ↑ ↓
	// [Y, X]
	@Override
	public boolean checkMove(int[] from, int[] to, String color, boolean afterMoveCheck) {
		
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];
		
		Square fromSquare = ChessBoard.board[fromY][fromX];
		Square toSquare = ChessBoard.board[toY][toX];
		
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
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
				if(toSquare.getType() == "blank") {
					forwardTwo = false;
					promotion(from, to);
					return true;
				}
			}
			// 두칸 전진
			else if((forwardTwo) && (fromX == toX) && (fromY - 2 == toY)) {
				if(toSquare.getType() == "blank") {
					forwardTwo = false;
					fromSquare.setEnpasant(true);
					return true;
				}
			}
			// 앙파상 or 죽이기
			else if((fromX - 1 == toX) || (fromX + 1 == toX) &&
					(fromY - 1 == toY)) {
				//앙파상
				if(toSquare.getType() == "blank") {
					if(ChessBoard.board[fromY][toX].getEnpasant() == true){
						ChessBoard.board[fromY][toX] = new BlankSpace();
						return true;
					}
				}
				//죽이기
				else {
					if(toSquare.getColor() == "black") {
						promotion(from, to);
						forwardTwo = false;
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
				if(toSquare.getType() == "blank") {
					forwardTwo = false;
					promotion(from, to);
					return true;
				}
			}
			// 두칸 전진
			else if((forwardTwo) && (fromX == toX) && (fromY + 2 == toY)) {
				if(toSquare.getType() == "blank") {
					forwardTwo = false;
					fromSquare.setEnpasant(true);
					return true;
				}
			}
			// 앙파상 or 죽이기
			else if((fromX - 1 == toX) || (fromX + 1 == toX) &&
					(fromY + 1 == toY)) {
				//앙파상
				if(toSquare.getType() == "blank") {
					if(ChessBoard.board[fromY][toX].getEnpasant() == true){
						ChessBoard.board[fromY][toX] = new BlankSpace();
						return true;
					}
				}
				//죽이기
				else {
					if(toSquare.getColor() == "white") {
						forwardTwo = false;
						promotion(from, to);
						return true;
					}
				}
			}
		}// 검정색 전진 끝
		
		return false;
	}
}
