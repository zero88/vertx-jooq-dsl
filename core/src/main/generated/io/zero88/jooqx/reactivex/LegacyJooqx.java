/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.zero88.jooqx.reactivex;

import io.vertx.reactivex.RxHelper;
import io.vertx.reactivex.ObservableHelper;
import io.vertx.reactivex.FlowableHelper;
import io.vertx.reactivex.impl.AsyncResultMaybe;
import io.vertx.reactivex.impl.AsyncResultSingle;
import io.vertx.reactivex.impl.AsyncResultCompletable;
import io.vertx.reactivex.WriteStreamObserver;
import io.vertx.reactivex.WriteStreamSubscriber;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.lang.rx.RxGen;
import io.vertx.lang.rx.TypeArg;
import io.vertx.lang.rx.MappingIterator;

/**
 * Represents for an executor that executes <code>jOOQ query</code> on <code>Vertx legacy JDBC client</code> connection
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.zero88.jooqx.LegacyJooqx original} non RX-ified interface using Vert.x codegen.
 */

@RxGen(io.zero88.jooqx.LegacyJooqx.class)
public class LegacyJooqx {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LegacyJooqx that = (LegacyJooqx) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final TypeArg<LegacyJooqx> __TYPE_ARG = new TypeArg<>(    obj -> new LegacyJooqx((io.zero88.jooqx.LegacyJooqx) obj),
    LegacyJooqx::getDelegate
  );

  private final io.zero88.jooqx.LegacyJooqx delegate;
  
  public LegacyJooqx(io.zero88.jooqx.LegacyJooqx delegate) {
    this.delegate = delegate;
  }

  public LegacyJooqx(Object delegate) {
    this.delegate = (io.zero88.jooqx.LegacyJooqx)delegate;
  }

  public io.zero88.jooqx.LegacyJooqx getDelegate() {
    return delegate;
  }


  public io.vertx.reactivex.core.Vertx vertx() { 
    io.vertx.reactivex.core.Vertx ret = io.vertx.reactivex.core.Vertx.newInstance((io.vertx.core.Vertx)delegate.vertx());
    return ret;
  }

  public io.vertx.reactivex.ext.sql.SQLClient sqlClient() { 
    io.vertx.reactivex.ext.sql.SQLClient ret = io.vertx.reactivex.ext.sql.SQLClient.newInstance((io.vertx.ext.sql.SQLClient)delegate.sqlClient());
    return ret;
  }

  public io.zero88.jooqx.reactivex.LegacyJooqxTx transaction() { 
    io.zero88.jooqx.reactivex.LegacyJooqxTx ret = io.zero88.jooqx.reactivex.LegacyJooqxTx.newInstance((io.zero88.jooqx.LegacyJooqxTx)delegate.transaction());
    return ret;
  }

  public org.jooq.DSLContext dsl() { 
    org.jooq.DSLContext ret = delegate.dsl();
    return ret;
  }

  public io.zero88.jooqx.LegacySQLPreparedQuery preparedQuery() { 
    io.zero88.jooqx.LegacySQLPreparedQuery ret = delegate.preparedQuery();
    return ret;
  }

  public io.zero88.jooqx.SQLErrorConverter<? extends java.lang.Throwable,? extends  java.lang.RuntimeException> errorConverter() {
    io.zero88.jooqx.SQLErrorConverter<? extends java.lang.Throwable,? extends java.lang.RuntimeException> ret = delegate.errorConverter();
    return ret;
  }

  public <T extends org.jooq.TableLike<?>, R> void execute(org.jooq.Query query, io.zero88.jooqx.adapter.SQLResultAdapter<io.vertx.ext.sql.ResultSet, io.zero88.jooqx.LegacySQLConverter, T, R> resultAdapter, Handler<AsyncResult<R>> handler) { 
    delegate.execute(query, resultAdapter, handler);
  }

  public <T extends org.jooq.TableLike<?>, R> void execute(org.jooq.Query query, io.zero88.jooqx.adapter.SQLResultAdapter<io.vertx.ext.sql.ResultSet, io.zero88.jooqx.LegacySQLConverter, T, R> resultAdapter) {
    execute(query, resultAdapter, ar -> { });
  }

  public <T extends org.jooq.TableLike<?>, R> io.reactivex.Maybe<R> rxExecute(org.jooq.Query query, io.zero88.jooqx.adapter.SQLResultAdapter<io.vertx.ext.sql.ResultSet, io.zero88.jooqx.LegacySQLConverter, T, R> resultAdapter) { 
    return AsyncResultMaybe.toMaybe($handler -> {
      execute(query, resultAdapter, $handler);
    });
  }

  public void batch(org.jooq.Query query, io.zero88.jooqx.BindBatchValues bindBatchValues, Handler<AsyncResult<io.zero88.jooqx.BatchResult>> handler) { 
    delegate.batch(query, bindBatchValues, handler);
  }

  public void batch(org.jooq.Query query, io.zero88.jooqx.BindBatchValues bindBatchValues) {
    batch(query, bindBatchValues, ar -> { });
  }

  public io.reactivex.Single<io.zero88.jooqx.BatchResult> rxBatch(org.jooq.Query query, io.zero88.jooqx.BindBatchValues bindBatchValues) { 
    return AsyncResultSingle.toSingle($handler -> {
      batch(query, bindBatchValues, $handler);
    });
  }

  public static LegacyJooqx newInstance(io.zero88.jooqx.LegacyJooqx arg) {
    return arg != null ? new LegacyJooqx(arg) : null;
  }

}