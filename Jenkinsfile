pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = '592992781575'
        AWS_REGION = 'ap-south-1'
        IMAGE_NAME = 'quantitymeasurement-backend'
        ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'docker-cicd',
                url: 'https://github.com/Utkarsh-37/QuantityMeasurementAppSpringBoot.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('quantity-measurement-app') {
                    sh 'docker build -t quantitymeasurement-backend:latest .'
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
                    aws ecr get-login-password --region ap-south-1 | \
                    docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                    '''
                }
            }
        }

        stage('Tag Image') {
            steps {
                sh 'docker tag quantitymeasurement-backend:latest ${ECR_URI}:latest'
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
                ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/mykey.pem ubuntu@172.31.36.244 << EOF

                aws ecr get-login-password --region ap-south-1 | \
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
