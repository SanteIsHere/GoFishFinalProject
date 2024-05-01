import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GoFish
 * D10 - Simulation of a "Go Fish" game
 */
public class GoFish extends Application {
    
    private static Stage stage = new Stage();
    private static Pool cardPool = new Pool();
    private static GameManager manager = new GameManager();
    private static CPUPlayer cpu = new CPUPlayer(manager);
    private static HumanPlayer player = new HumanPlayer(manager, cardPool,
     cpu);

    @Override
    public void start(Stage stage) {
        stage = GoFish.stage;

        stage.setTitle("Go Fish!");

        /* Display the splash/start screen at application start */
        stage.setScene(startScreen());

        stage.show();
    }

    /**
     * Build the Scene Graph for the Start/Title Screen
     * @return The `Scene` object representing the Title Screen
     */
    private static Scene startScreen() {
        Text title = new Text("Go Fish!");
        title.setFont(Font.font(null, FontWeight.BOLD, 30));

        // Button allowing a user to the start a game
        Button startGame = new Button("Start Game");
        
        /* D6 - Define the callback for the "Mouse Clicked" event on  
         * the `startGame` button
        */
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                GameManager.initGame();
            }
        });
        VBox root = new VBox(title, startGame);

        return new Scene(root);
    }

    /**
     * Build the Scene Graph for the game
     * @return The `Scene` object representing the Go Fish game 
     */
    private static Scene startGameScene() {
        BorderPane gameSpace = new BorderPane();

        // Give the game board a green background color
        gameSpace.setStyle("-fx-background-color: green;");

        HBox interactableSpace = new HBox(cardPool);

        gameSpace.setTop(cpu);
        BorderPane.setMargin(cpu, new Insets(20, 0, 0, 0));
        gameSpace.setCenter(interactableSpace);
        BorderPane.setMargin(interactableSpace, new Insets(50, 0, 50, 250));
        gameSpace.setBottom(player);
        BorderPane.setMargin(player, new Insets(0, 0, 20, 0));
        
        Pane root = new Pane(gameSpace);

        return new Scene(root);

    }

    private static Scene gameOverScreen() {
        BorderPane display = new BorderPane();

        display.setCenter(new Text("Game Over!"));

        return new Scene(display);
    }

    /* D7 - Inner Class managing game state 

     * D8 - The `GoFish` class "composes" or 
     * strongly aggregates the inner `GameManager`
     * class as their lifetimes are strongly tied
     */
    protected static class GameManager {         
        private static void initGame() {
            cardPool.dealCards(player, cpu);
            stage.setScene(startGameScene());
            stage.sizeToScene();
            stage.show();
        }

        /**
         * D13 - This method handles the `CardsExhaustedException`
         * by printing the message associated with the exception
         * to the SysErr stream and displaying the `Game Over` screen
         * indicating which player won
         * @param message
         */
        public void handleCardsExhausted(String message) {
            stage.setScene(gameOverScreen());
            System.err.println(message);
        }
    } 

    


    public static void main(String[] args) {
        launch();
    }
}