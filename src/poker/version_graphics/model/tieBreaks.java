package poker.version_graphics.model;

import java.util.ArrayList;

public class tieBreaks {
	Player player;
	public ArrayList<Player> playerList = new ArrayList<Player>();
	public ArrayList<Card> playerCards = new ArrayList<Card>();
	
	
	public tieBreaks(Player player1, Player player2) {
		this.player = player;
	}

}
