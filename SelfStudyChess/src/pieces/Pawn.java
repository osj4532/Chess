package pieces;

import game.BlankSpace;
import game.ChessBoard;
import game.Square;

public class Pawn extends Piece{

	private boolean enpassant = false;
	
	public boolean getEnpassant() {
		return enpassant;
	}
	
	public void setEnpassant(boolean en) {
		enpassant = en;
	}
	
	public Pawn(String colorIn) {
		super(colorIn, "pawn");
		
		if(color == "white"){
			symbol = "WPa";
		}
		else{
			symbol = "BPa";
		}
	}
	
	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		int moveForwardTwo; //초기 전진시 2칸전진
		int moveForwardOne;
		int pawnRowOnPlySide; //폰 초기 위치 (2칸 전진룰)
		
		Square toSquare = ChessBoard.board[moveToY][moveToX];
		
		if(!testKing){
			if(toSquare.getType() == "king"){
				return false; //왕을 잡을 수 없다.
			}
		}
		
		
		
		
		if(plyColor == "white"){ // 흰색일때 전진 및 초기 위치
			moveForwardTwo = -2;
			moveForwardOne = -1;
			pawnRowOnPlySide = 6;
		}
		else{ //검정
			moveForwardTwo = 2;
			moveForwardOne = 1;
			pawnRowOnPlySide = 1;
		}
		enpassant = false;
		if(moveToY == moveFromY + moveForwardOne){
			Pawn fromSquare1 = null;
			Pawn fromSquare2 = null;
			if(moveFromX != 0) {
				if(ChessBoard.board[moveFromY][moveFromX-1].getType() == "pawn") {
					fromSquare1 = (Pawn)ChessBoard.board[moveFromY][moveFromX-1];
				}
			}
			if(moveFromX != 7) {
				if(ChessBoard.board[moveFromY][moveFromX+1].getType() == "pawn") {
					fromSquare2 = (Pawn)ChessBoard.board[moveFromY][moveFromX+1];
				}
			}
			
			
			//폰이 다른 말을 잡을때
			if((moveToX == moveFromX - 1) || (moveToX == moveFromX + 1)){
				if((toSquare.getType() != "blank") && (toSquare.getColor() != plyColor)){
					return true; 
				}else if((moveToX == moveFromX - 1) &&
						(toSquare.getType() == "blank") &&
						(fromSquare1.getColor() != plyColor) &&
						(fromSquare1.getEnpassant() == true)) {
					ChessBoard.board[moveFromY][moveFromX-1] = new BlankSpace();
					return true;
				}else if((moveToX == moveFromX + 1) &&
						(toSquare.getType() == "blank") &&
						(fromSquare2.getColor() != plyColor)&&
						(fromSquare2.getEnpassant() == true)){
					ChessBoard.board[moveFromY][moveFromX+1] = new BlankSpace();
					return true;
				}
						
			}	
			//전진 조건
			else if((moveToX == moveFromX) && (toSquare.getType() == "blank")){ 
				return true;
			}
		}
		//초기 전진 조건
		else if((moveToY == moveFromY + moveForwardTwo) && (moveToX == moveFromX) && (toSquare.getType() == "blank")){ 
			if(moveFromY == pawnRowOnPlySide){ //if pawn moves from the starting row
				enpassant = true;
				System.out.println(enpassant);
				return true;
			}
		}
		
		return false;
	}	
}
