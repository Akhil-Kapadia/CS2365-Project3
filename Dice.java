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
        String value = "error";
        switch(getDiceInt()){
            case 0:
                value = "Arrow";
                break;
            case 1:
                value = "Dynamite";
                break;
            case 2:
                value = "Shoot person one over left or right";
                break;
            case 3:
                value = "Shoot person two over left or right";
                break;
            case 4:
                value = "Beer";
                break;
            case 5:
                value = "Gatling";
                break;
        }
        
        return value;
    }
    
}
