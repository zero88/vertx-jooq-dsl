package io.zero88.jooqx.integtest.reactive;

import java.util.Arrays;

import org.jooq.InsertResultStep;
import org.jooq.exception.SQLStateClass;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.zero88.jooqx.ReactiveDSLAdapter;
import io.zero88.jooqx.ReactiveSQLClientProvider.ReactiveJDBCClientProvider;
import io.zero88.jooqx.ReactiveSQLTest.ReactiveDBMemoryTest;
import io.zero88.jooqx.SQLErrorConverter;
import io.zero88.jooqx.integtest.H2SQLHelper;
import io.zero88.jooqx.integtest.h2.tables.Author;
import io.zero88.jooqx.integtest.h2.tables.records.AuthorRecord;
import io.zero88.jooqx.spi.H2DBProvider;
import io.zero88.jooqx.spi.JDBCErrorConverter;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class H2ReactiveFailedTest extends ReactiveDBMemoryTest<JDBCPool>
    implements H2DBProvider, H2SQLHelper, ReactiveJDBCClientProvider {

    @Override
    public SQLErrorConverter<? extends Throwable, ? extends RuntimeException> createErrorConverter() {
        return new JDBCErrorConverter();
    }

    @Test
    void test(VertxTestContext testContext) {
        final Checkpoint flag = testContext.checkpoint();
        final Author table = catalog().DEFAULT_SCHEMA.AUTHOR;
        final InsertResultStep<AuthorRecord> insert = executor.dsl()
                                                              .insertInto(table, table.ID, table.FIRST_NAME)
                                                              .values(Arrays.asList(DSL.defaultValue(table.ID), "abc"))
                                                              .returning(table.ID);
        executor.execute(insert, ReactiveDSLAdapter.instance().fetchJsonRecord(table),
                         ar -> assertJooqException(testContext, flag, ar,
                                                   SQLStateClass.C42_SYNTAX_ERROR_OR_ACCESS_RULE_VIOLATION,
                                                   "Table \"AUTHOR\" not found; SQL statement:\n" +
                                                   "insert into \"AUTHOR\" (\"ID\", \"FIRST_NAME\") values (default, ?) " +
                                                   "[42102-200]"));
    }

}