- Make sure you have the right key in dockerhub
- Create in Jenkins use with id="jenkins-dockerhub"

See here example https://www.youtube.com/watch?v=alQQ84M4CYU
but the method inside is different (i use WithCredential)

# Install new Jenkins as a pod:
helm repo add jenkins https://charts.jenkins.io
helm install myjenkins jenkins/jenkins
kubectl exec --namespace default -it svc/myjenkins -c jenkins -- /bin/cat /run/secrets/additional/chart-admin-password
kubectl --namespace default port-forward svc/myjenkins 8080:8080
kubectl create -f jenkins/rbac-admin.yaml

Acess to jenkins (http://127.0.0.1:8080) and and run the jenkins-docker-push.groovy and the jenkins-helm-install.groovy pipelines. 
