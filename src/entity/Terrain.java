/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

/**
 *
 * @author Allister
 */
public class Terrain extends Entity{

    public Terrain(float x, float y, int t) {
        super(x, y, true, false, false, false, Entity.AI.STATIC);
        addSprite(new String[]{"Block"});
        setTexture(t);
    }
    
    public Terrain(float x, float y){
        this(x, y, 0);
    }
    
}
