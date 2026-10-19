import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 * TEACHER-PROVIDED FRAMEWORK CODE.
 *
 * This class loads and draws images. Students should not edit this file for
 * the core assignment.
 */
public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    private Image image;

    public Sprite(String imageFileName, int x, int y, int width, int height) {
        image = loadImage(imageFileName);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    protected void moveBy(int changeX, int changeY) {
        x = x + changeX;
        y = y + changeY;
    }

    public boolean wasClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    public void changePicture(String imageFileName) {
        image = loadImage(imageFileName);
    }

    public void setLocation(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Image loadImage(String imageFileName) {
        try {
            URL imageURL = Sprite.class.getResource("/imgs/" + imageFileName);

            if (imageURL == null) {
                System.out.println("Could not find image: " + imageFileName);
                return null;
            }

            return Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception exception) {
            System.out.println("Could not load image: " + imageFileName);
            return null;
        }
    }
}
