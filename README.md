# FAST-maven-jenkins-plugin

## Introduction

TODO Describe what your plugin does here

## Project Replication

In order to replicate the project follow these steps:

### Pre-requisites

1. Have git installed - [Download](https://git-scm.com/downloads)

2. Have Python version 3 installed - [Download](https://www.python.org/downloads/)

3. Have the pip installed - [How to install pip](https://pip.pypa.io/en/stable/cli/pip_install/)

4. Have the Java version 11+ installed - [Download](https://www.oracle.com/java/technologies/downloads/)

5. Maven >= 3.8.1 - [Download](https://maven.apache.org/download.cgi)

### Getting started

1. Clone the repository:
   ```bash
   git clone https://github.com/Wms5/TG.git
   ```
2. Open the repository where the FAST-maven-jenkins-plugin repository was cloned:
	```bash
	cd FAST-maven-jenkins-plugin
	```
3. Run the following command:
	```bash
	mvn hpi:run
	```
4. Open Jenkins on browser:
   ```bash
   http://localhost:8080/jenkins
   ```
### Jenkins Configuration
   
1. Install maven plugin on Jenkins:

	1. Click on Manage Jenkins
    1. Click on Manage Plugins
    1. Click on Available 
    1. Search for Maven Integration and select it
    1. Click on Install and Restart

2. Install Git plugin:

    1. Click on Manage Jenkins
	1. Click on Manage Plugins
	1. Click on Available
    1. Search for Git and select it
    2.  Click on Install and Restart

3. Configure Maven on Jenkins:
   manage jenkins -> global tool configuration -> maven -> add maven
   -> Name and last version -> apply and save

4. Create new 
   1. Click on New Task
   2. 
    

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

Criar projeto maven: 

