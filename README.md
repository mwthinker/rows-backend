# rows-backend [![CI build](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/mwthinker/rows-backend/graph/badge.svg?token=T6CE5XBPEQ)](https://codecov.io/gh/mwthinker/rows-backend) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
A backend for a game of five in a row on an infinite board. Using spring boot and websockets for communications to the browser.

## Prerequisites
Java with Jdk 21, maven. Docker and docker-compose for running the server in a container.

Frontend code ([client](https://github.com/mwthinker/rows-backend/blob/master/client/README.md)) using node.js and yarn.

## Run java directly in the terminal
```bash
mvn spring-boot:run
# or run with a dev profile with settings in application-dev.properties
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Page is available at http://localhost:3000/

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

Connected to server, from server:
```json
{
  "type": "S2C_USER",
  "id": "aa42acc7-76d6-446e-a287-df99e29707b4"
}
```

Error message:
```json
{
  "type": "ERROR",
  "message": "Protocol error"
}
```

From client get games:
```json
{
  "type": "C2S_GET_GAMES"
}
```
Response from server
```json
{
  "type": "S2C_GAMES",
  "games": [
    {
      "gameId": "1234",
      "players": [
        {
          "player": "X",
          "id": "aa42acc7-76d6-446e-a287-df99e29707b4"
        }
      ]
    }
  ]
}
```

Client want to create a game.
```json
{
  "type": "C2S_CREATE_GAME"
}
```
response from SERVER
```json
{
  "type": "S2C_CREATED_GAME",
  "gameId": "aa42acc7-76d6-446e-a287-df99e29707b4",
  "player": "X"
}
```

Client want to join a game
```json
{
  "type": "C2S_JOIN_GAME",
  "gameId": "1234"
}
```
response from SERVER
```json
{
  "type": "S2C_JOINED_GAME",
  "player": "X"
}
```

Client first move:
```json
{
  "type": "S2C_MOVE",
  "x": 1,
  "y": 1
}
```
response from SERVER
```json
{
  "type": "S2C_MOVED",
  "gameId": "5678",
  "player": "X",
  "x": 1,
  "y": 1,
  "gameHash": "1234567890"
}
``` 
Or if the move is invalid
```json
{
  "type": "MOVE",
  "status": "FAIL",
  "message": "Invalid move",
  "gameHash": "1234567890"
}
```

Client want to get the whole game
```json
{
  "type": "C2S_GET_GAME",
  "gameId": "5678"
}
```
response from SERVER
```json
{
  "type": "S2C_GAME",
  "gameId": "5678",
  "board": {
    "bestOf": 5,
    "nextMove": "O",
    "cells": [
      { "x": 1, "y": 1, "player": "X" },
      { "x": 1, "y": 2, "player": "X" },
      { "x": 1, "y": 3, "player": "O" }
    ]
  },
  "gameHash": "1234567890"
}
```

## License
MIT
