/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;

/**
 * Handles User point of view
 * 
 * @author Allister Wright
 */
public class Viewport {
    public static Vector2f position;
    private float fov, aspect;
    private static Keybinds in = new Keybinds();
    
    public Viewport(float fov, float aspect){
        position = new Vector2f(1, 1);
        
        this.fov = fov;
        this.aspect = aspect;
        initProjection();
    }
    
    /**
     * Initialises GL settings
     */
    private void initProjection(){
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);      
        glMatrixMode(GL_MODELVIEW);
        
//        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);
        
        glCullFace(GL_BACK);
    }
    
    public float getX(){
        return position.x;
    }
    
    public float getY(){
        return position.y;
    }
    
    /**
     * Moves along XY plane
     * @param x 
     * @param y 
     */
    public static void moveTo(float x, float y){
        position.y = y;
        position.x = x;
    }
}