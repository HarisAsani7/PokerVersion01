package poker.version_graphics.model;

import java.util.ArrayList;

import javafx.scene.control.Label;
import poker.version_graphics.view.PokerGameView;

public class Player implements Comparable<Player> {
    public static final int HAND_SIZE = 5;
    private final String playerName; // This is the ID
    private final ArrayList<Card> cards = new ArrayList<>();
    private HandType handType;
    int wins;
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

//    public int addWin(int wins) {
//    	wins++;
//    	System.out.println(wins);
//    	return wins;
//    }
    
    public void addCard(Card card) {
    	if (cards.size() < HAND_SIZE) cards.add(card);
    //fullhouse test working
//  	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Clubs, Card.Rank.Ace)); // is also a method to check if it shows the right evaluation
//   	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Diamonds, Card.Rank.Ace));
//   	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Ace));
//   	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Clubs, Card.Rank.Jack));
//   	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Jack));
//    //flush test works
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Nine));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Six));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Queen));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Jack));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Five));
//	//straight test working
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Nine));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Diamonds, Card.Rank.Ten));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Clubs, Card.Rank.Jack));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Diamonds, Card.Rank.Queen));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Spades, Card.Rank.King));
//	//Straightflush test
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Nine));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Six));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Seven));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Eight));
//	if (cards.size() < HAND_SIZE) cards.add(new Card(Card.Suit.Hearts, Card.Rank.Five));   
}
    
    
    public void addWins(int wins) {
		this.wins += wins;
	}
    
    public int getWins() {
    	return wins;
    }
	public void discardHand() {
        cards.clear();
        handType = null;
    }
    
    public int getNumCards() {
        return cards.size();
    }


    /**
     * If the hand has not been evaluated, but does have all cards, 
     * then evaluate it.
     */
    public HandType evaluateHand() {
        if (handType == null && cards.size() == HAND_SIZE) {
            handType = HandType.evaluateHand(cards);
        }
        return handType;
    }

    /**
     * Hands are compared, based on the evaluation they have.
     */
    @Override
    public int compareTo(Player o) {
        return handType.compareTo(o.handType);
    }
}
