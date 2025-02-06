# Assignment 3 (Guess Around the world)

## HTML Image User

### Terminal Usage

## GUI Usage (Starting Up Program)

1. Create an Instance of the Langing Page

> In the Landing Page User prompt to enter a port number.
> By doing this the server instance will automatically start running.

LandingPage (entry point ot our program : house main function) - load a blank pane on to the scene and from there
the scene switcher load program starter window which is found in the ` startupwindow.fxml`.

#### Server Start UP

    - Server can be start up by specifying the port number on the landing page or by 
       pressing ok and accepting the defualt port number. 

#### Client will

    - Client is program to automatically connect to whatever port number the server is on

## Command Line usage

    - Server must be running befor program can start. if server is not running program will not start.

1. Server Command
   `./gradlew startServer -Pport=8080` -> run command in the program folder
2. Client
   `./gradlew startSudoClient -Pport=8080` -> run command in the program folder 