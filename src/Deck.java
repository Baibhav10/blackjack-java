import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;
                if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
                    value = 10;
                } else if (rank.equals("Ace")) {
                    value = 11;
                } else {
                    value = Integer.parseInt(rank);
                }
                cards.add(new Card(suit, rank, value));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.remove(0);
    }
}
