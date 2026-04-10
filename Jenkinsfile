pipeline {
    agent any

    environment {
        APP_NAME = "fortune-server"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B clean test package'
                    } else {
                        bat 'mvn -B clean test package'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker build -t ${APP_NAME}:${IMAGE_TAG} .'
                    } else {
                        bat 'docker build -t %APP_NAME%:%IMAGE_TAG% .'
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed. Image: ${APP_NAME}:${IMAGE_TAG}"
        }
    }
}
