package io.github.zero88.jooq.vertx;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.adapter.SqlResultAdapter;
import io.vertx.core.AsyncResult;
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
 * @since 1.0.0
 */
public interface VertxJooqExecutor<S, P, RS> {

    @NonNull Vertx vertx();

    @NonNull DSLContext dsl();

    @NonNull S sqlClient();

    @NonNull QueryHelper<P> helper();

    /**
     * Execute {@code jOOQ query} then return async result
     *
     * @param query        jOOQ query
     * @param resultMapper a result mapper
     * @param handler      a async result handler
     * @param <Q>          type of jOOQ Query
     * @param <T>          type of jOOQ TableLike
     * @param <R>          type of expectation result
     * @see Query
     * @see TableLike
     * @see SqlResultAdapter
     */
    <Q extends Query, T extends TableLike<?>, R> void execute(@NonNull Q query,
                                                              @NonNull SqlResultAdapter<RS, T, R> resultMapper,
                                                              @NonNull Handler<AsyncResult<R>> handler);

}
