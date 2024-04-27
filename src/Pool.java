import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Class representing the deck/pool of available cards
 * @field deck Deck of cards available to draw from (initially)
 */
public class Pool extends HBox {
    public ArrayList<Card> deck = new ArrayList<Card>(52);
    private ImageView img = new ImageView();
    private Text count = new Text("52");


    public Pool() {
        for (int count = 0; count < 52; count++) {
            deck.add(new GoFishCard());
        }
            

        img.setImage(new Image("resources/Deck/DeckFull.png"));

        getChildren().addAll(img, count);
    }

    public void dealCards(Player player, Player cpu) {
        for (int count = 0; count < 7; count++) {
            player.getHand().add(drawCard());
            cpu.getHand().add(drawCard());
        }
    }

    public Card drawCard() {
        int deckSize = Integer.parseInt(count.getText());
        count.setText(String.format("%d", deckSize - 1));
        return deck.remove((int)(Math.random()*deck.size()));
    }
}
