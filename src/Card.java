import java.util.Map;
import static java.util.Map.entry;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
* Concrete class representing a "Go Fish" card. Implements 
the `Comparable` interface and `compareTo` method to allow comparisons
between instances (and sorting)

@field suit The suit of the card
@field rank The card's rank (randomly generated integer 1-13) 
*/
public class Card extends ImageView implements Comparable<Card> {
    // Shared field - an array containing all valid suits for a card
    private static String[] validSuits =
    {"Clubs", "Diamonds", "Hearts", "Spades"};
   
    // Map that assigns numeric ranks to their symbolic value
    private static Map<Integer, String> rankMap =
    Map.ofEntries(
        entry(1, "A"),
        entry(11, "J"),
        entry(12, "Q"),
        entry(13, "K")
    );

    // The suit assigned to a `Card` instance
    private String suit;

    // The rank assigned to a `Card` instance (randomly chosen)
    private int rank = (int)(Math.random()*13)+1;
    
    public Card() {
        // Assign a random suit to the card
        this.suit = validSuits[(int)(Math.random()*4)];


        // Set the image for a (Player's) card
        setImage(
            new Image(String.format("resources/%s/tile%03d.png", suit, rank-1))
        );
    }

    @Override
    /**
     * Implement `compareTo` to allow
     * for comparisons between cards
     */
    public int compareTo(Card otherCard) {
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

    /**
     * Accessor for card's rank
     * @return The card's rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Accessor for card's suit
     * @return The card's suit
     */
    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return String.format("Card: %s of %s", 
        getSymbolicRank(), suit);
    }
}
