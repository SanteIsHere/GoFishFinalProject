import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

class Player extends HBox {
    private List<Card> hand = 
    new ArrayList<Card>();
    private int books = 0;
    private ImageView img = new ImageView();
    
    public Player() {
        img.setImage(new Image("resources/playerIcon.png"));
    }

    public void updateHand() {
        hand.sort(null);
        getChildren().setAll(hand);
        getChildren().set(0, img);
    }

    public List<Card> getHand() {
        return hand;
    }
}
