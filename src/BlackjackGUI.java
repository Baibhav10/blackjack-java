import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlackjackGUI extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    private JTextArea playerArea;
    private JTextArea dealerArea;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;

    public BlackjackGUI() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();

        // Set up the main window
        setTitle("Blackjack");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(0, 100, 0)); // Deep green felt table

        // Custom border for card-like appearance
        Border cardBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // Initialize components
        playerArea = new JTextArea(5, 25);
        dealerArea = new JTextArea(5, 25);
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");

        // Style text areas
        playerArea.setEditable(false);
        dealerArea.setEditable(false);
        playerArea.setFont(new Font("Courier New", Font.BOLD, 16));
        dealerArea.setFont(new Font("Courier New", Font.BOLD, 16));
        playerArea.setBackground(new Color(255, 255, 240)); // Ivory card background
        dealerArea.setBackground(new Color(255, 255, 240));
        playerArea.setForeground(new Color(0, 0, 0));
        dealerArea.setForeground(new Color(0, 0, 0));
        playerArea.setBorder(BorderFactory.createTitledBorder(
            cardBorder, "Player's Hand", 0, 0, new Font("Serif", Font.BOLD, 14), Color.WHITE));
        dealerArea.setBorder(BorderFactory.createTitledBorder(
            cardBorder, "Dealer's Hand", 0, 0, new Font("Serif", Font.BOLD, 14), Color.WHITE));

        // Add shadow effect to text areas
        JPanel playerPanel = new JPanel(new BorderLayout());
        JPanel dealerPanel = new JPanel(new BorderLayout());
        playerPanel.setBackground(new Color(0, 100, 0));
        dealerPanel.setBackground(new Color(0, 100, 0));
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        dealerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        playerPanel.add(new JScrollPane(playerArea), BorderLayout.CENTER);
        dealerPanel.add(new JScrollPane(dealerArea), BorderLayout.CENTER);

        // Style status label
        statusLabel.setFont(new Font("Serif", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(139, 69, 19)); // Rich wood brown
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Style buttons with gradient and hover effects
        Font buttonFont = new Font("Serif", Font.BOLD, 16);
        hitButton.setFont(buttonFont);
        standButton.setFont(buttonFont);
        newGameButton.setFont(buttonFont);
        styleButton(hitButton, new Color(0, 120, 215), new Color(0, 80, 180)); // Blue gradient
        styleButton(standButton, new Color(200, 0, 0), new Color(160, 0, 0)); // Red gradient
        styleButton(newGameButton, new Color(0, 150, 0), new Color(0, 120, 0)); // Green gradient

        // Center panel for hands
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 15, 15));
        centerPanel.setBackground(new Color(0, 100, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        centerPanel.add(dealerPanel);
        centerPanel.add(playerPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(0, 100, 0));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);

        // Add components to frame
        add(statusLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        hitButton.addActionListener(e -> {
            playerHand.addCard(deck.dealCard());
            updateHands();
            if (playerHand.getValue() > 21) {
                statusLabel.setText("You busted! Dealer wins.");
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
            }
        });

        standButton.addActionListener(e -> dealerPlay());

        newGameButton.addActionListener(e -> startGame());

        startGame();
    }

    private void styleButton(JButton button, Color startColor, Color endColor) {
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);

        // Gradient background
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, endColor, 0, b.getHeight(), startColor));
                g2.fillRect(0, 0, b.getWidth(), b.getHeight());
                g2.dispose();
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, startColor, 0, c.getHeight(), endColor));
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                super.paint(g2, c);
                g2.dispose();
            }
        });

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.YELLOW, 1),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            }
        });
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI frame = new BlackjackGUI();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Center the window
        });
    }
}