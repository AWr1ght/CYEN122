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

    public Pack(String[] filenames) {
        super(true, true, false, filenames, Entity.AI.CHASE);
    }
    
}
