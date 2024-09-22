import java.awt.*;
/*
 * This is a 2d traffic racer game designed using java swing
 * @author Omkar Mhamal
 */

 /*
  * Car class which helps to draw car
  */
public class Car {
    public int x, y;
    private int car_width=80;
    private int car_height=150; 
    private Image image;

    /*
     * Car constructor
     */
    public Car(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    // Move car left 
    public void moveLeft() {
        x -= 100;
    }

    // Move car right
    public void moveRight() {
        x += 100;
    }

    // Move car down
    public void moveDown(int speed) {
        y += speed;
    }

    // Draw the car
    public void draw(Graphics g) {
        g.drawImage(image, x, y, car_width, car_height, null);
        
    }

    // Collision detection
    public boolean collidesWith(Car other) {
        //this is player car and other is enemy car
        return this.x < other.x + car_width && 
                this.x + car_width > other.x && 
                this.y < other.y + car_height && 
                this.y + car_width > other.y;
    }
}
