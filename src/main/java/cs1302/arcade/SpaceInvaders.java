package cs1302.arcade;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.*;
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
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import java.lang.*;

/**
 * A game of Space Invaders that creates 40 aliens per level, and increases the difficulty
 * each level by increasing the speed at which the aliens move.
 */
public class SpaceInvaders extends Application
{
    // Creation of the scene elements
    Group screen;
    Scene spaceInvaders;
    Pane background;
    // Creation of the list of invaders as well as the ship, bullet, and crash objects
    ArrayList<ImageView> invaders;
    ImageView ship;
    ImageView bullet;
    ImageView crash;
    // These values will be initialized for movement control of the aliens
    Boolean moveRight;
    Boolean movedDown;
    double currentSpeed;
    // Keyframe and timeline instances to be used multiple times
    KeyFrame keyFrame;
    Timeline timeline;
    // Creation of variables used in the scoring of the game
    int s;
    Text score;
    int l;
    Text level;

    /**
     * Initial setup for the space invader game.
     * Creates the score board, level counter, ship,  and the scene. calls the addInvaders method
     * as well as a list of imageviews in order to track the amount of aliens on screen.
     */
    private void setup()
        {
            s = 0;
            score = new Text("Score: " + s);
            l = 1;
            level = new Text("Level: " + l);
            screen = new Group();
            spaceInvaders = new Scene(screen, 640, 480, Color.BLACK);
            background = new Pane();
            Image shipPic = new Image("Ship.png", 65, 40, true, true);
            ship = new ImageView(shipPic);
            ship.setX(300);
            ship.setY(420);
            background.getChildren().add(ship);
            invaders = new ArrayList<ImageView>();
            addInvaders();
            screen.getChildren().add(background);
            background.getChildren().add(score);
            background.getChildren().add(level);
            level.setX(550);
            level.setY(20);
            level.setFill(Color.WHITE);
            score.setX(10);
            score.setY(20);
            score.setFill(Color.WHITE);
        }

