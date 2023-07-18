# Bits Please Front-End
A repository for the Bits Please team during the summer of 2023 Intro to Software Engineering Course.

## Getting Started

#### Clone Repository
Clone the repository to your local machine: 

`git clone https://github.com/rharwood/bits_please`

`cd bits_please`

#### Config Setup
1. Copy `config.ini.template` file to `config.ini`
2. Put your Bits Please API Key in `config.ini` after `api.key=`
   1. No punctuation needed
3. Save `config.ini`

#### Build Project with Maven
If needed, install Maven. If unsure, run `mvn -v` in terminal.
- [Install Maven Windows](https://phoenixnap.com/kb/install-maven-windows)
- [Install Maven Linux/Ubuntu](https://www.digitalocean.com/community/tutorials/install-maven-linux-ubuntu#installing-maven-on-linux-ubuntu)

`mvn dependency:tree`

`mvn clean package`

#### Run the Application
`java -jar target/trivial-compute-1.0-SNAPSHOT.jar`



## Directory Structure
**game**/ (package) - The **_game_** package encapsulates the core components of the Trivial Compute Game, including the game controller for managing the flow of the game, player data for storing player information, and the main class that initializes and runs the game.

- GameBoard.java
- GameController.java
- GameData.java
- PlayerData.java
- Question.java
- Square.java
- TrivialComputeGame.java


**pages**/ (package) - The **_pages_** package contains classes representing different GUI pages of the Trivial Compute Game, including the welcome page, player name input page, question editor page, gameplay page, winner page, and instructions page.

- GameplayPage.java
- GameSettingsPage.java
- InstructionsPage.java
- PlayerNameInputPage.java
- QuestionAnswerPage.java
- QuestionEditorPage.java
- WelcomePage.java
- WinnerPage.java

**bits_please_api**/ (package) - The **_bits_please_api_** package is the intended interface for interacting with the Bits Please REST API. Contains classes for initializing client, creating/modifying/deleting/retrieving questions. 

- `Note: Please Ensure the SQL Instance is started prior to dev/testing.` 
  - ([Bits Please Backend Repo - start-stop-bits-please-db](https://github.com/TheFrogThatIs/bits_please_backend/tree/main/CloudFunctions/start-stop-bits-please-db))
- APIRequestException.java
- APITest.java
  - _Executable. Good for testing these classes._
- BitsPleaseRequest.java
- ConfigReader.java
- CreateQuestions.java
- InitializeClient.java
- JSONQuestions.java
- ModifyQuestions.java
- QuestionDifficulty.java
- RetrieveQuestions.java

### Dependencies
- **Maven**: _com.apache.httpcomponents:httpclient:4.3.2_
- **Maven**: _com.json:json:20230618_