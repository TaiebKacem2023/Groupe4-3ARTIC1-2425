pipeline{
    agent any
    stages{
        stage('get from github'){
            steps{
                echo 'pulling from tahani branch';
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
        stage('MVN sonarqube'){
            steps{
                echo 'quality code';
                withSonarQubeEnv('examenTpFoyer') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=examenTpFoyer'
                }
            }
        }
        stage('MVN deploy'){
            steps{
                echo 'deploying';
                sh "mvn deploy"
            }
        }


    }
}