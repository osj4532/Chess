package chess;

import java.util.ArrayList;

public class BlankSpace extends Square{

	public BlankSpace() {
		super("none", "none");
		super.setSymbol("   ");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean moveCheck(int[] from, int[] to, String color, boolean check) {
		return false;
	}

	@Override
	public ArrayList<ArrayList<Integer>> attackPathCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArrayList<Integer>> movePathCheck() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
