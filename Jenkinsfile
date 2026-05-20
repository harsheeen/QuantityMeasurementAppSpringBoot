pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = '668628646562'
        AWS_REGION = 'eu-north-1'
        IMAGE_NAME = 'qm-app'
        ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
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
                dir('quantity-measurement-app') {
                    sh 'docker build -t qm-app:latest .'
                }
            }
        }

        stage('Login to ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-ecr-creds'
                ]]) {

                    sh '''
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

        stage('Deploy to Application EC2') {
            steps {
                sh '''
                ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/mykey.pem ubuntu@172.31.41.179 << EOF

                aws ecr get-login-password --region ${AWS_REGION} | \
                docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

                cd ~/QuantityMeasurementAppSpringBoot/quantity-measurement-app

                docker compose pull
                docker compose up -d --force-recreate

EOF
                '''
            }
        }
    }
}
