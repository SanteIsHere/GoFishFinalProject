import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CPUPlayer extends Player {
    private Text requestedRank = new Text();

    public CPUPlayer() {
        requestedRank.setVisible(false);
        getChildren().add(requestedRank);
    }

    @Override
    public void updateHand() {
        super.updateHand();
        for (Card card: hand) {
            card.setImage(new Image("resources/CardBackBlue.png"));
        }
        getChildren().add(requestedRank);
    }

    @Override
    public String toString() {
        return "Computer-controlled Player";
    }

    public void takeTurn(HumanPlayer player) {
        int initHandSize = hand.size();
        requestedRank.setVisible(true);
        int randomRank = (int)((Math.random()*13)+1);
        requestedRank.setText(
            String.format("CPU requested cards of rank: %d", 
            randomRank)
        );
        requestCards(randomRank, player);

        if (hand.size() > initHandSize)
            requestedRank.setText(
                String.format("Received %d cards of rank: %d",
            (hand.size() - initHandSize), randomRank));
        else
            requestedRank.setText("No cards received...");

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                requestedRank.setVisible(false);
                
            }
        });
        
        delay.play();
        
    }
}
