/**
 * D11 - Custom exception class handling case where the pool is
 * exhausted in a GoFish game
 */
public class CardsExhaustedException extends IndexOutOfBoundsException {
    public CardsExhaustedException(String message) {
        super(message);
    }
}
