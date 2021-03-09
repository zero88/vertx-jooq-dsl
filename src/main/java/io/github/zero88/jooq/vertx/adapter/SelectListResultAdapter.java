package io.github.zero88.jooq.vertx.adapter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.VertxJooqRecord;
import io.github.zero88.jooq.vertx.converter.ResultSetConverter;

import lombok.NonNull;

public final class SelectListResultAdapter<RS, C extends ResultSetConverter<RS>,
                                              T extends TableLike<? extends Record>, R>
    extends AbstractSqlResultAdapter<RS, C, T, List<R>> {

    private final BiFunction<SqlResultAdapter<RS, C, T, List<R>>, RS, List<R>> function;

    private SelectListResultAdapter(@NonNull T table, @NonNull C converter,
                                    @NonNull BiFunction<SqlResultAdapter<RS, C, T, List<R>>, RS, List<R>> function) {
        super(table, converter);
        this.function = function;
    }

    @Override
    public @NonNull SelectStrategy strategy() {
        return SelectStrategy.MANY;
    }

    @Override
    public @NonNull List<R> convert(@NonNull RS resultSet) {
        return function.apply(this, resultSet);
    }

    public static <RS, C extends ResultSetConverter<RS>, T extends TableLike<? extends Record>> SelectListResultAdapter<RS, C, T, VertxJooqRecord<?>> vertxRecord(
        @NonNull T table, @NonNull C converter) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convertVertxRecord(rs, a.table()));
    }

    public static <RS, C extends ResultSetConverter<RS>, T extends TableLike<? extends Record>, R extends Record> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull R record) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, a.table(), record));
    }

    public static <RS, C extends ResultSetConverter<RS>, T extends TableLike<? extends Record>> SelectListResultAdapter<RS, C, T, Record> create(
        @NonNull T table, @NonNull C converter, @NonNull Collection<Field<?>> fields) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, table, fields));
    }

    public static <RS, C extends ResultSetConverter<RS>, T extends TableLike<? extends Record>, R> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull Class<R> outputClass) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convert(rs, a.table(), outputClass));
    }

    //TODO fix it
    public static <RS, C extends ResultSetConverter<RS>, T extends Table<? extends Record>, R extends Record> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter) {
        return new SelectListResultAdapter<>(table, converter, (a, rs) -> a.converter().convert(rs, a.table()));
    }

    public static <RS, C extends ResultSetConverter<RS>, T extends TableLike<? extends Record>, R extends Record,
                      Z extends Table<R>> SelectListResultAdapter<RS, C, T, R> create(
        @NonNull T table, @NonNull C converter, @NonNull Z toTable) {
        return new SelectListResultAdapter<>(table, converter,
                                             (a, rs) -> a.converter().convert(rs, a.table(), toTable));
    }

}
