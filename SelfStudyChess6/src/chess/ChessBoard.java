package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessBoard {
	
	private static ArrayList<Integer> wKYX = null;
	private static ArrayList<Integer> bKYX = null;
	private static ArrayList<ArrayList<Integer>> allWAttackPath = null;
	private static ArrayList<ArrayList<Integer>> allBAttackPath = null;
	private static ArrayList<ArrayList<Integer>> a2kPath = new ArrayList<ArrayList<Integer>>();
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
			board[1][5] = new BlankSpace();
			board[0][6] = new Bishop("black",0,6);
			board[0][3] = new Pawn("black",0,3);
			board[0][5] = new Pawn("black",0,5);
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
			//board[7][7] = new Rook("white", 7, 7);
			board[7][7] = new Queen("white", 7, 7);
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
		//System.out.println();
		System.out.println(allBAttackPath);
	}
	//체크
	private static void check(int runNum) {
		int count = 0;
		int sy, sx;
		int by = -1, bx = -1;
		int tmp;
		int ay = -1, ax = -1;	//공격자 위치
		
		ArrayList<ArrayList<Integer>> onePieceAttackPath = null;
		
		
		if(runNum == 1) {
			sy = bKYX.get(0);
			sx = bKYX.get(1);
			// 왕을 위협하는 말이 1개일때 그 말의 모든 공격 경로를 담는다.
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getColor() == "white") {
						if(board[i][j].attackPathCheck().contains(bKYX)) {
							count++;
							if(count > 1) break;
							onePieceAttackPath = board[i][j].attackPathCheck();
							ay = i;
							ax = j;
							by = i;
							bx = j;
						}
					}
				}
			}
			
			// 왕과 공격 말 사이의 값을 리스트에 담는다.
			if(count == 1) {
				if(sy > by) {
					tmp = sy;
					sy = by;
					by = tmp;
				}
				if(sx > bx) {
					tmp = sx;
					sx = bx;
					bx = tmp;
				}
				if(sy == by) by++;
				if(sx == bx) bx++;
				// 두 좌표 사이에 값
				for(int i = sy; i < by; i++) {
					for(int j = sx; j < bx; j++) {
						ArrayList<Integer> test = new ArrayList<Integer>(Arrays.asList(i,j));
						if(onePieceAttackPath.contains(test)){
							a2kPath.add(test);
						}
					}
				}
				// 공격자 위치 넣어주고, 왕위치 삭제
				a2kPath.add(new ArrayList<Integer>(Arrays.asList(ay,ax)));
				a2kPath.remove(new ArrayList<Integer>(Arrays.asList(bKYX.get(0),bKYX.get(1))));
				System.out.println("asd"+a2kPath);
				
			}
			
			//카운트가 0이상이면 체크 트루 아니면 체크 펄스
			if(count > 0) board[bKYX.get(0)][bKYX.get(1)].setCheck(true);
			else board[bKYX.get(0)][bKYX.get(1)].setCheck(false);
			
			
			
		}else {
			sy = wKYX.get(0);
			sx = wKYX.get(1);
			// 왕을 위협하는 말이 1개일때 그 말의 모든 공격 경로를 담는다.
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getColor() == "black") {
						if(board[i][j].attackPathCheck().contains(wKYX)) {
							count++;
							if(count > 1) break;
							onePieceAttackPath = board[i][j].attackPathCheck();
							ay = i;
							ax = j;
							by = i;
							bx = j;
						}
					}
				}
			}
			
			// 왕과 공격 말 사이의 값을 리스트에 담는다.
			if(count == 1) {
				if(sy > by) {
					tmp = sy;
					sy = by;
					by = tmp;
				}
				if(sx > bx) {
					tmp = sx;
					sx = bx;
					bx = tmp;
				}
				if(sy == by) by++;
				if(sx == bx) bx++;
				
				for(int i = sy; i < by; i++) {
					for(int j = sx; j < bx; j++) {
						ArrayList<Integer> test = new ArrayList<Integer>(Arrays.asList(i,j));
						if(onePieceAttackPath.contains(test)){
							a2kPath.add(test);
						}
					}
				}
				a2kPath.add(new ArrayList<Integer>(Arrays.asList(ay,ax)));
				a2kPath.remove(new ArrayList<Integer>(Arrays.asList(wKYX.get(0),wKYX.get(1))));
				System.out.println("asd"+a2kPath);
			}
			
			
			if(count > 0) board[wKYX.get(0)][wKYX.get(1)].setCheck(true);
			else board[wKYX.get(0)][wKYX.get(1)].setCheck(false);
		}
	}
	//체크시 막을 말이 있는지 검사
	private static boolean canBlock(int runNum) {
		if(runNum == 1) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getColor() == "white") {
						for(int k = 0; k < a2kPath.size(); k++)
						if(board[i][j].movePathCheck().contains(a2kPath.get(k))) {
							return true;
						}
					}
				}
			}
		
			return false;
		}else {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getColor() == "black") {
						for(int k = 0; k < a2kPath.size(); k++)
						if(board[i][j].movePathCheck().contains(a2kPath.get(k))) {
							return true;
						}
					}
				}
			}
		
			return false;
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
	//움직이면 왕위험한지 검사
	private static boolean moveDanger(int runNum, int[] from, int[] to, Square[][] board) {
		
		return false;
	}
	//메인
	public static void main(String[] args) {			
		System.out.println("=====> CHESS <====="); //title
		
		boolean isCheck = false;
		
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
						isCheck = board[wKYX.get(0)][wKYX.get(1)].isCheck(); 
						if(isCheck)
							System.out.println("white check");
						
						move = whitePly.getMove();
					}
					else{ //검정색 턴
						isCheck = board[bKYX.get(0)][bKYX.get(1)].isCheck();
						if(isCheck)
							System.out.println("black check");
						move = blackPly.getMove();
					}
				
					// if check => -1(말 이동경로 != 상대 모든 공격경로)
					if(isCheck) {
						
					}
					
					
					
					if(move[0][0] == -1){ //잘못 입력하면 다시
						System.out.println("잘못된 위치 입니다. 다시 고르세요.");
						runNum--;
						continue;
					}
					
					int[] moveFrom = move[0];
					int[] moveTo = move[1];
					Square fromSquare = board[moveFrom[1]][moveFrom[0]];
					
					
					// 움직이면 왕위험
					boolean md = moveDanger(runNum, moveFrom, moveTo, board);
					
					boolean checkValue;
					
					if(runNum == 1){
						checkValue = fromSquare.moveCheck(moveFrom, moveTo, "white", md);
					}else {
						checkValue = fromSquare.moveCheck(moveFrom, moveTo, "black", md);
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
						check(runNum);
						if(stalemate()) {
							draw(); //보드 출력
							System.out.println("StaleMate!");
							return;
						}
						if(checkmate() == 1 && !canBlock(1)) {
							draw(); //보드 출력
							System.out.println("White CheckMate!");
							return;
						}else if(checkmate() == 2 && !canBlock(2)) {
							draw(); //보드 출력
							System.out.println("Black CheckMate!");
							return;
						}
						break;
					}
					System.out.println("잘못된 이동입니다. 다시 이동하세요.");
				}
			}
		}
	}
}
