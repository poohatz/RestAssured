pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pobieranie kodu źródłowego z repozytorium
                git 'https://github.com/poohatz/RestAssured'
            }
        }

        stage('Build and Test') {
            steps {
                // Uruchomienie budowy i testów Mavena
                sh 'mvn clean test'
            }
        }

        stage('Publish reports') {

            steps{
                publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])
            }

        }

    }

}
