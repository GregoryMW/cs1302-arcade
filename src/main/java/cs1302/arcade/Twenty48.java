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
import javafx.geometry.*;

public class Twenty48 extends ArcadeApp
{

    Scene scene2048;
    Gridpane grid;


    public void setup()
        {
            grid = new GridPane();
        }
    
    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            screen.requestFocus();
            stage.setTitle("2048");
            stage.setScene(scene2048);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        }
}
