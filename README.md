# rabbitmk-k8s-project

[![Rabbit MQ](https://cdn.iconscout.com/icon/free/png-128/rabbitmq-282296.png)](hhttps://www.rabbitmq.com/)
[![Helm](https://helm.sh/img/helm.svg)](https://helm.sh/)


# About K8s RabbitMQ project

The project infrastructure is based on a queueing mechanism implemented using RabbitMQ
* Producer - will send messages every X seconds to a queue found in rabbitmq server.
* RabbitMQ - an application that is able to store data in a queue fashion allowing us to
have the ability to maintain a queue of messages
* Consumer - will listen to new messages on a queue in RabbitMQ server and will print
them to STDOUT

# Install
Implimentation will be used as the following:
* Heml will install all relevat compenets (producer, consumer and rabbitMQ).
- Go to the following folder cd helm/rmq/ and run heml upgrade -i rmq ./

# CI
Upon Dockerfile, python or other relevant change [This Jenkins jenkins-docker-push pipeline ](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/Jenkins/jenkins-docker-push.groovy) will be used to build new docker images and send them to the dockerhub repository.

Jenkins setup for this configuration:
make sure to add relevant creadentials in dockerhub (dedicated security password for the jenkins) and create jenkins credential (or global env variable) to store the passwrod in a secure manner 

# DC

# Producer
# Comsumer
