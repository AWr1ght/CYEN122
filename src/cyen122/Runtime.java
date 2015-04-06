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
    private Viewport cam = Game.cam;
    
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
        setCamera();
        
        for (Entity curr : entities) {
            curr.setY(curr.getY() + curr.getVY());
            doGravity(curr);
            
            // Make the current entity collide iff the entity shouldn't move
            if(curr.getAI() != Entity.AI.STATIC){
                for(Entity e : world.getNear(curr).values()){
                    if(e != null && curr != e){
                        ArrayList<Integer> collisions = curr.isColliding(e);
                        if(Game.DEBUG) System.out.println(collisions.size());
                        if(collisions.contains(1)){
                            if(Game.DEBUG) System.out.println("Hit on the Bottom");
                            if(collisions.size() == 1){
                                if(curr instanceof Player) ((Player) curr).setJumping(false);
                                curr.setVY(0);
                                curr.setY((int) (curr.getY() + 1));
                            }
                        }
                        if(collisions.contains(2)){
                            if(Game.DEBUG) System.out.println("Hit to the Left");
                            curr.setX((int) curr.getX() + 1);
                        } 
                        if(collisions.contains(3)){
                            if(Game.DEBUG) System.out.println("Hit to the Right");
                            curr.setX((int) curr.getX());
                        }
                        if(collisions.contains(4)){
                            if(Game.DEBUG) System.out.println("Hit to the Top");
                            curr.setY((int) (curr.getY()));
                        }
                    }
                }
            }
            
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
        if(in.getUp())
            player.jump();
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
    
    private void setCamera(){
        float toX, toY;
        toX = player.getX() - 5;
        toY = Math.max(0, Math.min(player.getY() - 4, world.getHeight()));
        cam.moveTo(toX, 0);
    }
    
    /**
     * Renders the world entities each game tick
     */
    public void renderWorld(){
        glClear(GL_COLOR_BUFFER_BIT);
        for(Entity e : entities){
            Renderer.render(e, cam);
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
    
    private void doGravity(Entity e){
        if(e.hasGravity())
                e.setVY(e.getVY() - .01f);
    }
}
