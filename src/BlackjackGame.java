import java.util.Scanner;

public class BlackjackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        while (true) {
            System.out.println("Your hand: " + playerHand + " Total: " + playerHand.getValue());
            System.out.println("Dealer shows: " + dealerHand.toString().split(",")[0] + " and [Hidden]");

            if (playerHand.getValue() > 21) {
                System.out.println("You busted! Dealer wins.");
                return;
            }

            System.out.println("Hit or Stand? (h/s)");
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("h")) {
                playerHand.addCard(deck.dealCard());
            } else if (action.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }

        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.dealCard());
        }

        System.out.println("Dealer's hand: " + dealerHand + " Total: " + dealerHand.getValue());

        if (dealerHand.getValue() > 21) {
            System.out.println("Dealer busted! You win.");
        } else if (playerHand.getValue() > dealerHand.getValue()) {
            System.out.println("You win!");
        } else if (playerHand.getValue() < dealerHand.getValue()) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("Push! It's a tie.");
        }
    }
}
