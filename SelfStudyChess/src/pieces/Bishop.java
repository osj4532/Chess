package pieces;

import game.ChessBoard;
import game.Square;

public class Bishop extends Piece{

	public Bishop(String colorIn) {
		super(colorIn, "bishop");
		
		if(color == "white"){
			symbol = "WBi";
		}
		else{
			symbol = "BBi";
		}
	}

	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean testKing) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		Square toSquare = ChessBoard.board[moveToY][moveToX];
		
		int moveDistance = Math.abs(moveToX - moveFromX);
		
		if(!testKing){
			if(toSquare.getType() == "king"){
				return false; //왕을 잡을수 없다.
			}
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
		

		for(int diagMoveAway = 1; diagMoveAway <= moveDistance; diagMoveAway++){
			
			if(direction == "topRite"){
				testSquare = ChessBoard.board[moveFromY - diagMoveAway][moveFromX + diagMoveAway];
			}
			else if(direction == "botRite"){
				testSquare = ChessBoard.board[moveFromY + diagMoveAway][moveFromX + diagMoveAway];
			}
			else if(direction == "topLeft"){
				testSquare = ChessBoard.board[moveFromY - diagMoveAway][moveFromX - diagMoveAway];
			}
			else{ //botLeft
				testSquare = ChessBoard.board[moveFromY + diagMoveAway][moveFromX - diagMoveAway];
			}
			
			if((testSquare.getType() != "blank") && (diagMoveAway != moveDistance)){
				return false;
			}
			else if((diagMoveAway == moveDistance) && ((testSquare.getColor() != plyColor) || (testSquare.getType() == "blank"))){
				return true;
			}
		}
		return false;
	}
}
