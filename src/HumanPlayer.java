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
            System.out.println("Requesting cards of rank " + Integer.valueOf(rankInput.getText()));
            requestCards(Integer.valueOf(rankInput.getText()), opponent);
        });
        controls.getChildren().addAll(requestBttn, rankInput);
        // getChildren().add(controls);
    }

    @Override
    public void updateHand() {
        super.updateHand();
        getChildren().add(0, controls);
        for (Node node: getChildren())
            if (node instanceof Card)
                ((Card)node).setImage(new Image(
                    String.format("resources/%s/tile%03d.png", 
                    ((Card)node).getSuit(), ((Card)node).getRank()-1)
                    ));
    }
}
