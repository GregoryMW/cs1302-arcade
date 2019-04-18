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

public class SpaceInvaders extends ArcadeApp
{
    Group screen = new Group();
    Scene spaceInvaders = new Scene(screen, 640, 480, Color.BLACK);
    
    

    public Scene getScene()
        {
            return spaceInvaders;
        }
}
