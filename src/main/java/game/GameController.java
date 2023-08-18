package game;

import bits_please_api.APIRequestException;
import multiplayer.GameplayController;
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
    private boolean isOnlineGame;
    private boolean isMultiplayerController;
    private GameplayController multiplayerController;
    
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
        instructionsPage = new InstructionsPage(this);

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
        playerNameInputPage.refreshPlayerNameInputPage();
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
     * @param playersInitialized - boolean to confirm player initialization
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
     * @param winnerIndex - index of winning player
     */
    public void showWinnerPage(int winnerIndex) {
        disposePages();
        winnerPage = new WinnerPage(this, winnerIndex);
        winnerPage.setVisible(true);
    }
    /**
     * Shows the Instructions page.
     * @param returnPage - string defining the return page
     */
    public void showInstructionsPage(String returnPage){
        disposePages();
        instructionsPage.returnPage = returnPage;
        instructionsPage.setVisible(true);
    }

    // Other methods for managing the game flow, handling user input, etc.
    // TODO: Consider only disposing the page that is currently active by passing it as arg to here
    private void disposePages(){
        // Dispose all locations instructions can be called from
        welcomePage.dispose();
        playerNameInputPage.dispose();
        gameSettingsPage.dispose();
        questionEditorPage.dispose();
        if (gameplayPage != null){
            gameplayPage.dispose();
        }
        instructionsPage.dispose();
    }

    public boolean isMultiplayerController() {
        return isMultiplayerController;
    }

    public void setMultiplayerController(boolean multiplayerController) {
        isMultiplayerController = multiplayerController;
    }

    public boolean isOnlineGame() {
        return isOnlineGame;
    }

    public void setOnlineGame(boolean onlineGame) {
        isOnlineGame = onlineGame;
    }

    public void initializeMultiplayerController(){
        multiplayerController = GameplayController.getInstance(this);
    }
    public GameplayController getMultiplayerController(){
        return multiplayerController;
    }

    public void refreshNameInputPage(){
        playerNameInputPage.refreshPlayerNameInputPage();
    }
    public void playerColorChoice(int playerIndex, String colorString) {
        playerNameInputPage.playerChoseColor(playerIndex, colorString);
    }
}
