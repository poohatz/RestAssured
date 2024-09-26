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
            post {
                always {
                    // Zawsze wyświetl raporty testów TestNG, nawet jeśli testy nie powiodły się
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
                success {
                    // Archiwizowanie pliku JAR po udanym budowaniu
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Publish reports') {

            steps{
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])
            }

        }

    }

}
