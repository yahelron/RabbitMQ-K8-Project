pipeline {
      agent { kubernetes { 
            label 'test'
            idleMinutes "5"
        }}
    stages {
        stage('install kube') {
            steps { 
                sh '''
                curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
                curl -LO "https://dl.k8s.io/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
                install kubectl ~
                ls
                chmod +x kubectl
                ./kubectl get pod
                curl -LO "https://get.helm.sh/helm-v3.9.0-linux-amd64.tar.gz"
                rm -rf rabbitmk-k8s-project/
                '''
                
            }
        }
        stage('Deploy helm chart') {
            steps { 
                sh '''
                git clone https://github.com/yahelron/rabbitmk-k8s-project/
                cd rabbitmk-k8s-project/helm/rmq 
                ../../../linux-amd64/helm upgrade -i rmq ./
                '''
            }
        }

    }
}
