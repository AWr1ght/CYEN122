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
public class Shambler extends Entity{
    
    
    public Shambler(String[] filenames) {
        super(true, true, true, filenames, Entity.AI.SHAMBLE);
    }
}
