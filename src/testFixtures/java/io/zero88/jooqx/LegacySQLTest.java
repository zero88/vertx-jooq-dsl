package io.zero88.jooqx;

import org.testcontainers.containers.JdbcDatabaseContainer;

import io.zero88.jooqx.BaseSQLTestImpl.DBContainerSQLTest;
import io.zero88.jooqx.BaseSQLTestImpl.DBMemorySQLTest;
import io.zero88.jooqx.DBProvider.DBContainerProvider;
import io.zero88.jooqx.DBProvider.DBMemoryProvider;
import io.zero88.jooqx.LegacySQLClientProvider.LegacySQLExecutorProvider;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;

public interface LegacySQLTest<K, D extends DBProvider<K>>
    extends BaseSQLTest<SQLClient, JsonArray, ResultSet, LegacySQLExecutor, K, D>, LegacySQLExecutorProvider,
            LegacySQLClientProvider {

    @Override
    default SQLClientProvider<SQLClient> clientProvider() { return this; }

    @Override
    default LegacySQLExecutorProvider executorProvider() { return this; }

    abstract class LegacyDBContainerTest<K extends JdbcDatabaseContainer<?>>
        extends DBContainerSQLTest<SQLClient, JsonArray, ResultSet, LegacySQLExecutor, K>
        implements LegacySQLTest<K, DBContainerProvider<K>> {

    }


    abstract class LegacyDBMemoryTest extends DBMemorySQLTest<SQLClient, JsonArray, ResultSet, LegacySQLExecutor>
        implements LegacySQLTest<String, DBMemoryProvider> {

    }

}