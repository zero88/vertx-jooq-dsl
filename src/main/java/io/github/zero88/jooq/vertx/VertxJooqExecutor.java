package io.github.zero88.jooq.vertx;

import java.util.function.Function;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.adapter.SqlResultAdapter;
import io.github.zero88.jooq.vertx.converter.ResultSetConverter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.sql.SQLClient;
import io.vertx.sqlclient.SqlClient;

import lombok.NonNull;

/**
 * Represents for an executor that executes {@code jOOQ query} on {@code Vertx SQL client} connection
 *
 * @param <S>  Type of Vertx SQL client. Might be {@link SqlClient} or {@link SQLClient}
 * @param <P>  Type of Vertx SQL bind value holder
 * @param <RS> Type of Vertx SQL Result set holder
 * @see VertxLegacyJdbcExecutor
 * @see VertxReactiveSqlExecutor
 * @see VertxBatchExecutor
 * @since 1.0.0
 */
public interface VertxJooqExecutor<S, P, RS> extends VertxBatchExecutor {

    /**
     * Vertx
     *
     * @return vertx
     */
    @NonNull Vertx vertx();

    /**
     * Define jOOQ DSL context
     *
     * @return DSL context
     * @see DSLContext
     */
    @NonNull DSLContext dsl();

    /**
     * Defines sql client
     *
     * @return sql client
     */
    @NonNull S sqlClient();

    /**
     * Defines query helper
     *
     * @return query helper
     */
    @NonNull QueryHelper<P> helper();

    /**
     * Defines an error converter that rethrows an uniform exception by {@link SqlErrorConverter#reThrowError(Throwable)
     * if any error in execution time
     *
     * @return error handler
     * @apiNote Default is {@link SqlErrorConverter#DEFAULT} that keeps error as it is
     * @see SqlErrorConverter
     */
    @NonNull SqlErrorConverter<? extends Throwable, ? extends RuntimeException> errorConverter();

    /**
     * Execute {@code jOOQ query} then return async result
     *
     * @param query         jOOQ query
     * @param resultAdapter a result adapter
     * @param handler       a async result handler
     * @param <Q>           type of jOOQ Query
     * @param <T>           type of jOOQ TableLike
     * @param <C>           type of result set converter
     * @param <R>           type of expectation result
     * @see Query
     * @see TableLike
     * @see SqlResultAdapter
     */
    default <Q extends Query, T extends TableLike<?>, C extends ResultSetConverter<RS>, R> void execute(
        @NonNull Q query, @NonNull SqlResultAdapter<RS, C, T, R> resultAdapter,
        @NonNull Handler<AsyncResult<R>> handler) {
        execute(query, resultAdapter).onComplete(handler);
    }

    /**
     * Like {@link #execute(Query, SqlResultAdapter, Handler)} but returns a {@code Future} of the asynchronous result
     *
     * @param query         jOOQ query
     * @param resultAdapter a result adapter
     * @param <Q>           type of jOOQ Query
     * @param <T>           type of jOOQ TableLike
     * @param <C>           type of result set converter
     * @param <R>type       of expectation result
     * @return a {@code Future} of the asynchronous result
     */
    <Q extends Query, T extends TableLike<?>, C extends ResultSetConverter<RS>, R> Future<R> execute(@NonNull Q query,
                                                                                                     @NonNull SqlResultAdapter<RS, C, T, R> resultAdapter);

    default <X> void withTransaction(@NonNull Function<VertxJooqExecutor<S, P, RS>, Future<X>> transaction,
                                     @NonNull Handler<AsyncResult<X>> handler) {
        withTransaction(transaction).onComplete(handler);
    }

    <X> Future<X> withTransaction(@NonNull Function<VertxJooqExecutor<S, P, RS>, Future<X>> transaction);

}
