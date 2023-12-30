# Remote Desktop Application 
DeskView is a java remote desktop application that provides a way for users to access and control a remote computer or server from a different location. 

<!-- ABOUT THE PROJECT -->
## About The Project

This project has implemented features which includes: 
* Uses Java socket to send data to the server via the transmission control protocol to simulate remote control.
* Uses java threads to concurrently receive and send data between client and server.
* User interface developed using javaFx.
* Uses Java robot to manipulate controls.


![Product Name Screen Shot][product-screenshot1]

![Product Name Screen Shot][product-screenshot2]



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
## Usage
Connection is established through the Connection Id which is supplied on the front page when the application is started up. Supplying this connection Id to another user will request access and prompt a menu to pop up
which the Server will have to accept. After that screen sharing will begin where mouse and keyboard inputs will be avaliable to the Server.


[product-screenshot1]: images/image1.png
[product-screenshot2]: images/image2.png




