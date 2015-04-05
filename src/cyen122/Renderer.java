/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import entity.Entity;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

/**
 * Handles displaying the world and game scene
 * @author Allister
 */
public class Renderer {
    
    /**
     * Renders a single entity
     * @param e the Entity to render
     */
    public static void render(Entity e){
        Texture t = e.getTexture();
        t.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2i(toPixels(e.getX(), "x"),
                       toPixels(e.getY()+e.getHeight(), "y"));
            glTexCoord2f(0, 1);
            glVertex2i(toPixels(e.getX(), "x"),
                       toPixels(e.getY(), "y"));
            glTexCoord2f(1, 1);
            glVertex2i(toPixels(e.getX()+e.getWidth(), "x"),
                       toPixels(e.getY(), "y"));
            glTexCoord2f(1, 0);
            glVertex2i(toPixels(e.getX()+e.getWidth(), "x"),
                       toPixels(e.getY()+e.getHeight(), "y"));
        glEnd();
        t.release();
    }
    
    /**
     * Converts metric World/Entity space to Pixelspace
     * @param t location in Cartesian tilespace
     * @return pixel location of the object
     */
    private static int toPixels(float t, String dir){
        if(dir.equals("x"))
            return (int) (t*32);
        if(dir.equals("y"))
            return (int) (Display.getHeight()-(t*32));
        return 0;
    }
}
