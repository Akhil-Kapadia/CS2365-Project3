package cs2365_project3;

import java.util.Random;

public class Dice {
    Random rand = new Random();
    
    private int diceValue;
    
    //roll the dice, int value 0-5
    public void rollDice()
    {
        this.diceValue = rand.nextInt(6);
    }
    
    public int getDiceInt()
    {
        return this.diceValue;
    }
    
    public String getDiceString()
    {
        switch(getDiceInt()){
            case 0:
                return "Arrow";
            case 1:
                return "Dynamite";
            case 2:
                return "Shoot person one over left or right";
            case 3:
                return "Shoot person two over left or right";
            case 4:
                return "Beer";
            case 5:
                return "Gatling";
        }
        
        return "Error";
    }
    
}
