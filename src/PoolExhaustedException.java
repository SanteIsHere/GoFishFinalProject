/**
 * Custom exception class handling case where the pool is
 * exhausted in a GoFish game
 */
public class PoolExhaustedException extends RuntimeException {
    public PoolExhaustedException(String message, Throwable err) {
        super(message, err);
    }
}
