import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    private JTextArea playerArea;
    private JTextArea dealerArea;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;

    public BlackjackGUI() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();

        setTitle("Blackjack");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Player area
        playerArea = new JTextArea(5, 20);
        dealerArea = new JTextArea(5, 20);
        statusLabel = new JLabel("Welcome to Blackjack!");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.add(new JScrollPane(playerArea));
        centerPanel.add(new JScrollPane(dealerArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerHand.addCard(deck.dealCard());
                updateHands();
                if (playerHand.getValue() > 21) {
                    statusLabel.setText("You busted! Dealer wins.");
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);
                }
            }
        });

        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dealerPlay();
            }
        });

        startGame();
    }

    private void startGame() {
        playerHand = new Hand();
        dealerHand = new Hand();
        deck = new Deck();

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        updateHands();
        statusLabel.setText("Game started! Your move.");
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void updateHands() {
        playerArea.setText("Player's hand: " + playerHand + "\nValue: " + playerHand.getValue());
        dealerArea.setText("Dealer's hand: " + dealerHand.toString().split(",")[0] + " and [Hidden]");
    }

    private void dealerPlay() {
        dealerArea.setText("Dealer's hand: " + dealerHand + "\nValue: " + dealerHand.getValue());
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.dealCard());
            dealerArea.setText("Dealer's hand: " + dealerHand + "\nValue: " + dealerHand.getValue());
        }

        if (dealerHand.getValue() > 21) {
            statusLabel.setText("Dealer busted! You win.");
        } else {
            int playerTotal = playerHand.getValue();
            int dealerTotal = dealerHand.getValue();
            if (playerTotal > dealerTotal) {
                statusLabel.setText("You win!");
            } else if (playerTotal < dealerTotal) {
                statusLabel.setText("Dealer wins!");
            } else {
                statusLabel.setText("Push! It's a tie.");
            }
        }

        hitButton.setEnabled(false);
        standButton.setEnabled(false);
    }
}
