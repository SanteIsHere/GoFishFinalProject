import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class HumanPlayer extends Player {
    private VBox controls = new VBox();
    
    public HumanPlayer(Pool cardPool, CPUPlayer opponent) {
        /* Initialize a `Button` control and `TextField` to allow a `Player`
         * to request cards of a rank
         */

        pool = cardPool;

        Button requestBttn = new Button("Ask for rank");

        TextField rankInput = new TextField();

        rankInput.setPromptText("Enter a number: 1-13");
        
        requestBttn.setOnAction((ActionEvent ev)
        -> {
            if (!takenTurn) {
                System.out.println("Requesting cards of rank " + Integer.valueOf(rankInput.getText()));
                requestCards(Integer.valueOf(rankInput.getText()), opponent);
                takenTurn = true;
                try {
                    controls.setVisible(false);
                    rankInput.clear();
                    TimeUnit.SECONDS.sleep(1);
                    opponent.takeTurn(this);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                takeTurn();
            }
            
        });
        controls.getChildren().addAll(requestBttn, rankInput);
    }

    public void takeTurn() {
        controls.setVisible(true);
        takenTurn = false;
    }

    /**
     * Change cards to be visible (for a Human Player)
     */
    @Override
    public void updateHand() {
        super.updateHand();
        getChildren().add(0, controls);
        for (Node node: getChildren())
            if (node instanceof Card) {
                Card card = (Card)node;
                card.setImage(new Image(
                    String.format("resources/%s/tile%03d.png", 
                    card.getSuit(), card.getRank()-1)
                    ));
            }
                
    }
}
