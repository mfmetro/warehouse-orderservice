# Warehouse System - OrderService
External service to be used in the _CODE University Warehouse System_ project to simulate incoming orders. After startup, the OrderService generates random orders in random time intervals.

## Running the Service
In order to start up the OrderService, you need to checkout this repository, build the application, and build the docker image.

```bash
# checkout repository
$ git clone https://github.com/mfmetro/warehouse-orderservice.git

# build the application
$ cd warehouse-orderservice
$ mvn package

# build the Docker image
docker build . -t="metro/orderservice"
```

You are then able to start the `metro/orderservice` image.

```bash
# start the image
$ docker run -d -p 8080:8080 metro/orderservice

# use docker ps to find out your container's id
$ docker ps
CONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS              PORTS                     NAMES
1a06b1c64d3e        metro/orderservice   "/bin/sh -c 'java -j…"   2 minutes ago       Up 2 minutes        0.0.0.0:8080->8080/tcp   eager_davinci

# show logs (you will see that some articles and orders already have been created)
$ docker logs 1a06
```

As soon as the container is running, you can access the Swagger UI for the OrderService via `http://localhost:8080/swagger-ui.html` in your browser.

```bash
# stop container
$ docker stop 1a06
```
