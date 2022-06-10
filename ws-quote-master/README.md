# ws-quote

This project implements a sample stock price quotation web service using Jax-WS

# Compiling the Service

Goto the service folder and build the code by typing:

`mvn package`

# Running the Service

Compile the service and then type:

`mvn exec:java`

# Dockerising the Service

Compile the service and then type:

`docker build -t ws-service:latest .`

# Compiling the Client

Goto the client folder and build the code by typing:

`mvn package`

# Running the Client

Compile the client and then type:

`mvn exec:java`

# Dockerising the Client

Compile the client and then type:

`docker build -t ws-client:latest .`

# Running the example with `docker-compose`

Go to the root folder "ws-quote" which contains the docker-compose.yml file and type:

`docker-compose up`
