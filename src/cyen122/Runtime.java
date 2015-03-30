/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import entity.Entity;
import entity.Player;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import world.World;

/**
 * Handles game logic and User Input
 * @author Allister
 */
public class Runtime {
    private Keybinds in = new Keybinds();
    private World world;
    private Player player;
    private ArrayList<Entity> entities;
    
    public Runtime(World w, Player p){
        world = w;
        player = p;
        world.add(player);
    }
    
    public Runtime(){
        world = new World();
        player = new Player(5, 3, new String[]{""});
        world.add(player);
    }
    
    /**
     * Does game ticks on non-static objects (i.e. Player, Shamblers, and Pack)
     */
    public void tick(){
        entities = world.getEntities();
        tickInput();
        
        // As long as the player is spawned before other entities, this for order should work best
        for(int i = 0; i < entities.size(); i++){
            switch(entities.get(0).getAI()){
                case PLAYER:    // The player
                    
                    break;
                case CHASE:     // Deadly, deadly mob
                    
                    break;
                case SHAMBLE:   // Live Obstacles
                    
                    break;
                case STATIC:    // Terrain, Hurdles
                    
                    break;
                default:        // New, undefined AI
                    System.out.println("AI not Enabled");
            }
        }
        
        renderWorld();
    }
    
    /**
     * Checks user input each game tick
     */
    private void tickInput(){
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
    }
    
    /**
     * Renders the world entities each game tick
     */
    public void renderWorld(){
        glClear(GL_COLOR_BUFFER_BIT);
        for(Entity e : world.getEntities()){
            Renderer.render(e);
        }
        Display.update();
        Display.sync(144);
    }
}
