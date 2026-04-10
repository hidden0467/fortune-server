pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        IMAGE_REGISTRY = "${env.IMAGE_REGISTRY ?: ''}"
        IMAGE_REPOSITORY = "${env.IMAGE_REPOSITORY ?: 'fortune-server'}"
        IMAGE_TAG = "${env.IMAGE_TAG ?: env.BUILD_NUMBER}"
        BUILD_DOCKER = "${env.BUILD_DOCKER ?: 'false'}"
        DOCKER_PUSH = "${env.DOCKER_PUSH ?: 'false'}"
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
                        sh 'chmod +x mvnw && ./mvnw -B clean verify'
                    } else {
                        bat 'mvnw.cmd -B clean verify'
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.jar'
                }
            }
        }

        stage('Build Docker Image') {
            when {
                expression {
                    return env.BUILD_DOCKER == 'true'
                }
            }
            steps {
                script {
                    def imageRef = env.IMAGE_REGISTRY?.trim()
                        ? "${env.IMAGE_REGISTRY}/${env.IMAGE_REPOSITORY}:${env.IMAGE_TAG}"
                        : "${env.IMAGE_REPOSITORY}:${env.IMAGE_TAG}"

                    env.IMAGE_REF = imageRef

                    if (isUnix()) {
                        sh "docker build -t ${imageRef} ."
                    } else {
                        bat "docker build -t ${imageRef} ."
                    }
                }
            }
        }

        stage('Publish Docker Image') {
            when {
                expression {
                    return env.BUILD_DOCKER == 'true' &&
                        env.DOCKER_PUSH == 'true' &&
                        env.DOCKER_CREDENTIALS_ID?.trim() &&
                        env.IMAGE_REGISTRY?.trim()
                }
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: env.DOCKER_CREDENTIALS_ID,
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    script {
                        if (isUnix()) {
                            sh '''
                              echo "$DOCKER_PASSWORD" | docker login "$IMAGE_REGISTRY" -u "$DOCKER_USERNAME" --password-stdin
                              docker push "$IMAGE_REF"
                              docker logout "$IMAGE_REGISTRY"
                            '''
                        } else {
                            bat '''
                              echo %DOCKER_PASSWORD% | docker login %IMAGE_REGISTRY% -u %DOCKER_USERNAME% --password-stdin
                              docker push %IMAGE_REF%
                              docker logout %IMAGE_REGISTRY%
                            '''
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully. Image reference: ${env.IMAGE_REF ?: 'fortune-server local image'}"
        }
    }
}
