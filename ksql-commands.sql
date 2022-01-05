CREATE STREAM riderLocations (profileId VARCHAR, latitude DOUBLE, longitude DOUBLE)
  WITH (kafka_topic='locations', value_format='json', partitions=1);

CREATE TABLE currentLocation AS
SELECT profileId,
       LATEST_BY_OFFSET(latitude) AS la,
       LATEST_BY_OFFSET(longitude) AS lo
FROM riderlocations
GROUP BY profileId
    EMIT CHANGES;

CREATE TABLE ridersNearMountainView AS
SELECT ROUND(GEO_DISTANCE(la, lo, 37.4133, -122.1162), -1) AS distanceInMiles,
       COLLECT_LIST(profileId) AS riders,
       COUNT(*) AS count
  FROM currentLocation
  GROUP BY ROUND(GEO_DISTANCE(la, lo, 37.4133, -122.1162), -1);