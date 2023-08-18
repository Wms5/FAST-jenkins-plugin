# FAST-maven-jenkins-plugin

## Introduction

TODO Describe what your plugin does here

## Project Replication

While this plugin is not available on Jenkins marketplace you can run locally.
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
   1. Click on Manage Jenkins
   2. Click on Global tool configuration 
   3. Find Maven section
   4. Click on add maven
   5. Insert a name and Click on "install automatically"
   6. Apply and Save

4. Create new Jenkins Job
   1. Click on New Item
   2. Give a name to the Item and click "ok"

### Jenkins Job Configuration

1. Select "Git" as source code management and insert a Repository URL
2. On the "Build Steps" section, click on "Add build step" and select "Invoke top-level Maven targets"
3. Select maven version as the configured steps back and insert "build" on goals
4. Click on "Add build step" and select "FAST"
5. Insert the algorithm to make the prioritization. Ex: FAST-pw
6. Click on "Add build step" and select "Invoke top-level Maven targets"
7. Select maven version as the configured steps back and insert "test -Dtest=FASTPrioritizedSuite" 
8. Click on "Save" button

### Running the Jenkins Job 
Click on "Build Now"

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)