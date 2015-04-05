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
import world.WorldTest;

/**
 * Handles game logic and User Input
 * @author Allister
 */
public class Runtime {
    private Keybinds in = new Keybinds();
    private final World world;
    private Player player;
    private ArrayList<Entity> entities;
    
    public Runtime(World w){
        world = w;
        player = world.getPlayer();
    }
    
    /**
     * Does game ticks on non-static objects (i.e. Player, Shamblers, and Pack)
     */
    public void tick(){
        entities = world.getEntities();
        trim();
        tickInput();
        
        for (Entity curr : entities) {
            // Make the current entity collide iff the entity shouldn't move
            if(curr.getAI() != Entity.AI.STATIC){
                for(Entity e : entities){
                    if(curr != e){
                        if(curr.isColliding(e) == 1 && e.isSolid()){
                            curr.setVY(0);
                            curr.setY((int) curr.getY() + 1);
                        }
                        /*
                        *   TODO: implement more stuff
                        *   .
                        *   .
                        *   .
                        */
                    }
                }
            }
            
            // Make the current entitiy fall
            curr.setY(curr.getY() + curr.getVY());
            if(curr.hasGravity())
                curr.setVY(curr.getVY() - .01f);
            
            switch(curr.getAI()){
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
        if(in.getUp()){
            player.jump();
        } 
        if(in.getDown())
            // TODO: Replace with sliding. . . somewhen
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
        for(Entity e : entities){
            Renderer.render(e);
        }
        Display.update();
        Display.sync(144);
    }
    
    /**
     * Removes entities with invalid location data
     */
    private void trim(){
        for(int trimIndex = 0; 
                  trimIndex < world.getEntities().size(); 
                    trimIndex++){
            if(world.getEntity(trimIndex).getX() < 0)
                world.kill(trimIndex);
            if(world.getEntity(trimIndex).getX() > world.getWidth())
                world.kill(trimIndex);
            if(world.getEntity(trimIndex).getY() < 0)
                world.kill(trimIndex);
            if(world.getEntity(trimIndex).getY() > world.getHeight())
                world.kill(trimIndex);
        }
        entities = world.getEntities();
        
        // enabled respawning for testing purposes
        player = world.getPlayer();
    }
}
