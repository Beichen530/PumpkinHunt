/**
 * STUDENT FILE: Dog behavior.
 *
 * The GameWorld tells the Dog where the Duck landed. Your job is to make the
 * Dog move toward that location and report when the retrieval is complete.
 */
public class Dog extends Sprite {
    private static final int DOG_WIDTH = 138;
    private static final int DOG_HEIGHT = 188;

    // STUDENT SETTING
    private int speed = 6;

    private final int homeX;
    private final int homeY;
    private int targetX;
    private boolean retrieving = false;
    private boolean retrievedDuck = false;
    Music winSound = new Music("win.wav", false);

    public Dog() {
        super("happyFarmer.png", 40, GameWorld.GROUND_TOP - DOG_HEIGHT+170,
                DOG_WIDTH, DOG_HEIGHT);

        homeX = x;
        homeY = y;
    }

    /** This method is provided so GameWorld can begin a retrieval. */
    public void startRetrieving(int duckX) {
        if (!retrieving) {
            targetX = duckX;
            retrieving = true;
            retrievedDuck = false;
            changePicture("happyFarmer.png");
        }
    }

    /**
     * STEP 5: make the Dog move toward targetX.
     *
     * Use if statements:
     * - If x is less than targetX, increase x.
     * - If x is greater than targetX, decrease x.
     * - When x is close enough, set retrievedDuck to true and retrieving to
     *   false.
     */
    public void update() {
        if (!retrieving) {
            return;
        }

        // Write your Dog movement code here.
        if (retrieving == true && retrievedDuck == false) {
	        if (x < targetX) {
	        	x += 15;
	        }
	        if (x > targetX) {
	        	x -= 20;
	        }
	        if (Math.abs(targetX - x) < 90) {
	        	retrievedDuck = true;
	        	retrieving = false;
	        	winSound.play();
	        }
        }
        }
    

    public boolean isRetrieving() {
        return retrieving;
    }

    public boolean hasRetrievedDuck() {
        return retrievedDuck;
    }

    /** After Step 5, run the game several times and verify that reset works. */
    public void reset() {
        setLocation(homeX, homeY);
        retrieving = false;
        retrievedDuck = false;
        changePicture("happyFarmer.png");
    }
}
