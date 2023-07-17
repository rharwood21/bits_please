## Game Package

The `game` package contains the core components of the Trivial Compute Game.

### Files

- `GameController.java`: This class handles the game logic and flow, coordinating the different pages and managing the game state.
- `PlayerData.java`: This class stores the player information such as names and scores.
- `TrivialComputeGame.java`: The main class that initializes and runs the Trivial Compute Game.

### Usage

To run the Trivial Compute Game, follow these steps:

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
