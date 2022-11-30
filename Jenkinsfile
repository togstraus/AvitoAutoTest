node {
    def mvnHome
    stage('Preparation') {
        checkout([$class: 'GitSCM', branches: [[name: '*/avito']],
        userRemoteConfigs: [[url: 'https://github.com/togstraus/AvitoAutoTest.git']]])
        mvnHome = tool 'M3'
    }
    stage('Build') {
        withEnv(["MVN_HOME=$mvnHome"]) {
            if(isUnix()) {
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean test'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test/)
            }
        }
    }
    stage('Report') {
        allure includeProperties: false, jdk: '', properties: [[key: 'allure.issues.tracker.pattern', value: 'https:atara-services.ru']], results: [[path: 'target/allure-results'], [path: 'other_target/allure-results']]
    }
}