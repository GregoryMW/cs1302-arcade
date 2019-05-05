package cs1302.arcade;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.stage.WindowEvent;

/** an arcade app with the games Space Invaders and 2048. */
public class ArcadeApp extends Application
{
    HBox menuScreen = new HBox(100);
    Stage spi;
    Stage tfe;
    Button sIStart;
    Button twenty48Start;

    /**
     * The main menu for the arcade app. contains a retro grid background
     * with two buttons, one for each of the games.
     */
    public void startMenu()
        {
            Image sIPic = new Image("SpaceInvadersLogo.jpg", 200, 200, false, true);
            ImageView logo = new ImageView(sIPic);
            sIStart = new Button();
            sIStart.setGraphic(logo);
            sIStart.setOnAction(launchSI());
            Image twenty48Pic = new Image("2048Logo.png", 200, 200, false, true);
            ImageView logo2 = new ImageView(twenty48Pic);
            twenty48Start = new Button();
            twenty48Start.setGraphic(logo2);
            twenty48Start.setOnAction(launch2048());
            Image backgroundPic = new Image("retroBG.png", 640, 480, false, true);
            menuScreen.setBackground(
                new Background(new BackgroundImage(
                                   backgroundPic,BackgroundRepeat.NO_REPEAT,
                                   BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                   BackgroundSize.DEFAULT)));
            menuScreen.setAlignment(Pos.CENTER);
            menuScreen.getChildren().addAll(sIStart, twenty48Start);
        }

    /**
     * when the Space Invader button is pushed, launches a new window
     * that runs the game.
     * @return handler the action event to launch the game.
     */
    public EventHandler<ActionEvent> launchSI()
        {
            spi = new Stage();
            EventHandler<ActionEvent> handler = e ->
                {
                    Thread t = new Thread(() -> {
                            Platform.runLater(() -> new SpaceInvaders().start(spi));
                            sIStart.setDisable(true);
                            twenty48Start.setDisable(true);
                    });
                    t.setDaemon(true);
                    t.start();
                };
            return handler;
        }

    /**
     * when the 2048 button is pushed, launches a new window
     * that runs the game.
     * @return handler the action event to launch the game.
     */
    public EventHandler<ActionEvent> launch2048()
        {
            tfe = new Stage();
            EventHandler<ActionEvent> handler =  e ->
            {
                Thread t = new Thread(() -> {
                        Platform.runLater(() -> new Twenty48().start(tfe));
                        sIStart.setDisable(true);
                        twenty48Start.setDisable(true);
                });
                t.setDaemon(true);
                t.start();
            };
            return handler;
        }
                
                            

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            startMenu();
            spi.setOnCloseRequest((WindowEvent event) -> {
                    sIStart.setDisable(false);
                    twenty48Start.setDisable(false);
                });
            tfe.setOnCloseRequest((WindowEvent event) -> {
                    sIStart.setDisable(false);
                    twenty48Start.setDisable(false);
                });
            Scene scene = new Scene(menuScreen, 640, 480);
            stage.setTitle("cs1302-arcade!");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();

        } // start

} // ArcadeApp
