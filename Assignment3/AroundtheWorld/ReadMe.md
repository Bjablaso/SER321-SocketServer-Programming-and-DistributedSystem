# Assignment 3: Guess Around the World

## Overview
**Guess Around the World** is an interactive game where users guess
locations based on displayed images. The game runs on a client-server
architecture, where the server manages game data and client interactions.

## Features
- Theoretical multiplayer support
- Timer-based scoring system
- Sequential question progression
- Pop-up UI for correct answers
- Command-line and GUI support

---

## **Running the Application**

### **1. Server Setup**
Before starting the game, you must start the server.

#### **Terminal Command to Start Server**
Run the following command inside the project directory:
```sh
./gradlew startServer -Pport=8889
```
- `-Pport=8889` specifies the server port (default: 8889).
- The server must be running before starting the client.
- The server can be started locally or on **AWS**.
- The current **AWS-hosted server** is available, but users can choose to **run it locally** if needed.

### **2. Client Setup**
Once the server is running, launch the client.

#### **Terminal Command to Start Client**
```sh
./gradlew startSudoClient Pip="51.20.144.68"-Pport=8889 //-> gradle haing cache issue run via application code umtil it resolve
```
- The client will automatically connect to the running server.

### **3. GUI Usage (Starting the Program)**
1. **Launch the Game**
   - Start the **Landing Page**, which is the entry point for the application.
   - This page prompts users to enter a **port number**.
   - Pressing `OK` without a number will accept the **default port (8080)**.

2. **Server Auto-Start**
   - If the server is not already running, it will **start automatically** when a port is entered.
   - If the server is not available, the program **will not start**.

3. **Gameplay**
   - The game starts in `startupwindow.fxml`.
   - Players will see an image and select the correct answer from multiple choices.
   - The **countdown timer** determines the final score:
      - **> 15 sec:** Full points (100)
      - **1-15 sec:** Minimum points (50)
      - **0 sec:** No points (0)
   - If the player selects the **correct answer**, a **Win Screen popup** appears, and the game progresses to the next round.

---

## **Technical Implementation**

### **Main Classes & Responsibilities**
| Class | Responsibility |
|-----------------|--------------------------------------------------|
| `LandingPage.java` | Entry point, initializes server and UI setup |
| `ViewSwitcher.java` | Handles scene transitions between UI screens |
| `GameWindowController.java` | Manages game logic, timer, and answer validation |
| `LoadData.java` | Loads and manages game data sequentially |
| `CountDown.java` | Manages countdown timer and score allocation |
| `SocketServer.java` | Manages server-side logic |
| `Client.java` | Handles client-server communication |

### **File Structure**
```
/GuessAroundTheWorld
│── src/main/java
│   │── AppLogic/
│   │   │── LoadData.java
│   │   │── CountDown.java
│   │── Server/
│   │   │── SocketServer.java
│   │── Client/
│   │   │── Client.java
│   │── UI/
│   │   │── GameWindowController.java
│   │   │── LandingPage.java
│   │   │── ViewSwitcher.java
│── resources/
│   │── fxml/
│   │   │── startupwindow.fxml
│   │   │── winScreen.fxml
│   │── css/style.css
│── build.gradle
│── README.md
```

---

## **Defects & Known Issues**
- **Terminal Execution Issue:**
   - The application cannot currently be executed **directly from the terminal** due to a **caching issue**.
   - This will be **completed in the next few hours**.

- **Scoring System Not Yet Implemented:**
   - The scoring system has **not yet been fully implemented** due to time constraints.
   - This will be **completed in the next few hours**.

- **Personal Time Constraints:**
   - Due to **work-related issues** and **military exercises**, I had to dedicate less time to class this week.
   - I attempted to optimize and learn new things with **JavaFX and FXML**, but in the future, I will **stick to the required approach** to ensure efficiency.

---

## **Additional Notes**
- Ensure **JavaFX is installed** before running the game if depedency dont work.
- The application is built using **Gradle**, so all dependencies should resolve automatically.


### **Support & Troubleshooting**
If you encounter any issues, check the console logs for error messages and
verify that the **server is running** before starting the client.

[Github](https://github.com/Bjablaso/ser321-spring25-A-Bjablaso/tree/main/Assignment3/AroundtheWorld)