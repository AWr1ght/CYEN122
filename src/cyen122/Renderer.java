/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import entity.Entity;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Allister
 */
public class Renderer {
    
    
    public static void render(Entity e){
//        e.getTexture().bind();
        glBegin(GL_QUADS);
            glColor3f(0, 1, 1);
//            glTexCoord2f(0, 0);
            glVertex2i(0, -32);
//            glTexCoord2f(0, 1);
            glVertex2i(0, 0);      // TODO: Replace 32 with texture height
//            glTexCoord2f(1, 1);
            glVertex2i(32, 0);
//            glTexCoord2f(1, 0);
            glVertex2i(32, -32);
        glEnd();
    }
    
    /**
     * 
     * @param t location in Cartesian tilespace
     * @return 
     */
    private static int toPixels(float t, String dir){
        if(dir.equals("x"))
            return (int) t*32;
        if(dir.equals("y"))
            return (int) (Display.getHeight()-(t*32));
        return 0;
    }
}
