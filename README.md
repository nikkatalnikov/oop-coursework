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
 * Launch the app: `sbt run` and then exec `curl localhost:9000`
 * Use Auth token (test only) for protected API endpoints: `Bearer YOUR_TOKEN`
 * Try to make requests to the API with curl: 
 
```
curl -XPOST localhost:9000/signup -H "Content-Type: application/json" -d '{"login": "abcb", "password": "abcd", "name": "Nik", "surname": "1234"}' -v
curl -XPOST localhost:9000/login -H "Content-Type: application/json" -d '{"login": "abcb", "password": "abcd"}' -v
curl -XGET -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoiYWJjYiIsInBhc3N3b3JkIjoiYWJjZCJ9fQ.Q1oHVWeJZAt0eag7DFMr5n7mDvhWCEXn465hN782V5c" localhost:9000/getAll
```

### Schema evolutions and seeds
Check the following folder: `conf/evolutions/default`