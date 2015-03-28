/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import static cyen122.Game.world;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Superclass for the player, obstacles, and terrain
 * @author Allister Wright
 */
public class Entity {
    protected enum AI{STATIC, SHAMBLE, CHASE}
    
    private float x, y;
    private boolean isSolid;
    private boolean isDamaging;
    private boolean isSlowing;
    private String sprite;  // texture filename
    private Texture texture;
    private static AI ai;
    
    // array of sprites for different animation states
    // TODO: Implement entity.Texture for sprite variation
    public Entity(float x0, float y0, boolean solid, boolean dmg, boolean slow,
                  String[] filenames, AI ai){
        isSolid = solid;
        isDamaging = dmg;
        isSlowing = slow;
        sprite = filenames[0];
        this.ai = ai;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public Texture getTexture(){
        return texture;
    }
    
    public AI getAI(){
        return ai;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public void setY(float y){
        this.y = y;
    }
    
    public void setTexture(String t){
        loadTexture(t);
    }
    
    public void setAI(AI ai){
        this.ai = ai;
    }
    
    private void loadTexture(String t){
        sprite = t;
        try{
            // Source: https://www.youtube.com/watch?v=naE3nbreSUo
            if(this instanceof Terrain){
                texture = TextureLoader.getTexture("PNG", 
                                        new FileInputStream(new File("res/sprites/static/" + sprite + ".png")));
            } else {
                texture = TextureLoader.getTexture("PNG", 
                                        new FileInputStream(new File("res/sprites/static/" + sprite + ".png")));
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
