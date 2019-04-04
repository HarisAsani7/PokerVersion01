package poker.version_graphics.model;

import java.util.ArrayList;
import java.util.Collections;

import poker.version_graphics.model.*;

public class tieBreaks {
	Player player;
	
	
	private String output;
	
	public tieBreaks(ArrayList<Player> winners) {
		for (Player p : winners) {
			System.out.println(p.getPlayerName());
		}
		
		String HandType = "";
	

	Player currentWinner = winners.get(0);
	
	boolean tied = false;
	
	switch (winners.get(0).getWinsOrdinal()) {
		case 0:
		HandType = "Highest Card";
		
		output = getHighestCard(winners).getPlayerName() + "has won!!";
		break;
		
		case 1:
			HandType = "One Pair";
			output = EvaluatePair(winners) + "has won!!";
			break;
		
		case 2:
			HandType = "TwoPair";
			output = EvaluateTwoPair(winners).getPlayerName() + "has won!!";
			break;
			
		case 3:
			HandType = "Three of a Kind";
			for (Player p : winners) {
				if (EvaluateThreeOfAKind(p.getCards()) > EvaluateThreeOfAKind(currentWinner.getCards())) {
					currentWinner = p;
				}
			}
		output = currentWinner + "has won!!";
		break;
		
		case 4:
			HandType = "Straight";
			for (Player p : winners) {
				if(EvaluateStraight(p.getCards()) > EvaluateStraight (currentWinner.getCards())) {
					currentWinner = p;
				} else if (EvaluateStraight(p.getCards()) == EvaluateStraight(currentWinner.getCards()) && p.getPlayerName() != currentWinner.getPlayerName())
					tied = true;				
			}
			if (!tied) {
				output = currentWinner + "has won!!";
			} else {
				output = getHighestCard(winners) + "has won!!";
			}
			break;
		case 5:
			HandType = "Flush";
			output = getHighestCard(winners) +" has won!!";
			
			break;
		case 6:
			HandType = "Full House";
			for (Player p : winners) {
				if (EvaluateThreeOfAKind(p.getCards()) > EvaluateThreeOfAKind(currentWinner.getCards())) {
					currentWinner = p;
				}
			}
			
			output = currentWinner+" has won!!";
			
			break;
		case 7:
			HandType = "Four Of A Kind";
			for (Player p : winners) {
				if (EvalFourOfAKind(p.getCards()) > EvalFourOfAKind(currentWinner.getCards())) {
					currentWinner = p;
				}
			}
			
			output = currentWinner +" has won!!";
			
			break;
			
		case 8:
			HandType = "Straight Flush";
			for (Player p : winners) {
				if (EvaluateStraight(p.getCards()) > EvaluateStraight(currentWinner.getCards())) {
					currentWinner = p;
				} else if (EvaluateStraight(p.getCards()) == EvaluateStraight(currentWinner.getCards()) && p.getPlayerName() !=currentWinner.getPlayerName())
					tied = true;
			}
			if (!tied) {
				output = currentWinner+" has won!!";
			} else {
				output = getHighestCard(winners) +" has won!!";
			}
			
			break;		
	}
	}
	
	public String getOutPut() {
		return output;
	}
	
	private ArrayList<Integer> getSortedOrdinal(poker.version_graphics.model.Player p) {
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for (Card c: p.getCards()) sorted.add(c.getRank().ordinal());
		
		Collections.sort(sorted);
		return sorted;
	}
	
	private Player getHighestCard(ArrayList<Player> players) {
		int counter = 1;
		boolean winnerfound = false;
		Player currentWinner = players.get(0);
		ArrayList<Integer> sortedCards = getSortedOrdinal(currentWinner);
		ArrayList<Integer> sortedCardsP = new ArrayList<>();
		
		while (!winnerfound) { 
			 for (Player p : players) {
				 sortedCardsP.clear();
				 sortedCardsP = getSortedOrdinal(p);
	
				 if (sortedCardsP.get(sortedCardsP.size()-counter) > sortedCards.get(sortedCards.size()-counter) 
						 && p.getPlayerName() !=currentWinner.getPlayerName()) {
					 		currentWinner = p;
					 		winnerfound = true;
					 		sortedCards.clear();
					 		sortedCards = getSortedOrdinal(p);
				 
			 } else if (sortedCardsP.get(sortedCardsP.size()-counter) == sortedCards.get(sortedCards.size()-counter) 
					 && p.getPlayerName() !=currentWinner.getPlayerName()){
				 			winnerfound = false;
				 			counter++;
			 } else if (sortedCardsP.get(sortedCardsP.size()-counter) < sortedCards.get(sortedCards.size()-counter)){
				 winnerfound = true;
			 	}
			 } 
		 }
		return currentWinner;
	}
	
