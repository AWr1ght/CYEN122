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
public class BrainCoin extends Entity{
    public BrainCoin(float x0, float y0) {
        super(x0, y0, false, false, false, false, Entity.AI.STATIC);
        addSprite("braincoin", 236);
        setTexture(1, true, .1356f);
    }
}
