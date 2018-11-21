package game;


import java.util.Scanner;

import pieces.King;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;


public class ChessBoard {
	static final char[] SIDE_CHAR = {'A','B','C','D','E','F','G','H'};
	public static Square[][] board = new Square[8][8];
	private static String cas_w = "";
	private static String cas_b = "";
	
	
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
								if(checkmate(runNum) == 0)
									return false;
							}
							else if(runNum == 2) {
								if(checkmate(runNum) == 0)
									return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public static int checkmate(int runNum) {
		// 0 => 노말, 1 => 체크, 2 => 체크메이트, 3 => 스테일메이트
		int[] k = {0,0};
		int[] t = {0,0};
		int ofXmin = 0, ofXmax = 0, ofYmin = 0 , ofYmax = 0;
		// 흰색킹 체크
		if(runNum == 1) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].color == "white" && board[i][j].type == "king") {
						k[0] = i;
						k[1] = j;
						break;
					}
				}
			}
		}
		// 검정색킹 체크
		else{
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].color == "black" && board[i][j].type == "king") {
						k[0] = i;
						k[1] = j;
						break;
					}
				}
			}
		}// 원래 왕위치
		
		//if(check(runNum,k[0],k[1])) {

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
				if(!(check(runNum,i,j)) && board[i][j].type == "blank" ) return 1;		
				if((check(runNum,i,j)) && board[i][j].type == "blank" ) return 0;
			}
		}
		return 2;
		//}
		//else return 0;
	}
	
	public static boolean check(int runNum, int kx, int ky) {
		Square threat = null;
		int[] k = {kx,ky};
		int[] t = {0,0};
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				threat = board[i][j];
				if(runNum == 1 && threat.getColor() == "black") {
					t[0] = i;
					t[1] = j;
					if(threat.checkMove(t, k, "black", true)) {
						return true;
					}
				}
				if(runNum == 2 && threat.getColor() == "white") {
					t[0] = i;
					t[1] = j;
					if(threat.checkMove(t, k, "white", true)) {
						return true;
					}
				}
			}
		}
		return false;
		
	}

	private static void update(int[] origLoc, int[] newLoc){
		board[newLoc[1]][newLoc[0]] = board[origLoc[1]][origLoc[0]];
		board[origLoc[1]][origLoc[0]] = new BlankSpace();
	}
	
	//초기 말 배치
	private static void setup() {
		
		/*board[0][0] = new Rook("black");
		board[0][1] = new Knight("black");
		board[0][2] = new Bishop("black");
		board[0][3] = new Queen("black");
		board[0][4] = new King("black");
		board[0][5] = new Bishop("black");
		board[0][6] = new Knight("black");
		board[0][7] = new Rook("black");*/
		
		for(int i = 0; i<8; i++) {
			if(i == 5) board[0][i] = new King("black");
			else board[0][i] = new BlankSpace();
		}
		
		
		
		//블랙 폰 배치
		for(int i = 0; i< 8; i++) {
			/*if(i == 3) board[1][i] = new Rook("white");
			else if(i == 6) board[1][i] = new BlankSpace();
			else board[1][i] = new Pawn("black");*/
			if(i == 5) board[1][i] = new Pawn("white");
			else board[1][i] = new BlankSpace();
		}
				
		//빈칸배치
		for(int i = 2; i <6; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j] = new BlankSpace();
			}
		}
		
		//화이트 폰 배치
		for(int i = 0; i <8; i++) {
			//board[6][i] = new Pawn("white");
			board[6][i] = new BlankSpace();
			board[7][i] = new BlankSpace();
		}
		
		/*board[7][0] = new Rook("white");	
		board[7][1] = new Knight("white");
		board[7][2] = new Bishop("white");
		board[7][3] = new Queen("white");
		board[7][4] = new King("white");
		board[7][5] = new Bishop("white");
		//board[7][6] = new Knight("white");
		board[7][6] = new BlankSpace();
		board[7][7] = new Rook("white");*/
		
		board[2][4] = new King("white");
		
	}
	//판그리기
	public static void draw() {
		System.out.print("\n   ");
		//위치 문자
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
			//심볼을 받아서 배치
			for(Square j: board[i]){
				System.out.print("|" + j.getSymbol() + "|");
			}
			//위치 번호
			System.out.print(" " + (8 - i) + " "); 
			
			System.out.print("\n   ");
			
			for(int j = 0; j < 8; j++){
				System.out.print(" --- ");
			}
			System.out.print("\n");
		}
		//위치 문자
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
			System.out.print("Player " + playerNum + " 이름을 입력해 주세요.\n>> ");
			name = sc.nextLine().trim();
			
			//이름이 비어있지 않거나 빈칸이나 텝이 없거나 전에 입력한 이름과 같지 않으면
			if(!name.isEmpty() && !(name.contains(" ") || name.contains("\t")) && !name.equals(prevName)) 
				break;
			else
				System.out.println("다른 이름을 입력해 주세요.");
		}
		return name;
	}
	//셋업 -> 킹위치 -> 룩위치(2개다) -> 캐슬링 호출
	public static boolean castling(int moveTo, int runNum) {
		Square temp;
		if(runNum == 1) {
			if (cas_w == "long") {
				for(int i = 1; i < 4; i++) {
					if(board[7][i].getColor() != "null") {
						return false;
					}
				}
				temp = board[7][4];
				board[7][4] = new BlankSpace();
				board[7][3] = board[7][0];
				board[7][2] = temp;
				board[7][0] = new BlankSpace();
				return true;
			}else if(cas_w == "short") {
				for(int i = 5; i < 7; i++) {
					if(board[7][i].getColor() != "null") {
						return false;
					}
				}
				temp = board[7][4];
				board[7][4] = new BlankSpace();
				board[7][5] = board[7][7];
				board[7][6] = temp;
				board[7][7] = new BlankSpace();
				return true;
			}else if(cas_w == "both") {
				if(moveTo == 2) cas_w = "long";
				else if(moveTo == 6) cas_w = "short";
				else return false;
				return castling(moveTo, runNum);
			}
		}
		else {
			if (cas_b == "long") {
				for(int i = 1; i < 4; i++) {
					if(board[0][i].getColor() != "null") {
						return false;
					}
				}
				temp = board[0][4];
				board[0][4] = new BlankSpace();
				board[0][3] = board[0][0];
				board[0][2] = temp;
				board[0][0] = new BlankSpace();
				return true;
			}else if(cas_b == "short") {
				System.out.println("a");
				for(int i = 5; i < 7; i++) {
					if(board[0][i].getColor() != "null") {
						return false;
					}
				}
				temp = board[0][4];
				board[0][4] = new BlankSpace();
				board[0][5] = board[0][7];
				board[0][6] = temp;
				board[0][7] = new BlankSpace();
				return true;
			}else if(cas_b == "both") {
				System.out.println("r");
				if(moveTo == 2) cas_b = "long";
				else if(moveTo == 6) cas_b = "short";
				else return false;
				return castling(moveTo, runNum);
			}
		}
		return false;
	}
	
	public static Square promotion(Square piece, int moveTo) {
		Square checkpro = piece;
		
		if(checkpro.color == "white") {
			if(checkpro.type == "pawn") {
				if(moveTo == 0) {
					checkpro = new Queen("white");
				}
			}
		}
		else if(checkpro.color == "black") {
			if(checkpro.type == "pawn") {
				if(moveTo == 7) {
					checkpro = new Queen("black");
				}
			}
		}
		return checkpro;
	}
	
	public static void main(String[] args) {
		
		int oldcm = 0;
		
		System.out.println("=====> CHESS <====="); //title
		
		String ply1Name = getName(1, null);
		String ply2Name = getName(2, ply1Name);
		
		Player whitePly = new Player(ply1Name, "white");
		Player blackPly = new Player(ply2Name, "black");
		
		setup(); //초기 말 배치
		
		while(true){
			for(int runNum = 1; runNum <= 2; runNum++){ 
				draw(); //판 그리기
				
				int move[][] = new int[2][2];
				
				
				while(true){
					if(board[7][4].getType() == "king") {
						King test_k = (King)board[7][4];
						if(test_k.getCastlingCheck() == false) {
							if(board[7][0].getType() == "rook") {
								Rook test_r = (Rook)board[7][0];
								if(test_r.getCastlingCheck() == false) {
									cas_w = "long";
								}
							}
							if(board[7][7].getType() == "rook") {
								Rook test_r = (Rook)board[7][7];
								if(test_r.getCastlingCheck() == false) {
									if(cas_w == "long") cas_w = "both";
									else cas_w = "short";
								}
							}
						}
					} //if end
					else cas_w = "";
					if(board[0][4].getType() == "king") {
						King test_k = (King)board[0][4];
						if(test_k.getCastlingCheck() == false) {
							if(board[0][0].getType() == "rook") {
								Rook test_r = (Rook)board[0][0];
								if(test_r.getCastlingCheck() == false) {
									cas_b = "long";
								}
							}
							if(board[0][7].getType() == "rook") {
								Rook test_r = (Rook)board[0][7];
								if(test_r.getCastlingCheck() == false) {
									if(cas_b == "long") cas_b = "both";
									else cas_b = "short";
								}
							}
						}
					} //if end
					else cas_b = "";
					
					int cm = checkmate(runNum);
					if(runNum == 1){ //첫턴
						if (cm == 1){
							System.out.println("white Check!");
							oldcm = cm;
						}
						else if (cm == 2){
							System.out.println("white Check Mate!");
							return ;
						}
						
						int[][] cntmove = whitePly.getMove();
						if(checkmate(runNum) == 2 || checkmate(runNum)== 1) {
							System.out.println("왕이 여전히 위험합니다!!");
							runNum --;
							continue;
						}
						else move = cntmove;
						
						
						if(stalemate(runNum)) {
							System.out.println("Stale Mate!");
							return;
						}
						//1else move = whitePly.getMove();
						
						for(int i = 0; i<8; i++) {
							for(int j = 0; j < 8; j++) {
								if(board[i][j].getColor() == "white" && board[i][j].getType() == "pawn") {
									Pawn ep = (Pawn)board[i][j];
									ep.setEnpassant(false);
									System.out.println(ep.getEnpassant());
								}
							}
						}
					}
					else{ //두번째 턴
						if (cm == 1){
							System.out.println("black Check!");
							oldcm = cm;
						}else if (cm == 2){
							System.out.println("black Check Mate!");
							return ;
						} 
						System.out.println(cm);
						
						int[][] cntmove = blackPly.getMove();
						if(checkmate(runNum) == 2 || checkmate(runNum) == 1) {
							System.out.println("왕이 여전히 위험합니다!!");
							runNum ++;
							continue;
						}
						else move = cntmove;
						
						if(stalemate(runNum)) {
							System.out.println("Stale Mate!");
							return;
						}
						//else move = blackPly.getMove();
						
						for(int i = 0; i<8; i++) {
							for(int j = 0; j < 8; j++) {
								if(board[i][j].getColor() == "black" && board[i][j].getType() == "pawn") {
									Pawn ep = (Pawn)board[i][j];
									ep.setEnpassant(false);
									System.out.println(ep.getEnpassant());
								}
							}
						}
					}
					if(move[0][0] == -1){ //입력이 맞지 않으면 다시
						System.out.println("잘못된 위치입니다. 다시 입력하세요.");
						continue;
					}
					
					int[] moveFrom = move[0];
					int[] moveTo = move[1];
					Square fromSquare = board[moveFrom[1]][moveFrom[0]];
					
					boolean checkValue;
					if(runNum == 1){
						checkValue = fromSquare.checkMove(moveFrom, moveTo, "white", false); //이동 검사
					}
					else{
						checkValue = fromSquare.checkMove(moveFrom, moveTo, "black", false);
					}
					if(fromSquare.getType() == "king" && moveTo[0] == 2 || moveTo[0] == 6) {
						if(cas_w != "") castling(moveTo[0], runNum);
						else if(cas_b != "") castling(moveTo[0], runNum);
					}
					if(checkValue){
						update(moveFrom, moveTo);
						board[moveTo[1]][moveTo[0]] = promotion(fromSquare, moveTo[1]);
						break;
					}
					System.out.println("잘못된 이동입니다. 다시 움직여주세요.");
				}
			}
		}
	}
}
