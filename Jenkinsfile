pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = '668628646562'
        AWS_REGION = 'eu-north-1'
        IMAGE_NAME = 'qm-app'
        ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
        APP_SERVER = 'ubuntu@172.31.41.179'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/harsheeen/QuantityMeasurementAppSpringBoot.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t qm-app:latest .'
            }
        }

        stage('Login To ECR') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'aws-ecr-creds',
                        usernameVariable: 'AWS_ACCESS_KEY_ID',
                        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                    )
                ]) {

                    sh '''
                    aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                    aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                    aws configure set region ${AWS_REGION}

                    aws ecr get-login-password --region ${AWS_REGION} | \
                    docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                    '''
                }
            }
        }

        stage('Tag Image') {
            steps {
                sh 'docker tag qm-app:latest ${ECR_URI}:latest'
            }
        }

        stage('Push Image') {
            steps {
                sh 'docker push ${ECR_URI}:latest'
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['ec2-ssh-key']) {

                    sh '''
                    ssh -o StrictHostKeyChecking=no ${APP_SERVER} << EOF

                    aws ecr get-login-password --region ${AWS_REGION} | \
                    docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

                    cd ~/QuantityMeasurementAppSpringBoot

                    git pull

                    docker compose pull

                    docker compose up -d --force-recreate

                    EOF
                    '''
                }
            }
        }
    }
}
