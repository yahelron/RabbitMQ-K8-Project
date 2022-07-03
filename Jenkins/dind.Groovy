podTemplate(yaml: '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:19.03.1
    command:
    - sleep
    args:
    - 99d
    env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
  - name: docker-daemon
    image: docker:19.03.1-dind
    securityContext:
      privileged: true
    env:
      - name: DOCKER_TLS_CERTDIR
        value: ""
''') {
	node(POD_LABEL) {
		git 'https://github.com/yahelron/RabbitMQ-K8S-Project'
		container('docker') {
			sh 'docker version'
            sh 'cd RabbitMQ-K8S-Project/docker/consumer/'
            sh 'docker build -t test1 ./'
            sh 'docker images'
			
		}
	}
}