	private Player EvaluatePair(ArrayList<Player> players) {
		int CurrentWinnerPair = -1;
		int CurrentWinnerHighCard = -1;
		int counter = 1;
		boolean winnerfound = false;
		
		Player currentWinner = players.get(0);
		while (!winnerfound) {
		for (Player p : players) {
			int TempWinnerPair = -1;
			int TempHighestCard = -1;
	
			ArrayList<Integer> clonedCards = new ArrayList<>();
			for (Card c : p.getCards()) {
				clonedCards.add(c.getRank().ordinal());
			}
			
			Collections.sort(clonedCards);
		
			for (int i = 0; i < clonedCards.size() - 1 ; i++) {
				for (int j = i + 1; j < clonedCards.size() ; j++) {
					if (clonedCards.get(i) == clonedCards.get(j)) {
						TempWinnerPair = clonedCards.get(j);
					clonedCards.remove(j);
					clonedCards.remove(i);
					}
				}
			}
			if (TempWinnerPair > CurrentWinnerPair) {
				currentWinner = p;
				winnerfound = true;
				CurrentWinnerPair = TempWinnerPair;
			} else if (TempWinnerPair == CurrentWinnerPair && p.getPlayerName() !=currentWinner.getPlayerName()) {
				winnerfound = false;
			
				TempHighestCard = clonedCards.get(clonedCards.size()-counter);
				if (TempHighestCard > CurrentWinnerHighCard) {
					currentWinner = p;
					winnerfound = true;
					CurrentWinnerHighCard = TempHighestCard;
				} else if (TempHighestCard == CurrentWinnerHighCard && p.getPlayerName() !=currentWinner.getPlayerName()) {
					counter++;
					winnerfound = false;
				}
			}
		}
		}
		return currentWinner;
	}
	
	private Player EvaluateTwoPair(ArrayList<Player> players) {
		
		Player currentWinner = players.get(0);
		
		int valueHigherPair = -1;
		int valueLowerPair = -1;
		int remainingcard = -1;
		
		for (Player p : players) {
			int tempHigherPair = 0;
			int tempLowerPair = 0;
			int tempRemaining = 0;
			
				ArrayList<Card> clonedCards = (ArrayList<Card>) p.getCards().clone();
				boolean firstPairFound = false;
				for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
					for (int j = i + 1; j < clonedCards.size() && !firstPairFound; j++) {
						if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
							firstPairFound = true;
							tempHigherPair = clonedCards.get(i).getRank().ordinal();
							clonedCards.remove(j);
							clonedCards.remove(i); 
						}
					}
				}
							for (int k = 0; k < clonedCards.size() - 1 ; k++) {
								for (int l = k + 1; l < clonedCards.size() ; l++) {
									if (clonedCards.get(k).getRank() == clonedCards.get(l).getRank()) {
										tempLowerPair = clonedCards.get(l).getRank().ordinal();
									clonedCards.remove(l);
									clonedCards.remove(k);
									}
								}
							}
							
							tempRemaining = clonedCards.get(0).getRank().ordinal();
							if (tempHigherPair < tempLowerPair) {
								int temp = tempHigherPair;
								tempHigherPair = tempLowerPair;
								tempLowerPair = temp;
							}
					
					if (tempHigherPair > valueHigherPair) {
						currentWinner = p;
						valueHigherPair = tempHigherPair;
					} else if (tempHigherPair == valueHigherPair && currentWinner.getPlayerName() !=p.getPlayerName()) {
						if (tempLowerPair > valueLowerPair) {
							currentWinner = p;
							valueLowerPair = tempLowerPair;
						} else if (tempRemaining == remainingcard && currentWinner.getPlayerName() !=p.getPlayerName()) {
							currentWinner = p;
							remainingcard = tempRemaining;
						}
					}
		}
		
		return currentWinner;
	}
	
	private int EvaluateThreeOfAKind(ArrayList<Card> cards) {
	int highest = 0;

	for (int i = 0; i < cards.size() - 2; i++) {
		for (int j = i + 1; j < cards.size() - 1; j++) {
			for (int k = j + 1; k < cards.size(); k++) {
				if (cards.get(i).getRank() == cards.get(j).getRank()
						&& cards.get(j).getRank() == cards.get(k).getRank())
					highest = cards.get(j).getRank().ordinal();
				}
			}
		}
	return highest;
	}
	
	private int EvaluateStraight(ArrayList<Card> cards) {
		ArrayList<Integer> cardsSorted = new ArrayList<>();
		for (Card c : cards) {
			cardsSorted.add(c.getRank().ordinal());
		}
		
		Collections.sort(cardsSorted);
		int highest = cardsSorted.get(cardsSorted.size()-1);
		return highest;
		
	}
	
	private int EvalFourOfAKind(ArrayList<Card> cards) {
		
		int highest = 0;
		for (int i = 0; i < cards.size() - 3; i++) {
			for (int j = i + 1; j < cards.size() - 2; j++) {
				for (int k = j + 1; k < cards.size() - 1; k++) {
					for (int l = k + 1; l < cards.size(); l++) {
						if (cards.get(i).getRank() == cards.get(j).getRank()
								&& cards.get(j).getRank() == cards.get(k).getRank()
								&& cards.get(k).getRank() == cards.get(l).getRank()
								&& cards.get(i).getSuit() != cards.get(j).getSuit()
								&& cards.get(j).getSuit() != cards.get(k).getSuit()
								&& cards.get(k).getSuit() != cards.get(l).getSuit()
								&& cards.get(l).getSuit() != cards.get(i).getSuit()
								&& cards.get(i).getSuit() != cards.get(k).getSuit()
								&& cards.get(j).getSuit() != cards.get(l).getSuit()) {
								highest = cards.get(l).getRank().ordinal();
						}
					}
				}
			}
		}
		return highest;
	} 
}
