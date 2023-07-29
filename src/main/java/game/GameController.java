package game;

import bits_please_api.APIRequestException;
import pages.*;

import javax.swing.*;

/**
 * The game controller for managing the navigation and flow of the Trivial Compute Game.
 */
public class GameController {
    private WelcomePage welcomePage;
    private GameSettingsPage gameSettingsPage;
    private PlayerNameInputPage playerNameInputPage;
    private QuestionEditorPage questionEditorPage;
    private GameplayPage gameplayPage;
    private WinnerPage winnerPage;
    private InstructionsPage instructionsPage;
    private QuestionAnswerPage questionAnswerPage;
    /**
     * Constructs a game.GameController object.
     * Initializes the GUI screens for the game.
     */
    public GameController() {
        // Create instances of GUI screens
        welcomePage = new WelcomePage(this);
        gameSettingsPage = new GameSettingsPage(this);
        playerNameInputPage = new PlayerNameInputPage(this);
        questionEditorPage = new QuestionEditorPage(this);
        // gameplayPage = new GameplayPage(this); // Cannot initialize here, given dependency on Player names
        winnerPage = new WinnerPage(this);
        instructionsPage = new InstructionsPage(this);
        questionAnswerPage = new QuestionAnswerPage(this);

        // Start the application by displaying the welcome page
        showWelcomePage();
    }
    /**
     * Shows the welcome page.
     */
    public void showWelcomePage() {
        disposePages();
        welcomePage.setVisible(true);
    }
    /**
     * Shows the game settings page.
     */
    public void showGameSettingsPage() {
        disposePages();
        gameSettingsPage.setVisible(true);
    }
    /**
     * Shows the player name input page.
     */
    public void showPlayerNameInputPage() {
        disposePages();
        playerNameInputPage.setVisible(true);
    }
    /**
     * Shows the question editor page.
     */
    public void showQuestionEditorPage() {
        disposePages();
        questionEditorPage.setVisible(true);
    }
    /**
     * Shows the gameplay page.
     */
    public void showGameplayPage(boolean playersInitialized) {
        disposePages();
        try {
            GameData.initializeQuestionMap(); // Initialize Questions for Gameplay
        } catch (APIRequestException e){
            JOptionPane.showMessageDialog(null, "Questions Not Initialized!\n" +
                    "Please Ensure Database is up.", "Error", JOptionPane.ERROR_MESSAGE);

            return; // This return will close the application
        }

        if (playersInitialized){
            gameplayPage = new GameplayPage(this);
            gameplayPage.updatePlayerNames(); // Update player names in the pages.GameplayPage
        }
        gameplayPage.setVisible(true);
    }
    /**
     * Shows the winner page.
     */
    public void showWinnerPage() {
        disposePages();
        winnerPage.setVisible(true);
    }

    /**
     * Shows the question and answer page
     */
    public void showQuestionAnswerPage(Question currentQuestion) {
        disposePages();
        questionAnswerPage.setCurrentQuestion(currentQuestion);
        questionAnswerPage.setVisible(true);
    }
    
    public void showInstructionsPage(String returnPage){
        disposePages();
        instructionsPage.returnPage = returnPage;
        instructionsPage.setVisible(true);
    }

    // Other methods for managing the game flow, handling user input, etc.
    private void disposePages(){
        // Dispose all locations instructions can be called from
        welcomePage.dispose();
        playerNameInputPage.dispose();
        gameSettingsPage.dispose();
        questionEditorPage.dispose();
        if (gameplayPage != null){
            gameplayPage.dispose();
        }
        winnerPage.dispose();
        instructionsPage.dispose();
        questionAnswerPage.dispose();
    }
}
