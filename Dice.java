package cs2365_project3;

import java.util.Random;

public class Dice {
    Random rand = new Random();
    
    private int diceValue;
    private int diceIndex;
    private boolean reroll = false;
    
    public Dice(int index, boolean reroll)
    {
        this.diceIndex = index; //give each dice an index to reference it by
        this.reroll = reroll; //set the reroll flag
    }
    
    //roll the dice, int value 0-5
    public void rollDice()
    {
        this.diceValue = rand.nextInt(6);
    }
    
    public void setReroll(boolean canReroll)
    {
        this.reroll = canReroll;
    }
    
    public boolean getReroll()
    {
        return this.reroll;
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
