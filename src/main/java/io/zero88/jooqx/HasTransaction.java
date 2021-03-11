package io.zero88.jooqx;

import lombok.NonNull;

interface HasTransaction {

    /**
     * Open transaction executor
     *
     * @param <E> Type of VertxJooqExecutor
     * @return transaction executor
     * @see SQLTxExecutor
     */
    @NonNull <S, P, RS, E extends SQLExecutor<S, P, RS>> SQLTxExecutor<S, P, RS, E> transaction();

}