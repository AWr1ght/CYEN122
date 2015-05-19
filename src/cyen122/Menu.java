/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

import java.io.FileNotFoundException;
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
public class Menu {

    private final String filename;
    private final Texture texture;
    private final MenuButton[] buttons;

    public Menu(String f, MenuButton[] buttons) {
        filename = f;
        texture = setTexture();
        this.buttons = buttons;
        render();
    }

    public void render() {
        texture.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2i(0, 0);
            glTexCoord2f(0, 1);
            glVertex2i(0, Display.getHeight());
            glTexCoord2f(1, 1);
            glVertex2i(Display.getWidth(), Display.getHeight());
            glTexCoord2f(1, 0);
            glVertex2i(Display.getWidth(), 0);
        glEnd();
    }

    private Texture setTexture() {
        try {
            return TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream(
                            "res/menus/" + filename + ".png"));
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filename");
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }

    public MenuButton[] getButtons() {
        return buttons;
    }
}
