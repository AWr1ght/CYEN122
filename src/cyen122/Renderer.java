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
     * @param frame the frame to render
     * @param cam the camera to render wrt
     */
    public static void render(Entity e, int frame, Viewport cam){
        float dx = (float) e.getFrameWidth()/e.getTexture().getTextureWidth();
        Texture t = e.getTexture();
        t.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(frame*dx, 0);
            glVertex2i(toPixels(e.getX() - cam.getX(), "x"),
                       toPixels(e.getY()+e.getHeight()- cam.getY(), "y"));
            glTexCoord2f(frame*dx, 1);
            glVertex2i(toPixels(e.getX()- cam.getX(), "x"),
                       toPixels(e.getY()- cam.getY(), "y"));
            glTexCoord2f(dx*(frame+1), 1);
            glVertex2i(toPixels(e.getX()+e.getWidth()- cam.getX(), "x"),
                       toPixels(e.getY()- cam.getY(), "y"));
            glTexCoord2f(dx*(frame+1), 0);
            glVertex2i(toPixels(e.getX()+e.getWidth()- cam.getX(), "x"),
                       toPixels(e.getY()+e.getHeight()- cam.getY(), "y"));
        glEnd();
    }
    
    /**
     * Converts metric World/Entity space to Pixelspace
     * @param t location in continuous tilespace
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
