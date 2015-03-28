/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import entity.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Superclass for campaign levels
 * 
 * @author Allister Wright
 */
public class World {
    private String filename;    // Every level will have a map containing the level data
    private Entity[][] map;   // The result of the setup file being loaded into memory
    
    public World(String filename){
        map = new Entity[30][10];
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                map[i][j] = new Empty();
            }
        }
        
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
        map = new Entity[30][20];
        for(int i = 0; i < 30; i++){
            map[i][2] = new Terrain(new String[]{"Block"});
        }
    }
    
    public Entity[][] getMap(){
        return map;
    }
    
    public void set(int x, int y, Entity e){
        map[Math.max(0, Math.min(map.length, x))][Math.max(0, Math.min(map[0].length, y))] = e;
    }
}
