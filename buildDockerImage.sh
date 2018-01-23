#!/bin/bash
IMAGE_NAME=metro/orderservice

if mvn clean package; then
  printf "\nMaven build successful. Building docker image %s...\n\n" $IMAGE_NAME
  docker build . -t="$IMAGE_NAME"
else
  printf "\nMaven build not successful. Aborting.\n\n"
fi
