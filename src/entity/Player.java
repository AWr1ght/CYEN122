/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import world.World;

/**
 *
 * @author Allister
 */
public class Player extends Entity{
    private World parent;
    private int weapon;     // TODO: Enumerate weapons
    
    public Player(float x, float y, String[] filenames) {
        super(x, y, true, false, false, true, filenames, AI.PLAYER);
    }
    
    public void jump(){
        if(parent.getAt(getX(), getY()) != null ||
               parent.getAt(getX()+1, getY()) != null){
            setVY(.3f);
            setY(getY()+.4f);
        }
    }
    
    public void setWorld(World w){
        parent = w;
    }
}
