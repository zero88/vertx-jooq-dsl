package io.zero88.jooqx.adapter;

import org.jooq.Record;
import org.jooq.TableLike;

import io.zero88.jooqx.SQLResultConverter;

import lombok.NonNull;

/**
 * SQL Result adapter receives Result set then mapping to expected result
 *
 * @param <R> Type of Vertx Result set
 * @param <C> Type of result set converter
 * @param <T> Type of jOOQ Table
 * @param <O> Type of an expectation output
 * @see TableLike
 * @see Record
 * @see SQLResultConverter
 * @since 1.0.0
 */
public interface SQLResultAdapter<R, C extends SQLResultConverter<R>, T extends TableLike<? extends Record>, O>
    extends HasStrategy {

    /**
     * A current context holder
     *
     * @return jOOQ table
     * @see TableLike
     */
    @NonNull T table();

    /**
     * Declares Result set converter
     *
     * @return converter
     */
    @NonNull C converter();

    /**
     * Convert result set to expected result
     *
     * @param resultSet result set
     * @return result
     */
    O convert(@NonNull R resultSet);

}
