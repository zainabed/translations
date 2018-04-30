pipeline {

	agent any
	
	tools {
		maven 'Maven 3.2.1'
	}
	
	stages {
	
		stage("Unit Test") {
		
			steps {
				sh "mvn clean test"
			}
		}
	
	}
}