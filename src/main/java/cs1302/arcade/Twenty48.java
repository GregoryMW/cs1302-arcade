package cs1302.arcade;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/** A game of 2048 that uses the arrow keys to move. */
public class Twenty48 extends Application
{
    // Utilizes the GameBoard class to setup a new game
    GameBoard board = new GameBoard();

    /**
     * This checks which control input has been pressed in order to call the
     * appropriate movement method, either up, down, left, or right
     * @return event the EventHandler that gives the key pressed
     */
    public EventHandler<? super KeyEvent> inputCheck()
        {
            return event -> {
                {
                    if (event.getCode() == KeyCode.RIGHT)
                    {
                        // Creates a new array in order to track which tiles have already
                        // been multiplied, this makes sure no tiles are multiplied twice
                        // in a single move
                        board.newHasMult();
                        board.moveRight();
                        if (board.getMoved())
                        {
                            // If any of the tiles moved, then add a new tile to the board
                            board.placeRandom(1);
                        }
                    }
                    else if (event.getCode() == KeyCode.LEFT)
                    {
                        board.newHasMult();
                        board.moveLeft();
                        if (board.getMoved())
                        {
                            board.placeRandom(1);
                        }
                    }
                    else if (event.getCode() == KeyCode.UP)
                    {
                        board.newHasMult();
                        board.moveUp();
                        if (board.getMoved())
                        {
                            board.placeRandom(1);
                        }
                    }
                    else if (event.getCode() == KeyCode.DOWN)
                    {
                        board.newHasMult();
                        board.moveDown();
                        if (board.getMoved())
                        {
                            board.placeRandom(1);
                        }
                    }
                }
            };
        }
    
    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            board.getGroup().requestFocus();
            board.getGroup().setOnKeyPressed(inputCheck());
            stage.setTitle("2048");
            stage.setScene(board.getScene());
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        }
}
