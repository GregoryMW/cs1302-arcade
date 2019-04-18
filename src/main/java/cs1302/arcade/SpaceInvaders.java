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

public class SpaceInvaders extends ArcadeApp
{
    Group screen;
    Scene spaceInvaders;
    Pane background;
    ArrayList<ImageView> invaders;
    
    public SpaceInvaders(){
        screen = new Group();
        spaceInvaders = new Scene(screen, 640, 480, Color.BLACK);
        background = new Pane();
        invaders = new ArrayList<>();

        screen.getChildren().add(background);
        
    }

    public Scene getScene()
        {
            return spaceInvaders;
        }

    public void addInvaders(){
        for(int i = 0; i < 20; i++){
            invaders.add(new ImageView("Alien1.png"));
            background.getChildren().add(invaders.get(i)); 
        }
    }
}
