pipeline {
    agent { label 'docker' }
    stages {
        stage('Build') {
            steps {
                script {
                    props=readProperties file: 'gradle.properties'
                    VERSION="${props.version}-${props.apiVersion}"
                }
                sh "docker build --tag 'dtr.rogfk.no/fint-beta/consumer-felles-kodeverk:${VERSION}' --build-arg apiVersion=${props.apiVersion} ."
            }
        }
        stage('Publish') {
            when {
                branch 'master'
            }
            steps {
                withDockerRegistry([credentialsId: 'dtr-rogfk-no', url: 'https://dtr.rogfk.no']) {
                    sh "docker push 'dtr.rogfk.no/fint-beta/consumer-felles-kodeverk:${VERSION}'"
                }
            }
        }
    }
}
