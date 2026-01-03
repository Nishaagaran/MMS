pipeline {
    agent any
    
    environment {
        // Application configuration
        APP_NAME = 'movie-management-system'
        APP_VERSION = "${env.BUILD_NUMBER}"
        DOCKER_IMAGE = "${APP_NAME}:${APP_VERSION}"
        DOCKER_IMAGE_LATEST = "${APP_NAME}:latest"
        
        // Docker registry configuration (update these values)
        DOCKER_REGISTRY = credentials('docker-registry-url') ?: 'your-registry.io'
        DOCKER_REPOSITORY = "${DOCKER_REGISTRY}/${APP_NAME}"
        DOCKER_CREDENTIALS_ID = 'docker-registry-credentials'
        
        // Maven configuration
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=256m'
    }
    
    options {
        // Discard old builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        
        // Timeout after 30 minutes
        timeout(time: 30, unit: 'MINUTES')
        
        // Add timestamps to console output
        timestamps()
        
        // Retry build up to 3 times on failure
        retry(3)
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out code from ${env.GIT_BRANCH}"
                    checkout scm
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "Building Spring Boot application..."
                    sh """
                        mvn clean compile -B
                    """
                }
            }
            post {
                success {
                    echo "Build stage completed successfully"
                }
                failure {
                    echo "Build stage failed"
                    error("Build failed")
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
                    echo "Running unit tests..."
                    sh """
                        mvn test -B
                    """
                }
            }
            post {
                always {
                    // Publish test results
                    junit 'target/surefire-reports/*.xml'
                    
                    // Publish test coverage reports (if you add coverage plugin)
                    // publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]
                }
                success {
                    echo "All tests passed"
                }
                failure {
                    echo "Tests failed"
                    error("Tests failed")
                }
            }
        }
        
        stage('Package') {
            steps {
                script {
                    echo "Packaging application..."
                    sh """
                        mvn package -DskipTests -B
                    """
                }
            }
            post {
                success {
                    echo "Application packaged successfully"
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
                failure {
                    echo "Packaging failed"
                    error("Packaging failed")
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    echo "Building Docker image: ${DOCKER_IMAGE}"
                    sh """
                        docker build -t ${DOCKER_IMAGE} -t ${DOCKER_IMAGE_LATEST} .
                    """
                }
            }
            post {
                success {
                    echo "Docker image built successfully"
                    script {
                        // List the built images
                        sh "docker images | grep ${APP_NAME}"
                    }
                }
                failure {
                    echo "Docker build failed"
                    error("Docker build failed")
                }
            }
        }
        
        stage('Docker Push') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                script {
                    echo "Pushing Docker image to registry..."
                    
                    // Tag images with registry prefix
                    def taggedImage = "${DOCKER_REPOSITORY}:${APP_VERSION}"
                    def taggedLatest = "${DOCKER_REPOSITORY}:latest"
                    
                    sh """
                        docker tag ${DOCKER_IMAGE} ${taggedImage}
                        docker tag ${DOCKER_IMAGE_LATEST} ${taggedLatest}
                    """
                    
                    // Login to Docker registry
                    withCredentials([usernamePassword(
                        credentialsId: "${DOCKER_CREDENTIALS_ID}",
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh """
                            echo \${DOCKER_PASS} | docker login ${DOCKER_REGISTRY} -u \${DOCKER_USER} --password-stdin
                        """
                    }
                    
                    // Push images
                    sh """
                        docker push ${taggedImage}
                        docker push ${taggedLatest}
                    """
                    
                    echo "Docker images pushed successfully:"
                    echo "  - ${taggedImage}"
                    echo "  - ${taggedLatest}"
                }
            }
            post {
                success {
                    echo "Docker images pushed successfully to registry"
                }
                failure {
                    echo "Docker push failed"
                    error("Docker push failed")
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "Pipeline execution completed"
                
                // Clean up Docker images to save space
                sh """
                    docker image prune -f || true
                """
            }
        }
        success {
            echo "Pipeline succeeded!"
            // You can add notifications here (email, Slack, etc.)
        }
        failure {
            echo "Pipeline failed!"
            // You can add failure notifications here
        }
        cleanup {
            // Clean workspace
            cleanWs()
        }
    }
}

