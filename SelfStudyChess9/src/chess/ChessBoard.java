package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChessBoard {
	
	public static ArrayList<Integer> wKYX = null;
	public static ArrayList<Integer> bKYX = null;
	private static boolean canBlock = false;
	public static ArrayList<ArrayList<Integer>> allWAttackPath = null;
	public static ArrayList<ArrayList<Integer>> allBAttackPath = null;
	public static ArrayList<ArrayList<Integer>> allW = null;
	public static ArrayList<ArrayList<Integer>> allB = null;
	private static int[] checker = {9, 9};
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
			
			board[0][0] = new Rook("black", 0 ,0);
			board[0][1] = new Knight("black",0,1);
			board[0][2] = new Bishop("black",0,2);
			board[0][3] = new Queen("black",0,3);
			board[0][4] = new King("black", 0, 4);
			board[0][5] = new Bishop("black",0,5);
			board[0][6] = new Knight("black",0,6);
			board[0][7] = new Rook("black", 0, 7);
			//board[0][0] = new BlankSpace();
			//board[0][1] = new BlankSpace();
			//board[0][2] = new BlankSpace();
			//board[0][3] = new BlankSpace();
			//board[0][4] = new BlankSpace();
			//board[0][5] = new BlankSpace();
			//board[0][6] = new BlankSpace();
			//board[0][7] = new BlankSpace();
			
			//블랙 폰 배치
			for(int i = 0; i< 8; i++) {
				board[1][i] = new Pawn("black",1,i);//BlankSpace(); 
			}
			//빈칸배치
			for(int i = 2; i <6; i++) {
				for(int j = 0; j<8; j++) {
					board[i][j] = new BlankSpace();
				}
			}
			//화이트 폰 배치
			for(int i = 0; i <8; i++) {
				board[6][i] = new Pawn("white", 6, i);//BlankSpace();// 
			}
			
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
		allW = new ArrayList<ArrayList<Integer>>();
		allB = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getType() != "king") {
					if(board[i][j].getColor() == "white") {
						allW.addAll(board[i][j].attackPathCheck());
						if(board[i][j].getType() == "pawn") allW.addAll(board[i][j].movePathCheck());
						allWAttackPath.addAll(board[i][j].attackPathCheck());
						for(int index = 0; index < board[i][j].attackPathCheck().size(); index++) {
							if(board[i][j].attackPathCheck().get(index).equals(bKYX)) {
								checker[0] = i;
								checker[1] = j;
							}
						}
					}
					else if(board[i][j].getColor() == "black") {
						allB.addAll(board[i][j].attackPathCheck());
						if(board[i][j].getType() == "pawn") allB.addAll(board[i][j].movePathCheck());
						allBAttackPath.addAll(board[i][j].attackPathCheck());
						for(int index = 0; index < board[i][j].attackPathCheck().size(); index++) {
							if(board[i][j].attackPathCheck().get(index).equals(wKYX)) {
								checker[0] = i;
								checker[1] = j;
							}
						}
					
					}
				}
			}
		}
	}
	//체크
	private static void check() {
		int count = 0;
		int checkerIndex = 0;
	
		ArrayList<ArrayList<Integer>> checkerRange = null;
		ArrayList<Integer> tmp = null;
		
		//Black
		for(int i = 0; i < allWAttackPath.size(); i++) {
			if(allWAttackPath.get(i).equals(bKYX) ) {
				count++;
			}
		}

		//공격자와 왕 사이 경로
		
		if(count == 1) {
			checkerRange = new ArrayList<ArrayList<Integer>>();
			checkerIndex = board[checker[0]][checker[1]].attackPathCheck().indexOf(bKYX) - 1;
			
			//공격자의 공격경로 계산
			while(true) {
				//좌우 바로 옆일 경우
				if(bKYX.get(0) == checker[0] && (Math.abs(bKYX.get(1) - checker[1]) == 1) ||
						bKYX.get(1) == checker[1] && (Math.abs(bKYX.get(0) - checker[0]) == 1)) break;
				//대각선 바로 옆일경우
				if((Math.abs(bKYX.get(1) - checker[1]) == 1) && (Math.abs(bKYX.get(1) - checker[1]) == 1)) break;
				
				//그외
				tmp = new ArrayList<Integer>(Arrays.asList(board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(0), board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(1)));
				checkerRange.add(tmp);
				if(Math.abs(checker[0] - board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(0)) == 1 || 
						Math.abs(checker[1] - board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(1)) == 1) 
					break;
				checkerIndex--;
			}
			
			// 왕을 방어할수 있는 말
			for(int i = 0; i < 8; i ++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getType() != "none" && board[i][j].getColor() == "black" && 
							board[i][j].getType() != "king") {
						
						//경로 방어
						for(int k = 0; k < checkerRange.size(); k ++) {
							if(board[i][j].movePathCheck().contains(checkerRange.get(k))) {
								canBlock = true;
							}
						}
					}
					if(board[i][j].getType() != "none" && board[i][j].getColor() == "black") {
						//공격하여 방어
						for(int index = 0; index < board[i][j].attackPathCheck().size(); index++) {	
							if(board[i][j].attackPathCheck().get(index).get(0) == checker[0] &&
									board[i][j].attackPathCheck().get(index).get(1) == checker[1]) {
								canBlock = true;
							}
						}
					}
					
				}
			}
		}
		
		if(count > 0)
			board[bKYX.get(0)][bKYX.get(1)].setCheck(true);
		else
			board[bKYX.get(0)][bKYX.get(1)].setCheck(false);
		
		count = 0;
		
		for(int i = 0; i < allBAttackPath.size(); i++) {
			if(allBAttackPath.get(i).equals(wKYX) ) {
				count++;
			}
		}
	
		
		if(count == 1) {
			checkerRange = new ArrayList<ArrayList<Integer>>();
			checkerIndex = board[checker[0]][checker[1]].attackPathCheck().indexOf(wKYX) - 1;
			
			//공격자의 공격경로 계산
			while(true) {
				//좌우 바로 옆일 경우
				if(wKYX.get(0) == checker[0] && (Math.abs(wKYX.get(1) - checker[1]) == 1) ||
						wKYX.get(1) == checker[1] && (Math.abs(wKYX.get(0) - checker[0]) == 1)) break;
				//대각선 바로 옆일경우
				if((Math.abs(wKYX.get(1) - checker[1]) == 1) && (Math.abs(wKYX.get(1) - checker[1]) == 1)) break;
				
				//그외
				tmp = new ArrayList<Integer>(Arrays.asList(board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(0), board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(1)));
				checkerRange.add(tmp);
				if(Math.abs(checker[0] - board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(0)) == 1 || 
						Math.abs(checker[1] - board[checker[0]][checker[1]].attackPathCheck().get(checkerIndex).get(1)) == 1) 
					break;
				checkerIndex--;
			}
			
			// 왕을 방어할수 있는 말
			for(int i = 0; i < 8; i ++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getType() != "none" && board[i][j].getColor() == "white" && 
							board[i][j].getType() != "king") {
						
						//경로 방어
						for(int k = 0; k < checkerRange.size(); k ++) {
							if(board[i][j].movePathCheck().contains(checkerRange.get(k))) {
								canBlock = true;
							}
						}
					}
					if(board[i][j].getType() != "none" && board[i][j].getColor() == "black") {
						//공격하여 방어
						for(int index = 0; index < board[i][j].attackPathCheck().size(); index++) {	
							if(board[i][j].attackPathCheck().get(index).get(0) == checker[0] &&
									board[i][j].attackPathCheck().get(index).get(1) == checker[1]) {
								canBlock = true;
							}
						}
					}
					
				}
			}
		}
		
		
		if(count > 0)
			board[wKYX.get(0)][wKYX.get(1)].setCheck(true);
		else
			board[wKYX.get(0)][wKYX.get(1)].setCheck(false);
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
			ArrayList<ArrayList<Integer>>tmp = new ArrayList<ArrayList<Integer>>(board[bKYX.get(0)][bKYX.get(1)].attackPathCheck());
			all.addAll(tmp);
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
	// 체크가 아닌데 움직인 뒤 왕이 체크가 되면 못움직이게
	private static boolean afterMoveDanger(int[] From, int runNum) {
		List<Integer> from = new ArrayList<Integer>(Arrays.asList(From[1],From[0]));
		List<ArrayList<Integer>> cvtPath = new ArrayList<ArrayList<Integer>>(); 
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(runNum == 1) {
					if(board[i][j].getColor() == "black"){
						if(board[i][j].checkPathCheck() != null) {
							int index = board[i][j].checkPathCheck().size() - 1;
							for(int k = index; k >= 0; k--) {
								if(k != 0) {
									int y = board[i][j].checkPathCheck().get(k).get(0);
									int x = board[i][j].checkPathCheck().get(k).get(1);
									int by = board[i][j].checkPathCheck().get(k-1).get(0);
									int bx = board[i][j].checkPathCheck().get(k-1).get(1);
									if(Math.abs(y - by) != 1 || Math.abs(x - bx) != 1) break;
									cvtPath.add(board[i][j].checkPathCheck().get(k));
								}else {
									cvtPath.add(board[i][j].checkPathCheck().get(k));
								}
							}
							cvtPath.add(new ArrayList<Integer>(Arrays.asList(i,j)));
							
							if(cvtPath.contains(wKYX) && cvtPath.contains(from)) {
								return true;
							}
						}
					}
				}else {
					if(board[i][j].getColor() == "white"){
						if(board[i][j].checkPathCheck() != null) {
							int index = board[i][j].checkPathCheck().size() - 1;
							for(int k = index; k >= 0; k--) {
								if(k != 0) {
									int y = board[i][j].checkPathCheck().get(k).get(0);
									int x = board[i][j].checkPathCheck().get(k).get(1);
									int by = board[i][j].checkPathCheck().get(k-1).get(0);
									int bx = board[i][j].checkPathCheck().get(k-1).get(1);
									if(Math.abs(y - by) > 1 || Math.abs(x - bx) > 1) break;
									cvtPath.add(board[i][j].checkPathCheck().get(k));
								}else {
									cvtPath.add(board[i][j].checkPathCheck().get(k));
								}
							}
							cvtPath.add(new ArrayList<Integer>(Arrays.asList(i,j)));
							
							if(cvtPath.contains(bKYX) && cvtPath.contains(from)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	//메인
	public static void main(String[] args) {			
		System.out.println("=====> CHESS <====="); //title
		ChessBoard b = new ChessBoard(); 
		
		boolean isCheck = false;
		
		String ply1Name = getName(1, null);
		//String ply2Name = getName(2, ply1Name);
		
		Player whitePly = new Player(ply1Name, "white");
		Player blackPly = new AlphaBetaPlayer("black", 1);
		//Player blackPly = new Player(ply2Name, "black");
		
		//초기 왕위치
		wKYX = new ArrayList<Integer>(Arrays.asList(7, 4));
		bKYX = new ArrayList<Integer>(Arrays.asList(0, 4));
		
		setup(); //초기 보드 세팅
		
		//체스 루프 시작
		while(true){
			for(int runNum = 1; runNum <= 2; runNum++){ //run for each player
				draw(); //보드 출력
				
				int move[][] = new int[2][2];
				
				
				for(int i = 0; i < 8; i ++) {
					for(int j = 0; j < 8; j++) {
						if(board[i][j].getType()=="king") {
							System.out.println(board[i][j].movePathCheck());
						}
					}
				}
				
				
				while(true){
					if(runNum == 1){ //흰색 턴
						isCheck = board[wKYX.get(0)][wKYX.get(1)].isCheck(); 
						if(isCheck)
							System.out.println("white check");
						
						move = whitePly.getMove(b);
					}
					else{ //검정색 턴
						isCheck = board[bKYX.get(0)][bKYX.get(1)].isCheck();
						if(isCheck)
							System.out.println("black check");
						move = blackPly.getMove(b);
					}
					
					
					if(move[0][0] == -1){ //잘못 입력하면 다시
						System.out.println("잘못된 위치 입니다. 다시 고르세요.");
						continue;
					}
					
					
					
					int[] moveFrom = move[0];
					int[] moveTo = move[1];
					Square fromSquare = board[moveFrom[1]][moveFrom[0]];
					
					// from랑 왕 좌표가 모두 어태커 좌표에 포함 되면 
					boolean amd = afterMoveDanger(moveFrom, runNum);
					
					if(amd) {
						System.out.println("왕이 위험합니다.");
						continue;
					}
					
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
						if(checkmate() == 1 && canBlock == false) {
							draw(); //보드 출력
							System.out.println("White CheckMate!");
							return;
						}else if(checkmate() == 2 && canBlock == false) {
							draw(); //보드 출력
							System.out.println("Black CheckMate!");
							return;
						}
						canBlock = false;
						break;
					}
					System.out.println("잘못된 이동입니다. 다시 이동하세요.");
				}
			}
		}
	}
}
