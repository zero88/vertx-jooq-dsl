package io.github.zero88.jooq.vertx.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero88.jooq.vertx.JsonRecord;
import io.github.zero88.jooq.vertx.adapter.SelectStrategy;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;

import lombok.NonNull;

final class LegacySQLResultConverterImpl extends ResultSetConverterImpl<ResultSet> implements LegacySQLResultConverter {

    protected <T extends TableLike<? extends Record>, R> List<R> doConvert(ResultSet resultSet, T table,
                                                                           @NonNull Function<JsonRecord<?>, R> mapper) {
        final Map<String, Field<?>> fieldMap = table.fieldStream()
                                                    .collect(Collectors.toMap(Field::getName, Function.identity()));
        final Map<Integer, Field<?>> map = getColumnMap(resultSet, fieldMap::get);
        final List<JsonArray> results = resultSet.getResults();
        if (strategy == SelectStrategy.MANY) {
            return results.stream().map(row -> toRecord(table, map, row)).map(mapper).collect(Collectors.toList());
        } else {
            warnManyResult(results.size() > 1);
            return results.stream()
                          .findFirst()
                          .map(row -> toRecord(table, map, row))
                          .map(mapper)
                          .map(Collections::singletonList)
                          .orElse(new ArrayList<>());
        }
    }

    @SuppressWarnings( {"unchecked", "rawtypes"})
    private <T extends TableLike<? extends Record>> JsonRecord<?> toRecord(T table, Map<Integer, Field<?>> map,
                                                                           JsonArray row) {
        JsonRecord<?> record = JsonRecord.create((Table<JsonRecord>) table);
        map.forEach((k, v) -> record.set((Field<Object>) v, v.getType().cast(row.getValue(k))));
        return record;
    }

    private Map<Integer, Field<?>> getColumnMap(ResultSet resultSet, Function<String, Field<?>> lookupField) {
        return IntStream.range(0, resultSet.getNumColumns())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, i -> lookupField.apply(resultSet.getColumnNames().get(i))));
    }

    @Override
    public int batchResultSize(List<Integer> batchResult) {
        return batchResult.size();
    }

}