/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

import entity.Entity;
import entity.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import world.World;

/**
 * Runs Game logic
 *
 * @author Allister Wright
 */
public class Game {
    public static final boolean DEBUG = true;
    
    public static Keybinds in = new Keybinds();
    public static Viewport cam;
    public static World world = new World();
    public static Player player = new Player(5, 3, new String[]{"Block"}); 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initDisplay();
        world.add(player);
        gameLoop();
        cleanUp();
    }

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
    
    public static void renderWorld(){
        for(Entity e : world.getEntities()){
            Renderer.render(e);
        }
    }
    
    public static void gameLoop(){
        while (!Display.isCloseRequested()){        // While not forced to closed
            glClear(GL_COLOR_BUFFER_BIT);
            renderWorld();
            
            if(DEBUG){
                if(in.getAttack()){
                    System.out.println("X Pixel: " + Mouse.getX());
                    System.out.println(" X Tile: " + (int) Mouse.getX()/32);
                    System.out.println("  Y Pixel: " + Mouse.getY());
                    System.out.println("   Y Tile: " + (int) Mouse.getY()/32);
                }
            }
            
            if(in.getUp())
                player.setY(player.getY() + .2f);
            if(in.getDown())
                player.setY(player.getY() - .2f);
            if(in.getLeft())
                player.setX(player.getX() - .2f);
            if(in.getRight())
                player.setX(player.getX() + .2f);
            if(in.getJump())
                player.setY(player.getY() + 2);
            if(in.getEsc());
            
            Display.update();
            Display.sync(144);
        }
    }
    
    public static void cleanUp(){
        Display.destroy();
    }
}
