/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

import static cyen122.Game.cam;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;

import world.*;

/**
 * Handles Game States and User calls
 *
 * @author Allister Wright
 */
public class Game {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;
    public static final boolean DEBUG = true;
    
    // Source: https://www.youtube.com/watch?v=pFUqYPUB_m4
    public static enum State {
        MAIN_MENU, LEVEL_SELECT, LOADING, PLAYING, LEVEL_FINISHED, CREDITS
    }
    
//    private static Frame frame;
//    private static final Canvas canvas = new Canvas();
    
    private static Keybinds in = new Keybinds();    
    private static State state = State.MAIN_MENU;
    protected static Viewport cam;
    protected static Runtime run;
    protected static World world;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initApp();
        gameLoop();
        cleanUp();
    }
    
    /**
     * Called on Game startup - creates windows and layout
     */
    public static void initApp() {
        /* Sources:
                forum.lwjgl.org/index.php?topic=4275.0
                http://wiki.lwjgl.org/index.php?title=Using_a_Resizeable_AWT_Frame_with_LWJGL
        */
//        frame = new Frame("Zombie Run");
//        frame.setLayout(new BorderLayout());
//        frame.add(canvas, BorderLayout.CENTER);
        
        try {
//            Display.setParent(canvas);
//            canvas.setSize(WIDTH, HEIGHT);
//            canvas.requestFocus();
//            frame.setResizable(false);
//            frame.pack();
            
            // Source: Dr. Gourd's Simple Sample Game
//            frame.setLocation(
//                    (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth())/2,
//                    (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight())/2);
//            frame.setVisible(true);
            
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Zombie Run");
            
            Display.create();
            glEnable(GL_TEXTURE_2D);

            Mouse.create();
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);

            cam = new Viewport(70, ((float) WIDTH / (float) HEIGHT));
        } catch (LWJGLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        addListeners();
    }
    
    private static void addListeners(){
//        // Source: http://stackoverflow.com/questions/9093448/do-something-when-the-close-button-is-clicked-on-a-jframe
//        frame.addWindowListener(new WindowAdapter(){
//            @Override
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });
//        
//        canvas.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseReleased(MouseEvent e){
//                System.out.println("Mouse Pressed!");
//                setState(State.PLAYING);
//            }
//        });
    }
    
    /**
     * Handles game states & console debug info
     */
    public static void gameLoop() {
        while (!Display.isCloseRequested()) {   // While not forced to closed
            glClear(GL_COLOR_BUFFER_BIT);
            if(Game.DEBUG) System.out.println(state);
            
            checkState();

            if (DEBUG) {
                System.out.println("Exited checkState()");
                if (in.getAttack()) {
                    System.out.println("X Pixel: " + Mouse.getX());
                    System.out.println("#X Tile: " + (int) (Mouse.getX() / 32f + cam.getX()));
                    System.out.println("   Y Pixel: " + Mouse.getY());
                    System.out.println("   #Y Tile: " + (int) (Mouse.getY() / 32f + cam.getY()));
                }
            }
            
            Display.update();
            Display.sync(144);
        }
    }
    
    /**
     * Called on world startup
     */
    public static void initWorld() {
        // TODO: Add String in constructor to create different worlds
        world = new WorldTest();
        run = new Runtime(world);
    }
    
    public static void setState(State s){
        state = s;
    }
    
    public static void checkState(){
        switch (state) {
            case MAIN_MENU:
                System.out.println("PRESS LMB TO PLAY");
                if(in.getAttack())
                    setState(State.PLAYING);
                break;
            case PLAYING:
                if(run == null) initWorld();
                run.tick();
                break;
        }
    }
    
    /**
     * Kills the Display on game exit
     */
    public static void cleanUp() {
        Display.destroy();
//        frame.dispose();
        System.exit(0);
    }
    
    /**
     * Checks if a value is within a range Designed to clean up collision
     * detection
     *
     * @param v the value to check
     * @param min the minimum
     * @param max the maximum
     * @return min <= v && max >= v;
     */
    public static boolean inRange(float v, float min, float max) {
        return min <= v && max >= v;
    }
}