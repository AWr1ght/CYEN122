/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import cyen122.Game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Superclass for the player, obstacles, and terrain
 *
 * @author Allister Wright
 */
public abstract class Entity {

    public enum AI {

        STATIC, PLAYER, SHAMBLE, CHASE
    }

    private float x, y, w, h, vx, vy;
    private final boolean isSolid, isDamaging, isSlowing;
    private boolean hasGravity;
    private String[] sprite;    // animation filenames
    private Texture texture;    // static texture filename
    private AI ai;
    
    // TODO: Implement Entity(...) for animated and static entities
    public Entity(float x0, float y0,
            boolean solid, boolean dmg, boolean slow, boolean grav,
            String[] filenames, AI ai) {
        setTexture(filenames[0]);
        x = x0;
        y = y0;
        w = (float) (texture.getImageWidth()/32);
        h = (float) (texture.getImageHeight()/32);
        vx = 0;
        vy = 0;
        isSolid = solid;
        isDamaging = dmg;
        isSlowing = slow;
        hasGravity = grav;
        sprite = filenames;
        this.ai = ai;
    }

    public Entity(float x0, float y0, float width, float height,
            boolean solid, boolean dmg, boolean slow, boolean grav,
            String[] filenames, AI ai) {
        setTexture(filenames[0]);
        x = x0;
        y = y0;
        w = width;
        h = height;
        vx = 0;
        vy = 0;
        isSolid = solid;
        isDamaging = dmg;
        isSlowing = slow;
        hasGravity = grav;
        sprite = filenames;
        this.ai = ai;
    }

    // Getters and Setters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return w;
    }

    public float getHeight() {
        return h;
    }

    public float getVX() {
        return vx;
    }

    public float getVY() {
        return vy;
    }
    
    public boolean isSolid(){
        return isSolid;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public Texture getTexture() {
        return texture;
    }

    public AI getAI() {
        return ai;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float w) {
        this.w = Math.max(0, w);
    }

    public void setHeight(float h) {
        this.h = Math.max(0, h);
    }

    public void setVX(float v) {
        vx = v;
    }

    public void setVY(float v) {
        vy = v;
    }

    public void setGravity(boolean grav) {
        hasGravity = grav;
    }

    public void setTexture(String t) {
        texture = loadTexture(t);
    }

    public void setAI(AI ai) {
        this.ai = ai;
    }

//    public abstract void setWorld(World aThis);
    /**
     * @param e the other Entity to check collision
     * @return The direction of collision: 
     *      0 = No collission 
     *      1 = down 
     *      2 = left 
     *      3 = right 
     *      4 = up
     */
    public int isColliding(Entity e) {
        if (y < e.y + e.h) {
            return 1;
        } else if (x < e.x + e.w) {
            return 2;
        } else if (x + w > e.x) {
            return 3;
        } else if (y + e.h < e.y) {
            return 4;
        }
        // If none of the entities registered a collision
        return 0;
    }

    /**
     * Sets the current texture to display
     *
     * @param t the filename of the texture to load
     */
//    private Texture loadTexture(String t){
    private Texture loadTexture(String t) {
        if(Game.DEBUG) System.out.println("Loading " + t);
        try{
            //Source: https://www.youtube.com/watch?v=naE3nbreSUo
            return TextureLoader.getTexture("PNG", 
                       new FileInputStream(
                           new File("res/sprites/static/" + t + ".png")));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;    // Will cause a crash on NullPointerExeption
    }
}
