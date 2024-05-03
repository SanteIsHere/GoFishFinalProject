

public class CPUPlayer extends Player {
    

    public CPUPlayer(GoFish.GameManager manager) {
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
