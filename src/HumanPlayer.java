import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HumanPlayer extends Player {
    private VBox controls = new VBox();
    
    public HumanPlayer(GoFish.GameManager manager, Pool cardPool, CPUPlayer opponent) {
        /* Initialize a `Button` control and `TextField` to allow a `Player`
         * to request cards of a rank
         */

        pool = cardPool;

        Button requestBttn = new Button("Ask for rank");

        TextField rankInput = new TextField();

        rankInput.setPromptText("Enter a number: 1-13");
        
        /* D5 - Define a callback function for the "Ask for cards" button,
         * using a Lambda Expression implementing the functional interface
         * `EventHandler<ActionEvent>`
         */
        requestBttn.setOnAction((ActionEvent ev)
        -> {
            try {
                System.out.println("Requesting cards of rank " + Integer.valueOf(rankInput.getText()));
                requestCards(Integer.valueOf(rankInput.getText()), opponent);
                FadeTransition revealControls = new FadeTransition(Duration.millis(350), controls);
                revealControls.setFromValue(1.0);
                revealControls.setToValue(0.0);
                revealControls.setOnFinished((event) -> {controls.setVisible(false);}
                );
                revealControls.play();
                rankInput.clear(); // Clear the text input field
                opponent.takeTurn(this);
                takeTurn();
            }
            catch (CardsExhaustedException ex) {
                manager.handleCardsExhausted(ex.getMessage());
            }
            catch (NumberFormatException ex) {
                indicateStatus("Invalid rank... Please enter a rank from 1-13");
            }
        });
        controls.getChildren().addAll(requestBttn, rankInput);
    }

    public void takeTurn() {
        FadeTransition revealControls = new FadeTransition(Duration.millis(350), controls);
        revealControls.setFromValue(0.0);
        revealControls.setToValue(1.0);
        revealControls.setOnFinished((event) -> {controls.setVisible(true);}
        );
        revealControls.play();
    }

    /**
     * D4 - Override the non-`abstract` method `updateHand()`
     * to extend its functionality for human players
     * 
     * Change cards to be visible (for a Human Player)
     */
    @Override
    public void updateHand() {
        super.updateHand();
        getChildren().add(0, controls);
        for (Node node: getChildren()) {
            if (node instanceof Card) {
                Card card = (Card)node;
                card.setImage(new Image(
                    String.format("resources/%s/tile%03d.png", 
                    card.getSuit(), card.getRank()-1)
                ));

            }
        }
    }
}
