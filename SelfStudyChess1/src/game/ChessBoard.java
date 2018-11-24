package game;


import java.util.Scanner;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;


public class ChessBoard {
	static final char[] SIDE_CHAR = {'A','B','C','D','E','F','G','H'};
	public static Square[][] board = new Square[8][8];
	
	public static boolean stalemate(int runNum) {
		int[] x = {0,0}, y = {0,0};
		
		for(int i = 0; i <8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getType() != "blank") {
					x[0] = i;
					x[1] = j;
					/*switch(board[i][j].getType()) {
					case "pawn": 
						if(board[i][j].getSymbol()=="WPa") {
							
						}else {
							
						}
						break;
					case "rook": break;
					case "knight": break;
					case "bishop": break;
					case "queen": break;
					case "king": break;
					}*/
					for(int k = 0; k < 8; k++) {
						for(int l = 0; l < 8; l++) {
							y[0] = k;
							y[1] = l;
							if(runNum == 1) {
								if(checkmate(runNum,x,y) == 0)
									return false;
							}
							else if(runNum == 2) {
								if(checkmate(runNum,x,y ) == 0)
									return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public static int checkmate(int runNum, int[] wK, int[] bK) {
		// 0 => �끂留�, 1 => 泥댄겕, 2 => 泥댄겕硫붿씠�듃
		int[] k = {0,0};
		int[] t = {0,0};
		int ofXmin = 0, ofXmax = 0, ofYmin = 0 , ofYmax = 0;
		
		if(runNum == 1)
			k = wK;
		else
			k = bK;
		
		if(board[k[1]][k[0]].isCheck()) {

			if(k[0] == 0 && k[1] == 0) {
				ofXmin = 1;
				ofYmax = -1;
			}else if(k[0] == 0 && k[1] == 7) {
				ofXmax = -1;
				ofYmax = 1;
			}else if(k[0] == 7 && k[1] == 0) {
				ofXmin = 1;
				ofYmax = -1;
			}else if(k[0] == 7 && k[1] == 7) {
				ofXmax = -1;
				ofYmax = -1;
			}else {
				if(k[0] == 0) {
					ofYmin = 1;
				}else if(k[0] == 7) {
					ofYmax = -1;
				}
				if(k[1] == 0) {
					ofXmin = 1;
				}else if(k[1] == 7) {
					ofXmax = -1;
				}
			}
			
			for(int i = k[0] -1 + ofYmin; i <= k[0] + 1 + ofYmax; i++) {
				for(int j = k[1] -1 + ofXmin; j <= k[1] + 1 + ofXmax; j++) {
					System.out.println("["+i +", " +j +"]");
					t[0] = j;
					t[1] = i;
					//if((!(check(runNum, t, wK, bK)) && board[i][j].getType() == "blank")) return 1;		
				}
			}
			return 2;
		}
		return 0;
	}
	
	private static void check(int runNum, int[] moveTo, int[] wk, int[] bk) {
		
		int[] wKXY = wk;
		int[] bKXY = bk;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square toSquare = board[i][j];
				int[] tXY = {j, i};
				if(runNum == 1) {
					if(toSquare.getColor() == "white" && toSquare.getType() != "king") {
						if(toSquare.checkMove(tXY, bKXY, "white", false)) {
							board[bKXY[1]][bKXY[0]].setCheck(true);
							return;
						}
					}
				}else {
					if(toSquare.getColor() == "black" && toSquare.getType() != "king") {
						if(toSquare.checkMove(tXY, wKXY, "black", false)) {
							board[wKXY[1]][wKXY[0]].setCheck(true);
							return;
						}
					}
				}
			}
		}
		if(runNum == 1) {
			board[bKXY[1]][bKXY[0]].setCheck(false);
		}else {
			board[wKXY[1]][wKXY[0]].setCheck(false);
		}
	}
	

	private static void update(int[] origLoc, int[] newLoc){
		board[newLoc[1]][newLoc[0]] = board[origLoc[1]][origLoc[0]];
		board[origLoc[1]][origLoc[0]] = new BlankSpace();
	}
	
	//珥덇린 留� 諛곗튂
	private static void setup() {
		
		board[0][0] = new Rook("black");
		board[0][1] = new Knight("black");
		board[0][2] = new Bishop("black");
		board[0][3] = new Queen("black");
		board[0][4] = new King("black");
		board[0][5] = new Bishop("black");
		board[0][6] = new Knight("black");
		board[0][7] = new Rook("black");		
		
		//釉붾옓 �룿 諛곗튂
		for(int i = 0; i< 8; i++) {
			//board[1][i] = new Pawn("black");
			board[1][i] = new BlankSpace();
		}
				
		//鍮덉뭏諛곗튂
		for(int i = 2; i <6; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j] = new BlankSpace();
			}
		}
		
		//�솕�씠�듃 �룿 諛곗튂
		for(int i = 0; i <8; i++) {
			//board[6][i] = new Pawn("white");
			board[6][i] = new BlankSpace();
		}
		
		board[7][0] = new Rook("white");	
		board[7][1] = new Knight("white");
		board[7][2] = new Bishop("white");
		board[7][3] = new Queen("white");
		board[7][4] = new King("white");
		board[7][5] = new Bishop("white");
		board[7][6] = new Knight("white");
		board[7][7] = new Rook("white");
		
		
		
	}
	//�뙋洹몃━湲�
	public static void draw() {
		System.out.print("\n   ");
		//�쐞移� 臾몄옄
		for(char i: SIDE_CHAR){ 
			System.out.print("  " + i + "  ");
		}
		System.out.print("\n   ");
		
		for(int i = 0; i < 8; i++){
			System.out.print(" --- ");
		}
		
		System.out.print("\n");
		for(int i = 0; i < 8; i++){ 
			System.out.print(" " + (8 - i) + " "); 
			//�떖蹂쇱쓣 諛쏆븘�꽌 諛곗튂
			for(Square j: board[i]){
				System.out.print("|" + j.getSymbol() + "|");
			}
			//�쐞移� 踰덊샇
			System.out.print(" " + (8 - i) + " "); 
			
			System.out.print("\n   ");
			
			for(int j = 0; j < 8; j++){
				System.out.print(" --- ");
			}
			System.out.print("\n");
		}
		//�쐞移� 臾몄옄
		System.out.print("   ");
		for(char i: SIDE_CHAR){ 
			System.out.print("  " + i + "  ");
		}
		System.out.print("\n\n");
	}
	
	private static String getName(int playerNum, String prevName){ 
		String name;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("Player " + playerNum + " �씠由꾩쓣 �엯�젰�빐 二쇱꽭�슂.\n>> ");
			name = sc.nextLine().trim();
			
			//�씠由꾩씠 鍮꾩뼱�엳吏� �븡嫄곕굹 鍮덉뭏�씠�굹 �뀦�씠 �뾾嫄곕굹 �쟾�뿉 �엯�젰�븳 �씠由꾧낵 媛숈� �븡�쑝硫�
			if(!name.isEmpty() && !(name.contains(" ") || name.contains("\t")) && !name.equals(prevName)) 
				break;
			else
				System.out.println("�떎瑜� �씠由꾩쓣 �엯�젰�빐 二쇱꽭�슂.");
		}
		return name;
	}
	
