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
public class Portal extends Entity{
    public Portal(float x0, float y0) {
        super(x0, y0, true, false, true, false, Entity.AI.STATIC);
        addSprite("easter-egg-portal", 188);
        setTexture(1, true);
    }
    
}
