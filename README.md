# rows-backend [![CI build](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/mwthinker/rows-backend/graph/badge.svg?token=T6CE5XBPEQ)](https://codecov.io/gh/mwthinker/rows-backend) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
A backend for a game of five in a row on an infinite board. Using spring boot and websockets for communications to the browser.

## Prerequisites
Java with Jdk 21, maven. Docker and docker-compose for running the server in a container.

Frontend code ([client](https://github.com/mwthinker/rows-backend/blob/master/client/README.md)) using node.js and yarn.

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

To log in to the container for debugging maybe
```bash
docker exec -it rows-backend /bin/bash
```

To rebuild the container with no cache and show build output
```bash
docker compose build --progress=plain --no-cache
```

## WebSocket Protocol

At connection from SERVER.
```json
{
  "type": "CONNECT",
  "id": "1234"
}
```
response from CLIENT
```json
{
  "name": "CONNECT",
  "result": "SUCCESS"
}
```

Client want to create a game
```json
{
  "type": "CREATE_GAME",
  "id": "1234"
}
```
response from SERVER
```json
{
  "name": "CREATE_GAME",
  "status": "SUCCESS",
  "gameId": "5678",
  "player": "X"
}
```

Client want to join a game
```json
{
  "type": "JOIN_GAME",
  "gameId": "1234"
}
```
response from SERVER
```json
{
  "name": "JOIN_GAME",
  "status": "SUCCESS",
  "gameId": "5678",
  "player": "X"
}
```

Client first move:
```json
{
  "type": "MOVE",
  "id": "1234",
  "player": "X",
  "x": 1,
  "y": 1
}
```
response from SERVER
```json
{
  "name": "MOVE",
  "id": "1234",
  "status": "SUCCESS",
  "gameId": "5678",
  "player": "X",
  "x": 1,
  "y": 1,
  "gamehash": "1234567890"
}
``` 
Or if the move is invalid
```json
{
  "name": "MOVE",
  "status": "FAIL",
  "message": "Invalid move",
  "gamehash": "1234567890"
}
```

Client want to get the game board
```json
{
  "type": "GET_BOARD",
  "gameId": "5678"
}
```
response from SERVER
```json
{
  "name": "GET_BOARD",
  "status": "SUCCESS",
  "gameId": "5678",
  "game": {
    "bestOf": 5,
    "nextMove": "O",
    "board": [
      { "x": 1, "y": 1, "player": "X" },
      { "x": 1, "y": 2, "player": "X" },
      { "x": 1, "y": 3, "player": "O" }
    ]
  },
  "gamehash": "1234567890"
}
```





## License
MIT
