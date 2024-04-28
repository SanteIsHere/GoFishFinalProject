import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

class Player extends HBox implements Drawable {
    private List<GoFishCard> hand = 
    new ArrayList<GoFishCard>();
    private int books = 0;
    private ImageView img = new ImageView();
    
    public Player() {
        img.setImage(new Image("resources/playerIcon.png"));
        getChildren().add(img);
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
        // Add the cards in the hand to the `ObservableList`

        getChildren().setAll(hand);
    }

    /**
     * Get and remove all cards with the input `rank`
     * @param rank The rank of cards to search for
     * @return Array of cards with
     */
    public GoFishCard draw(int rank) throws PoolExhaustedException {
        try {
            if (hand.isEmpty())
                throw new PoolExhaustedException("Hand is empty"); 
            else {
                for (GoFishCard card: hand)
                    if (card.getRank() == rank )
                        return hand.remove(hand.indexOf(card));
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
        for (GoFishCard card: hand)
            if (card.getRank() == rank)
                rankFound = true;
        
        return rankFound;
    }

    public void requestCards(int rank, Player otherPlayer) {
        while (otherPlayer.hasRank(rank))
            hand.add(otherPlayer.draw(rank));
        
        updateHand();
    }

    public void draw(Pool pool) {
        //
        hand.add(pool.draw());
    }

    public List<GoFishCard> getHand() {
        return hand;
    }
}
