# Trustly Technical Challenge for Developers - Back-End

### Requirements

- JAVA JDK 11
- Maven
- Docker and docker-compose (for running the application inside a container)

### Running the application

#### Without docker

- On the root project folder, compile the application with the following command: `mvn clean install`
- Next, execute: `java -Djdk.tls.client.protocols=TLSv1.1,TLSv1.2 -jar target/challenge-0.0.1-SNAPSHOT.jar`

#### With docker-compose

- On the root project folder, compile the application with the following command: `mvn clean install`
- Next, execute: `docker-compose -f docker/docker-compose up -d`

### Request example

`http://localhost:8080/scrapRepoByUrl?repoUrl=https://github.com/{owner}/{repository}`
