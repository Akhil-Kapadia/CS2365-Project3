package cs2365_project3;

import java.util.ArrayList;
import java.util.Collections;

public class DiceController {
    
    ArrayList<Dice> diceArray;
    
    public DiceController()
    {
        this.diceArray = new ArrayList<>(5);
        for(int i = 0; i < 5; i++)
        {
            Dice newDice = new Dice(i+1, true); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
        }
    }
    
    public void setDiceArray(ArrayList<Dice> DA)
    {
        this.diceArray = DA;
    }
    
    public ArrayList<Dice> getDiceArray()
    {
        return new ArrayList<>(diceArray);
    }
    
    public ArrayList<Dice> sortDiceArray() //sort the dice array by order of how you resolve
    {
        ArrayList<Dice> sortDiceArray = getDiceArray();
        for(int i = 0; i < sortDiceArray.size()-1; i++)
        {
            if(sortDiceArray.get(i).getDiceInt() > sortDiceArray.get(i+1).getDiceInt())
            {
                Collections.swap(sortDiceArray, i, i+1);
                i = 0;
            }
        }
        
        return diceArray;
    }
    
    public void rollAllDice()
    {
        ArrayList<Dice> array = getDiceArray();
        for(Dice dice : array)
        {
            if(dice.getReroll())
            {
                dice.rollDice();
                dice.setReroll(false); //resets all reroll flags to false
            }
        }
        
        setDiceArray(array);
    }
    
    public int checkFrequency(int diceValue)
    { 
        int count = 0;
        for(Dice dice : getDiceArray())
            if(dice.getDiceInt() == diceValue)
                count++;
        
        return count;
    }
    
    public void printAllDice()
    {
        int c = 0;
        for(Dice dice : getDiceArray())
        {
            System.out.println("Dice " + c + ": " + dice.getDiceString());
            c++;
        }
        System.out.println();
    }
    
}
