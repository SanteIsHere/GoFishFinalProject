import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class representing the deck/pool of available cards
 * @field deck Deck of cards available to draw from (initially)
 */
public class Pool extends HBox {
    private ArrayList<Card> deck = new ArrayList<Card>(52);
    private ImageView img = new ImageView();
    private Text count = new Text(String.format("%d cards left", deck.size()));


    public Pool() {
        for (int count = 0; count < 52; count++) {
            deck.add(new Card());
        }

        img.setImage(new Image("resources/Deck/DeckFull.png"));
        count.setFill(Color.WHITE);
        

        getChildren().addAll(img, count);
    }

    /**
     * Deal cards to players when the game begins
     * 
     * D2 - The parameters of the method are polymorphic references
     * to objects that inherit/sub-class `Player` - `HumanPlayer`s or `CPUPlayer`s
     * @param player
     * @param cpu
     */
    public void dealCards(Player player, Player cpu) {
        for (int count = 0; count < 7; count++) {
            player.goFish(this);
            cpu.goFish(this);
        }
    }

    /**
     * Draw from the top of the pool/deck
     */
    public Card draw() throws PoolExhaustedException {
        if (deck.isEmpty())
            throw new PoolExhaustedException("No cards left in the pool...");
        else {
            // Remove the top-most card from the pool
            Card drawnCard = deck.remove(0);
            // Update the count indicator (`Text` component)
            count.setText(String.format("%d cards left", deck.size())); 
            return drawnCard;
        }
    }
}