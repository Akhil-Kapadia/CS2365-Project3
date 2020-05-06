package cs2365_project3;

import java.util.Random;

/**
 * Dice class that handles rolling, rerolling, normal dice, expansion dice, and dice face values
 * @author 
 */
public class Dice {
    Random rand = new Random();
    
    private int diceValue;
    private int diceIndex;
    private int diceType;
    private boolean reroll = false;
    
    /**
     * Method to create references to the objects of index, reroll, and type
     * @param index integer, used to reference sides of dice by index
     * @param reroll boolean, true if reroll selected
     * @param type integer, to get which dice is being used
     */
    public Dice(int index, boolean reroll, int type)
    {
        this.diceIndex = index; //give each dice an index to reference it by
        this.reroll = reroll; //set the reroll flag
        this.diceType = type;
    }
    
    /**
     * Method to roll dice using a random number between 0-5 
     */
    public void rollDice()
    {
        this.diceValue = rand.nextInt(6);
    }
    
    /**
     * Method for setting reroll and checking if possible, also character Black Jack
     * special ability to reroll Dynamite
     * @param canReroll boolean, true if reroll selected
     * @param name string, used for character name "BLACK JACK"
     */
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
    
    /**
     * Method to get if reroll
     * @return reroll boolean, true if reroll selected and possible 
     */
    public boolean getReroll()
    {
        return this.reroll;
    }
    
    /**
     * Method to get the dice value when rolled
     * @return diceValue integer, number rolled when dice is rolled
     */
    public int getDiceInt()
    {
        return this.diceValue;
    }
    
    /**
     * Method to return the dice index
     * @return diceIndex integer
     */
    public int getDiceIndex()
    {
        return this.diceIndex;
    }
    
    /**
     * Method to get the dice type for normal or expansion dice
     * @return diceType integer, 1 normal dice, 2 duel dice, 3 coward dice, 4 loudmouth dice
     */
    public int getDiceType()
    {
        return this.diceType;
    }
    
    /**
     * Method to get which ie has been selected and dice values are set here 
     * Switch statement used after each dice type to get the dice integer which corresponds to attributes
     * @return value string, attribute of dice side 
     */
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
