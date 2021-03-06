package io.github.zero88.jooq.vertx;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.converter.ResultBatchConverter;
import io.github.zero88.jooq.vertx.converter.ResultSetConverter;
import io.github.zero88.jooq.vertx.record.VertxJooqRecord;
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
 * @param <BS> Type of Batch result
 * @since 1.0.0
 */
public interface VertxJooqExecutor<S, P, RS, BS extends BatchResult> {

    @NonNull Vertx vertx();

    @NonNull DSLContext dsl();

    @NonNull S sqlClient();

    @NonNull QueryHelper<P> helper();

    /**
     * Execute {@code jOOQ query} then return async result list of {@link VertxJooqRecord}
     *
     * @param query       jOOQ query
     * @param rsConverter a result set converter
     * @param handler     a async result handler
     * @param <Q>         type of jOOQ Query
     * @param <T>         type of jOOQ TableLike
     * @see Query
     * @see TableLike
     * @see ResultSetConverter#convert(RS)
     * @see VertxJooqRecord
     */
    <Q extends Query, T extends TableLike<?>> void execute(@NonNull Q query,
                                                           @NonNull ResultSetConverter<RS, T> rsConverter,
                                                           @NonNull Handler<AsyncResult<List<VertxJooqRecord<?>>>> handler);

    /**
     * Execute {@code jOOQ query} then return
     *
     * @param query       jOOQ query
     * @param rsConverter a result set converter
     * @param table       an expectation context holder keep a converting record type
     * @param handler     an async result handler
     * @param <Q>         type of jOOQ Query
     * @param <T>         type of jOOQ TableLike
     * @param <R>         type of Record
     * @see Query
     * @see TableLike
     * @see Record
     * @see ResultSetConverter#convert(RS, Table)
     */
    <Q extends Query, T extends TableLike<?>, R extends Record> void execute(@NonNull Q query,
                                                                             @NonNull ResultSetConverter<RS, T> rsConverter,
                                                                             @NonNull Table<R> table,
                                                                             @NonNull Handler<AsyncResult<List<R>>> handler);

    /**
     * Execute {@code jOOQ query} then return list of record asynchronous
     *
     * @param query       jOOQ query
     * @param rsConverter a result set converter
     * @param recordClass an expectation record class
     * @param handler     an async result handler
     * @param <Q>         type of jOOQ Query
     * @param <T>         type of jOOQ TableLike
     * @param <R>         type of expectation record
     * @see Query
     * @see TableLike
     * @see ResultSetConverter#convert(RS, Class)
     */
    <Q extends Query, T extends TableLike<?>, R> void execute(@NonNull Q query,
                                                              @NonNull ResultSetConverter<RS, T> rsConverter,
                                                              @NonNull Class<R> recordClass,
                                                              @NonNull Handler<AsyncResult<List<R>>> handler);

    /**
     * Batch execute {@code jOOQ DML} then return total record asynchronous
     *
     * @param <Q>             type of jOOQ Query
     * @param <T>             type of jOOQ TableLike
     * @param query           jOOQ query
     * @param bindBatchValues a bind batch values
     * @param rsConverter     a result set converter
     * @param handler         an async result handler
     * @see Query
     * @see TableLike
     * @see BindBatchValues
     * @see ResultBatchConverter
     */
    <Q extends Query, T extends TableLike<?>> void batchExecute(@NonNull Q query,
                                                                @NonNull BindBatchValues bindBatchValues,
                                                                @NonNull ResultBatchConverter<RS, T> rsConverter,
                                                                @NonNull Handler<AsyncResult<BS>> handler);

}
