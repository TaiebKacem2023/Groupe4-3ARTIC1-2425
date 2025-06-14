pipeline{
    agent any
     environment {
        SONARQUBE_SERVER = 'http://localhost:9000/'
        SONAR_TOKEN = '4225d12c02a1166b5b29b55a993a08b3e0bd4cf7'
        DOCKERHUB_CREDENTIALS = 'token-dockerhub'
        IMAGE_NAME = 'foyer'
        IMAGE_TAG = 'latest'
    }
    stages{
        stage('get from github'){
            steps{
                echo 'pulling from tahani branch';
                git branch : 'tahani-cherif-etudiant',
                url : 'git@github.com:TaiebKacem2023/Groupe4-3ARTIC1-2425.git',
                credentialsId:'github-ssh-key'
            }
        }
         stage('RUN DATABASE'){
    steps{
        echo 'Stopping and removing any existing database container...';
        sh "docker rm -f database || true"
        echo 'Starting database container...';
        sh "docker-compose up --build -d database"
        sh "sleep 20"
    }
}

        stage('MVN clean'){
            steps{
               echo 'cleaning';
               sh "mvn clean"
            }
        }
        stage('MVN compile'){
            steps{
                echo 'compiling';
                sh "mvn compile"
            }
        }

        stage('MVN test'){
            steps{
                echo 'testing';
                sh "mvn test"
            }
        }
        stage('SonarQube analysis') {
            steps {
                echo "Code analysis"
                sh "mvn sonar:sonar -Dsonar.url=${SONARQUBE_SERVER} -Dsonar.login=${SONAR_TOKEN}"
            }
        }
        stage('MVN package'){
            steps{
                echo 'MVN package';
                sh "mvn package"
            }
        }
              stage('Deploy to Nexus') {
            steps {
                script {
                    sh "mvn deploy"
                }
            }
        }
         stage('Building image docker'){
              steps{
                 echo 'building ...';
                 sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
              }
         }
          stage('Push docker image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_PASSWORD'
                )]) {
                    echo 'Login ...'
                    sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
                    echo 'Tagging image...'
                    sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}"
                    echo 'Pushing docker image to docker hub...'
                    sh "docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
           stage('Docker Compose') {
            steps {
                 echo '🧹 Stopping and removing old containers...'
                sh '''
                    docker stop phpmyadmin backend || true
                    docker rm phpmyadmin backend || true
                '''
                sh 'docker-compose down || true'
                sh 'docker-compose up -d --build'
            }
        }


    }

    post {
        success {
            echo '✅ Pipeline terminée avec succès !'
        }
        failure {
            echo '❌ La pipeline a échoué.'
        }


    }
}