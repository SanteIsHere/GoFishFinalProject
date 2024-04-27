import javafx.scene.image.Image;

/**
* Concrete class representing a "Go Fish" card. Implements 
the `Comparable` interface and `compareTo` method to allow comparisons
between instances (and sorting)

@field suit The suit of the card
@field rank The card's rank (randomly generated integer 1-13) 
*/
public class GoFishCard extends Card implements Comparable<GoFishCard> {
    private String suit;
    private int rank = (int)(Math.random()*13)+1;
    
    public GoFishCard() {
        this.suit = validSuits[(int)(Math.random()*4)];

        // Debug: Print path of image to console to validate 
        // System.out.println(String.format("resources/%s/tile%03d.png", suit, rank));

        // Set the image for the card
        setImage(new Image(
            String.format("resources/%s/tile%03d.png", suit, rank-1)
            )
        );

        System.out.println(this);
    }

    @Override
    /**
     * Implement `compareTo` to allow
     * for comparisons between cards
     */
    public int compareTo(GoFishCard otherCard) {
        return (this.rank - otherCard.rank);
    }

    /**
     * Get the symbolic representation of this card's rank:
     * e.g 1 -> "A"
     * 2 .. 10 -> "2 .. 10"
     * 11 -> "J"
     * 12 -> "Q"
     * 13 -> "K"
     * @return The symbol associated with the rank
     */
    private String getSymbolicRank() {
        if (this.rank < 2 || this.rank > 10)
            return rankMap.get(this.rank);
        else
            return Integer.toString(this.rank);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(": %s of %s", 
        getSymbolicRank(), suit);
    }
}
