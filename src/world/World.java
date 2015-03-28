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
     */
    public World(){
        entities = new ArrayList();
        for(int i = 0; i < 30; i++){
            entities.add(new Terrain(i, 2, new String[]{"Block"}));
        }
    }
    
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    
    public void add(Entity e){
        entities.add(e);
    }
}
