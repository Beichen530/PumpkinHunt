import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import loopsPractice2.LoopsPractice2;
import java.util.LinkedList;
import java.util.Random;

/**
 * STUDENT FILE: object declarations.
 *
 * The Swing setup is provided. Students will add more Duck declarations and
 * add those objects to the GameWorld as the game progresses.
 */
public class Frame extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    // ===============================
    // STUDENT OBJECTS
    // ===============================
    public static int duckamount = 15;
    
    private Random rand = new Random(); 
    private LinkedList<Duck> ducks = new LinkedList<Duck>();

    private Dog dogObject = new Dog();
    private GameWorld world = new GameWorld(dogObject);
    
    private Timer timer;
    
    Music hitSound = new Music("Hit (1).wav", false);
    Music bgSound = new Music("BG (1).wav", false);
    Music shotSound = new Music("Shot (1).wav", true);


    public Frame() {
        // STEP 6: Add each Duck to the world after declaring it above.
    	bgSound.loop();
    	
    	for (int i = 0; i < duckamount; i++) {
    		int x = 0 + rand.nextInt(800);
    		int y = 0 + rand.nextInt(GameWorld.GROUND_TOP - 50);

    	
    		ducks.add(new Duck(x,y));
    	}
    	
    	for (int i = 0; i < duckamount; i++) {
    		world.addDuck(ducks.get(i));
    	}
        world.start();

        setPreferredSize(new Dimension(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                world.handleClick(event.getX(), event.getY());
                shotSound.play();
            }
        });

        JFrame window = new JFrame("Pumpkin Hunt");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(this);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	if (!world.punish) {
    		world.update();
    		repaint();
    	} else {
    		timer.stop();
    		LoopsPractice2.main(null);
    	}
    }

    public static void main(String[] args) {
        new Frame();
    }
}
