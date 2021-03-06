package io.zero88.jooqx.integtest.spi.mysql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.vertx.core.Vertx;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxTestContext;
import io.vertx.mysqlclient.MySQLPool;
import io.zero88.jooqx.DSLAdapter;
import io.zero88.jooqx.ReactiveTestDefinition.ReactiveRxHelper;
import io.zero88.jooqx.spi.mysql.MySQLPoolProvider;
import io.zero88.jooqx.spi.mysql.MySQLReactiveTest;

@Disabled
//FIXME: Don't understand why it doesnt connect
public class MySQLReARxTest extends MySQLReactiveTest<MySQLPool>
    implements MySQLPoolProvider, MySQLHelper, ReactiveRxHelper {

    @Override
    @BeforeEach
    public void tearUp(Vertx vertx, VertxTestContext ctx) {
        super.tearUp(vertx, ctx);
        this.prepareDatabase(ctx, this, connOpt, "mysql_schema.sql", "mysql_data/book_author.sql");
    }

    @Test
    void test_query_authors(VertxTestContext ctx) {
        final io.zero88.jooqx.integtest.mysql.tables.Authors table = schema().AUTHORS;
        Checkpoint cp = ctx.checkpoint();
        rxPool(jooqx).rxExecute(jooqx.dsl().selectFrom(table), DSLAdapter.fetchJsonRecords(table)).subscribe(recs -> {
            ctx.verify(() -> Assertions.assertEquals(7, recs.size()));
            cp.flag();
        }, ctx::failNow);
    }

}
