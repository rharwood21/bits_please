# Bits Please Front-End
A repository for the Bits Please team during the summer of 2023 Intro to Software Engineering Course.

### Pre-Requisites
#### Config Setup
1. Copy `config.ini.template` file to `config.ini`
2. Put your Bits Please API Key in `config.ini` after `api.key=`
   1. No punctuation needed
3. Save `config.ini`

#### Dependencies
1. In your IDE, add the following as a project dependency: 
   1. Example (IntelliJ IDEA): 
      1. File > Project Structure > Project Settings > Modules > 
      2. Add > Library > From Maven > Search `com.apache.httpcomponents:httpclient:4.3.2`
```
com.apache.httpcomponents.httpclient
com.json
```
2. Versions used during development:
   1. **Maven**: _com.apache.httpcomponents:httpclient:4.3.2_
   2. **Maven**: _com.json:json:20230618_


## Directory Structure
**game**/ (package) - The **_game_** package encapsulates the core components of the Trivial Compute Game, including the game controller for managing the flow of the game, player data for storing player information, and the main class that initializes and runs the game.

- GameController.java
- PlayerData.java
- TrivialComputeGame.java
- GameData.java
- GameBoard.java

**pages**/ (package) - The **_pages_** package contains classes representing different GUI pages of the Trivial Compute Game, including the welcome page, player name input page, question editor page, gameplay page, winner page, and instructions page.

- GameplayPage.java
- InstructionsPage.java
- PlayerNameInputPage.java
- QuestionEditorPage.java
- WelcomePage.java
- WinnerPage.java

**bits_please_api**/ (package) - The **_bits_please_api_** package is the intended interface for interacting with the Bits Please REST API. Contains classes for initializing client, creating/modifying/deleting/retrieving questions. 

- `Note: Please Ensure the SQL Instance is started prior to dev/testing.` 
  - ([Bits Please Backend Repo - start-stop-bits-please-db](https://github.com/TheFrogThatIs/bits_please_backend/tree/main/CloudFunctions/start-stop-bits-please-db))
- APIRequestException.java
- APITest.java -- Executable. Good for testing these classes.
- BitsPleaseRequest.java
- ConfigReader.java
- **CreateQuestions.java**
- InitializeClient.java
- **ModifyQuestions.java**
- **RetrieveQuestions.java**

### Dependencies
```
com.apache.httpcomponents.httpclient
com.json
```
Used in Development (As Project Depedency):
- **Maven**: _com.apache.httpcomponents:httpclient:4.3.2_
- **Maven**: _com.json:json:20230618_