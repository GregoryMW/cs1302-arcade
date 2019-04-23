package cs1302.arcade;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.geometry.*;
import java.util.ArrayList; 

public class SpaceInvaders extends Application
{
    Group screen;
    Scene spaceInvaders;
    Pane background;
    ArrayList<ImageView> invaders;
    
    public void setup()
        {
            screen = new Group();
            spaceInvaders = new Scene(screen, 640, 480, Color.BLACK);
            background = new Pane();
            invaders = new ArrayList<ImageView>();
            addInvaders();
            screen.getChildren().add(background);
        }

    public void addInvaders()
        {
            Image alien1 = new Image("Alien1.png", 30, 30, false, true);
            int count = 0;
            for(int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 4; j ++)
                {
                    invaders.add(new ImageView(alien1));
                    invaders.get(count).relocate(25 + i * 50, 20 + j * 30);
                    background.getChildren().add(invaders.get(count));
                    count ++;
                }
            }
        }

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            setup();
            stage.setTitle("Space Invaders");
            stage.setScene(spaceInvaders);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();

        } // start
}
