# Remote Desktop Application 


<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot1]]
[![Product Name Screen Shot][product-screenshot2]]


DeskView is a java remote desktop application that provides a way for users to access and control a remote computer or server from a different location. This project has implemented many features which includes: 
* Uses Java socket to send data to the server via the transmission control protocol to simulate remote control.
* Uses java threads to concurrently receive and send data between client and server.
* User interface developed using javaFx.




<!-- GETTING STARTED -->
## Getting Started

Instructions on setting up this project locally on your own machine.

### Prerequisites



This project was built with Maven version 3.9.6 and Java version 21.0.1

#### Linux
* Maven
  ```sh
  sudo apt install maven
  ```
* Java
    ```sh
  sudo apt install default-jdk
  ```

#### Windows
* Maven
  1. Download and Extract package from Maven website
  2. Add MAVEN_HOME System Variable to the maven directory
  3. Add MAVEN_HOME Directory in PATH Variable
 
     
* Java
  1. Download Java from oracle website and complete the installer
  2. Add Java to System Variables
  3. Add JAVA_HOME Variable to path
  
    
### Installation

_Installation and setup of application_

1. Clone the repo
   ```sh
   git clone https://github.com/fishnchipa/DeskView.git
   ```   
2. Install the dependencies 
   ```sh
   mvn install
   ```
3. Run the application with
   ```sh
   mvn javafx:run
   ```
   _or_
   
      ```sh
   mvn clean javafx:run
   ```






