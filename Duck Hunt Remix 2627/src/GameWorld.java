import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 * TEACHER-PROVIDED FRAMEWORK CODE.
 *
 * GameWorld stores three named Ducks, draws scenery and objects in layers,
 * and coordinates the Dog. Added Ducks appear when the game starts.
 * Students should not edit this file for the core assignment.
 */
public class GameWorld {
    public static final int WORLD_WIDTH = 900;
    public static final int WORLD_HEIGHT = 600;
    public static final int GROUND_TOP = 370;

    private LinkedList<Duck> ducks = new LinkedList<Duck>();
    private Duck duckToRetrieve;
    private Dog dog;
    private Background background = new Background();
    private Foreground foreground = new Foreground();
    private Tree tree = new Tree();
    //private Bush bush1 = new Bush(90, GROUND_TOP - 44, 130, 55);
    //private Bush bush2 = new Bush(510, GROUND_TOP - 38, 120, 49);
    private int duckAmount = Frame.duckamount;
    private int activeDucks = duckAmount;

    private int stars = 5;
    private boolean finished = false;
    private boolean won = false;
    private boolean missed = false;
    public boolean punish = false;
    private int counter = 0;
    Music missSound = new Music("Miss.wav", false);
    Music loseSound = new Music("Wrath.wav", false);
    public GameWorld(Dog dog) {
        this.dog = dog;
    }

    public void addDuck(Duck duck) {
        ducks.add(duck);
    }

    public void start() {
    	for (int i = 0; i < duckAmount; i++) {
    		ducks.get(i).activate();
    	}
    }

    /** Updates each named Duck, then the Dog and game progression. */
    public void update() {
    	if (missed && counter == 16) {
    		missSound.play();
    	}
    	if (missed && counter >= 32) {
    		dog.changePicture("happyFarmer.png");
    		missed = false;
    		counter = 0;
    	}
    	
        if (finished) {
            return;
        }

        for (int i = 0; i < duckAmount; i++) {
    		ducks.get(i).update();
    	}
        
        dog.update();

        if (duckToRetrieve != null && duckToRetrieve.hasLanded()
                && !dog.isRetrieving() && !dog.hasRetrievedDuck()) {
            dog.startRetrieving(duckToRetrieve.getX());
        }

        if (duckToRetrieve != null && dog.hasRetrievedDuck()) {
            duckToRetrieve.deactivate();
            duckToRetrieve = null;
            dog.reset();

            if (allDucksRetrieved()) {
                finished = true;
                won = true;
            }
        }
        if (missed) {
            counter++;
            dog.changePicture("angryFarmer.png");
        }
    }

    public void paint(Graphics g) {
        background.paint(g);
        tree.paint(g);
        foreground.paint(g);
        //bush1.paint(g);
        //bush2.paint(g);

        for (int i = 0; i < duckAmount; i++) {
        	if (ducks.get(i).isActive()) {
        		ducks.get(i).paint(g);
        	}
    	}

        dog.paint(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD, 20));
        g.drawString("Pumpkins Smashed!: " + (duckAmount - activeDucks), 20, 60);
        if ((stars*100/5) <= 40) {
        	g.setColor(Color.RED);
        }
        g.drawString("Happynes: " + (int) (stars*100/5) + "%", 20, 30);
        
        if (finished) {
            g.setFont(new Font("Impact", Font.BOLD, 50));

            if (won) {
                g.drawString("You smashed every pumpkin!", 245, 80);
            } else {
                g.drawString("Out of ammo!", 350, 80);
                
                punish = true;
            }
        }
    }

    public void handleClick(int mouseX, int mouseY) {
        if (finished) {
            return;
        }

        if (duckToRetrieve != null) {
            return;
        }

        Duck clickedDuck = null;
        int hitDucks = 0;
        for (int i = 0; i < duckAmount; i++) {
        	if (ducks.get(i).wasClicked(mouseX, mouseY)) {
        		clickedDuck = ducks.get(i);
        		hitDucks++;
        	} 
    	}
        if (hitDucks == 0) {
    		missed = true;
    	} else {
    		activeDucks--;
    	}

        if (clickedDuck != null) {
            clickedDuck.startFalling();
            duckToRetrieve = clickedDuck;
        } else {
            stars = stars - 1;

            if (stars <= 0) {
                finished = true;
                won = false;
                loseSound.play();
            }
        }
    }

    private boolean allDucksRetrieved() {
    	int success = 0;
    	for (int i = 0; i < duckAmount; i++) {
    		if ((ducks.get(i) == null) || ducks.get(i).isActive()) {
    			success++;
    		}
        
    	}
    	return success <= 0;
    }
}
