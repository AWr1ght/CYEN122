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
public class Spike extends Entity{
    
    public Spike(float x, float y, int t) {
        super(x, y, true, true, false, false, Entity.AI.STATIC);
        addSprite("dangerous-spike", 200);
        setTexture(t, true);
    }
    
    public Spike(float x, float y){
        this(x, y, 1);
    }
    
}
