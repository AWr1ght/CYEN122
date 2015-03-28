/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Contains Keybindings for the game
 * @author Origianlly Gus Michel : Editied by Allister Wright
 */
public class Keybinds {
    
    public static class Key {
        public final int value;
            private Key(int value) {
                this.value = value;
            }
            public static final Key up = new Key(Keyboard.KEY_W);
            public static final Key down = new Key(Keyboard.KEY_S);
            public static final Key left = new Key(Keyboard.KEY_A);
            public static final Key right = new Key(Keyboard.KEY_D);
            public static final Key jump = new Key(Keyboard.KEY_SPACE);
            public static final Key esc = new Key(Keyboard.KEY_ESCAPE);
    }

    public boolean getUp() { return getKeyState(Key.up);}
    public boolean getDown() { return getKeyState(Key.down);}
    public boolean getLeft() { return getKeyState(Key.left);}
    public boolean getRight() { return getKeyState(Key.right);}
    public boolean getJump() { return getKeyState(Key.jump);}
    public boolean getEsc() { return getKeyState(Key.esc);}
            
    public boolean getAttack() { return Mouse.isButtonDown(0);}

    public boolean getKeyState (Key key) {
	return getRawKeyState(key.value);
    }

    public boolean getRawKeyState (int key) {
	return Keyboard.isKeyDown(key);
    }
}
