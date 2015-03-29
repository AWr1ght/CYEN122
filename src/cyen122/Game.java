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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initDisplay();
        initRuntime();
        gameLoop();
        cleanUp();
    }
    
    /**
     * Called on Game startup
     */
    public static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(1024, 768));
            Display.setTitle("666");
            Display.create();
            
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
                    System.out.println("#X Tile: " + (int) Mouse.getX()/32);
                    System.out.println("   Y Pixel: " + Mouse.getY());
                    System.out.println("   #Y Tile: " + (int) Mouse.getY()/32);
                }
            }
        }
    }
    
    /**
     * Called on world startup
     */
    public static void initRuntime(){
        run = new Runtime();
    }
    
    /**
     * Kills the Display on game exit
     */
    public static void cleanUp(){
        Display.destroy();
    }
}
