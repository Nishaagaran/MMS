pipeline {
    agent any
    
    environment {
        // Application configuration
        APP_NAME = 'movie-management-system'
        APP_VERSION = "${env.BUILD_NUMBER}"
        
        // Maven configuration (Java 17 compatible - MaxPermSize removed in Java 8+)
        MAVEN_OPTS = '-Xmx1024m'
    }
    
    tools {
        jdk 'java17'
        maven 'Maven3'
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
                    bat """
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
                    bat """
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
                    bat """
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
    }
    
    post {
        always {
            script {
                echo "Pipeline execution completed"
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

