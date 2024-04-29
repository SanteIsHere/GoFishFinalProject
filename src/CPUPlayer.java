import javafx.scene.image.Image;

public class CPUPlayer extends Player {
    @Override
    public void updateHand() {
        super.updateHand();
        for (Card card: hand) {
            card.setImage(new Image("resources/CardBackBlue.png"));
        }
    }
}
