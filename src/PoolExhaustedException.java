/**
 * Custom exception class handling case where the pool is
 * exhausted in a GoFish game
 */
public class PoolExhaustedException extends IndexOutOfBoundsException {
    public PoolExhaustedException(String message) {
        super(message);
    }
}
