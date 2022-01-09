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