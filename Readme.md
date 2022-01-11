## KSQL Learnings


### Important commands
- Start a ksqlDB's interactive CLI. See: https://ksqldb.io/quickstart.html
  Run this command to connect to the ksqlDB server and enter an interactive command-line interface (CLI) session.
    ```shell
    docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
    ```
  
- CREATE A STREAM
  ```sql
  CREATE STREAM userprofile (userid INT, firstname VARCHAR, lastname VARCHAR, countrycode VARCHAR, rating DOUBLE)
  WITH (VALUE_FORMAT = 'JSON', KAFKA_TOPIC = 'USERPROFILE');
  ```
- Query against the stream
  ```sql
  select firstname, lastname, countrycode, rating from userprofile EMIT CHANGES;
  ```
  
- Push queries
  - Long-running queries
  ```sql
  select * from driverlocations emit changes;
  ```
- Pull queries
  - Return the current state of the table
  - Based on aggregated tables
  - Query against the rowKey
    ```sql
    select countrycode, numdrivers from countryDrivers where rowkey = 'AU';
    ```
    
- Create a source connector from the ksql-cli
```sql
CREATE SOURCE CONNECTOR `mysql-jdbc-source` WITH(
"connector.class"='io.confluent.connect.jdbc.JdbcSourceConnector', 
"connection.url"='jdbc:mysql://sems-mysql:3308/sems_slim', 
"mode"='incrementing',
"incrementing.column.name"='ref',
"table.whitelists"='job',
"connection.password"='root',
"connection.user"='root',
"topic.prefix"='db-',
"key"='id'
);
```