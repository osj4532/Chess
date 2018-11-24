package pieces;

import game.ChessBoard;
import game.Square;

public class Queen extends Square{

	public Queen(String color) {
		super(color, "queen");
		
		if(color == "white"){
			setSymbol("WQu");
		}
		else{
			setSymbol("BQu");
		}
	}

	@Override
	public boolean checkMove(int[] moveFromReq, int[] moveToReq, String plyColor, boolean afterMoveCheck) {
		
		int moveFromX = moveFromReq[0];
		int moveFromY = moveFromReq[1];
		int moveToX = moveToReq[0];
		int moveToY = moveToReq[1];
		
		String direction;
		String type; 
		
		if(afterMoveCheck){
			return false;
		}
		
		if(moveToY == moveFromY){ 
			if(moveToX > moveFromX){
				direction = "rite";
				type = "straight";
			}
			else{
				direction = "left";
				type = "straight";
			}
		}
		
		else if(moveToX == moveFromX){
			if(moveToY > moveFromY){
				direction = "bot";
				type = "straight";
			}
			else{
				direction = "top";
				type = "straight";
			}
		}else if(moveToX > moveFromX){
			if(moveToY < moveFromY){
				direction = "topRite";
				type = "diagonal";
			}
			else{
				direction = "botRite";
				type = "diagonal";
			}
		}
		else if(moveToX < moveFromX){
			if(moveToY < moveFromY){
				direction = "topLeft";
				type = "diagonal";
			}
			else{
				direction = "botLeft";
				type = "diagonal";
			}
		}
		else{
			return false;
		}
		
		
		Square testSquare;
		
		if(type == "diagonal"){
			int moveDistance = Math.abs(moveToX - moveFromX);
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
		}
		else{ //직선
			if((direction == "rite") || (direction == "left")){
				int displaceMax = Math.abs(moveToX - moveFromX); 
		
				for(int displace = 1; displace <= displaceMax; displace++){
					if(direction == "rite"){
						testSquare = ChessBoard.board[moveFromY][moveFromX + displace];
					
						if((testSquare.getType() != "blank") && (displace != displaceMax)){
						return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "blank") || (testSquare.getColor() != plyColor))){
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[moveFromY][moveFromX - displace];
					
						if((testSquare.getType() != "blank") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "blank") || (testSquare.getColor() != plyColor))){
							return true;
						}
					}
				}
			}
			else{ 
				int displaceMax = Math.abs(moveToY - moveFromY); 
				
				for(int displace = 1; displace <= displaceMax; displace++){ 	
				
					if(direction == "top"){
						testSquare = ChessBoard.board[moveFromY - displace][moveFromX];
					
						if((testSquare.getType() != "blank") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "blank") || (testSquare.getColor() != plyColor))){
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[moveFromY + displace][moveFromX];
					
						if((testSquare.getType() != "blank") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "blank") || (testSquare.getColor() != plyColor))){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
