package io.zero88.jooqx;

import java.util.Collection;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.zero88.jooqx.ReactiveSQLImpl.ReactiveDSLAI;
import io.zero88.jooqx.ReactiveSQLImpl.ReactiveSQLRBC;
import io.zero88.jooqx.adapter.SelectListResultAdapter;

import lombok.NonNull;

/**
 * Vertx Reactive SQL DSL
 *
 * @see ReactiveSQLBatchConverter
 * @since 1.0.0
 */
public interface ReactiveDSL extends DSLAdapter<RowSet<Row>, ReactiveSQLResultConverter> {

    static @NonNull ReactiveDSL adapter() {
        return new ReactiveDSLAI();
    }

    static @NonNull ReactiveDSL create(@NonNull ReactiveSQLResultConverter converter) {
        return new ReactiveDSLAI(converter);
    }

    /**
     * Batch
     *
     * @param table give table
     * @param <T>   Type of table
     * @return batch adapter
     */
    default <T extends TableLike<?>> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T,
                                                                JsonRecord<?>> batchJsonRecords(
        @NonNull T table) {
        return SelectListResultAdapter.jsonRecord(table, new ReactiveSQLRBC());
    }

    /**
     * Batch
     *
     * @param table  given table
     * @param fields given fields
     * @param <T>    Type of table
     * @return batch adapter
     * @see TableLike
     */
    default <T extends TableLike<? extends Record>> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T,
                                                                               Record> batch(
        @NonNull T table, @NonNull Collection<Field<?>> fields) {
        return SelectListResultAdapter.create(table, new ReactiveSQLRBC(), fields);
    }

    /**
     * Batch
     *
     * @param table       given table
     * @param outputClass given output class
     * @param <T>         Type of table
     * @param <R>         Type ot output class
     * @return batch adapter
     * @see TableLike
     */
    default <T extends TableLike<? extends Record>, R> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T,
                                                                                  R> batch(
        @NonNull T table, @NonNull Class<R> outputClass) {
        return SelectListResultAdapter.create(table, new ReactiveSQLRBC(), outputClass);
    }

    /**
     * Batch
     *
     * @param table given table
     * @param <T>   Type of table
     * @return batch adapter
     * @see TableLike
     */
    default <T extends Table<R>, R extends Record> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T, R> batch(
        @NonNull T table) {
        return SelectListResultAdapter.create(table, new ReactiveSQLRBC());
    }

    /**
     * Batch
     *
     * @param <T>   Type of table
     * @param <R>   Type of record
     * @param <Z>   Type of expectation table
     * @param table given table
     * @return batch adapter
     * @see TableLike
     */
    default <T extends TableLike<? extends Record>, R extends Record, Z extends Table<R>> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T, R> batch(
        @NonNull T table, @NonNull Z toTable) {
        return SelectListResultAdapter.create(table, new ReactiveSQLRBC(), toTable);
    }

    /**
     * Batch
     *
     * @param table  given table
     * @param record Type of record
     * @param <T>    Type of table
     * @param <R>    Type expectation record
     * @return batch adapter
     */
    default <T extends TableLike<? extends Record>, R extends Record> SelectListResultAdapter<RowSet<Row>, ReactiveSQLBatchConverter, T, R> batch(
        @NonNull T table, @NonNull R record) {
        return SelectListResultAdapter.create(table, new ReactiveSQLRBC(), record);
    }

}