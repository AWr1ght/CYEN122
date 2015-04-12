/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;
import world.*;

/**
 * Handles Game States and User calls
 *
 * @author Allister Wright
 */
public class Game {
    public static final boolean DEBUG = true;
    
    private static Keybinds in = new Keybinds();
    protected static Viewport cam;
    protected static Runtime run;
    protected static World world;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initDisplay();
        initWorld();
        gameLoop();
        cleanUp();
    }
    
    /**
     * Called on Game startup
     */
    public static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(960, 720));
            Display.setTitle("Zombie Run");
            Display.create();
            glEnable(GL_TEXTURE_2D);
            
            Mouse.create(); 
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            
            cam = new Viewport(70, ((float) Display.getWidth() / (float) Display.getHeight()));
        } catch (LWJGLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles game states & console debug info
     */
    public static void gameLoop(){
        while (!Display.isCloseRequested()){        // While not forced to closed
            run.tick();
            
            if(DEBUG){
                if(in.getAttack()){
                    System.out.println("X Pixel: " + Mouse.getX());
                    System.out.println("#X Tile: " + (int) (Mouse.getX()/32f + cam.getX()));
                    System.out.println("   Y Pixel: " + Mouse.getY());
                    System.out.println("   #Y Tile: " + (int) (Mouse.getY()/32f + cam.getY()));
                }
            }
        }
    }
    
    /**
     * Called on world startup
     */
    public static void initWorld(){
        world = new WorldTest();
        run = new Runtime(world);
    }
    
    /**
     * Kills the Display on game exit
     */
    public static void cleanUp(){
        Display.destroy();
    }
    
    /**
     * Checks if a value is within a range
     * Designed to clean up collision detection
     * @param v the value to check
     * @param min the minimum
     * @param max the maximum
     * @return min <= v && max >= v;
     */
    public static boolean inRange(float v, float min, float max){
        return min <= v && max >= v;
    }
}
