import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * D6 - Class representing the deck/pool of available cards
 * @field deck Deck of cards available to draw from (initially)
 */
public class Pool extends HBox {
    private ArrayList<GoFishCard> deck = new ArrayList<GoFishCard>(52);
    private ImageView img = new ImageView();
    private Text count = new Text(String.format("%d cards left", deck.size()));


    public Pool() {
        for (int count = 0; count < 52; count++) {
            deck.add(new GoFishCard());
        }

        img.setImage(new Image("resources/Deck/DeckFull.png"));

        getChildren().addAll(img, count);
    }

    public void dealCards(Player player, Player cpu) {
        for (int count = 0; count < 7; count++) {
            player.draw(this);
            cpu.draw(this);
        }
    }

    /**
     * Draw from the top of the pool/deck
     */
    public GoFishCard draw() throws PoolExhaustedException {
        try {
            // Remove the top-most card from the pool
            GoFishCard drawnCard = deck.remove(0);
            // Update the count indicator (`Text` component)
            count.setText(String.format("%d cards left", deck.size())); 
            return drawnCard;
        } catch (PoolExhaustedException e) {
            System.err.println("No cards remaining in the pool...");
        }
        return null;
    }
}