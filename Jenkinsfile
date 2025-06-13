pipeline{
    agent any
    stages{
        stage('get from github'){
            steps{
                echo 'pulling from khouloud branch';
                git branch : 'main',
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
        stage('MVN install'){
            steps{
                echo 'installing';
                 sh "mvn install"
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
        //stage('MVN sonarqube'){
             //steps{
             //    echo 'quality code';
             //    withSonarQubeEnv('examenTpFoyer') {
             //        sh 'mvn sonar:sonar -Dsonar.projectKey=examenTpFoyer'
            //     }
           //  }
      //   }
        stage('MVN deploy'){
            steps{
                echo 'deploying';
                sh "mvn deploy"
            }
        }
         stage('Building image'){
              steps{
                 echo 'building ...';
                 sh "docker build -t tahanicherif/foyer:1.0.0 ."
              }
         }

          stage('pushing'){
              steps{
                  echo 'pushing to docker'
                  //sh " docker push khouloud01/foyer:1.0.0"
                  withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                      sh " docker push tahanicherif/foyer:1.0.0"
                  }
              }
          }


    }
}