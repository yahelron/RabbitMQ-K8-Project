
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
    image: ubuntu:latest
    tty: true
    command 'cat'
    serviceAccount 'myjenkins'
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
        stage ('ubunto'){
        git branch: 'main', url: 'https://github.com/yahelron/rabbitmk-k8s-project.git'
        container('helm') {
            echo "ls command ubunto"
            sh 'ls'
            sh '''
            apt update -y
            apt install curl -y
            apt install wget -y
            curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
            curl -LO "https://dl.k8s.io/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
            echo "$(cat kubectl.sha256)  kubectl" | sha256sum --check
            install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
            chmod +x kubectl
            mkdir -p ~/.local/bin
            mv ./kubectl ~/.local/bin/kubectl
            kubectl get pod -n yahel
            wget https://get.helm.sh/helm-v3.9.0-linux-amd64.tar.gz
            tar -zxvf helm-v3.9.0-linux-amd64.tar.gz
            linux-amd64/helm

            ls
            '''     
//            sh 'helm list'
 //           sh 'helm repo add jenkins https://charts.jenkins.io'
   //         sh 'helm install myjenkins2 jenkins/jenkins'
  //             apt install git -y
     //       git clone https://github.com/yahelron/rabbitmk-k8s-project
        //    cd rabbitmk-k8s-project/helm/rmq
	
        }
        }
    }
}