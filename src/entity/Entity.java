/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import cyen122.Game;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Superclass for the player, obstacles, terrain, etc.
 *
 * @author Allister Wright
 */
public abstract class Entity {

    public enum AI {
        STATIC, PLAYER, SHAMBLE, CHASE
    }
    
    private static final float DEFAULT_MAX_SPEED = .2f;

    private float x, y, w, h, vx, vy, maxLatSpeed;
    private short direction;
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
        direction = 0;
        maxLatSpeed = .3f;
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
        direction = 0;
        maxLatSpeed = .3f;
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
    
    public int getDirection(){
        return direction;
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
        vx = Math.max(-maxLatSpeed, Math.min(v, maxLatSpeed));
        if(Math.abs(vx) < .01) vx = 0;      // handle floating point rounding errors
        direction = (vx == 0) ? 0 : (short) (Math.abs(vx)/vx);
        if(Game.DEBUG) System.out.println(vx + " : " + direction);
    }

    public void setVY(float v) {
        vy = v;
    }
    
    public void setMaxSpeed(float m){
        maxLatSpeed = (m == 0) ? DEFAULT_MAX_SPEED : Math.min(0, m);
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
    public ArrayList<Integer> isColliding(Entity e) {
        ArrayList<Integer> collisions = new ArrayList();
        if (e.isSolid){
            if(y <= e.y + e.h && y >= e.y + e.h/2f)
                collisions.add(1);
            if(y + h >= e.y && y + h <= e.y + e.h/2f)
                collisions.add(4);
            if(x + w >= e.x && x + w <= e.x + e.w/2 
                    && y > e.y + .1 && y < e.y + e.h -.1
                    )
                collisions.add(3);
            if(x < e.x + e.w && x >= e.x + e.w/2
                    && y > e.y + .1 && y < e.y + e.h - .1)
                collisions.add(2);
        }
        return collisions;
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
                       ResourceLoader.getResourceAsStream(
                           "res/sprites/static/" + t + ".png"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;    // Will cause a crash on NullPointerExeption
    }
}
