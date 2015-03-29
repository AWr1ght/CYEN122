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
public class Pack extends Entity{

    public Pack(float x, float y, String[] filenames) {
        super(x, y, true, true, false, true, filenames, Entity.AI.CHASE);
    }
    
}
