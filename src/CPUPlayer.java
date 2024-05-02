import javafx.scene.image.Image;

public class CPUPlayer extends Player {
    

    public CPUPlayer(GoFish.GameManager manager) {
    }

    @Override
    public void updateHand() {
        super.updateHand();
        for (Card card: hand) {
            card.setImage(new Image("resources/CardBackBlue.png"));
        }
    }

    @Override
    public String toString() {
        return "Computer-controlled Player";
    }

    public void takeTurn(HumanPlayer player) {
        int randomRank = ((int)(Math.random()*13))+1;
        requestCards(randomRank, player);        
    }
}
