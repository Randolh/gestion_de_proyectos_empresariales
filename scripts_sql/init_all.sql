SELECT '>> 1/3: Ejecutando 01_ddl_schema.sql (DDL)...' AS '';
SOURCE 01_ddl_schema.sql;

SELECT '>> 2/3: Ejecutando 02_dml_seeds.sql (DML)...' AS '';
SOURCE 02_dml_seeds.sql;

SELECT '>> 3/3: Ejecutando 03_dql_consultas.sql (DQL)...' AS '';
SOURCE 03_dql_consultas.sql;

SELECT '>> ¡Todos los scripts se ejecutaron exitosamente! <<' AS '';