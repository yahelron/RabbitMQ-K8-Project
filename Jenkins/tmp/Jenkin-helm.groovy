
podTemplate(yaml: '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:19.03.1-dind
    securityContext:
      privileged: true
    env:
      - name: DOCKER_TLS_CERTDIR
        value: ""
  - name: helm
    image: lachlanevenson/k8s-helm:latest
    tty: true
    securityContext:
      privileged: true
    env:
      - name: helm
        value: ""
''') {
    node(POD_LABEL) {
        stage ('Docker-Login')
        container('docker')
        {
            			withCredentials([usernamePassword(credentialsId: 'jenkins-dockerhub', passwordVariable: 'password', usernameVariable: 'username')]) {
                         sh '''
                         echo ${password} | docker login -u ${username} --password-stdin
                         '''
                    }
        }
        stage ('helm'){
        git branch: 'main', url: 'https://github.com/yahelron/rabbitmk-k8s-project.git'
        container('helm') {
            
            sh 'helm list'

			
        }
        }
    }
}