package pieces;

import game.ChessBoard;
import game.Square;

public class Bishop extends Square{

	public Bishop(String color) {
		super(color, "bishop");
		
		if(color == "white"){
			setSymbol("WBi");
		}
		else{
			setSymbol("BBi");
		}
	}

	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean afterMoveCheck) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		int moveDistance = Math.abs(moveToX - moveFromX);
		
		if(afterMoveCheck){
			return false;
		}
		
		String direction; //방향
		
		if(moveToX > moveFromX){
			if(moveToY < moveFromY){
				direction = "topRite";
			}
			else{
				direction = "botRite";
			}
		}
		else{
			if(moveToY < moveFromY){
				direction = "topLeft";
			}
			else{
				direction = "botLeft";
			}
		}
		
		
		Square testSquare; 
		int compairX = 9, compairY = 9;
		
		for(int diagMoveAway = 1; diagMoveAway <= moveDistance; diagMoveAway++){
			
			if(direction == "topRite"){
				testSquare = ChessBoard.board[moveFromY - diagMoveAway][moveFromX + diagMoveAway];
				if(diagMoveAway == moveDistance) {
					compairX = moveFromX + diagMoveAway;
					compairY = moveFromY - diagMoveAway;
				}
			}
			else if(direction == "botRite"){
				testSquare = ChessBoard.board[moveFromY + diagMoveAway][moveFromX + diagMoveAway];
				if(diagMoveAway == moveDistance) {
					compairX = moveFromX + diagMoveAway;
					compairY = moveFromY + diagMoveAway;
				}
			}
			else if(direction == "topLeft"){
				testSquare = ChessBoard.board[moveFromY - diagMoveAway][moveFromX - diagMoveAway];
				if(diagMoveAway == moveDistance) {
					compairX = moveFromX - diagMoveAway;
					compairY = moveFromY - diagMoveAway;
				}
			}
			else{ //botLeft
				testSquare = ChessBoard.board[moveFromY + diagMoveAway][moveFromX - diagMoveAway];
				if(diagMoveAway == moveDistance) {
					compairX = moveFromX - diagMoveAway;
					compairY = moveFromY + diagMoveAway;
				}
			}
			
			if((testSquare.getType() != "blank") && (diagMoveAway != moveDistance)){
				return false;
			}
			else if(((moveToX == compairX) && (moveToY == compairY)) && ((testSquare.getColor() != plyColor) || (testSquare.getType() == "blank"))){
				return true;
			}
		}
		return false;
	}
}
