import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import javafx.scene.image.ImageView;

/**
 * D3 - Abstract Class representing a Card. Extends `ImageView` so that it 
 * can be part of a Scene Graph
 * 
 * @field rank - The value associated with the Card (1-13)
 * @field suit - The card's suit ("Clubs", "Diamonds", 
 * "Hearts", "Spades")
 */
abstract class Card extends ImageView {
    protected static String[] validSuits =
    {"Clubs", "Diamonds", "Hearts", "Spades"};
   
    protected static Map<Integer, String> rankMap =
    Map.ofEntries(
        entry(1, "A"),
        entry(11, "J"),
        entry(12, "Q"),
        entry(13, "K")
    );

    @Override
    public String toString() {
        return "Go Fish Card";
    }


}