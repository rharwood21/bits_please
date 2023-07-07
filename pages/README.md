## Pages Package

The `pages` package contains the graphical user interface (GUI) pages for the Trivial Compute Game.

### Files

- `GameplayPage.java`: Represents the gameplay page where the actual game takes place. Displays questions, options, and tracks player scores.
- `InstructionsPage.java`: Provides instructions and rules for playing the Trivial Compute Game. Displays a scrollable image or PDF content.
- `PlayerNameInputPage.java`: Allows players to enter their names before starting the game. Supports 1-4 player names.
- `QuestionEditorPage.java`: Enables editing and customization of game questions, including adding, modifying, or deleting questions.
- `WelcomePage.java`: The initial page of the game that welcomes the players and provides an introduction.
- `WinnerPage.java`: Displays the winner(s) of the Trivial Compute Game, along with their final scores.

### Usage

To run the Trivial Compute Game and interact with the GUI pages, follow these steps:

1. Make sure you have Java Development Kit (JDK) installed on your system.
2. Compile the Java files in both the `game` and `pages` packages using the `javac` command. For example:
   ```
   javac game/*.java pages/*.java
   ```
3. Run the game using the `java` command and specify the fully qualified class name of `TrivialComputeGame` in the `game` package as the entry point. For example:
   ```
   java game.TrivialComputeGame
   ```
4. The game will start, and you will be presented with the welcome page. Follow the instructions on each page to navigate through the game, input player names, edit questions, play the game, and see the winner(s).

Please make sure to adjust the `javac` and `java` commands based on your specific directory structure and package names.