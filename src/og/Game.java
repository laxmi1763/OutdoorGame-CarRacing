/* Author Name: Laxmi Chari
Roll No: 22
Title: Program to implement Outdoor game - CAR RACING using JAVA
Start Date: 28/09/2024
Modified Date: 30/09/2024
Description: This code implements a simple car racing game using JFrame and KeyListener. The player's car moves left or right, avoiding other cars that approach from the top. 
The game keeps track of the score and speed, and ends when the player's car collides with an obstacle.
*/
package og;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements KeyListener, ActionListener {

    // Player's car position
    private int xpos = 300;
    private int ypos = 700;
    
    // Images for player and opponent cars
    private ImageIcon car;
    private Timer timer;
    
    // Random number generator for opponent car positions
    Random random = new Random();
    
    // Opponent car variables
    private int roadmove = 0;
    private int carxpos[] = {100, 200, 300, 400, 500};  // X positions for cars
    private int carypos[] = {-240, -480, -720, -960, -1200};  // Y positions
    private int cxpos1 = 0, cxpos2 = 2, cxpos3 = 4;  // Opponent car X positions
    private int cypos1 = random.nextInt(5), cypos2 = random.nextInt(5), cypos3 = random.nextInt(5);  // Random Y positions for opponent cars
    int y1pos = carypos[cypos1], y2pos = carypos[cypos2], y3pos = carypos[cypos3];  // Initialize opponent Y positions
    private ImageIcon car1, car2, car3;  // Opponent cars

    // Score and speed variables
    private int score = 0, delay = 100, speed = 90;
    
    // Game status flags
    private boolean gameover = false, paint = false;
    
    // Constructor initializes the game window
    public Game(String title) {
        super(title);
        setBounds(300, 10, 700, 700);  // Set window size and position
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        setResizable(false);
    }

    // Main paint method to render the game objects
    public void paint(Graphics g) {
        // Draw green background (grass)
        g.setColor(Color.green);
        g.fillRect(0, 0, 700, 700);
        
        // Draw red borders (side barriers)
        g.setColor(Color.red);
        g.fillRect(90, 0, 10, 700);  // Left barrier
        g.fillRect(600, 0, 10, 700); // Right barrier
        
        // Draw black road
        g.setColor(Color.black);
        g.fillRect(100, 0, 500, 700);
        
        // Draw road lane dividers (white dashed lines)
        if (roadmove == 0) {
            for (int i = 0; i <= 700; i += 100) {
                g.setColor(Color.white);
                g.fillRect(350, i, 10, 70);
            }
            roadmove = 1;
        } else {
            for (int i = 50; i <= 700; i += 100) {
                g.setColor(Color.white);
                g.fillRect(350, i, 10, 70);
            }
            roadmove = 0;
        }

        // Draw player's car
        car = new ImageIcon("C:\\Users\\chari\\eclipse-workspace\\og\\src\\images\\white.png");
        car.paintIcon(this, g, xpos, ypos);
        ypos -= 40;  // Update position
        if (ypos < 500) {
            ypos = 500;  // Keep player's car from moving off the screen
        }

        // Draw opponent cars
        car1 = new ImageIcon("C:\\Users\\chari\\eclipse-workspace\\og\\src\\images\\car1.png");
        car2 = new ImageIcon("C:\\Users\\chari\\eclipse-workspace\\og\\src\\images\\car2.png");
        car3 = new ImageIcon("C:\\Users\\chari\\eclipse-workspace\\og\\src\\images\\car4.png");
        
        car1.paintIcon(this, g, carxpos[cxpos1], y1pos);
        car2.paintIcon(this, g, carxpos[cxpos2], y2pos);
        car3.paintIcon(this, g, carxpos[cxpos3], y3pos);
        
        // Update Y positions of opponent cars
        y1pos += 50;
        y2pos += 50;
        y3pos += 50;

        // Reset opponent car positions if they go off the screen
        if (y1pos > 700) {
            cxpos1 = random.nextInt(5);
            y1pos = carypos[cypos1];
        }
        if (y2pos > 700) {
            cxpos2 = random.nextInt(5);
            y2pos = carypos[cypos2];
        }
        if (y3pos > 700) {
            cxpos3 = random.nextInt(5);
            y3pos = carypos[cypos3];
        }

        // Check for collisions
        if ((y1pos < ypos && y1pos + 175 > ypos && carxpos[cxpos1] == xpos) ||
            (y2pos < ypos && y2pos + 175 > ypos && carxpos[cxpos2] == xpos) ||
            (y3pos < ypos && y3pos + 175 > ypos && carxpos[cxpos3] == xpos)) {
            gameover = true;  // End game if collision occurs
        }

        // Draw score and speed display
        g.setColor(Color.blue);
        g.fillRect(120, 35, 210, 50);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(125, 40, 200, 40);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score : " + score, 140, 67);
        g.drawString(speed + " Km/h", 400, 67);
        score++;  // Increment score
        speed++;  // Increment speed

        // Update delay and speed based on score
        if (speed > 140) {
            speed = 240 - delay;
        }
        if (score % 50 == 0) {
            delay -= 10;
            if (delay < 60) {
                delay = 60;
            }
        }

        // Delay between updates
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check for game over and display message
        if (gameover) {
            g.setColor(Color.blue);
            g.fillRect(120, 210, 460, 200);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(130, 220, 440, 180);
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.setColor(Color.yellow);
            g.drawString("-_- Game Over -_-", 165, 270);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 190, 340);
            if (!paint) {
                repaint();
                paint = true;
            }
        } else {
            repaint();
        }
    }

    // Main method to start the game
    public static void main(String args[]) {
        Game c = new Game("Car Game");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle car movement using left/right arrow keys
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !gameover) {
            xpos -= 100;
            if (xpos < 100) {
                xpos = 100;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !gameover) {
            xpos += 100;
            if (xpos > 500) {
                xpos = 500;
            }
        }
        // Restart game if 'Enter' is pressed after game over
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameover) {
            gameover = false;
            paint = false;
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // Auto-generated method (no actions defined)
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Auto-generated method (additional key controls for 'a' and 's')
        if (e.getKeyChar() == 'a' && !gameover) {
            xpos -= 100;
        }
        if (e.getKeyChar() == 's' && !gameover) {
            xpos += 100;
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // Auto-generated method (no actions defined)
    }

    // Reset game variables after game over
    private void resetGame() {
        cxpos1 = 0;
        cxpos2 = 2;
        cxpos3 = 4;
        cypos1 = random.nextInt(5);
        cypos2 = random.nextInt(5);
        cypos3 = random.nextInt(5);
        y1pos = carypos[cypos1];
        y2pos = carypos[cypos2];
        y3pos = carypos[cypos3];
        speed = 90;
        score = 0;
        delay = 100;
        xpos = 300;
        ypos = 700;
    }
}
