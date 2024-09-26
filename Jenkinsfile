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
                // Uruchomienie testów TestNG
                sh 'mvn clean test'
            }
            post {
                always {
                    // Zawsze archiwizuj raporty TestNG, nawet w przypadku niepowodzenia
                    archiveArtifacts artifacts: 'target/**/*', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            // Dodaj opcję archiwizowania raportów HTML
            publishHTML([target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target',
                reportFiles: 'index.html',
                reportName: 'TestNG HTML Report'
            ]])
        }
    }
}
