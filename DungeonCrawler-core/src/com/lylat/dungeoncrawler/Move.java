package com.lylat.dungeoncrawler;

public enum Move {
	NONE, 
	WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT, 
	SWORD_UP, SWORD_DOWN, SWORD_LEFT, SWORD_RIGHT, 
	BOW_UP, BOW_DOWN, BOW_LEFT, BOW_RIGHT;

	public boolean isEqual(Move move) {
		if (this == move)
			return true;
		else
			return false;
	}

}
