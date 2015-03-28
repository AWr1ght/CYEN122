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
public class Empty extends Entity{

    public Empty() {
        super(false, false, false, new String[]{" "}, Entity.AI.STATIC);
    }
    
}
