import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private static Player player = new Player();
    private static Player cpu = new Player();
    /* D6 - The `GoFish` class composes the `Pool`
     * class as a `Pool` 
     */
    private static Pool cardPool = new Pool();

    @Override
    public void start(Stage stage) {
        stage = GoFish.stage;

        stage.setTitle("Go Fish!");

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
                stage.setScene(startGameScene());
                stage.show();
                System.out.println(stage.getScene());
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



        HBox interactableSpace = new HBox(cardPool, new Button("Ask for rank"));

        gameSpace.setTop(cpu);
        BorderPane.setMargin(cpu, new Insets(20, 0, 0, 0));
        gameSpace.setCenter(interactableSpace);
        BorderPane.setMargin(interactableSpace, new Insets(50, 0, 50, 250));
        gameSpace.setBottom(player);
        BorderPane.setMargin(player, new Insets(0, 0, 20, 0));
        
        Pane root = new Pane(gameSpace);


        initializeGame();

        return new Scene(root);

    }

    // Inner Class managing game state
    static class Game {
        
    }

    /**
     * Builds
     */
    private static void initializeGame() {
        cardPool.dealCards(player, cpu);
        cardPool.draw(-1);
    }


    public static void main(String[] args) {
        launch();
    }
}