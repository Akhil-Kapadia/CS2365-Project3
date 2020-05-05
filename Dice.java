package cs2365_project3;

import java.util.Random;

public class Dice {
    Random rand = new Random();
    
    private int diceValue;
    private int diceIndex;
    private int diceType;
    private boolean reroll = false;
    
    public Dice(int index, boolean reroll, int type)
    {
        this.diceIndex = index; //give each dice an index to reference it by
        this.reroll = reroll; //set the reroll flag
        this.diceType = type;
    }
    
    //roll the dice, int value 0-5
    public void rollDice()
    {
        this.diceValue = rand.nextInt(6);
    }
    
    public void setReroll(boolean canReroll, String name)
    {
        if(canReroll == true) //you can always set dice to false
        {
            if(getDiceString().equals("Dynamite") && name.equals("BLACK JACK")) //black jack can reroll dynamite
                this.reroll = canReroll;
            else if(!getDiceString().equals("Dynamite")) //if its not dynamite can reroll
                this.reroll = canReroll;
            else
                this.reroll = false;
        }
        else
            this.reroll = false;
    }
    
    public boolean getReroll()
    {
        return this.reroll;
    }
    
    public int getDiceInt()
    {
        return this.diceValue;
    }
    
    public int getDiceIndex()
    {
        return this.diceIndex;
    }
    
    public int getDiceType()
    {
        return this.diceType;
    }
    
    public String getDiceString()
    {
        String value = "error";
        int type = getDiceType();
        if(type == 1)
        {
            switch(getDiceInt()){ //normal dice
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
        }
        
        else if(type == 2) // duel dice
        {
          switch(getDiceInt())
          {
            case 0:
                value = "Arrow";
                break;
            case 1:
                value = "Dynamite";
                break;
            case 2:
                value = "Whiskey Bottle";
                break;
            case 3:
                value = "Duel Guns";
                break;
            case 4:
                value = "Duel Guns";
                break;
            case 5:
                value = "Gatling"; 
                break;
          }
        }
        
        else if(type == 3)// coward dice
        {
          switch(getDiceInt())
          {
            case 0:
                value = "Arrow";
                break;
            case 1:
                value = "Broken Arrow";
                break;
            case 2:
                value = "Shoot person one over left or right";
                break;
            case 3:
                value = "Beer";
                break;
            case 4:
                value = "Dynamite";
                break;
            case 5:
                value = "Double Beer";
                break;
          }
          
          
        }
        
        else if(type == 4)// loudmouth dice
        {
          switch(getDiceInt())
          {
            case 0:
                value = "Arrow";
                break;
            case 1:
                value = "Bullet";
                break;
            case 2:
                value = "Shoot person one over left or right twice";
                break;
            case 3:
                value = "Shoot person two over left or right twice";
                break;
            case 4:
                value = "Dynamite";
                break;
            case 5:
                value = "Double Gatling";
                break;
          }
        }

        return value;
    }
    
}
