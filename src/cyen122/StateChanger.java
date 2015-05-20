/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

/**
 * DID SOMEBODY ORDER SOME SPAGHETTI (CODE)?!?!?!
 * @author Allister
 */
public class StateChanger {
    public StateChanger(String state){
        Game.setState(Game.State.valueOf(state));
        Game.menuCreated = false;
    }
}
