package io.zero88.jooqx;

import org.jooq.DSLContext;
import org.jooq.Query;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.SQLOperations;
import io.zero88.jooqx.LegacySQLImpl.LegacyInternal;
import io.zero88.jooqx.LegacySQLImpl.LegacyJooqxImpl;
import io.zero88.jooqx.adapter.SQLResultAdapter;
import io.zero88.jooqx.datatype.DataTypeMapperRegistry;

import lombok.NonNull;

/**
 * Represents for an executor that executes {@code jOOQ query} on {@code Vertx legacy JDBC client} connection
 *
 * @see SQLClient
 * @see SQLConnection
 * @see SQLOperations
 * @see JDBCClient
 * @see ResultSet
 * @since 1.0.0
 */
@VertxGen
public interface LegacyJooqx extends LegacySQLImpl.LegacyInternal<SQLClient> {

    @GenIgnore
    static LegacyJooqxBuilder builder() {
        return new LegacyJooqxBuilder();
    }

    @Override
    @NonNull Vertx vertx();

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    @NonNull DSLContext dsl();

    @Override
    @NonNull SQLClient sqlClient();

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    @NonNull LegacySQLPreparedQuery preparedQuery();

    @Override
    @NonNull LegacySQLCollector resultCollector();

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    @NonNull SQLErrorConverter errorConverter();

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    @NonNull DataTypeMapperRegistry typeMapperRegistry();

    @Override
    @SuppressWarnings("unchecked")
    @NonNull LegacyJooqxTx transaction();

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    <T, R> Future<@Nullable R> execute(@NonNull Query query, @NonNull SQLResultAdapter<T, R> adapter);

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    default <T, R> void execute(@NonNull Query query, @NonNull SQLResultAdapter<T, R> resultAdapter,
                                @NonNull Handler<AsyncResult<@Nullable R>> handler) {
        LegacyInternal.super.execute(query, resultAdapter, handler);
    }

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    default void batch(@NonNull Query query, @NonNull BindBatchValues bindBatchValues,
                       @NonNull Handler<AsyncResult<BatchResult>> handler) {
        LegacyInternal.super.batch(query, bindBatchValues, handler);
    }

    @Override
    @GenIgnore(GenIgnore.PERMITTED_TYPE)
    Future<BatchResult> batch(@NonNull Query query, @NonNull BindBatchValues bindBatchValues);

    @GenIgnore
    class LegacyJooqxBuilder extends SQLExecutorBuilder<SQLClient, JsonArray, LegacySQLPreparedQuery, ResultSet,
                                                       LegacySQLCollector, LegacyJooqx> {

        @Override
        public LegacyJooqx build() {
            return new LegacyJooqxImpl(vertx(), dsl(), sqlClient(), preparedQuery(), resultCollector(),
                                       errorConverter(), typeMapperRegistry());
        }

    }

}
