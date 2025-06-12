pipeline{
    agent any
     environment {
        SONARQUBE_SERVER = 'http://localhost:9000/'
        SONAR_TOKEN = '04570e1f89a3b4ea53cca10f08a70357bbbd8139'
    }
    stages{
        //   stage('Set Version') {
        //     steps {
        //         sh "mvn versions:set -DnewVersion=${VERSION}"
        //     }
        // }
        stage('get from github'){
            steps{
                echo 'pulling from tahani branch';
                git branch : 'tahani-cherif-etudiant',
                url : 'git@github.com:TaiebKacem2023/Groupe4-3ARTIC1-2425.git',
                credentialsId:'github-ssh-key'
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
        //       stage('Deploy to Nexus') {
        //     steps {
        //         script {
        //             def pom = readMavenPom file: 'pom.xml'
        //             def version = pom.version
        //             def repo = version.contains("SNAPSHOT") ? "maven-snapshots" : "maven-releases"
        //             def repoUrl = "http://172.26.160.39:8081/repository/${repo}"

        //             sh "mvn deploy"
        //         }
        //     }
        // }
         stage('Building image docker'){
              steps{
                 echo 'building ...';
                 sh "docker build -t tahanicherif/foyer:1.0.0 ."
              }
         }

         stage('pushing docker hub'){
              steps{
                  echo 'pushing to docker'
                  sh "docker login -u tahanicherif -p tahani123"
                  sh " docker push tahanicherif/foyer:1.0.0"
                //   withCredentials([usernamePassword(credentialsId: 'd8b99c11-39f9-49f8-8718-97581a71865a', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                //       sh " docker push tahanicherif/foyer:1.0.0"
                //   }
              }
          }
           stage('Docker Compose') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
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