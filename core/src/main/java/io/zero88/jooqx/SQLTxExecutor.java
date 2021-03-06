package io.zero88.jooqx;

import java.util.function.Function;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import lombok.NonNull;

/**
 * Represents for transaction executor
 *
 * @param <S>  Type of Vertx SQL client
 * @param <B>  Type of Vertx query param holder
 * @param <P>  Type of SQL prepare query
 * @param <RS> Type of Vertx result set
 * @param <C>  Type of SQL result collector
 * @param <E>  Type of jOOQ.x executor
 * @since 1.0.0
 */
public interface SQLTxExecutor<S, B, P extends SQLPreparedQuery<B>, RS, C extends SQLResultCollector<RS>,
                                  E extends SQLExecutor<S, B, P, RS, C>> {

    /**
     * Run the transactional code
     *
     * @param function transaction function
     * @param handler  handler
     * @param <X>      Type of result
     */
    default <X> void run(@NonNull Function<E, Future<X>> function, @NonNull Handler<AsyncResult<X>> handler) {
        run(function).onComplete(handler);
    }

    /**
     * Like {@link #run(Function, Handler)} but returns a {@code Future} of the asynchronous result
     *
     * @param function transaction function
     * @param <X>      Type of result
     * @return a {@code Future} of the asynchronous result
     */
    <X> Future<X> run(@NonNull Function<E, Future<X>> function);

}
