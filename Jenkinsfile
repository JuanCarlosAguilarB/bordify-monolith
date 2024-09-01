pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'my-spring-boot-app'
        DOCKER_REGISTRY = 'my-docker-registry'
        DOCKER_TAG = "${env.BUILD_ID}"
        SONAR_URL = "${env.SONAR_URL}"
        SONAR_TOKEN = "${env.SONAR_TOKEN}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Clonar el repositorio
                    checkout([$class: 'GitSCM',
                              branches: [[name: '*/master']],
                              userRemoteConfigs: [[url: 'https://github.com/JuanCarlosAguilarB/bordify-monolith']]
                             ])
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Building project without running tests
                    sh './gradlew build -x test'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    sh './gradlew test'
                }
            }
        }

        stage('Run Integration Tests') {
            steps {
                script {
                    sh './gradlew integrationTest'
                }
            }
        }

        stage('Analyze Code Coverage') {
            steps {
                script {
                    sh './gradlew jacocoTestReport'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Ejecutar análisis de SonarQube
                    sh """
                        ./gradlew sonar \
                            -Dsonar.projectKey=monolith \
                            -Dsonar.projectName=monolith \
                            -Dsonar.host.url=${SONAR_URL} \
                            -Dsonar.token=${SONAR_TOKEN}
                    """
                }
            }
        }

//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     // build the docker image and push it to the registry
//                     sh """
//                         docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_TAG} .
//                         docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_TAG}
//                     """
//                 }
//             }
//         }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
