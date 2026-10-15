pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        COMPOSE_PROJECT_NAME = 'expense-tracker-api'
        IMAGE_NAME = 'expense-tracker-api'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build and Test') {
            steps {
                sh 'mvn -B -ntp clean verify'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .'
            }
        }

        stage('Verify Image') {
            steps {
                sh 'docker image inspect ${IMAGE_NAME}:${BUILD_NUMBER}'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'expense-db-password',
                        variable: 'DB_PASSWORD'
                    )
                ]) {
                    withEnv(["IMAGE_TAG=${BUILD_NUMBER}"]) {
                        sh '''
                            docker compose config --quiet
                            docker compose up -d --no-build
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar',
                             allowEmptyArchive: true

            junit testResults: 'target/surefire-reports/*.xml',
                  allowEmptyResults: true
        }

        success {
            echo 'Build, tests, Docker image creation, and deployment succeeded.'
        }

        failure {
            echo 'Pipeline failed. Review the stage logs.'
        }
    }
}
