import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * D3 - Abstract class representing a Player
 */
abstract class Player extends HBox {
    /* D1 - Player's hand of cards,
     * a polymorphic reference of interface
     * type `List` to an `ArrayList` of `Card`s
     */
    protected List<Card> hand = 
    new ArrayList<Card>();
    
    // The no. of books the player holds
    private int books = 0;
    
    /* D9 - Players depend on/use a shared pool of cards */
    protected static Pool pool;
    
    // Map of each rank to their count in the player's hand
    private HashMap<Integer, Integer> rankCount = 
    new HashMap<Integer, Integer>();
    
    // Visual indicator for book count
    private Text booksHeld = new Text("Books held: 0");
    
    // `Text` node that displays the result of a player's actions
    private Text statusIndicator = new Text();
    
    // Container for indicators
    private VBox indicators = new VBox(booksHeld, statusIndicator);

    // Manager for the `Player` instance
    protected static GoFish.GameManager manager = new GoFish.GameManager();


    public Player() {
        booksHeld.setFill(Color.WHITE);
        statusIndicator.setFill(Color.WHITE);
        statusIndicator.setWrappingWidth(100);
    }

    /**
     * Updates the contents of the player's hand (visually - adds the card objects to 
     * the children of the `HBox`)
    */
    public void updateHand() {
        // Sort the hand
        hand.sort(null);
        // Clear the children attached to the `HBox`
        getChildren().clear();
        
        // Add the book count indicator and cards in the hand to the `ObservableList`
        getChildren().add(indicators);
        for (Card card: hand) {
            card.setOpacity(0.0);
            getChildren().add(card);
            FadeTransition cardAddAnim = new FadeTransition(Duration.millis(500), card);

            cardAddAnim.setFromValue(0.0);
            cardAddAnim.setToValue(1.0);
            
            cardAddAnim.play();
        }
    }

    /**
     * Get and remove all cards with the input `rank`
     * @param rank The rank of cards to search for
     * @return Array of cards with
     */
    public Card drawCards(int rank) throws CardsExhaustedException {
        if (hand.isEmpty())
            /* D12 - Throw our custom exception class `CardsExhaustedException` when a `Player`
             * has no more cards to draw from
             */
            throw new CardsExhaustedException("Player has no more cards to draw from...");
        for (Card card: hand) {
            if (card.getRank() == rank) {
                /* Print out to the console (and to the `Text` node) that a card
                 * of the requested rank was acquired
                 */
                System.out.printf("Got card! (From '%s') -> %s\n", this, card);
                return hand.remove(hand.indexOf(card));
            }
        }
        return null;
    }

    /**
     * Check if this instance of `Player` has a card with the input `rank`
     * @return true if a card with the rank is found, else false
     */
    private boolean hasRank(int rank) {
        boolean rankFound = false;
        for (Card card: hand)
            if (card.getRank() == rank)
                rankFound = true;
        
        return rankFound;
    }

    /**
     * Request cards of a specified rank from another player
     * @param rank The rank of cards to request
     * @param otherPlayer
     */
    public void requestCards(int rank, Player otherPlayer) throws NumberFormatException {
        
        if (otherPlayer.hasRank(rank))
        {
            int initHandSize = hand.size(); 
            while (otherPlayer.hasRank(rank))
                hand.add(otherPlayer.drawCards(rank));

            int newHandSize = hand.size()-initHandSize;
            statusIndicator.setText(
                String.format("Received %d cards of rank: %d",
            newHandSize, rank));

            indicateStatus(
                String.format("Received %d cards of rank: %d",
                 newHandSize, rank)
            );


            updateBooks();
        } else if (!(rank <= 13 && rank >= 1)) { 
            throw new NumberFormatException();
        } else {
            System.out.printf("Player (%s) has no cards of rank: %d... GoFish!\n",
             otherPlayer, rank);
            

            indicateStatus(
                String.format("Player (%s) has no cards of rank: %d... GoFish!\n",
                otherPlayer, rank)
            );

            goFish(pool);
        }
    }

    /**
     * Reveal the `Text` node indicating the result of a player action 
     * (Requesting cards, receiving cards, etc)
     * @param message
     */
    protected void indicateStatus(String message) {
        statusIndicator.setVisible(true);
        statusIndicator.setText(message);
            
        // Create a `PauseTransition` to set a delay before
        // hiding the `Text` node
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(new EventHandler<ActionEvent>() {
        
        /**
         * Hide the `Text` node and clear its text
         */
        @Override
        public void handle(ActionEvent event) {
            statusIndicator.setVisible(false);
            // getChildren().remove(statusIndicator);
            statusIndicator.setText("");
            }
        });

        delay.play();
    }


    /**
     * "Fish" a card from the pool
     * @param pool Deck of cards
     */
    public void goFish(Pool pool) {
        // Draw a card from the pool
        hand.add(pool.draw());
        // Check if any "books" were made
        updateBooks();
    }

    /**
     * Accessor for hand contents
     * @return The hand
     */
    public List<Card> getHand() {
        return hand;
    }

    
    private void updateBooks() {
        /* Initialize the `HashMap` mapping ranks to their total counts
         */
        // Clear the `HashMap` first
        rankCount.clear();
        for (Card card: hand) {
            int cardRank = card.getRank();
            // If the rank is a key in the map, increment its value
            if (rankCount.containsKey(cardRank))
                rankCount.put(cardRank,
                rankCount.get(cardRank)+1);
            // Assign the rank as a key to the map with initial value 1
            else
                rankCount.put(cardRank,
                1);
        }
        
        // Find and remove cards of the same rank if their count is 4 or greater: Increment the count of books
        for (Map.Entry<Integer, Integer> rankMapping: rankCount.entrySet()) {
            if (rankMapping.getValue() >= 4) {
                books++;
                booksHeld.setText(String.format("Books held: %d", books));
                hand.removeIf((card) -> (card.getRank() == rankMapping.getKey()));
            }
        }

        updateHand();
    }

    @Override
    public String toString() {
        return "Human Player";
    }
}
