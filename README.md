# rows-backend [![CI build](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/mwthinker/rows-backend/graph/badge.svg?token=T6CE5XBPEQ)](https://codecov.io/gh/mwthinker/rows-backend) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
A backend for a game of five in a row on an infinite board. Using spring boot and websockets for communications to the browser.

## Prerequisites
Java with Jdk 21, maven. Docker and docker-compose for running the server in a container.

## Run java directly in the terminal
```bash
mvn spring-boot:run
```

Page is available at http://localhost:8080/

## Run docker for server
To install on Docker on Ubuntu/Debian
```bash
sudo apt update
sudo apt install docker-compose-plugin
```

Build the docker image and run it
```bash
docker compose build --progress=plain --no-cache
docker compose up -d
```


To log in to the container for debugging maybe?
```bash
docker exec -it rows-backend /bin/bash
```

To rebuild the container with no cache and show build output
```bash
docker compose build --progress=plain --no-cache
```

## License
MIT
