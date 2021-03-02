package io.github.zero88.jooq.vertx.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.record.VertxJooqRecord;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import lombok.NonNull;

/**
 * Bug <a href="vertx-sql-client#909">https://github.com/eclipse-vertx/vertx-sql-client/issues/909</a>
 *
 * @param <T> Type of jOOQ Table
 * @see Record
 * @see TableLike
 * @see ResultSetConverter
 */
public class SqlRowRecordConverter<T extends TableLike<? extends Record>>
    extends AbstractRowRecordConverter<RowSet<Row>, T> implements ResultSetConverter<RowSet<Row>, T> {

    public SqlRowRecordConverter(@NonNull T table) {
        super(table);
    }

    public List<VertxJooqRecord<?>> convert(@NonNull RowSet<Row> resultSet) {
        final List<VertxJooqRecord<?>> records = new ArrayList<>();
        resultSet.iterator().forEachRemaining(row -> {
            VertxJooqRecord<?> record = new VertxJooqRecord<>((Table<VertxJooqRecord>) table());
            IntStream.range(0, row.size())
                     .mapToObj(row::getColumnName)
                     .map(this::fieldMapper)
                     .filter(Objects::nonNull)
                     .forEach(f -> record.set(f, row.get(f.getType(), f.getName())));
            records.add(record);
        });
        return records;
    }

}