# Event Streaming Applications with Kafka Streams, Spring Kafka and Actuator

<img src="https://assets.confluent.io/m/1661ef5e4ff82d3d/original/20200122-PNG-web-dev-logo-denim.png" />

## Prerequisites
- IDE
- Minimal Java 11
- Maven (Optional)
- Docker

**Please ensure you have prepared your machine well in advance of the workshop. Your time during the workshop is valuable, and we want to use it for learning, rather than setting up machines.**

## Preparing your machine for the workshop
- [Install the pre-requisites](#install-the-pre-requisites)
- [Get a copy of this repository](#get-a-copy-of-this-repository)
- [Exercises](#exercises)

### Install the pre-requisites
- Install an IDE like https://www.jetbrains.com/idea/download/
- (Optional) Download Maven https://maven.apache.org/download.cgi and install https://maven.apache.org/install.html
- Download docker https://www.docker.com/products/docker-desktop/

### Get a copy of this repository
Clone or download this repo.

### Exercises Basic Kafka
1. Start docker (when installed) with ./start.sh (or build your own cmd file)
2. Go to the basic / src / resources directory
3. In the src code, follow all the comments and begin from the controller, to the service, and to the processors
4. When filled in the code, execute the test

### Exercises Kafka Streams
1. Go to the PaymentKStream, read the comments and fill in the missing code
2. Do the same for the FraudKStream
3. Now execute the test in directory basic again. 
4. It should pass now. If not, retry. Sometimes Kafka needs a wake up call.