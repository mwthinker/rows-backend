# rows-backend [![CI build](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/mwthinker/rows-backend/actions/workflows/ci.yml) [![codecov](https://codecov.io/gh/mwthinker/rows-backend/graph/badge.svg?token=T6CE5XBPEQ)](https://codecov.io/gh/mwthinker/rows-backend) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
A backend for a game of five in a row on an infinite board. Using spring boot and websockets for communications to the browser.

## Run docker for server
To install on Docker on Ubuntu/Debian
```bash
sudo apt update
sudo apt install docker-compose-plugin
```

```bash
# To log in to the container for debugging maybe?
docker compose build --progress=plain --no-cache
docker compose up -d
```

```bash
# To log in to the container for debugging maybe?
docker exec -it rows-backend /bin/bash
```

```bash
# To rebuild the container with no cache and show build output
docker compose build --progress=plain --no-cache
```

## License
MIT
