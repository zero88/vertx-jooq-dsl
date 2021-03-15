--- EnumDataType
INSERT INTO enum_data_type ("id", "currentMood", "currentWeather") VALUES (1, 'ok', 'sunny');
INSERT INTO enum_data_type ("id", "currentMood", "currentWeather") VALUES (2, 'unhappy', 'cloudy');
INSERT INTO enum_data_type ("id", "currentMood", "currentWeather") VALUES (3, 'happy', 'rainy');
INSERT INTO enum_data_type ("id", "currentMood", "currentWeather") VALUES (4, null, null);
INSERT INTO enum_data_type ("id", "currentMood", "currentWeather") VALUES (5, 'ok', 'sunny');

--- CustomDataType
INSERT INTO udt_data_type ("id", "address") VALUES (1, ('US Open', 'Any,town', null, 10, true));
INSERT INTO udt_data_type ("id", "address") VALUES (2, ('Any""town', 'special","town', '',10, false));
