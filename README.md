# Guildmaster

Third iteration of my Discord bot. Contains various functionality I just wanted to have.

## Setup

### Docker

1. Run the container with the `DISCORD_TOKEN` environment variable set. The image is available either on [Dockerhub](https://hub.docker.com/repository/docker/hoi15a/guildmaster/general) or [GitHub Packages](https://github.com/Hoi15A/guildmaster/pkgs/container/guildmaster).
   1. Dockerhub: `docker run -e DISCORD_TOKEN=<your token> hoi15a/guildmaster:latest`
   2. GitHub: `docker run -e DISCORD_TOKEN=<your token> ghcr.io/hoi15a/guildmaster:latest`

### Docker-Compose

Example `docker-compose.yml` that can be used to run the bot:
```yaml
version: '3'
services:
   guildmaster:
      image: "hoi15a/guildmaster:latest"
      container_name: "guildmaster"
      environment:
         - DISCORD_TOKEN=<your token>

      restart: unless-stopped
```
