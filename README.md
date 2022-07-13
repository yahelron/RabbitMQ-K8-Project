# rabbitmk-k8s-project

[![Rabbit MQ](https://cdn.iconscout.com/icon/free/png-128/rabbitmq-282296.png)](hhttps://www.rabbitmq.com/)
[![Helm](https://helm.sh/img/helm.svg)](https://helm.sh/)


# About K8s RabbitMQ project

The project infrastructure is based on a queueing mechanism implemented using RabbitMQ
* 
* RabbitMQ - an application that is able to store data in a queue fashion allowing us to
have the ability to maintain a queue of messages



# Install
Implimentation will be used as the following:
* Heml will install all relevat compenets (producer, consumer and rabbitMQ).
- Go to the following folder cd helm/rmq/ and run heml upgrade -i consumer ./

# CI
Upon Dockerfile, python or other relevant change [This Jenkins jenkins-docker-push pipeline ](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/Jenkins/jenkins-docker-push.groovy) will be used to build new docker images and send them to the dockerhub repository.

Jenkins setup for this configuration:
make sure to add relevant creadentials in dockerhub (dedicated security password for the jenkins) and create jenkins credential (or global env variable) to store the passwrod in a secure manner Docker login the support by jenkins will be something like this: echo ${password} | docker login -u ${username} --password-stdin

# DC
Helm automatic implimentation via jenkins will be added soon.

# Producer
Producer - will send messages every 20 seconds to a queue found in rabbitmq server.
# Comsumer
Consumer - will listen to new messages on a queue in RabbitMQ server and will print them to STDOUT.
* There are two consumers you can use. old consumer works with python 2. whereas the new consumer work with python3 and run [prometheus](https://prometheus.io/docs/introduction/overview/) metrics.
* in order to expose the metrics to k8s cluster please run following k8s command:
  kubectl expose pod [consumer pod name]  --type=ClusterIP --port=9422
