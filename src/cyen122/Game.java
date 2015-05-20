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
        MAIN_MENU, CHAR_SELECT, PLAYING, LEVEL_FINISHED, EASTER_EGG, KIA
    }
    
//    private static Frame frame;
//    private static final Canvas canvas = new Canvas();
    
    private static Keybinds in = new Keybinds();    
    private static State state = State.MAIN_MENU;
    protected static boolean menuCreated;
    protected static Viewport cam;
    protected static Runtime run;
    protected static World currLevel;
    protected static Menu menu;     // just to keep it from dying
    
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
            Display.setTitle("Revenge of The Dead");
            
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
            
            Display.update();
            Display.sync(144);

            if (DEBUG) {
                if (in.getAttack()) {
                    System.out.println("X Pixel: " + Mouse.getX());
                    System.out.println("#X Tile: " + (int) (Mouse.getX() / 32f + cam.getX()));
                    System.out.println("   Y Pixel: " + Mouse.getY());
                    System.out.println("   #Y Tile: " + (int) (Mouse.getY() / 32f + cam.getY()));
                }
            }
        }
    }
    
    /**
     * Called on world startup
     */
    public static void initWorld(String levelName) {
        // TODO: Add String in constructor to create different worlds
        currLevel = new World(levelName);
        run = new Runtime(currLevel);
    }
    
    public static void initWorld(World w){
        currLevel = w;
        run = new Runtime(currLevel);
    }
    
    /**
     * Signals to destroy the World during Garbage collection
     */
    public static void killWorld(){
        currLevel = null;
        run = null;     
    }
    
    public static void setState(State s){
        state = s;
        menuCreated = false;
    }
    
    public static void makeMenu(String f, MenuButton[] buttons){
        menu = new Menu(f, buttons);
    }
    
    public static void breakMenu(){
        menu = null;
    }
    
    public static void checkSoftButtons(){
        if(menu == null) return;
        for (MenuButton button : menu.getButtons()) {
            if(inRange(Mouse.getX(), button.getX(), button.getX() + button.getWidth()) &&
               inRange(Mouse.getY(), button.getY(), button.getY() + button.getHeight())){
                // saw this next line here: http://goo.gl/Z0q4wM
                if(button.getTarget().getName().equals(World.class.getName())){
                    makeMenu("six3", new MenuButton[]{});   // splash while loading
                    initWorld((World) button.isClicked());
                    setState(State.PLAYING);
                }
                if(button.getTarget().getName().equals(StateChanger.class.getName())){
                    button.isClicked();
                }
                breakMenu();
            }
        }
    }
    
    public static void checkState(){
        switch (state) {
            case MAIN_MENU:
                if(!menuCreated) {
                    makeMenu("Test", new MenuButton[]{
                               new MenuButton(300, 370, 200, 70,
                                     StateChanger.class, "CHAR_SELECT")
                                   });
                    menuCreated = true;
                }
                menu.render();
                break;
            case CHAR_SELECT:
                if(!menuCreated) {
                    makeMenu("charselect", new MenuButton[]{
                               new MenuButton(350, 300, 70, 130,
                                     World.class, "Level1"),
                               new MenuButton(440, 285, 90, 205,
                                     World.class, "Level1")
                                         });
                    menuCreated = true;
                }
                menu.render();
                break;
            case PLAYING:
                if(run == null) initWorld("Level1");    // a default world to load; vestigial
                if(menuCreated) menuCreated = false;
                run.tick();
                break;
            case KIA:
                if(!menuCreated) {
                    makeMenu("deathscreen", new MenuButton[]{
                                   new MenuButton(0, 0, WIDTH, HEIGHT,
                                         StateChanger.class, "MAIN_MENU")
                                          });
                    menuCreated = true;
                }
                menu.render();
                break;
            case LEVEL_FINISHED:
                makeMenu("six3", new MenuButton[]{
                             new MenuButton(0, 0, WIDTH, HEIGHT,
                                   StateChanger.class, "MAIN_MENU")
                               });
                killWorld();
                break;
            case EASTER_EGG:
                makeMenu("six3", new MenuButton[]{
                             new MenuButton(0, 0, WIDTH, HEIGHT,
                                   StateChanger.class, "MAIN_MENU")
                               });
                killWorld();
                break;
        }
        if(in.getAttack()) checkSoftButtons();
    }
    
    /**
     * Kills the Display on game exit
     */
    public static void cleanUp() {
        killWorld();
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