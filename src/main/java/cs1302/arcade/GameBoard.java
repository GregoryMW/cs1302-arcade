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
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.util.*;

/**
 * This class represents a 16 x 16 grid consisting of tiles that make up a game
 * of 2048. The board contains 16 imageview objects, and swaps are images of the
 * tiles as needed.
 */
public class GameBoard
{
    // Creation of the gui of the game
    Group screen;
    Scene scene2048;
    Pane pane;
    int score;
    Text scoreText;
    ArrayList<ImageView> tiles;
    ArrayList<ImageView> hasMult;
    boolean moved;
    boolean canMoveUp;
    boolean canMoveDown;
    boolean canMoveLeft;
    boolean canMoveRight;

    // Creation of the tile images that can be swapped around as needed
    Image tile2 = new Image("2.png");
    Image tile4 = new Image("4.png");
    Image tile8 = new Image("8.png");
    Image tile16 = new Image("16.png");
    Image tile32 = new Image("32.png");
    Image tile64 = new Image("64.png");
    Image tile128 = new Image("128.png");
    Image tile256 = new Image("256.png");
    Image tile512 = new Image("512.png");
    Image tile1024 = new Image("1024.png");
    Image tile2048 = new Image("2048.png");

    /** Constructor for the game board. */
    public GameBoard()
        {
            screen = new Group();
            scene2048 = new Scene(screen, 400, 420);
            pane = new Pane();
            screen.getChildren().add(pane);
            ImageView background = new ImageView(new Image("2048BG.png"));
            pane.getChildren().add(background);
            canMoveUp = true;
            canMoveDown = true;
            canMoveLeft = true;
            canMoveRight = true;
            score = 0;
            scoreText = new Text("Score: " + score);
            scoreText.setX(10);
            scoreText.setY(415);
            pane.getChildren().add(scoreText);
            setup();
        }

    /**
     * Creates the 16 imageviews needed for the board, and places
     * two tiles to the board in random locations.
     */
    public void setup()
        {
            tiles = new ArrayList<ImageView>();
            int count = 0;
            for(int i = 0; i < 400; i += 100)
            {
                for (int j = 0; j < 400; j += 100)
                {
                    // Adds the imageview and places it at the cooresponding grid position
                    tiles.add(new ImageView());
                    tiles.get(count).setX(j);
                    tiles.get(count).setY(i);
                    pane.getChildren().add(tiles.get(count));
                    count ++;
                }
            }
            placeRandom(2);
        }

    /**
     * This method takes in the number of random tiles that need to be added
     * to the board, and places them at distinct positions.
     * @param amount the amount of random tiles that will be added to the board
     */
    public void placeRandom(int amount)
        {
            Random rand = new Random();
            for (int i = 1; i <= amount; i ++)
            {
                int r = rand.nextInt(16);
                if (tiles.get(r).getImage() != null)
                {
                    // If there already exists a tile at the random position,
                    // Does nothing, then tries again with a new random posiiton
                    i --;
                }
                else
                {
                    tiles.get(r).setImage(tile2);
                }
            }
        }

    /**
     * Moves any eligible tile to the rightmost part of the board. This method
     * does take into account similar tiles and mulitplies them accordingly,
     * Then adds the newly mulitplied tile to an array to keep track if it has already been
     * Used for multiplication, this makes sure a tile is not multiplied twice. Also checks to
     * see if the player can still move, and will end the game if the player can no longer move.
     */
    public void moveRight()
        {
            moved = false;
            for (int i = 15; i >= 0; i --)
            {
                // If a tile is already at the 4th column, does nothing
                if (i % 4 != 3 && tiles.get(i).getImage() != null)
                {
                    // Will move the tile over if there no tile to its right
                    if (tiles.get(i + 1).getImage() == null)
                    {
                        tiles.get(i + 1).setImage(tiles.get(i).getImage());
                        tiles.get(i).setImage(null);
                        moveRight();
                        moved = true;
                    }
                    // Will mulitply the two tiles together, swapping out tile images as needed
                    else if (tiles.get(i + 1).getImage() == tiles.get(i).getImage())
                    {
                        // Checks to see if the tiles have already been used in multiplication
                        // in this instance
                        if (!hasMult.contains(tiles.get(i))
                            &&!hasMult.contains(tiles.get(i + 1)))
                        {
                            multiply(i + 1);
                            hasMult.add(tiles.get(i + 1));
                            tiles.get(i).setImage(null);
                            moved = true;
                            scoreText.setText("Score: " + score);
                        }
                    }
                }
            }
        }

