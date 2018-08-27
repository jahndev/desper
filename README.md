# Destinder API REST Service
==========================
#####   by Jahn Arellano - mail: jahnarellano85@gmail.com

## Setting up your local environment

### Install tools

- Java 8
- Maven 3

### IMPORTANT Hosts AWS DB
configure the database host in your /etc/hosts file

	181.199.145.86/32 desperdb.cwze0xjbk2up.us-east-1.rds.amazonaws.com

### Running

	sh startup.sh 

### Shutdown

    sh shutdown.sh

### Build
    mvn dependency:tree
	mvn clean install package


### Running from IDE

- In Intellij IDEA import project like maven project
- Configure Run properties add + Application and configure the main class: BootStrap 
- run o debug and enjoy

### Service

Database:

	Read/Write User:
	user: desperdb
	password: desperdb

	DB Host Master:
	desperdb.cwze0xjbk2up.us-east-1.rds.amazonaws.com/desperdb

AWS Console account:

	user: jahnarellano85@gmail.com	
	password: HRHRB2w2d3
	https://console.aws.amazon.com/rds/home?region=us-east-1#dbinstances:id=desperdb;sf=all	
	
Configuration Files

- Credentials.json
    - "dbConnections" configure the database connection
    - "api-despegar" despegar api uri
    
- hotels-migration-config.json
    - "chunk" represent the number of Items (Hotels) to be processed
    - "offset" used to offset into result of hotels endpoint