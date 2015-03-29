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
    
    /**
     * Loads the world based on an image
     * @param filename the world image
     */
    public World(String filename){
        entities = new ArrayList();
        
        try{
            BufferedImage level = ImageIO.read(new File("res/levels/" + filename + ".png"));
//            setup = new Entity[level.getWidth()][level.getHeight()];
            // TODO: Setup image parser to fill setup array            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * FOR TESTING PURPOSES ONLY
     * Loads a generic world with a floor
     */
    public World(){
        entities = new ArrayList();
        for(int i = 0; i < 30; i++){
            entities.add(new Terrain(i, 2, new String[]{"Block"}));
        }
    }
    
    /**
     * 
     * @return the list of entities in a world
     */
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    
    /**
     * Adds an entity to the world
     * @param e the Entity to add
     */
    public void add(Entity e){
        entities.add(e);
    }
}
