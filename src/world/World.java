/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import entity.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Superclass for campaign levels
 * 
 * @author Allister Wright
 */
public class World {
    private String filename;    // Every level will have a map containing the level data
    private ArrayList<Entity> entities;
    private Entity[][] locations;   // to reduce collision detection time
                                     // only stores static entities
    
    /**
     * Loads the world based on an image
     * @param filename the world image
     */
    public World(String filename){
        try{
            BufferedImage level = ImageIO.read(new File("res/levels/" + filename + ".png"));
//            setup = new Entity[level.getWidth()][level.getHeight()];
            // TODO: Setup image parser to fill setup array            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        entities = new ArrayList();
    }
    
    /**
     * FOR TESTING PURPOSES ONLY
     * Loads a generic world with a floor
     */
    public World(){
        entities = new ArrayList();
        locations = new Entity[30][10];
        for(int i = 0; i < 30; i++){
            add(new Terrain(i, 2, new String[]{"Block"}));
        }
    }
    
    /**
     * @return the list of entities in a world
     */
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    
    /**
     * @param entityIndex the index to get
     * @return The Entity the specified index
     */
    public Entity getEntity(int entityIndex){
        if(entityIndex < entities.size() && entityIndex >= 0)
            return entities.get(entityIndex);
        else return null;
    }
    
    /**
     * @return The index of the player in the entity list
     */
    public int getPlayer(){
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i) instanceof Player){
                return i;
            }
        }
        return -1;  // invalid index
    }
    
    /**
     * Returns the Entity at a location
     * @param x 
     * @param y
     * @return 
     */
    public Entity getAt(int x, int y){
        if(x < 0 || x >= locations.length)
            return null;
        if(y < 0 || y >= locations[0].length)
            return null;
        return locations[x][y];
    }
    
    /**
     * To simplify calls
     * @param x
     * @param y
     * @return 
     */
    public Entity getAt(float x, float y){
        return getAt((int) x, (int) y);
    }
    
    public int getWidth(){
        return locations.length;
    }
    
    public int getHeight(){
        return locations[0].length;
    }
    
    /**
     * Adds an entity to the world
     * @param e the Entity to add
     */
    public void add(Entity e){
        entities.add(e);
        if(e instanceof Player)
            ((Player) e).setWorld(this);
        
        // resolves handling mobile entity location handling
        if(e.getAI() == Entity.AI.STATIC)
            locations[(int)e.getX()][(int)e.getY()] = e;
    }
    
    public void kill(int entityIndex){
        System.out.println("Killing entity" + entityIndex);
        // Respawn the player if it gets killed
        if(entities.get(entityIndex) instanceof Player)
            add(new Player(5, 3, new String[]{""}));
        entities.remove(entityIndex);
    }
}
