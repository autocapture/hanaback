def ecrLoginHelper="docker-credential-ecr-login"

pipeline {
    agent any

    stages {
        stage('Pull Codes from Github'){
            steps{
                checkout scm
            }
        }
        stage('Build Codes by Gradle') {
            steps {
                sh """
                ./gradlew clean bootJar
                """
            }
        }
        stage('Run Spring Boot App') {
            steps {
                script {
                    if (env.envSelected == "dev" || env.envSelected == "test") {
                        echo 'triggered by dev or test'
                        ansiblePlaybook installation: 'ansible2', inventory: 'dev.inv', playbook: 'ansible.yml', disableHostKeyChecking: true
                    } else {
                        echo 'triggered by prod'
                        input "Continue Deployment to Prod ? Are you Sure ?"
                        ansiblePlaybook installation: 'ansible2', inventory: 'dev.inv', playbook: 'ansible.yml', disableHostKeyChecking: true
                        // check below code for IP ssh based deployment
                        // for different Ips
                        // IP address and role goes in dev.inv
                        /**[webservers]
                          IP-address ansible_user=ec2-user
                          **/
                        // command changes to include crendeitalsId
                        // private-key values if your jenkins configured key to connect to server IP
                        // check the screenshot you have
                        // ansiblePlaybook crendeitalsId: 'private-key', installation: 'ansible2', inventory: 'dev.inv', playbook: 'ansible.yml', disableHostKeyChecking: true
                    }
                }
        }
    }
}
