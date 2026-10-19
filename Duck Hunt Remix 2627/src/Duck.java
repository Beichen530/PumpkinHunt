/**
 * STUDENT FILE: Duck behavior.
 *
 * Sprite provides the graphics code. Your job is to make the Duck move,
 * bounce, fall, and reset.
 */
import java.util.Random;

public class Duck extends Sprite {
    // ===============================
    // STUDENT SETTINGS
    // ===============================
    private int dx = (new Random()).nextInt(4)+2;
    private int dy = (new Random()).nextInt(2)+2;

    // ===============================
    // GAME STATE - mostly provided
    // ===============================
    private int homeX;
    private int homeY;
    private int fallSpeed = 20;
    private boolean active = false;
    private boolean falling = false;
    private boolean landed = false;
    Music hitSound = new Music("Hit (1).wav", false);
    public Duck() {
        this(150, 120);
    }

    public Duck(int startX, int startY) {
        // Change duck.gif to your own Halloween or fall image later.
        super("pumpkin.png", startX, startY, 90, 90);

        homeX = startX;
        homeY = startY;
    }

    /**
     * STEP 1: make the Duck move.
     * STEP 2: add the bouncing rules.
     * STEP 3: add the falling rules.
     */
    public void update() {
        if (!active) {
            return;
        }

        if (falling) {
            // STEP 3: Uncomment and complete the falling code.
             y = y + fallSpeed;
             fallSpeed = fallSpeed + 1;
             if (y >= GameWorld.GROUND_TOP) {
                 y = GameWorld.GROUND_TOP;
                 landed = true;
             }

            return;
        }

        // STEP 1: Uncomment these lines to move the Duck.
         x = x + dx;
         y = y + dy;

        // STEP 2: Add if statements that bounce the Duck off the edges.
        // Hint: reverse a direction by changing dx to -dx or dy to -dy.
        // Hint: GameWorld.WORLD_WIDTH is the width of the game.
        // Hint: GameWorld.GROUND_TOP is the top of the ground.
        if (x > GameWorld.WORLD_WIDTH-90 || x < 0) {
        	dx *= -1;
        }
        if (y > GameWorld.GROUND_TOP || y < 0) {
        	dy *= -1;
        }
        
    }

    /**
     * STEP 3: Uncomment the two lines below so a successful click starts the
     * falling behavior.
     */
    public void startFalling() {
        if (active && !falling) {
            changePicture("explosion2.gif");
            hitSound.play();
        	falling = true;
            fallSpeed = 2;
        }
    }

    public boolean hasLanded() {
        return active && landed;
    }

    public boolean isFalling() {
        return falling;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean wasClicked(int mouseX, int mouseY) {
        return active && !falling && super.wasClicked(mouseX, mouseY);
    }

    // These methods are provided so the GameWorld can manage progression.
    public void activate() {
        active = true;
        reset();
    }

    public void deactivate() {
        active = false;
    }
    

    /** STEP 4: verify that reset returns the Duck to its starting position. */
    public void reset() {
        x = homeX;
        y = homeY;
        fallSpeed = 2;
        falling = false;
        landed = false;
    }
}
