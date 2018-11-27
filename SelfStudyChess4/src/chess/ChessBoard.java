package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	
	private static ArrayList<Integer> wKYX = null;
	private static ArrayList<Integer> bKYX = null;
	private static ArrayList<ArrayList<Integer>> allWAttackPath = null;
	private static ArrayList<ArrayList<Integer>> allBAttackPath = null;
	static final char[] SIDE_CHAR = {'A','B','C','D','E','F','G','H'};
	public static Square[][] board = new Square[8][8];
		
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
	//초기 말 배치
	private static void setup() {
			
			//board[0][0] = new Rook("black", 0 ,0);
			//board[0][1] = new Knight("black");
			//board[0][2] = new Bishop("black");
			//board[0][3] = new Queen("black");
			board[0][4] = new King("black", 0, 4);
			//board[0][5] = new Bishop("black");
			//board[0][6] = new Knight("black");
			//board[0][7] = new Rook("black", 0, 7);
			board[0][0] = new BlankSpace();
			board[0][1] = new BlankSpace();
			board[0][2] = new BlankSpace();
			board[0][3] = new BlankSpace();
			board[0][5] = new BlankSpace();
			board[0][6] = new BlankSpace();
			board[0][7] = new BlankSpace();
			
			//블랙 폰 배치
			for(int i = 0; i< 8; i++) {
				board[1][i] = new Pawn("black",1,i); //BlankSpace();
			}
			board[1][7] = new BlankSpace();
			//빈칸배치
			for(int i = 2; i <6; i++) {
				for(int j = 0; j<8; j++) {
					board[i][j] = new BlankSpace();
				}
			}
			//board[2][3] = new King("white", 2, 3);
			//board[1][4] = new Pawn("white", 1, 4);
			//화이트 폰 배치
			for(int i = 0; i <8; i++) {
				board[6][i] = new BlankSpace();// Pawn("white");
			}
			
			//board[7][0] = new BlankSpace();	
			//board[7][1] = new BlankSpace();
			//board[7][2] = new BlankSpace();
			//board[7][3] = new BlankSpace();
			//board[7][4] = new BlankSpace();
			//board[7][5] = new BlankSpace();
			//board[7][6] = new BlankSpace();
			//board[7][7] = new BlankSpace();
			board[7][0] = new Rook("white", 7, 0);	
			board[7][1] = new Knight("white", 7, 1);
			board[7][2] = new Bishop("white", 7, 2);
			board[7][3] = new Queen("white", 7, 3);
			board[7][4] = new King("white", 7, 4);
			board[7][5] = new Bishop("white", 7, 5);
			board[7][6] = new Knight("white",7, 6);
			board[7][7] = new Rook("white", 7, 7);
			
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
			for(Square j : board[i]) {
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
	//판 업데이트
	private static void update(int[] origLoc, int[] newLoc){
		board[newLoc[1]][newLoc[0]] = board[origLoc[1]][origLoc[0]];
		board[origLoc[1]][origLoc[0]] = new BlankSpace();
	}
	//공격 경로 갱신
	private static void updateAttack() {
		
		allWAttackPath = new ArrayList<ArrayList<Integer>>();
		allBAttackPath = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getType() != "king") {
					if(board[i][j].getColor() == "white") {
						allWAttackPath.addAll(board[i][j].attackPathCheck());
					}
					else if(board[i][j].getColor() == "black") {
						allBAttackPath.addAll(board[i][j].attackPathCheck());
					}
				}
			}
		}
		
		
		System.out.println(allWAttackPath);
		System.out.println();
		System.out.println(allBAttackPath);
	}
	//체크
	private static void check() {
	
		for(int i = 0; i < allWAttackPath.size(); i++) {
			if(allWAttackPath.get(i).equals(bKYX) ) {
				board[bKYX.get(0)][bKYX.get(1)].setCheck(true);
				return ;
			}else {
				board[bKYX.get(0)][bKYX.get(1)].setCheck(false);
			}
		}

		for(int i = 0; i < allBAttackPath.size(); i++) {
			if(allBAttackPath.get(i).equals(wKYX) ) {
				board[wKYX.get(0)][wKYX.get(1)].setCheck(true);
				return ;
			}else {
				board[wKYX.get(0)][wKYX.get(1)].setCheck(false);
			}
			
		}
		
		
	}
	//체크 메이트
	private static int checkmate() {
		// 0 : 체크메이트 없음, 1 : 흰색 체크메이트, 2 : 검정색 체크메이트
		// 다른 말 이동 해서 왕을 보호하는 논리 추가하기
		ArrayList<ArrayList<Integer>> all;
		
		if(board[wKYX.get(0)][wKYX.get(1)].isCheck()) {
			all = new ArrayList<ArrayList<Integer>>(allBAttackPath);
			all.addAll(board[bKYX.get(0)][bKYX.get(1)].attackPathCheck());
			if(all.containsAll(board[wKYX.get(0)][wKYX.get(1)].movePathCheck()))
				return 1;
		}else if(board[bKYX.get(0)][bKYX.get(1)].isCheck()) {
			all = new ArrayList<ArrayList<Integer>>(allWAttackPath);
			all.addAll(board[wKYX.get(0)][wKYX.get(1)].attackPathCheck());
			if(all.containsAll(board[bKYX.get(0)][bKYX.get(1)].movePathCheck()))
				return 2;
		}
		
		return 0;
	}
	//스테일메이트
	private static boolean stalemate() {
		ArrayList<ArrayList<Integer>> all;
		if(allWAttackPath.isEmpty()) {
			all = new ArrayList<ArrayList<Integer>>(allBAttackPath);
			all.addAll(board[bKYX.get(0)][bKYX.get(1)].attackPathCheck());
			if(!board[wKYX.get(0)][wKYX.get(1)].isCheck()) {
				if(all.containsAll(board[wKYX.get(0)][wKYX.get(1)].attackPathCheck()))
					return true;
			}
		}
		if(allBAttackPath.isEmpty()) {
			all = new ArrayList<ArrayList<Integer>>(allWAttackPath);
			ArrayList<ArrayList<Integer>>tmp = new ArrayList<ArrayList<Integer>>(board[wKYX.get(0)][wKYX.get(1)].attackPathCheck());
			all.addAll(tmp);
			if(!board[bKYX.get(0)][bKYX.get(1)].isCheck()) {
				if(all.containsAll(board[bKYX.get(0)][bKYX.get(1)].attackPathCheck()))
					return true;
			}
		}
		return false;
	}
	//메인
	public static void main(String[] args) {			
		System.out.println("=====> CHESS <====="); //title
		
		String ply1Name = getName(1, null);
		String ply2Name = getName(2, ply1Name);
		
		Player whitePly = new Player(ply1Name, "white");
		Player blackPly = new Player(ply2Name, "black");
		
		//초기 왕위치
		wKYX = new ArrayList<Integer>(Arrays.asList(7, 4));
		bKYX = new ArrayList<Integer>(Arrays.asList(0, 4));
		
		setup(); //초기 보드 세팅
		
		//체스 루프 시작
		while(true){
			for(int runNum = 1; runNum <= 2; runNum++){ //run for each player
				draw(); //보드 출력
				
				int move[][] = new int[2][2];
				
				while(true){
					if(runNum == 1){ //흰색 턴
						if(board[wKYX.get(0)][wKYX.get(1)].isCheck())
							System.out.println("white check");
						move = whitePly.getMove();
					}
					else{ //검정색 턴
						if(board[bKYX.get(0)][bKYX.get(1)].isCheck())
							System.out.println("black check");
						move = blackPly.getMove();
					}
				
					if(move[0][0] == -1){ //잘못 입력하면 다시
						System.out.println("잘못된 위치 입니다. 다시 고르세요.");
						runNum--;
						continue;
					}
					
					int[] moveFrom = move[0];
					int[] moveTo = move[1];
					Square fromSquare = board[moveFrom[1]][moveFrom[0]];
					
					boolean checkValue;
					
					if(runNum == 1){
						checkValue = fromSquare.moveCheck(moveFrom, moveTo, "white", false);
					}else {
						checkValue = fromSquare.moveCheck(moveFrom, moveTo, "black", false);
					}
					
					if(checkValue){
						update(moveFrom, moveTo);
						for(int i = 0; i < 8; i++) {
							for(int j = 0; j < 8; j++) {
								if(board[i][j].getSymbol() == "WKi") {
									wKYX = new ArrayList<Integer>(Arrays.asList(i, j));
								}else if(board[i][j].getSymbol() == "BKi") {
									bKYX = new ArrayList<Integer>(Arrays.asList(i, j));
								}
							}
						}
						updateAttack();
						check();
						if(stalemate()) {
							draw(); //보드 출력
							System.out.println("StaleMate!");
							return;
						}
						if(checkmate() == 1) {
							draw(); //보드 출력
							System.out.println("White CheckMate!");
							return;
						}else if(checkmate() == 2) {
							draw(); //보드 출력
							System.out.println("Black CheckMate!");
							return;
						}
						System.out.println(board[bKYX.get(0)][bKYX.get(1)].movePathCheck());
						break;
					}
					System.out.println("잘못된 이동입니다. 다시 이동하세요.");
				}
			}
		}
	}
}
