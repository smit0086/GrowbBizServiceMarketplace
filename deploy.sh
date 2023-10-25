#!/bin/bash

DEPLOY_LOG="./deploy.log"
DOCKER_COMPOSE_LOG="./compose.log"

# Clear log file
: > "$DEPLOY_LOG"

# Clear docker compose log file
: > "$DOCKER_COMPOSE_LOG"

# Usage: log MESSAGE
log() {
    printf '%b ' "$@" '\n' | tee -a "$DEPLOY_LOG"
}

# Usage: check_docker_service_running SERVICE
check_docker_service_running() {
    if [ -z `docker compose ps -q $1` ] || [ -z `docker ps -q --no-trunc | grep $(docker compose ps -q $1)` ]; then
        log "$1 not running."
        return 1
    else
        log "$1 it's running."
        return 0
    fi
}

docker compose ps | grep backend

if [ "$?" -ne "0" ];
then
    log "No docker services running"
else
    log "Docker services running. stopping them"
    docker compose stop
    docker compose down
fi

docker compose build --no-cache
docker compose up --detach
docker compose logs > "$DOCKER_COMPOSE_LOG"
sleep 80
check_docker_service_running backend

if [ "$?" -eq "1" ]
then
    docker compose restart backend
fi