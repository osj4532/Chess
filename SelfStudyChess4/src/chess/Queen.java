package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Square{
	private int x, y = 0;
	private ArrayList<ArrayList<Integer>> attackPath;

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
	public boolean moveCheck(int[] from, int[] to, String plyColor, boolean afterMoveCheck) {
		
		int fromX = from[0];
		int fromY = from[1];
		int toX = to[0];
		int toY = to[1];
		
		String direction;
		String type; 
		
		if(afterMoveCheck){
			return false;
		}
		
		if(toY == fromY){ 
			if(toX > fromX){
				direction = "rite";
				type = "straight";
			}
			else{
				direction = "left";
				type = "straight";
			}
		}
		
		else if(toX == fromX){
			if(toY > fromY){
				direction = "bot";
				type = "straight";
			}
			else{
				direction = "top";
				type = "straight";
			}
		}else if(toX > fromX){
			if(toY < fromY){
				direction = "topRite";
				type = "diagonal";
			}
			else{
				direction = "botRite";
				type = "diagonal";
			}
		}
		else if(toX < fromX){
			if(toY < fromY){
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
			int moveDistance = Math.abs(toX - fromX);
			int compairX = 9, compairY = 9;
			
			for(int diagMoveAway = 1; diagMoveAway <= moveDistance; diagMoveAway++){
			
				if(direction == "topRite"){
					testSquare = ChessBoard.board[fromY - diagMoveAway][fromX + diagMoveAway];
					if(diagMoveAway == moveDistance) {
						compairX = fromX + diagMoveAway;
						compairY = fromY - diagMoveAway;
					}
				}
				else if(direction == "botRite"){
					testSquare = ChessBoard.board[fromY + diagMoveAway][fromX + diagMoveAway];
					if(diagMoveAway == moveDistance) {
						compairX = fromX + diagMoveAway;
						compairY = fromY + diagMoveAway;
					}
				}
				else if(direction == "topLeft"){
					testSquare = ChessBoard.board[fromY - diagMoveAway][fromX - diagMoveAway];
					if(diagMoveAway == moveDistance) {
						compairX = fromX - diagMoveAway;
						compairY = fromY - diagMoveAway;
					}
				}
				else{ //botLeft
					testSquare = ChessBoard.board[fromY + diagMoveAway][fromX - diagMoveAway];
					if(diagMoveAway == moveDistance) {
						compairX = fromX - diagMoveAway;
						compairY = fromY + diagMoveAway;
					}
				}
				
				if((testSquare.getType() != "none") && (diagMoveAway != moveDistance)){
					return false;
				}
				else if(((toX == compairX) && (toY == compairY)) && ((testSquare.getColor() != plyColor) || (testSquare.getType() == "none"))){
					y = toY;
					x = toX;
					return true;
				}
			}
		}
		else{ //직선
			if((direction == "rite") || (direction == "left")){
				int displaceMax = Math.abs(toX - fromX); 
		
				for(int displace = 1; displace <= displaceMax; displace++){
					if(direction == "rite"){
						testSquare = ChessBoard.board[fromY][fromX + displace];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
						return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = toY;
							x = toX;
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[fromY][fromX - displace];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = toY;
							x = toX;
							return true;
						}
					}
				}
			}
			else{ 
				int displaceMax = Math.abs(toY - fromY); 
				
				for(int displace = 1; displace <= displaceMax; displace++){ 	
				
					if(direction == "top"){
						testSquare = ChessBoard.board[fromY - displace][fromX];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = toY;
							x = toX;
							return true;
						}
					}
					else{
						testSquare = ChessBoard.board[fromY + displace][fromX];
					
						if((testSquare.getType() != "none") && (displace != displaceMax)){
							return false;
						}
						else if((displace == displaceMax) && ((testSquare.getType() == "none") || (testSquare.getColor() != plyColor))){
							y = toY;
							x = toX;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public ArrayList<ArrayList<Integer>> attackPathCheck() {
		// TODO Auto-generated method stub
		attackPath = new ArrayList<ArrayList<Integer>>();
		if(this.getColor() == "white") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() != "BKi") {
					if(ChessBoard.board[i][x].getType() != "none") {
						break;
					}
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() != "BKi") {
					if(ChessBoard.board[i][x].getType() != "none") {
						break;
					}
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() != "BKi") {
					if(ChessBoard.board[y][i].getType() != "none") {
						break;
					}
				}
			}	
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() != "BKi") {
					if(ChessBoard.board[y][i].getType() != "none") {
						break;
					}
				}
			}
			//북서
			int j = x - 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "BKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j--;
			}
			//북동
			j = x + 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "BKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j++;
			}
			//남서
			j = x - 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "BKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j--;
			}
			//남동
			j = x + 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "BKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j++;
			}
		}else if(this.getColor() == "black") {
			//북
			for(int i = y - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() != "WKi") {
					if(ChessBoard.board[i][x].getType() != "none") {
						break;
					}
				}
			}
			//남				
			for(int i = y + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, x));
				attackPath.add(tmp);
				if(ChessBoard.board[i][x].getSymbol() != "WKi") {
					if(ChessBoard.board[i][x].getType() != "none") {
						break;
					}
				}
			}
			//서
			for(int i = x - 1; i >= 0; i--) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() != "WKi") {
					if(ChessBoard.board[y][i].getType() != "none") {
						break;
					}
				}
			}	
			//동
			for(int i = x + 1; i <= 7; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(y, i));
				attackPath.add(tmp);
				if(ChessBoard.board[y][i].getSymbol() != "WKi") {
					if(ChessBoard.board[y][i].getType() != "none") {
						break;
					}
				}
			}
			//북서
			int j = x - 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "WKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j--;
			}
			//북동
			j = x + 1;
			for(int i = y - 1; i >= 0; i--) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "WKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j++;
			}
			//남서
			j = x - 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j<0) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "WKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j--;
			}
			//남동
			j = x + 1;
			for(int i = y + 1; i <= 7; i++) {
				if(j>7) break;
				ArrayList<Integer> tmp = new ArrayList<Integer>(Arrays.asList(i, j));
				attackPath.add(tmp);
				if(ChessBoard.board[i][j].getSymbol() != "WKi") {
					if(ChessBoard.board[i][j].getType() != "none") {
						break;
					}
				}
				j++;
			}
		}
		return attackPath;
	}

	@Override
	public ArrayList<ArrayList<Integer>> movePathCheck() {
		// TODO Auto-generated method stub
		return null;
	}

}
