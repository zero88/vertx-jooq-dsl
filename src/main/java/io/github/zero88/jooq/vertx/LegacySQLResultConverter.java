package io.github.zero88.jooq.vertx;

import java.util.List;

import io.vertx.ext.sql.ResultSet;

public interface LegacySQLResultConverter
    extends SQLResultSetConverter<ResultSet>, SQLResultBatchConverter<ResultSet, List<Integer>> {}