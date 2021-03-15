package io.zero88.jooqx.integtest.spi.pg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertx.core.Vertx;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxTestContext;
import io.zero88.jooqx.LegacyDSL;
import io.zero88.jooqx.UseJdbcErrorConverter;
import io.zero88.jooqx.datatype.DataTypeMapperRegistry;
import io.zero88.jooqx.datatype.UserTypeAsJooqType;
import io.zero88.jooqx.integtest.pgsql.enums.Mood;
import io.zero88.jooqx.integtest.pgsql.enums.Weather;
import io.zero88.jooqx.integtest.pgsql.tables.EnumDataType;
import io.zero88.jooqx.integtest.pgsql.tables.UdtDataType;
import io.zero88.jooqx.integtest.pgsql.tables.records.EnumDataTypeRecord;
import io.zero88.jooqx.integtest.pgsql.tables.records.UdtDataTypeRecord;
import io.zero88.jooqx.integtest.spi.pg.PostgreSQLHelper.PgUseJooqType;
import io.zero88.jooqx.spi.pg.PgSQLLegacyTest;

class PgLeGCustomTest extends PgSQLLegacyTest implements PostgreSQLHelper, UseJdbcErrorConverter, PgUseJooqType {

    @Override
    @BeforeEach
    public void tearUp(Vertx vertx, VertxTestContext ctx) {
        super.tearUp(vertx, ctx);
        this.prepareDatabase(ctx, this, connOpt, "pg_data/custom.sql");
    }

    @Override
    public DataTypeMapperRegistry typeMapperRegistry() {
        return PgUseJooqType.super.typeMapperRegistry()
                                  .addByColumn(catalog().PUBLIC.ENUM_DATA_TYPE.CURRENTMOOD,
                                               UserTypeAsJooqType.create(new EnumMoodConverter()))
                                  .addByColumn(catalog().PUBLIC.ENUM_DATA_TYPE.CURRENTWEATHER,
                                               UserTypeAsJooqType.create(new EnumWeatherConverter()))
                                  .addByColumn(catalog().PUBLIC.UDT_DATA_TYPE.ADDRESS,
                                               UserTypeAsJooqType.create(new FullAddressConverter()));
    }

    @Test
    void queryEnum(VertxTestContext ctx) {
        Checkpoint flag = ctx.checkpoint();
        final EnumDataType table = catalog().PUBLIC.ENUM_DATA_TYPE;
        jooqx.execute(jooqx.dsl().selectFrom(table).limit(1), LegacyDSL.adapter().fetchOne(table),
                      ar -> ctx.verify(() -> {
                          final EnumDataTypeRecord record = assertSuccess(ctx, ar);
                          System.out.println(record);

                          Assertions.assertEquals(Mood.ok, record.getCurrentmood());
                          Assertions.assertEquals(Weather.sunny, record.getCurrentweather());
                          flag.flag();
                      }));
    }

    @Test
    void queryCustom(VertxTestContext ctx) {
        Checkpoint flag = ctx.checkpoint();
        final UdtDataType table = catalog().PUBLIC.UDT_DATA_TYPE;
        jooqx.execute(jooqx.dsl().selectFrom(table).limit(1), LegacyDSL.adapter().fetchOne(table),
                      ar -> ctx.verify(() -> {
                          final UdtDataTypeRecord record = assertSuccess(ctx, ar);
                          Assertions.assertNotNull(record.getAddress());
                          Assertions.assertEquals("US Open", record.getAddress().getState());
                          Assertions.assertEquals("Any,town", record.getAddress().getCity());
                          Assertions.assertEquals("", record.getAddress().getStreet());
                          Assertions.assertEquals(10, record.getAddress().getNoa());
                          Assertions.assertEquals(true, record.getAddress().getHome());
                          flag.flag();
                      }));
    }

}
