package io.zero88.jooqx;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.Query;

import io.zero88.jooqx.datatype.DataTypeMapperRegistry;

import lombok.NonNull;

/**
 * Represents for SQL prepared query that transforms jOOQ Query to Vertx SQL prepared query
 *
 * @param <T> Type of Vertx bind value holder
 * @see LegacySQLPreparedQuery
 * @see ReactiveSQLPreparedQuery
 * @since 1.0.0
 */
interface SQLPreparedQuery<T> {

    /**
     * Generate jOOQ query to sql query in String
     *
     * @param configuration jOOQ configuration
     * @param query         jOOQ query
     * @return sql
     * @see Query
     */
    @NonNull String sql(@NonNull Configuration configuration, @NonNull Query query);

    /**
     * Capture jOOQ param in jOOQ query and convert to Vertx bind value holder
     *
     * @param query          jOOQ query
     * @param mapperRegistry Data type mapper registry
     * @return bind value holder
     * @see Param
     * @see Query
     * @see DataTypeMapperRegistry
     */
    @NonNull T bindValues(@NonNull Query query, @NonNull DataTypeMapperRegistry mapperRegistry);

    /**
     * Capture jOOQ param in jOOQ query and convert to Vertx bind value holder
     *
     * @param query           jOOQ query
     * @param bindBatchValues bind batch values
     * @param mapperRegistry  Data type mapper registry
     * @return list of bind value holder
     * @apiNote It is used for batch execution
     * @see BindBatchValues
     * @see SQLBatchExecutor#batch(Query, BindBatchValues)
     * @see DataTypeMapperRegistry
     */
    @NonNull List<T> bindValues(@NonNull Query query, @NonNull BindBatchValues bindBatchValues,
                                @NonNull DataTypeMapperRegistry mapperRegistry);

}
