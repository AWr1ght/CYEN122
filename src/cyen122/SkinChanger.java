/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyen122;

/**
 *
 * @author Allister
 */
public class SkinChanger {
    public SkinChanger(String t) {
        Game.initWorld("Level1");
        Game.getPlayer().setTexture(Integer.parseInt(t), false, -1);
        Game.setState(Game.State.PLAYING);
    }
}
