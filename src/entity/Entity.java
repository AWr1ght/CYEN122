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
    
    // TODO: Replace with states valid for zombie play
    public enum AI {
        STATIC, PLAYER, MOBILE
    }
    
    private static final float DEFAULT_MAX_SPEED = .2f;

    private float x, y, w, h, vx, vy, maxLatSpeed;
    private short direction;
    private final boolean isSolid, isDamaging, isSlowing;
    private boolean hasGravity;
    private String[] sprite;    // animation filenames
    protected ArrayList<String[]> validDisp;  // For storing valid animations in an entity
    
    private Texture texture;    // the currently loaded texture
    private AI ai;
    
    public Entity(float x0, float y0, boolean solid, boolean dmg, 
            boolean slow, boolean grav, AI ai) {
        // Let's just set a default to avoid a NullPointer Exception
        validDisp = new ArrayList<String[]>();
        addSprite(new String[]{"Block"});
        setTexture(0, 0);
        
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
        this.ai = ai;
    }

    public Entity(float x0, float y0, float width, float height,
            boolean solid, boolean dmg, boolean slow, boolean grav, AI ai) {
        validDisp = new ArrayList<String[]>();
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
//        if(Game.DEBUG) System.out.println("Y set to " + y);
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
//        if(Game.DEBUG) System.out.println(vx + " : " + direction);
    }

    public void setVY(float v) {
        vy = v;
    }
    
    public void setMaxSpeed(float m){
        maxLatSpeed = (m == 0) ? DEFAULT_MAX_SPEED : Math.max(0, m);
    }

    public void setGravity(boolean grav) {
        hasGravity = grav;
    }
    
    protected void addSprite(String[] frames){
        validDisp.add(frames);
    }

    public void setTexture(int textureIndex, int frame) {
        texture = loadTexture(textureIndex, frame);
    }

    public void setAI(AI ai) {
        this.ai = ai;
    }

//    public abstract void setWorld(World aThis);
    /**
     * @param e the other Entity to check collision
     * @return The direction of collision: 
     *      0 = No collision 
     *      1 = down 
     *      2 = left 
     *      3 = right 
     *      4 = up
     */
    public ArrayList<Integer> isColliding(Entity e) {
        ArrayList<Integer> collisions = new ArrayList();
        if (e.isSolid){
//            if(Game.DEBUG){
//                System.out.println("y: " + y);
//                System.out.println("y+h: " + (y+h));
//                System.out.println("   e.y: " + e.y);
//                System.out.println("   e.y+e.h: " + (e.y+e.h));
//            }
            
            if(Game.inRange(y, e.y + e.h/2f, e.y+e.h))
                if(Game.inRange(x, e.x, e.x + e.w))
                collisions.add(1);
            
            if(Game.inRange(e.y, y + h/2f, y + h))
                if(Game.inRange(x, e.x, e.x + e.w))
                    collisions.add(4);
            
            if(Game.inRange(e.x, x + w/2f, x + w))
                if (x + w - e.x > Math.abs(y - e.y))
                    collisions.add(3);
            
            if(Game.inRange(x, e.x + e.h/2f, e.x + e.h))
                if (e.x + e.w - x > Math.abs(y - e.y))
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
    private Texture loadTexture(int textureIndex, int frame) {
        if(Game.DEBUG) System.out.println("Loading Texture");
//        if(isStatic){
            try{
                //Source: https://www.youtube.com/watch?v=naE3nbreSUo
                return TextureLoader.getTexture("PNG", 
                           ResourceLoader.getResourceAsStream(
                               "res/sprites/static/" + validDisp.get(textureIndex)[frame] + ".png"));
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            } catch(ArrayIndexOutOfBoundsException e){
                try{
                //Source: https://www.youtube.com/watch?v=naE3nbreSUo
                return TextureLoader.getTexture("PNG", 
                           ResourceLoader.getResourceAsStream(
                               "res/sprites/static/" + validDisp.get(textureIndex)[
                                       frame%validDisp.get(textureIndex).length] + ".png"));
                } catch(FileNotFoundException ex){
                    e.printStackTrace();
                } catch(IOException ex){
                    e.printStackTrace();
                }
            }
        return null;    // Will cause a crash on NullPointerExeption
    }
}
