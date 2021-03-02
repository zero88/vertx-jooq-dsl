package io.github.zero88.jooq.vertx.converter.ext;

import java.util.function.Function;

import org.jooq.Converter;
import org.jooq.JSONB;

import io.vertx.core.json.JsonArray;

/**
 * @author jensklingsporn
 */
public class JSONBToJsonArrayBinding extends PGJsonToVertxJsonBinding<JSONB, JsonArray> {

    @Override
    public Converter<JSONB, JsonArray> converter() {
        return JSONBToJsonArrayConverter.getInstance();
    }

    @Override
    Function<String, JSONB> valueOf() {
        return JSONB::valueOf;
    }

    @Override
    String coerce() {
        return "::jsonb";
    }

}
