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
import static org.lwjgl.opengl.GL11.*;
import world.*;

/**
 * Handles Game States and User calls
 *
 * @author Allister Wright
 */
public class Game {
    
    public static final boolean DEBUG = true;
    
    // Source: https://www.youtube.com/watch?v=pFUqYPUB_m4
    public static enum State {
        MAIN_MENU, LEVEL_SELECT, LOADING, PLAYING, LEVEL_FINISHED, CREDITS
    }
    
    private static Keybinds in = new Keybinds();
    private static State state = State.PLAYING;
    protected static Viewport cam;
    protected static Runtime run;
    protected static World world;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initDisplay();
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
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);

            cam = new Viewport(70, ((float) Display.getWidth() / (float) Display.getHeight()));
        } catch (LWJGLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public static void initWorld() {
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
                if(in.getAttack()){
                    if(Game.DEBUG) System.out.println("\n\n\n\n\nRegistered");
                    setState(State.PLAYING);
                }
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
