/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

import static cyen122.Game.cam;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author allis_000
 */
public class Menu{
    private String filename;
    private MenuButton[] buttons;
    
    public Menu(String f, MenuButton[] buttons){
        filename = f;
        this.buttons = buttons;
        render();
    }
    
    private void render(){
        try{
            Texture t = TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream(
                        "res/menus/" + filename + ".png"));
            t.bind();
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2i(0, Display.getWidth());
                glTexCoord2f(0, 1);
                glVertex2i(0, 0);
                glTexCoord2f(1, 1);
                glVertex2i(Display.getWidth(), 0);
                glTexCoord2f(1, 0);
                glVertex2i(Display.getWidth(), Display.getHeight());
            glEnd();
        } catch(Exception e){
            
        }
    }
}
