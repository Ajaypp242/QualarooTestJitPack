node {
    stage('Checkout') {
        checkout scm
    }
    stage('Build and test') {
        sh './gradlew clean qualaroo-sdk:build jacocoTestReport -PQUALAROO_S3_ACCESS_KEY=0 -PQUALAROO_S3_SECRET_KEY=0'
        junit 'qualaroo-sdk/build/test-results/**/*.xml'
    }
    stage('UI tests') {
        sh "$ANDROID_HOME/emulator/emulator @NexusSAPI19"
        sh './gradlew connectedCheck'
    }
    stage('SonarQube analysis') {    
          def PULL_REQUEST = env.CHANGE_ID
          def scannerHome = tool 'SonarQubeScanner'
          withSonarQubeEnv('QualarooSonarQube') {
             echo "Running analysis on ${env.BRANCH_NAME}"        
             if (env.BRANCH_NAME == "dev") {            
                echo "Running full analysis because it's a dev branch" 
                sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectName='Android SDK' -Dsonar.projectKey=android-sdk -Dsonar.sources=qualaroo-sdk/src/main/java -Dsonar.language=java -Dsonar.java.binaries=qualaroo-sdk/build/intermediates/classes/debug -Dsonar.junit.reportPaths=qualaroo-sdk/build/test-results/testDebugUnitTest -Dsonar.jacoco.reportPaths=qualaroo-sdk/build/jacoco/testDebugUnitTest.exec -Dsonar.java.source=1.7"
             } else if (env.BRANCH_NAME != "master") {
                echo "Running preview analysis because it's probably a PR" 
                sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectName='Android SDK' -Dsonar.projectKey=android-sdk -Dsonar.sources=qualaroo-sdk/src/main/java -Dsonar.language=java -Dsonar.java.binaries=qualaroo-sdk/build/intermediates/classes/debug -Dsonar.junit.reportPaths=qualaroo-sdk/build/test-results/testDebugUnitTest -Dsonar.jacoco.reportPaths=qualaroo-sdk/build/jacoco/testDebugUnitTest.exec -Dsonar.java.source=1.7 -Dsonar.github.pullRequest=${env.CHANGE_ID} -Dsonar.github.repository=qualaroo/android-mobile-sdk-src -Dsonar.github.oauth=$GITHUB_ACCESS_TOKEN -Dsonar.analysis.mode=preview -Dsonar.github.disableInlineComments=true"
             }             
          }
    }
}
