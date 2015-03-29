/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

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
    public enum AI{STATIC, PLAYER, SHAMBLE, CHASE}
    
    private float x, y, w, h;
    private final boolean isSolid, isDamaging, isSlowing, hasGravity;
    private String sprite;  // texture filename
    private Texture texture;
    private AI ai;
    
    // array of sprites for different animation states
    // TODO: Implement entity.Texture for sprite variation
    public Entity(float x0, float y0, 
                  boolean solid, boolean dmg, boolean slow, boolean grav,
                  String[] filenames, AI ai){
        x = x0;
        y = y0;
        isSolid = solid;
        isDamaging = dmg;
        isSlowing = slow;
        hasGravity = grav;
        sprite = filenames[0];
        this.ai = ai;
    }
    
    public Entity(float x0, float y0, float width, float height, 
                  boolean solid, boolean dmg, boolean slow, boolean grav,
                  String[] filenames, AI ai){
        x = x0;
        y = y0;
        w = width;
        h = height;
        isSolid = solid;
        isDamaging = dmg;
        isSlowing = slow;
        hasGravity = grav;
        sprite = filenames[0];
        this.ai = ai;
    }
    
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public float getWidth(){
        return w;
    }
    
    public float getHeight(){
        return h;
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
    
    public void setWidth(float w){
        this.w = Math.max(0, w);
    }
    
    public void setHeight(float h){
        this.h = Math.max(0, h);
    }
    
    public void setTexture(String t){
        loadTexture(t);
    }
    
    public void setAI(AI ai){
        this.ai = ai;
    }
    
    
    /**
     * 
     * @param e the other Entity to check collision
     * @return The direction of collision:
     *      0 = No collission
     *      1 = down
     *      2 = left
     *      3 = right
     *      4 = up
     */
    public int isColliding(Entity e){
        if(y < e.y + e.h){
            return 1;
        } else if(x < e.x + e.w){
            return 2;
        } else if(x + w > e.x){
            return 3;
        } else  if(y + e.h < e.y){
            return 4;
        } else {
            return 0;
        }
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
