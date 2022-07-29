# rabbitmq-k8s-project

[![Rabbit MQ](https://cdn.iconscout.com/icon/free/png-128/rabbitmq-282296.png)](hhttps://www.rabbitmq.com/)
[![Helm](https://helm.sh/img/helm.svg)](https://helm.sh/)


# About K8s RabbitMQ project

The project infrastructure is based on a queueing mechanism implemented using RabbitMQ
* RabbitMQ - an application that is able to store data in a queue fashion allowing us to
have the ability to maintain a queue of messages
* Peoducer pod will send messages to relevant rabbimq queue.
* Consumer pod - will listen to new messages on a queue in RabbitMQ server
[![rabbitmq project](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/rabbitmq1.jpg)](https://helm.sh/)

# Install
Implementation will be used as the following:
* Heml will install all relevat components (producer, consumer and rabbitMQ).
- Go to the following folder cd helm/rmq/ and run heml upgrade -i consumer ./

# CI
Upon Dockerfile, python or other relevant change [This Jenkins jenkins-docker-push pipeline ](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/Jenkins/jenkins-docker-push.groovy) will be used to build new docker images and send them to the dockerhub repository.

Jenkins setup for this configuration:
Make sure to add relevant credentials in dockerhub (dedicated security password for the jenkins) and create jenkins credential (or global env variable) to store the password in a secure manner Docker login the support by jenkins will be something like this: echo ${password} | docker login -u ${username} --password-stdin

# DC
Helm chart will be installed with [This jenkins-helm-install.groovy pipeline ](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/Jenkins/jenkins-helm-install.groovy). 
note - when using a pod to run helm you have to have the right permissions to run. [This rbac change file ](https://github.com/yahelron/rabbitmk-k8s-project/blob/main/Jenkins/rbac-admin.yaml) will give jenkins service acount admin permission to run the charts.

# Producer
Producer - will send messages every 20 seconds to a queue found in rabbitmq server.
# Consumer
Consumer - will listen to new messages on a queue in RabbitMQ server and will print them to STDOUT.
* There are two consumers you can use. old consumer works with python 2. whereas the new consumer work with python3 and run [prometheus](https://prometheus.io/docs/introduction/overview/) metrics.
* in order to expose the metrics to k8s cluster please run following k8s command:
  kubectl expose pod [consumer pod name]  --type=ClusterIP --port=9422
  
  
 
 # Step by step configuration:


Prerequisites 
- Kubernetes system. you can use Minikube in your own computer.
- kubectl to comunicate with kubernetes
- helm in order to install this project and most of the mention components.

Jenkins
```
helm repo add jenkins https://charts.jenkins.io
helm install myjenkins jenkins/jenkins
kubectl exec --namespace default -it svc/myjenkins -c jenkins -- /bin/cat /run/secrets/additional/chart-admin-password
kubectl --namespace default port-forward svc/myjenkins 8080:8080
kubectl create -f jenkins/rbac-admin.yaml

Acess to jenkins (http://127.0.0.1:8080) and and run the jenkins-docker-push.groovy and the jenkins-helm-install.groovy pipelines. 
```

Helm create producer, consumer and rabbitmq server.
```
git clone https://github.com/yahelron/rabbitmk-k8s-project
cd helm/rmq/
heml upgrade -i consumer ./
```


Monitoring
```
helm repo add grafana  https://grafana.github.io/helm-charts
helm repo add  bitnami https://charts.bitnami.com/bitnami
helm repo update
kubectl apply -f ./monitoring/namespace.yml 
helm install prometheus --namespace monitoring prometheus-community/prometheus
kubectl apply -f monitoring/config.yml
helm install -f monitoring/values.yml  --namespace monitoring  grafana grafana/grafana
kubectl port-forward --namespace monitoring svc/grafana 3000:3000

If you use port-forward  got to http://127.0.0.1:300 and add relevant dashboards to grafana.
```

