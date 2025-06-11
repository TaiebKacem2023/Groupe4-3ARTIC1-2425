pipeline{
	agent any
     environment {

		SONARQUBE_SERVER = 'http://localhost:9000/'
        SONAR_TOKEN = 'a476724a264cb65208717568adef73e9b079677c'

    }
    stages{
		stage('get from github'){
			steps{
				echo 'pulling from taieb branch';
                git branch : 'Taieb-Kacem-Foyer',
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
                 sh "docker build -t taiebkacem/foyer:1.0.0 ."
              }
         }

         stage('pushing'){
			steps{
				echo 'pushing to docker'
                  sh "docker login -u taiebkacem -p Azerty@123--"
                  sh " docker push taiebkacem/foyer:1.0.0"
                //   withCredentials([usernamePassword(credentialsId: 'd8b99c11-39f9-49f8-8718-97581a71865a', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                //       sh " docker push taiebkacem/foyer:1.0.0"
                //   }
              }
          }


    }
}