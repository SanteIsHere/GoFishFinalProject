import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

/**
 * This class represents a Player's "hand" - 
 * all of the cards a Player possesses
 */
public class Hand extends FlowPane {
    
    private Player owner;

    public Hand(Player owner) {
        this.owner = owner;
    }

    /** Add a card to the Hand, and animate it
     * using a `ScaleTransition`
     * @param card The card to add
     */
    public void addCard(Card card) {

        // Add the card to the `FlowPane`
        getChildren().add(card);

        // Create a `ScaleTransition` to mimic a "bubbling effect" when a card is obtained
        ScaleTransition scaleCard = 
        new ScaleTransition(Duration.millis(500), card);
        scaleCard.setFromX(1.0);
        scaleCard.setFromY(1.0);
        scaleCard.setToX(1.5);
        scaleCard.setToY(1.5);
        scaleCard.setFromX(1.5);
        scaleCard.setFromY(1.5);
        scaleCard.setToX(1.0);
        scaleCard.setToY(1.0);

        scaleCard.play();
        update();
    }
     
    /**
     * Remove a card from the Hand, animating its removal with
     * a `FadeTransition` (incrementally decrease the card's
     * opacity before removing it)
     * @param card The card instance to remove
     * @return The removed card
     */
    public Card removeCard(Card card) {
        FadeTransition fadeOutCard = new FadeTransition(Duration.millis(250));
        fadeOutCard.setFromValue(1.0);
        fadeOutCard.setToValue(0.0);
        Card removedCard = (Card) getChildren().remove(getChildren().indexOf(card));
        fadeOutCard.play();
        update();
        return removedCard;
    }

    public int getSize() {
        return getChildren().size();
    }


    private void update() {
        if (owner instanceof HumanPlayer) {
            getChildren().forEach((card) -> {
                ((Card)card).setImage(new Image(
                    String.format("resources/%s/tile%03d.png", 
                    ((Card)card).getSuit(), ((Card)card).getRank()-1)));
             }
            );
        }
        // Owner is an instance of `CPUPlayer`
        else {
            getChildren().forEach((card)
            -> {((Card)card).setImage(new Image("resources/CardBackBlue.png"));}
            );
        }

        // TODO: Implement sorting (sort the `ObservableList` of cards)
    }
}
