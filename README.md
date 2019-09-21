## Scala Play MySQL sample app 

###  Prerequisites

1. Install [Docker](https://docs.docker.com/install/)
2. Get [Scala](https://www.scala-lang.org/download/) and [SBT](https://www.scala-sbt.org/1.0/docs/Setup.html)
3. Make sure SBT version is not less than 1.2.8 and there's Scala 2.11 sdk is set for the project

### How to run

 * Update the MySQL server url, username and password in `conf/application.conf`
 * Run Mysql container in Docker:
```
 docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=test_pass -e MYSQL_USER=log_db_user -e MYSQL_PASSWORD=log_db_password -e MYSQL_DATABASE=log_db -d mysql:latest
```
 * Wait until Mysql is started. Check logs here:
```
docker logs mysql
```
 * Launch the app: `sbt run` and then exec `curl localhost:9000`
 * Use Auth token (test only) for protected API endpoints: `Bearer YOUR_TOKEN`
 * Try to make requests to the API with curl.
    * Create user and get token (copy Authorization header from `/login` response)
 
```
curl -XPOST localhost:9000/signup -H "Content-Type: application/json" -d '{"login": "testuser", "password": "abcd", "name": "Nik", "surname": "Katalnikov"}' -v
curl -XPOST localhost:9000/login -H "Content-Type: application/json" -d '{"login": "testuser", "password": "abcd"}' -v
```

   * Check that everything works fine (Read, Create, Update, Delete):

```
curl -XGET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" localhost:9000/users   
[{"id":1,"login":"Nik","password":"Katalnikov","name":"testuser","surname":"abcd"}]
```
```
curl -XGET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" localhost:9000/deliveries
[{"id":1,"title":"Philosophi Naturalis Principia Mathematica","recipientNumber":1111111111,"createdAt":"2001-01-01 02:00:00","district":{"id":1,"title":"Pechecrsky_West","city":"Kyiv"},"courier":{"id":1,"name":"Isaac","surname":"Newton"}}
```
```
curl -XPOST -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" -H "Content-Type: application/json" localhost:9000/delivery -d '{"title": "Plato. Atlantis", "recipientNumber": 12344, "courier_id": 1, "district_id": 1}'
```  
```
curl -XPOST -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" -H "Content-Type: application/json" localhost:9000/delivery -d '{"title": "Plato. Dialogs", "recipientNumber": 12344, "courier_id": 1, "district_id": 1}'
```
```
curl -XPATCH -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" -H "Content-Type: application/json" localhost:9000/delivery -d '{"id": 4, "title": "Plato. Dialogs", "recipientNumber": 10000, "courier_id": 2, "district_id": 2}'
```  
```
curl -XDELETE -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoidGVzdHVzZXIiLCJwYXNzd29yZCI6ImFiY2QifX0.Fu6kWsQ6KNg_F8-iwbp60zcTn0AaZ-hu8MWUK1m0p-I" -H "Content-Type: application/json" localhost:9000/delivery -d '1'
```
 * Drop database:
```
docker rm mysql --force
```
 * Make DB read-only accessible from Metabase:
```
docker exec -it mysql bash
mysql -u root (then enter "test_pass" as as password)
CREATE USER 'metabase_user'@'%' IDENTIFIED WITH mysql_native_password BY 'metabase_password';
GRANT SELECT ON log_db.* TO 'metabase_user'@'%';
```

### Schema evolutions and seeds
Check the following folder: `conf/evolutions/default`