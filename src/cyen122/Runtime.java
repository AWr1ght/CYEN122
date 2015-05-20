/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import entity.*;
import java.util.ArrayList;
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
    private int score;
    private Player player;
    private ArrayList<Entity> entities;
    private ArrayList<Integer> toKill;
    
    public Runtime(World w){
        world = w;
        entities = new ArrayList<Entity>();
        toKill = new ArrayList<Integer>();
        
        player = world.getPlayer();
        score = 0;
    }
    
    /**
     * Does game ticks on non-static objects (i.e. Player, Spikes, Coins, etc.)
     */
    public void tick(){
        if(Game.DEBUG) System.out.println("RUNTIME TICK");
        entities = world.getEntities();
        trim();
        tickInput();
        setCamera();
        
        for (Entity curr : entities) {
            doMomentum(curr);
            
            // Make the current entity collide iff the entity shouldn't move
            if(curr.getAI() != Entity.AI.STATIC){
                for(Entity e : world.getNear(curr)){
                    if(e != null && curr != e){ // vestigial checks
                        ArrayList<Integer> collisions = curr.isColliding(e);
                        
                        // specialish cases
                        if(collisions.size() > 0){
                            if(e instanceof LevelEnd)
                                Game.setState(Game.State.LEVEL_FINISHED);
                            if(e instanceof Portal)
                                Game.setState(Game.State.EASTER_EGG);
                            if(e instanceof BrainCoin){
                                score += 10;
                                toKill.add(world.getIDfromInstance(e));
                            }
                            if(e.isLethal())
                                Game.setState(Game.State.KIA);
                        }
                        
                        if(e.isSolid()){
                            if(collisions.contains(1)){
                                if(Game.DEBUG) System.out.println("Hit on the Bottom");
                                if(collisions.size() == 1){
                                    if(curr instanceof Player) ((Player) curr).setJumping(false);
                                    curr.setVY(0);                                  // make the entitiy not fall
                                    curr.setVX(curr.getVX() -.3f*curr.getVX());     // create friction
                                    
                                    // Force upward if the tile above e is empty
                                    if(world.getAt(e.getX(), e.getY() + 1) == null && 
                                       world.getAt(e.getX() + 1, e.getY() + 1) == null)
                                        curr.setY((int) curr.getY() + 1);
                                }
                            }
                            if(collisions.contains(2)){
                                if(Game.DEBUG) System.out.println("Hit to the Left");
                                curr.setVX(0);
                                curr.setX((int) curr.getX() + 1);
                            } 
                            if(collisions.contains(3)){
                                if(Game.DEBUG) System.out.println("Hit to the Right");
                                curr.setVX(0);
                                curr.setX((int) curr.getX());
                            }
                            if(collisions.contains(4)){
                                if(Game.DEBUG) System.out.println("Hit to the Top");
                                curr.setVY(-.1f);   // Becomes spiderman if set to 0
                                curr.setY((int) curr.getY());
                            }
                        }
                    }
                }
            }
            
            switch(curr.getAI()){
                case PLAYER:    // The player
                    
                    break;
                case MOBILE:   // Live Obstacles
                    
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
        if(in.getShift())
            player.setMaxSpeed(.1f);
        else player.setMaxSpeed(0); //  resets to default
        if(in.getUp())
            player.jump();
        if(in.getDown())
            // TODO: Replace with sliding. . . somewhen
            player.setVY(player.getVY() - .2f);
        if(in.getLeft())
            player.setVX(player.getVX() - .2f);
        if(in.getRight())
            player.setVX(player.getVX() + .2f);
        if(in.getJump())
            player.jump();
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
            Renderer.render(e, 
                    (int) ((System.currentTimeMillis()*e.getFrameCount()/1000)%e.getFrameCount()),
                    cam);
        }
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
        for(Object i : toKill.toArray()){
            world.kill((int) i);
        }
        toKill = new ArrayList<Integer>();  // just reset it
        
        entities = world.getEntities();
        
        if(world.getPlayerID() == -1) Game.setState(Game.State.KIA);
    }
    
    /**
     * Does gravity on an entity
     * @param e the entity to accelerate downwards
     */
    private void doMomentum(Entity e){
        
        // Optimisation step - avoids trying to move nonmoving things
        if(e.getAI() != Entity.AI.STATIC){
            e.setY(e.getY() + e.getVY());
            e.setX(e.getX() + e.getVX());
        }
        
        if(e.hasGravity())
                e.setVY(e.getVY() - .01f);
    }
}
