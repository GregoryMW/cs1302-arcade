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

public class ArcadeApp extends Application
{
    HBox menuScreen = new HBox(20);
    VBox spaceInvaderOpt = new VBox();
    VBox twenty48Opt = new VBox(40);
    SpaceInvaders spaceInvaders = new SpaceInvaders();
    Stage app;
    Stage spi;
    Stage tfe;
    Button sIStart;
    Button twenty48Start;

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
            Image backgroundPic = new Image("retroBG.jpg", 640, 480, false, true);
            menuScreen.setBackground(
                new Background(new BackgroundImage(
                                   backgroundPic,BackgroundRepeat.NO_REPEAT,
                                   BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                   BackgroundSize.DEFAULT)));
            menuScreen.setAlignment(Pos.CENTER);
            spaceInvaderOpt.getChildren().add(sIStart);
            spaceInvaderOpt.setAlignment(Pos.CENTER);
            twenty48Opt.getChildren().add(twenty48Start);
            twenty48Opt.setAlignment(Pos.CENTER);
            menuScreen.getChildren().addAll(spaceInvaderOpt, twenty48Opt);
        }

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

    Group group = new Group();           // main container
    Random rng = new Random();           // random number generator
    Rectangle r = new Rectangle(20, 20); // some rectangle

    /**
     * Return a mouse event handler that moves to the rectangle to a random
     * position any time a mouse event is generated by the associated node.
     * @return the mouse event handler
     */
    private EventHandler<? super MouseEvent> createMouseHandler() {
	return event -> {
	    System.out.println(event);
	    r.setX(rng.nextDouble() * (640 - r.getWidth()));
	    r.setY(rng.nextDouble() * (480 - r.getHeight()));
	};
    } // createMouseHandler

    /**
     * Return a key event handler that moves to the rectangle to the left
     * or the right depending on what key event is generated by the associated
     * node.
     * @return the key event handler
     */
    private EventHandler<? super KeyEvent> createKeyHandler() {
	return event -> {
	    System.out.println(event);
	    if (event.getCode() == KeyCode.LEFT)  r.setX(r.getX() - 10.0);
	    if (event.getCode() == KeyCode.RIGHT) r.setX(r.getX() + 10.0);
	    // TODO bounds checking
	};
    } // createKeyHandler

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            app = stage;
            startMenu();

            spi.setOnCloseRequest((WindowEvent event1) -> {
                    sIStart.setDisable(false);
                    twenty48Start.setDisable(false);
                });
            Scene scene = new Scene(menuScreen, 640, 480);
            app.setTitle("cs1302-arcade!");
            app.setScene(scene);
            app.sizeToScene();
            app.setResizable(false);
            app.show();

        } // start

} // ArcadeApp
