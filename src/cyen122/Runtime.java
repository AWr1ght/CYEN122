/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyen122;

import entity.Entity;
import java.util.ArrayList;

/**
 * Handles game logic and logic-tick rate
 * @author Allister
 */
public class Runtime {
    private static ArrayList<Entity> entities = Game.world.getEntities();
    
    /**
     * Does game ticks on non-static objects (i.e. Player, Shamblers, and Pack)
     */
    public static void tick(){
        
        // As long as the player is spawned before other entities, this for order should work best
        for(int i = 0; i < entities.size(); i++){
            switch(entities.get(0).getAI()){
                case PLAYER:    // The player
                    
                    break;
                case CHASE:     // Deadly, deadly mob
                    
                    break;
                case SHAMBLE:   // Live Obstacles
                    
                    break;
                case STATIC:    // Terrain, Hurdles
                    
                    break;
                default:        // New, undefined AI
                    System.out.println("AI not Enabled");
            }
        }
    }
}
