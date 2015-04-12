/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import cyen122.Game;
import entity.*;
import java.awt.Color;
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
    private float[] spawn = new float[2];
    
    /**
     * Loads the world based on an image
     * @param filename the world image
     */
    public World(String filename){
        entities = new ArrayList();
        try{
            BufferedImage level = ImageIO.read(new File("res/levels/" + filename + ".png"));
            locations = new Entity[level.getWidth()][level.getHeight()];
            
            // i is World X Position
            // j is World Y Position
            for(int i = 0; i < level.getWidth(); i++){
                for(int j = 0; j < level.getHeight(); j++){
                    // Source : http://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
//                    if(Game.DEBUG){
//                        System.out.println("Getting color at " 
//                                    + i + ", " + (level.getHeight() - j - 1));
//                    }
                    Color c = new Color(level.getRGB(i, (level.getHeight() - j - 1)));
                    int rgb = 0x010000*c.getRed()
                             + 0x000100*c.getGreen()
                             + c.getBlue();
                    switch(rgb){
                        case 0x00ff00:      // The Player
                            spawn[0] = i; spawn[1] = j;
                            add(new Player(i, j, new String[]{"Slope"}));
                            break;
                        case 0x0000ff:      // Terrain
                            add(new Terrain(i, j));
                            break;
                        case 0x00ffff:      // Slope Up
//                            add(new Slope(i, j, new String[]{"Slope"}));
                            break;
                        case 0x7f7f7f:      // Background
                            break;
                        default:
                            System.out.println(rgb + 
                                   " does not fit the color scheme at "
                                   + i + ", " + j);
                    }
                }
            }    
        } catch(Exception e){
            e.printStackTrace();
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
    public Player getPlayer(){
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i) instanceof Player){
                return (Player) entities.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Entity> getNear(Entity e){
        ArrayList<Entity> out = new ArrayList();
        Entity check;
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                check = getAt((int) e.getX() + i, (int) e.getY() + j);
                if(check != null)
                    out.add(check);
            }
        }
        
        if(Game.DEBUG) 
            if(out.size() > 0)
                System.out.println(out.size() + " Entities near " + 
                    e.getX() + ", " + e.getY());
        
        return out;
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
        
        // resolves handling mobile entity location handling
        if(e.getAI() == Entity.AI.STATIC)
            locations[(int)e.getX()][(int)e.getY()] = e;
    }
    
    public void kill(int entityIndex){
        System.out.println("Killing entity" + entityIndex);
        // Respawn the player if it gets killed
        if(entities.get(entityIndex) instanceof Player)
            add(new Player(spawn[0], spawn[1], new String[]{"Slope"}));
        entities.remove(entityIndex);
    }
}