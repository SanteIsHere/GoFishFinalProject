import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
        getChildren().add(booksHeld);
        getChildren().addAll(hand);
    }

    /**
     * Get and remove all cards with the input `rank`
     * @param rank The rank of cards to search for
     * @return Array of cards with
     */
    public Card drawCards(int rank) throws PoolExhaustedException {
        try {
            if (hand.isEmpty())
                throw new PoolExhaustedException("Hand is empty"); 
            else {
                for (Card card: hand)
                    if (card.getRank() == rank) {
                        System.out.printf("Got card! (From '%s') -> %s\n", this, card);
                        return hand.remove(hand.indexOf(card));
                    }
            }
        } catch (PoolExhaustedException ex) {
            ex.printStackTrace();
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
    public void requestCards(int rank, Player otherPlayer) {
        if (otherPlayer.hasRank(rank))
        {
           while (otherPlayer.hasRank(rank))
                hand.add(otherPlayer.drawCards(rank));
            updateBooks();
        } else {
            System.out.printf("Player (%s) has no cards of rank: %d... GoFish!\n",
             otherPlayer, rank);
            goFish(pool);
        }
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