    /**
     *  Adds the 40 aliens to the scene, 10 in each row.
     */
    private void addInvaders()
        {
            Image alien1 = new Image("Alien.png", 40, 30, false, true);
            int count = 0;
            for(int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 4; j ++)
                {
                    invaders.add(new ImageView(alien1));
                    invaders.get(count).setX(25 + i * 50);
                    invaders.get(count).setY(20 + j * 30);
                    background.getChildren().add(invaders.get(count));
                    count ++;
                }
            }
        }

    /**
     * Uses a key input to determine which direction, left or right, to move the
     * ship in. Also has bounds checks as well.
     */
    private void moveShip(KeyCode key) {
        if (key == KeyCode.LEFT && ship.getX() > 10 && background.getChildren().contains(ship)){
            ship.setX(ship.getX() - 10.0);}
        if (key == KeyCode.RIGHT && ship.getX() + 50 < 630 &&
            background.getChildren().contains(ship)){
            ship.setX(ship.getX() + 10.0);}
    }

    /**
     * Begins by moving the aliens to the right. if an alien reaches the edge of the
     * Scene window, the aliens will move down, and begin to move in the opposite
     * direction it was going in before. The speed of the aliens will increase after
     * clearing the screen and moving on to the next level.
     */
    private void moveAliens(double speed)
        {
            Thread t = new Thread (() -> {
                    setMovement(true);
                    EventHandler<ActionEvent> moveAliens = e -> {
                        setDown(false);
                        movementCheck();
                        if (getMovement() && getDown() == false)
                        {
                            moveRight();
                        }
                        else if (getMovement() == false && getDown() == false)
                        {
                            moveLeft();
                        }
                        if (invaders.size() == 0)
                        {
                            timeline.stop();
                            endLevel(speed + speed/2);
                        }
                    };
                    keyFrame = new KeyFrame(Duration.seconds(speed), moveAliens);
                    timeline = new Timeline();
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.play();
            });
            t.setDaemon(true);
            t.start();
        }

    /**
     * Helper method for moveAliens() method */
    public void movementCheck()
        {
            invaders.stream().forEach(a -> {
                    if (a.getX() >= 580 && getMovement())
                    {
                        moveDown();
                        setDown(true);
                        setMovement(false);
                    }
                    if (a.getX() <= 40 && getMovement() == false)
                    {
                        moveDown();
                        setDown(true);
                        setMovement(true);
                    }
                    if(ship.getBoundsInLocal().intersects(a.getBoundsInLocal()))
                    {
                        timeline.stop();
                        crash = new ImageView(new Image
                                              ("Crash.png", 65, 40, true, true));
                        crash.setX(ship.getX());
                        crash.setY(ship.getY());
                        background.getChildren().add(crash);
                        background.getChildren().remove(ship);
                        Text over = new Text("Game Over");
                        over.setX(200);
                        over.setY(220);
                        over.setFill(Color.WHITE);
                        over.setFont(new Font(50));
                        background.getChildren().add(over);
                    }
                });
        }

    /** Helper method for moveAliens method. */
    public void endLevel(double speed)
        {
            l ++;
            level.setText("Level: " + l);
            addInvaders();
            moveAliens(speed);
        }

    /** Sets the movement direction of the aliens.*/
    public void setMovement(boolean move)
        {
            moveRight = move;
        }

    /** Returns the boolean value of the current direction the aliens are moving in.*/
    public boolean getMovement()
        {
            return moveRight;
        }

    /**
     * Used to indicate that the aliens have moved down in the current
     * keyframe. This allows the aliens to only move down, then continue their
     * left or right movement.
     */
    public void setDown(boolean move)
        {
            movedDown = move;
        }

    /** Determines if the aliens have moved down or not. */
    public boolean getDown()
        {
            return movedDown;
        }

    /** Moves every alien 10 pixels to the right. */
    public void moveRight()
        {
            invaders.stream().forEach(a -> {
                    Platform.runLater(() -> a.setX(a.getX() + 10));
                });
        }

    /** Moves every alien 10 pixels to the left. */
    public void moveLeft()
        {
            invaders.stream().forEach(a -> {
                    Platform.runLater(() -> a.setX(a.getX() - 10));
                });
        }

    /** Moves every alien 10 pixels down. */
    public void moveDown()
        {
            invaders.stream().forEach(a -> {
                    Platform.runLater(() -> a.setY(a.getY() + 10));
                });
        }

    /**
     *  Initializes the bullet object
     *  and sets the bullet to come out of the top of the ship
     */  
    private void makeBullet()
        {
            Image bulletPic = new Image("Bullet.png", 10, 25, true, false);
            bullet = new ImageView(bulletPic);
            background.getChildren().add(bullet);
            bullet.setX(ship.getX() + 32);
            bullet.setY(ship.getY() - 30);
            fireBullet();
        }

    /**
     * Begins the movement of the newly created bullet object.
     * first will check if the bullet has reached the top of the screen.
     * If it has, it will be removed from the scene. Then checks to see if
     * it has hit an alien, and will remove the bullet as well as the alien if it has.
     */
    public void fireBullet()
        {
            Thread t = new Thread (() -> {
                    EventHandler<ActionEvent> moveBullet = e -> {
                        Platform.runLater(() -> bullet.setY(bullet.getY() - 5));
                        if (bullet.getY() < 0)
                        {
                            Platform.runLater(() -> background.getChildren().remove(bullet));
                            timeline.stop();
                        }
                        for (ImageView alien : invaders)
                        {
                            if(bullet.getBoundsInLocal().intersects(alien.getBoundsInLocal()))
                            {
                                s += 10;
                                score.setText("Score: " + s);
                                int index = invaders.indexOf(alien);
                                invaders.remove(index);
                                Platform.runLater(() -> {
                                        background.getChildren().remove(bullet);
                                        background.getChildren().remove(alien);
                                    });
                                timeline.stop();
                                break;
                            }
                        }
                    };
                    keyFrame = new KeyFrame(Duration.seconds(0.01), moveBullet);
                    timeline = new Timeline();
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.play();
            });
            t.setDaemon(true);
            t.start();
        }

    /**
     * This checks which control input has been pressed in order to call the
     * appropriate movement method, the firing of the bullet, or the
     * movement of the ship.
     */
    private EventHandler<? super KeyEvent> inputCheck()
        {
            return event -> {
                {
                    if (event.getCode() == KeyCode.SPACE)
                    {
                        if (background.getChildren().contains(bullet) == false)
                        {
                            makeBullet();
                        }
                    }
                    else if (event.getCode() == KeyCode.LEFT ||
                             event.getCode() == KeyCode.RIGHT);
                    {
                        moveShip(event.getCode());
                    }
                }
            };
        }
    
    /** {@inheritdoc} */
    @Override
    public void start(Stage stage)
        {
            setup();
            moveAliens(0.1);
            screen.requestFocus();
            screen.setOnKeyPressed(inputCheck());
            stage.setTitle("Space Invaders");
            stage.setScene(spaceInvaders);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        }
}
