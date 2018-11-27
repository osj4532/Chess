package chess;

import java.util.Scanner;

class Player {
	
	private String name;
	private String color;
	private final Scanner scanner = new Scanner(System.in);
	
	Player(String nameIn, String colorIn){
		name = nameIn;
		color = colorIn;
	}
	
	// A,B,C.... 체스 위치에 맞는 문자를 받아 숫자로 변환
	private int convertCharToNum(char charIn){ //-1이 리턴되면 맞지 않은 규칙
		int numOut = -1;
		
		for(int i = 0; i < ChessBoard.SIDE_CHAR.length; i++){
			
			if(ChessBoard.SIDE_CHAR[i] == charIn){
				numOut = i;
			}
		}
		return numOut;
	}
	
	// 1,2,3.... 체스 위치에 맞는 숫자(char형)를 받아 숫자로 변환
	private int convertCharNumtoNum(char charIn){
		int numOut = -1; 
		int convertedNum = Character.getNumericValue(charIn); 
		int[] sideNum = {1,2,3,4,5,6,7,8};
		
		
		for(int i: sideNum ){
			if(i == convertedNum){
				numOut = convertedNum;   
			}
		}
		return numOut;
	}
	
	public int[][] getMove(){ //x와 y의 배열을 리턴해주는 함수  이 걸로 움직일수 있다.
		
		int[][] move = new int[2][2];
		for(int runNum = 1; runNum <= 2; runNum++){
			while(true){
				if(runNum == 1){ //움직일 말이 있는 위치 묻기
					System.out.print(name + ", 움직일 말의 위치를 입력하세요. (EX: E4)\n>> ");
				}
				else{ //말이 움직일 위치 묻기
					System.out.print(name + ", 말이 움직일 위치를 입력하세요. (EX: E4)\n>> ");
				}
				String moveIn = scanner.nextLine().trim();
			
				//공백, 텝, 빈칸이 없고 문자길이가 2이하
				if(!moveIn.isEmpty() && moveIn.length() <= 2 && !(moveIn.contains(" ") || moveIn.contains("\t"))){
				
					if(!Character.isDigit(moveIn.charAt(0)) && Character.isDigit(moveIn.charAt(1))){
						//첫문자가 문자고 두번째문자가 숫자이면
						int x, y;
			
						if((x = convertCharToNum(Character.toUpperCase(moveIn.charAt(0)))) != -1){
							if((y = convertCharNumtoNum(moveIn.charAt(1))) != -1){
								y = 8 - y; //배열과 체스판의 숫자는 상하 반전이라서
								int tempArray[] = {x, y};
								if(runNum == 1){
									if(ChessBoard.board[y][x].getType() == "blank" || ChessBoard.board[y][x].getColor() != color){ 
										
										 
										//첫 번째 위치가 말을 가리 키지 않거나 말이 플레이어와 같은 색이 아닌 경우 -1로 가득 찬 배열을 반환합니다.
										tempArray[0] = -1;																
										tempArray[1] = -1;
										int[][] errorArray = {tempArray, tempArray};
										return errorArray;
									}
								}
								
								move[runNum - 1] = tempArray;
								break;
							}
						}
					}
				}
				System.out.println("잘못된 위치입니다. 다시 입력하세요."); //잘못된 위치일때
			}		
		}
		return move;
	}
}
