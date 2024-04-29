import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract class Player extends HBox {
    protected List<Card> hand = 
    new ArrayList<Card>();
    private int books = 0;
    protected static Pool pool;
    private HashMap<Integer, Integer> rankCount = 
    new HashMap<Integer, Integer>();

    /**
     * Updates the contents of the player's hand (visually - adds the card objects to 
     * the children of the `HBox`)
    */
    public void updateHand() {
        // Sort the hand
        hand.sort(null);
        // Clear the children attached to the `HBox`
        getChildren().clear();
        // Add the cards in the hand to the `ObservableList`

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
                        System.out.println("Got card!: " + card);
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
            updateHand();
        } else {
            System.out.println("Player has no cards of rank: " + rank + "... GoFish!");
            goFish(pool);
        }

        updateBooks();
    }

    /**
     * "Fish" a card from the pool
     * @param pool Deck of cards
     */
    public void goFish(Pool pool) {
        // Draw a card from the pool
        hand.add(pool.draw());
        // Update the hand (visually)
        updateHand();
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
        for (int rank: rankCount.values())
            if (rank >= 4) {
                books++;
                hand.removeIf((card) -> (card.getRank() == rank));
            }
                
    }
}
