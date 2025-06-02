pipeline{
    agent any
     environment {

        SONARQUBE_SERVER = 'http://localhost:9000/'
        SONAR_TOKEN = '7d0837a136ed8d8f56c1a2f613ccacb567fc78b79ab67a37adae7b9b54078921'

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
        stage('MVN clean'){
            steps{
               echo 'cleaning';
               sh "mvn clean"
            }
        }
        // stage('MVN install'){
        //     steps{
        //         echo 'installing';
        //          sh "mvn install"
        //          }
        // }
        stage('MVN compile'){
            steps{
                echo 'compiling';
                sh "mvn compile"
            }
        }
        stage('SonarQube analysis') {
            steps {
                echo "Code analysis"
                sh "mvn sonar:sonar -Dsonar.url=${SONARQUBE_SERVER} -Dsonar.login=${SONAR_TOKEN}"
            }
        }
        stage('MVN test'){
            steps{
                echo 'testing';
                sh "mvn test"
            }
        }
        //stage('MVN sonarqube'){
             //steps{
             //    echo 'quality code';
             //    withSonarQubeEnv('examenTpFoyer') {
             //        sh 'mvn sonar:sonar -Dsonar.projectKey=examenTpFoyer'
            //     }
           //  }
      //   }
        // stage('MVN deploy'){
        //     steps{
        //         echo 'deploying';
        //         sh "mvn deploy"
        //     }
        // }
         stage('Building image'){
              steps{
                 echo 'building ...';
                 sh "docker build -t tahanicherif/foyer:1.0.0 ."
              }
         }

         stage('pushing'){
              steps{
                  echo 'pushing to docker'
                  sh "docker login -u tahanicherif -p tahani123"
                  sh " docker push tahanicherif/foyer:1.0.0"
                //   withCredentials([usernamePassword(credentialsId: 'd8b99c11-39f9-49f8-8718-97581a71865a', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                //       sh " docker push tahanicherif/foyer:1.0.0"
                //   }
              }
          }


    }
}