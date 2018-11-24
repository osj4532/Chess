package pieces;

import game.ChessBoard;
import game.Square;

public class Knight extends Square{

	public Knight(String color) {
		super(color, "knight");
		
		if(color == "white"){
			setSymbol("WKn");
		}
		else{
			setSymbol("BKn");
		}
	}

	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean afterMoveCheck) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		Square toSquare = ChessBoard.board[moveToY][moveToX];
		
		// 움직인 뒤에도 체크(true)이면 못움직인다.
		if(afterMoveCheck) {
			return false;
		}
		
		boolean locationPass = false; 
		
		for(int displaceX = -2; displaceX <= 2; displaceX++){
		
			if(displaceX != 0){
				if(moveToX == moveFromX + displaceX){
					
					if(Math.abs(displaceX) == 1){ //x가 1번이면 y는 2번
						for(int displaceY = -2; displaceY <= 2; displaceY += 4){
							if(moveToY == moveFromY + displaceY){
								locationPass = true;
							}
						}
					}
					else{ //y가 1번이면 x는 두번
						for(int displaceY = -1; displaceY <= 1; displaceY += 2){
							if(moveToY == moveFromY + displaceY){
								locationPass = true;
							}
						}
					}
				}
			}
		}
		if(locationPass){
			
			if((toSquare.getType() == "blank") || (toSquare.getColor() != plyColor)){
				return true;
			}
		}
		
		return false;
	}
}
