package io.github.zero88.jooq.vertx.adapter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.JsonRecord;
import io.github.zero88.jooq.vertx.SQLResultSetConverter;
import io.github.zero88.jooq.vertx.adapter.HasStrategy.SelectMany;
import io.github.zero88.jooq.vertx.adapter.SQLResultAdapterImpl.InternalSelectResultAdapter;

import lombok.NonNull;

/**
 * Select list result adapter that defines output in {@code List} type
 *
 * @param <R> Type of Vertx Result set
 * @param <C> Type of result set converter
 * @param <T> Type of jOOQ Table
 * @param <O> Type of an expectation output
 * @since 1.0.0
 */
public final class SelectListResultAdapter<R, C extends SQLResultSetConverter<R>, T extends TableLike<? extends Record>, O>
    extends InternalSelectResultAdapter<R, C, T, O, List<O>> implements SelectMany {

    private SelectListResultAdapter(@NonNull T table, @NonNull C converter,
                                    @NonNull BiFunction<SQLResultAdapter<R, C, T, List<O>>, R, List<O>> function) {
        super(table, converter, function);
    }

    @Override
    public @NonNull List<O> convert(@NonNull R resultSet) {
        return getFunction().apply(this, resultSet);
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends TableLike<? extends Record>> SelectListResultAdapter<RS, C, T, JsonRecord<?>> jsonRecord(
        @NonNull T table, @NonNull C converter) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convertJsonRecord(rs, a.table()));
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends TableLike<? extends Record>, R extends Record> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull R record) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, a.table(), record));
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends TableLike<? extends Record>> SelectListResultAdapter<RS, C, T, Record> create(
        @NonNull T table, @NonNull C converter, @NonNull Collection<Field<?>> fields) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, table, fields));
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends TableLike<? extends Record>, R> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull Class<R> outputClass) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convert(rs, a.table(), outputClass));
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends Table<R>, R extends Record> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, a.table()));
    }

    public static <RS, C extends SQLResultSetConverter<RS>, T extends TableLike<? extends Record>, R extends Record,
                      Z extends Table<R>> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull Z toTable) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convert(rs, a.table(), toTable));
    }

}