    /**
     * Moves the tiles to the left with the same restrictions and abilities as the
     * moveRight method above.
     */
    public void moveLeft()
        {
            
            // Similar implementation as moveRight method
            moved = false;
            for (int i = 0; i < 16; i ++)
            {
                if (i % 4 != 0 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i - 1).getImage() == null)
                    {
                        tiles.get(i - 1).setImage(tiles.get(i).getImage());
                        tiles.get(i).setImage(null);
                        moveLeft();
                        moved = true;
                    }
                    else if (tiles.get(i - 1).getImage() == tiles.get(i).getImage())
                    {
                        if (!hasMult.contains(tiles.get(i))
                            && !hasMult.contains(tiles.get(i - 1)))
                        {
                            multiply(i - 1);
                            hasMult.add(tiles.get(i - 1));
                            tiles.get(i).setImage(null);
                            moved = true;
                            scoreText.setText("Score: " + score);
                        }
                    }

                }
            }
        }

    /**
     * Moves the tiles upward with the same restrictions and abilities as the
     * moveRight method above.
     */ 
    public void moveUp()
        {
            // Similar implementation as moveRight method
            moved = false;
            for (int i = 0; i < 16; i ++)
            {
                if (i > 3 && tiles.get(i).getImage() != null)
                {
                    if(tiles.get(i - 4).getImage() == null)
                    {
                        tiles.get(i - 4).setImage(tiles.get(i).getImage());
                        tiles.get(i).setImage(null);
                        moveUp();
                        moved = true;
                    }
                    else if (tiles.get(i - 4).getImage() == tiles.get(i).getImage())
                    {
                        if (!hasMult.contains(tiles.get(i))
                            && !hasMult.contains(tiles.get(i - 4)))
                        {
                            multiply(i - 4);
                            hasMult.add(tiles.get(i - 4));
                            tiles.get(i).setImage(null);
                            moved = true;
                            scoreText.setText("Score: " + score);
                        }
                    }

                }
            }
        }

    /**
     * Moves the tiles downward with the same restrictions and abilities as the
     * moveRight method above.
     */ 
    public void moveDown()
        {
            // Similar implementation as moveRight method.
            moved = false;
            for (int i = 15; i >= 0; i --)
            {
                if (i < 12 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i + 4).getImage() == null)
                    {
                        tiles.get(i + 4).setImage(tiles.get(i).getImage());
                        tiles.get(i).setImage(null);
                        moveDown();
                        moved = true;
                    }
                    else if (tiles.get(i + 4).getImage() == tiles.get(i).getImage())
                    {
                        if (!hasMult.contains(tiles.get(i))
                            && !hasMult.contains(tiles.get(i + 4)))
                        {
                            multiply(i + 4);
                            hasMult.add(tiles.get(i + 4));
                            tiles.get(i).setImage(null);
                            moved = true;
                            scoreText.setText("Score: " + score);
                        }
                    }
                }
            }
        }

    /** 
     * A method that will check if there is at least one tile that can be
     * moved to the right.
     * @return true if at least one tile can move right, false otherwise
     */
    public boolean canMoveRight()
        {
            for (int i = 15; i >= 0; i --)
            {
                if (i % 4 != 3 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i + 1).getImage() == null)
                    {
                        canMoveRight = true;
                    }
                    else if (tiles.get(i + 1).getImage() == tiles.get(i).getImage())
                    {
                        canMoveRight = true;
                    }
                }
            }
            return false;
        }

    /** 
     * A method that will check if there is at least one tile that can be
     * moved to the left.
     * @return true if at least one tile can move left, false otherwise
     */
    public boolean canMoveLeft()
        {
            for (int i = 0; i < 16; i ++)
            {
                if (i % 4 != 0 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i - 1).getImage() == null)
                    {
                        canMoveLeft = true;
                    }
                    else if (tiles.get(i - 1).getImage() == tiles.get(i).getImage())
                    {
                        canMoveLeft = true;
                    }
                }
            }
            return false;
        }

    /** 
     * A method that will check if there is at least one tile that can be
     * moved up.
     * @return true if at least one tile can move up, false otherwise
     */
    public boolean canMoveUp()
        {
            for (int i = 0; i < 16; i ++)
            {
                if (i > 3 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i - 4).getImage() == null)
                    {
                        return true;
                    }
                    else if (tiles.get(i - 4).getImage() == tiles.get(i).getImage())
                    {
                        return true;
                    }
                }
            }
            return false;
        }

    /** 
     * A method that will check if there is at least one tile that can be
     * moved down.
     * @return true if at least one tile can move down, false otherwise
     */
    public boolean canMoveDown()
        {
            for (int i = 15; i >= 0; i --)
            {
                if (i < 12 && tiles.get(i).getImage() != null)
                {
                    if (tiles.get(i + 4).getImage() == null)
                    {
                        canMoveDown = true;
                    }
                    else if (tiles.get(i + 4).getImage() == tiles.get(i).getImage())
                    {
                        canMoveDown = true;
                    }
                }
            }
            return false;
        }

    /**
     * This method checks which tile needs to be mulitplied, looks at its current
     * tile, and mulitplies it by two accordingly.
     * @param pos the current position of the node to be multiplied
     */
    public void multiply(int pos)
        {
            if (tiles.get(pos).getImage() == tile2){
                tiles.get(pos).setImage(tile4);
                score += 4;}
            else if (tiles.get(pos).getImage() == tile4){
                tiles.get(pos).setImage(tile8);
                score += 8;}
            else if (tiles.get(pos).getImage() == tile8){
                tiles.get(pos).setImage(tile16);
                score += 16;}
            else if (tiles.get(pos).getImage() == tile16){
                tiles.get(pos).setImage(tile32);
                score += 32;}
            else if (tiles.get(pos).getImage() == tile32){
                tiles.get(pos).setImage(tile64);
                score += 64;}
            else if (tiles.get(pos).getImage() == tile64){
                tiles.get(pos).setImage(tile128);
                score += 128;}
            else if (tiles.get(pos).getImage() == tile128){
                tiles.get(pos).setImage(tile256);
                score += 256;}
            else if (tiles.get(pos).getImage() == tile256){
                tiles.get(pos).setImage(tile512);
                score += 512;}
            else if (tiles.get(pos).getImage() == tile512){
                tiles.get(pos).setImage(tile1024);
                score += 1024;}
            else if (tiles.get(pos).getImage() == tile1024){
                tiles.get(pos).setImage(tile2048);
                score += 2048;}
        }

    /**
     * Returns the scene of the board.
     * @return scene2048 the scene of the board
     */
    public Scene getScene()
        {
            return scene2048;
        }

    /**
     * Returns the group of the board.
     * @return screen the group of the board
     */
    public Group getGroup()
        {
            return screen;
        }

    /**
     * Returns the boolean argument moved to see if the tiles had moved at all.
     * @return moved whether the tiles has moved or not
     */
    public boolean getMoved()
        {
            return moved;
        }

    /**
     * Creates a new array that will be used to contain any tiles that have
     * already been used in multiplication in one instance of a move.
     */
    public void newHasMult()
        {
            hasMult = new ArrayList<ImageView>();
        }

    /**
     * Checks in every direction if the player can still make a move.
     * If not, the game ends.
     */
    public void checkMove()
        {
            boolean end = true;
            for (int i = 0; i < 16; i ++)
            {
                if (tiles.get(i).getImage() == null)
                {
                    end = false;
                }
            }
            if (end && canMoveUp() == false && canMoveDown() == false
                && canMoveLeft() == false && canMoveRight() == false)
            {
                Text over = new Text("Game Over");
                over.setX(50);
                over.setY(200);
                over.setFont(new Font(50));
                pane.getChildren().add(over);
            }
        }

    /** Checks to see if the 2048 tile is on the board, if so, ends the game.*/
    public void checkIfWon()
        {
            for (int i = 0; i < 16; i ++)
            {
                if (tiles.get(i).getImage() == tile2048)
                {
                    for (int i = 0; i < 16; i ++)
                    {
                        if (tiles.get(i).getImage() == null)
                        {
                            tiles.get(i).setImage(new Image("WinTile.png"));
                        }
                    }
                    Text win = new Text("You Win!");
                    win.setX(75);
                    win.setY(200);
                    win.setFont(new Font(35));
                    pane.getChildren().add(win);
                }
            }
        }
}
