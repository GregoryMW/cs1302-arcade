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

public class Twenty48 extends Application
{

    Group screen;
    Scene scene2048;
    TilePane tile;

    public void setup()
        {
            screen = new Group();
            scene2048 = new Scene(screen, 480, 640);
            tile = new TilePane();
            createInitialTiles();
            screen.getChildren().add(tile);
        }

    public void createInitialTiles()
        {
            tile.setHgap(66);
            tile.setPrefColumns(4);
            tile.getChildren().add(new ImageView(new Image("2.png")));
        }
    
    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            setup();
            screen.requestFocus();
            stage.setTitle("2048");
            stage.setScene(scene2048);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        }
}
