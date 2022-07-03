def label = "docker-jenkins-${UUID.randomUUID().toString()}"
def home = "/home/jenkins"
def workspace = "${home}/workspace/build-docker-jenkins"
def workdir = "${workspace}/src/localhost/docker-jenkins/"

def ecrRepoName = "my-jenkins"
def tag = "$ecrRepoName:latest"

podTemplate(label: label,
		containers: [
				containerTemplate(name: 'jnlp', image: 'jenkins/jnlp-slave:alpine'),
				containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
		],
		volumes: [
				hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock'),
		],
) {
	node(label) {
	    git 'https://github.com/yahelron/RabbitMQ-K8S-Project.git'
		stage('Docker Build') {
			container('docker') {
			    sh 'docker images'
			    sh 'docker build -t producer docker/producer/ .'
			    sh 'docker images'
			    sh 'ls ${home}'
			    sh 'la ${workspace}'
				echo "Building docker image..."
				sh 'docker images'
			}
		}
	}
}