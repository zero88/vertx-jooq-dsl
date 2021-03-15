package io.zero88.jooqx.datatype.basic;

import java.math.BigDecimal;

import org.jetbrains.annotations.NotNull;

import io.vertx.sqlclient.data.Numeric;
import io.zero88.jooqx.datatype.JooqxConverter;

public final class BigDecimalConverter implements JooqxConverter<Numeric, BigDecimal> {

    @Override
    public BigDecimal from(Numeric vertxObject) { return vertxObject == null ? null : vertxObject.bigDecimalValue(); }

    @Override
    public Numeric to(BigDecimal jooqObject) { return jooqObject == null ? null : Numeric.create(jooqObject); }

    @Override
    public @NotNull Class<Numeric> fromType() { return Numeric.class; }

    @Override
    public @NotNull Class<BigDecimal> toType() { return BigDecimal.class; }

}
