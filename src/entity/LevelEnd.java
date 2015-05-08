/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author allis_000
 */
public class LevelEnd extends Entity{
    public LevelEnd(float x, float y, int t) {
        super(x, y, true, false, false, false, AI.STATIC);
        addSprite(new String[]{"Slope"});
        setTexture(t, 0);
    }
    
    public LevelEnd(float x, float y){
        this(x, y, 1);
    }
    
}
