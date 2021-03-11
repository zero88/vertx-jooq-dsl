package io.zero88.jooqx;

import java.util.List;

import org.jooq.Query;

import io.zero88.jooqx.adapter.SelectListResultAdapter;
import io.vertx.core.Handler;

import lombok.NonNull;

/**
 * Batch result includes returning record
 *
 * @param <R> Type of records
 * @see ReactiveSQLBatchExecutor#batch(Query, BindBatchValues, SelectListResultAdapter, Handler)
 * @since 1.0.0
 */
public interface BatchReturningResult<R> extends BatchResult {

    @NonNull List<R> getRecords();

}