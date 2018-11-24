package piece;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Square{
	private int x, y = 0;
	private ArrayList<ArrayList> attackPath;

	public Queen(String color, int y, int x) {
		super(color, "queen");
		
		if(color == "white"){
			setSymbol("WQu");
		}
		else{
			setSymbol("BQu");
		}
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean moveCheck(int[] moveFromReq, int[] moveToReq, String plyColor, boolean afterMoveCheck) {
		
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
				
				if((testSquare.getType() != "none") && (diagMoveAway != moveDistance)){
					return false;
				}
				else if(((moveToX == compairX) && (moveToY == compairY)) && ((testSquare.getColor() != plyColor) || (testSquare.getType() == "none"))){
					y = moveToY;
					x = moveToX;
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
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
						return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = moveToY;
							x = moveToX;
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[moveFromY][moveFromX - displace];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = moveToY;
							x = moveToX;
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
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = moveToY;
							x = moveToX;
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[moveFromY + displace][moveFromX];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = moveToY;
							x = moveToX;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public ArrayList<ArrayList> attackPathCheck() {
		// TODO Auto-generated method stub
		attackPath = new ArrayList<ArrayList>();
		if(this.getColor() == "white") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				//System.out.println(attackPath);
				if(ChessBoard.board[i][x].getType() != "none") {
					break;
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getType() != "none") {
					break;
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getType() != "none") {
					break;
				}
			}	
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getType() != "none") {
					break;
				}
			}
			//북서
			int j = x - 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j--;
			}
			//북동
			j = x + 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j++;
			}
			//남서
			j = x - 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j--;
			}
			//남동
			j = x + 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j++;
			}
		}else if(this.getColor() == "black") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				//System.out.println(attackPath);
				if(ChessBoard.board[i][x].getType() != "none") {
					break;
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getType() != "none") {
					break;
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getType() != "none") {
					break;
				}
			}	
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getType() != "none") {
					break;
				}
			}
			//북서
			int j = x - 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j--;
			}
			//북동
			j = x + 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j++;
			}
			//남서
			j = x - 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j--;
			}
			//남동
			j = x + 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getType() != "none") {
					break;
				}
				j++;
			}
		}
		return attackPath;
	}

}