	public static void main(String[] args) {
		
		System.out.println("=====> CHESS <====="); //title
		
		String ply1Name = getName(1, null);
		String ply2Name = getName(2, ply1Name);
		
		Player whitePly = new Player(ply1Name, "white");
		Player blackPly = new Player(ply2Name, "black");
		
		setup(); //珥덇린 留� 諛곗튂
		
		while(true){
			for(int runNum = 1; runNum <= 2; runNum++){ 
				draw(); //�뙋 洹몃━湲�
				
				int move[][] = new int[2][2];
				int wOldcm = 0, bOldcm = 0;
				
				while(true){
					
					int cm = 0;//checkmate(runNum);
					if(runNum == 1){ //泥ロ꽩
						if (cm == 1){
							System.out.println("white Check!");
							wOldcm = cm;
						}
						else if (cm == 2){
							System.out.println("white Check Mate!");
							return ;
						}
						if(wOldcm == 1) {
							int[][] cntmove = whitePly.getMove();
							/*if(checkmate(runNum) == 2 || checkmate(runNum)== 1) {
								System.out.println("�솗�씠 �뿬�쟾�엳 �쐞�뿕�빀�땲�떎!!");
								runNum --;
								continue;
							}
							else move = cntmove;*/
						}else move = whitePly.getMove();
						
						/*
						if(stalemate(runNum)) {
							System.out.println("Stale Mate!");
							return;
						}*/
						//1else move = whitePly.getMove();
					}
					else{ //�몢踰덉㎏ �꽩
						if (cm == 1){
							System.out.println("black Check!");
							bOldcm = cm;
						}else if (cm == 2){
							System.out.println("black Check Mate!");
							return ;
						} 
						System.out.println(cm);
						if(bOldcm == 1) {
							int[][] cntmove = blackPly.getMove();
							/*if(checkmate(runNum) == 2 || checkmate(runNum) == 1) {
								System.out.println("�솗�씠 �뿬�쟾�엳 �쐞�뿕�빀�땲�떎!!");
								runNum ++;
								continue;
							}
							else move = cntmove;*/
						}else move = whitePly.getMove();
						/*
						if(stalemate(runNum)) {
							System.out.println("Stale Mate!");
							return;
						}*/
						//else move = blackPly.getMove();
					}
					if(move[0][0] == -1){ //�엯�젰�씠 留욎� �븡�쑝硫� �떎�떆
						System.out.println("�옒紐삳맂 �쐞移섏엯�땲�떎. �떎�떆 �엯�젰�븯�꽭�슂.");
						continue;
					}
					
					int[] moveFrom = move[0];
					int[] moveTo = move[1];
					Square fromSquare = board[moveFrom[1]][moveFrom[0]];
					
					boolean checkValue;
					if(runNum == 1){
						checkValue = fromSquare.checkMove(moveFrom, moveTo, "white", false); //�씠�룞 寃��궗
					}
					else{
						checkValue = fromSquare.checkMove(moveFrom, moveTo, "black", false);
					}
					
					if(checkValue){
						update(moveFrom, moveTo);
						break;
					}
					System.out.println("�옒紐삳맂 �씠�룞�엯�땲�떎. �떎�떆 ��吏곸뿬二쇱꽭�슂.");
				}
			}
		}
	}
}
