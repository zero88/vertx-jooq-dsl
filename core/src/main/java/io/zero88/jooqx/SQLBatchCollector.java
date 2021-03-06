package io.zero88.jooqx;

import lombok.NonNull;

/**
 * Result batch converter
 *
 * @param <RS> Type of Vertx SQL result set
 * @param <BR> Type of Vertx SQL batch result
 * @see SQLResultCollector
 * @since 1.0.0
 */
interface SQLBatchCollector<RS, BR> extends SQLResultCollector<RS> {

    /**
     * Compute Batch Result size
     *
     * @param batchResult batch result
     * @return result size
     */
    int batchResultSize(@NonNull BR batchResult);

}
