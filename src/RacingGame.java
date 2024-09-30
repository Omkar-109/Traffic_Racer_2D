import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
/*
 * This is a 2d traffic racer game designed using java swing
 * @author Omkar Mhamal
 */


/*RacingGame class */
public class RacingGame extends JPanel implements ActionListener {
    private Timer timer;
    private Car playerCar;
    private ArrayList<Car> enemyCars;
    private int score;
    private int speed;  // Speed of enemy cars
    private int lineMove;
    private int[] lanes = {100, 200};
    private Image playerCarImage;
    private Image enemyCarImage;
    private int boardWidth=400;
    private int boardHeight=600;

    /*
     * Constructor
     */
    public RacingGame() {
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.GRAY);

        // Load images for the player and enemy cars
        playerCarImage = new ImageIcon("images\\player_car.png").getImage();
        enemyCarImage = new ImageIcon("images\\enemy_car.png").getImage();

        // Initialize the player car with the image
        playerCar = new Car(100, 450, playerCarImage);

        // Timer for the game loop
        timer = new Timer(10, this);
        timer.start();

        // Key listener for player movement
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && playerCar.x > lanes[0]) {
                    playerCar.moveLeft();
                }
                if (key == KeyEvent.VK_RIGHT && playerCar.x < lanes[1]) {
                    playerCar.moveRight();
                }
            }
        });

        // Initialize the list of enemy cars and add the first enemy car
        enemyCars = new ArrayList<>();
        addEnemyCar();
        score = 0;
        speed = 5;  // Initial speed of enemy cars
        lineMove = 5;
    }

    // Adding a single enemy car
    private void addEnemyCar() {
        int lane = lanes[(int) (Math.random() * 2)];
        enemyCars.add(new Car(lane, -100, enemyCarImage));
    }

    // Move enemy cars and check for collisions
    public void actionPerformed(ActionEvent e) {
        int k=2;
        if(score>=20){
            k=3;
        }
        if(score>=30){
            k=5;
        }
        lineMove=lineMove+k;
        if(lineMove>=40){
            lineMove=0;
        }
        for (int i = 0; i < enemyCars.size(); i++) {
            Car enemyCar = enemyCars.get(i);
            
            // Move down based on current speed
            enemyCar.moveDown(speed);
           

            // Check if the enemy car has reached y = 400, add a new enemy car
            if (enemyCar.y == 400 && enemyCars.size() == 1) {
                addEnemyCar();  // Add another enemy car
            }

            // Remove the car if it goes off-screen and increase score
            if (enemyCar.y > boardHeight) {
                enemyCars.remove(enemyCar);
                score++;
                break;  // Avoid ConcurrentModificationException
            }

            // Check for collision
            if (playerCar.collidesWith(enemyCar)) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score);
                System.exit(0);
            }
        }
        repaint();
    }

    // Draw the player and enemy cars
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw green background for grass on the sides
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 100, getHeight()); // Left grass area
        g.fillRect(300, 0, 100, getHeight()); // Right grass area

        // Draw gray road in the middle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(100, 0, 200, getHeight()); // Road width of 200 between two lanes

        // Draw road lines
        g.setColor(Color.WHITE);
        
    
        for (int i = 0; i < 15; i++) {
      
            g.fillRect((boardWidth/2)-15,i*40+lineMove , 10, 20);
        
        }
        
        // Draw player car
        playerCar.draw(g);

        // Draw enemy cars
        for (Car enemyCar : enemyCars) {
            enemyCar.draw(g);
        }

        // Display score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);

        // keys help 
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 15  ));
        g.drawString("use < > keys", 10, 550);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Traffic Racing Game");
        RacingGame game = new RacingGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
