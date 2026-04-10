pipeline {
    agent any

    tools {
        jdk 'jdk-21'
    }

    environment {
        APP_NAME  = 'fortune-server'
        IMAGE_TAG = "${env.BUILD_NUMBER ?: 'latest'}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Tests') {
            steps {
                sh './mvnw -B clean test'
            }
        }

        stage('Integration Tests & Coverage') {
            steps {
                sh './mvnw -B verify -DskipTests'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    junit '**/target/failsafe-reports/*.xml'
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }

        stage('Package') {
            steps {
                sh './mvnw -B package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${APP_NAME}:${IMAGE_TAG} ."
            }
        }
    }

    post {
        success {
            echo "Pipeline completed. Image: ${APP_NAME}:${IMAGE_TAG}"
        }
        failure {
            echo 'Pipeline failed – check the logs above.'
        }
        always {
            cleanWs()
        }
    }
}
