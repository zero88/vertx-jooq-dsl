package io.zero88.jooqx;

import java.util.List;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.zero88.jooqx.adapter.RowConverterStrategy;

import lombok.NonNull;

/**
 * Reactive result batch converter
 *
 * @see ReactiveSQLResultCollector
 * @since 1.0.0
 */
@VertxGen
public interface ReactiveSQLBatchCollector
    extends ReactiveSQLResultCollector, SQLBatchCollector<RowSet<Row>, RowSet<Row>> {

    @Override
    @NonNull <T, R> List<R> collect(@NonNull RowSet<Row> resultSet, @NonNull RowConverterStrategy<T, R> strategy);

    @Override
    int batchResultSize(@NonNull RowSet<Row> batchResult);

}
