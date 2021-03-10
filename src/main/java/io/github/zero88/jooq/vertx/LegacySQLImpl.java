package io.github.zero88.jooq.vertx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.conf.ParamType;

import io.github.zero88.jooq.vertx.SQLImpl.SQLPQ;
import io.github.zero88.jooq.vertx.SQLImpl.SQLRSC;
import io.github.zero88.jooq.vertx.adapter.SelectStrategy;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;

import lombok.NonNull;

final class LegacySQLImpl {

    static final class LegacySQLPQ extends SQLPQ<JsonArray> implements LegacySQLPreparedQuery {

        public String sql(@NonNull Configuration configuration, @NonNull Query query) {
            return sql(configuration, query, ParamType.INDEXED);
        }

        @Override
        protected JsonArray doConvert(Map<String, Param<?>> params, BiFunction<String, Param<?>, ?> queryValue) {
            JsonArray array = new JsonArray();
            params.entrySet()
                  .stream()
                  .filter(entry -> !entry.getValue().isInline())
                  .forEachOrdered(etr -> array.add(toDatabaseType(etr.getKey(), etr.getValue(), queryValue)));
            return array;
        }

    }


    static final class LegacySQLRSC extends SQLRSC<ResultSet> implements LegacySQLResultConverter {

        protected <T extends TableLike<? extends Record>, R> List<R> doConvert(ResultSet rs, T table,
                                                                               @NonNull Function<JsonRecord<?>, R> mapper) {
            final Map<String, Field<?>> fieldMap = table.fieldStream()
                                                        .collect(Collectors.toMap(Field::getName, Function.identity()));
            final Map<Integer, Field<?>> map = getColumnMap(rs, fieldMap::get);
            final List<JsonArray> results = rs.getResults();
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

        private Map<Integer, Field<?>> getColumnMap(ResultSet rs, Function<String, Field<?>> lookupField) {
            return IntStream.range(0, rs.getNumColumns())
                            .boxed()
                            .collect(Collectors.toMap(i -> i, i -> lookupField.apply(rs.getColumnNames().get(i))));
        }

        @Override
        public int batchResultSize(List<Integer> batchResult) {
            return batchResult.size();
        }

    }


    static final class LegacyDSLAI extends MiscImpl.DSLAdapterImpl<ResultSet, LegacySQLResultConverter>
        implements LegacyDSLAdapter {

        LegacyDSLAI(@NonNull LegacySQLResultConverter converter) {
            super(converter);
        }

        LegacyDSLAI() {
            super(new LegacySQLRSC());
        }

    }

}
