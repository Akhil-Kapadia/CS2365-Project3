package cs2365_project3;

import java.util.ArrayList;
import java.util.Collections;

public class DiceController {
    
    ArrayList<Dice> diceArray;
    
    public DiceController(int[] types)
    {
        int index = 0;
        this.diceArray = new ArrayList<>(5);
        for(int i = 0; i < types[0]; i++) //normal dice
        {
            Dice newDice = new Dice(index, true, 1); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[1]; i++) //loudmouth dice
        {
            Dice newDice = new Dice(index, true, 2); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[2]; i++) //coward dice
        {
            Dice newDice = new Dice(index, true, 3); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
        }
        for(int i = 0; i < types[3]; i++) //duel dice
        {
            Dice newDice = new Dice(index, true, 4); //set the dice index to be 1-5 and all flags to be true for first roll
            diceArray.add(newDice);
            index++;
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
    
    public ArrayList<Dice> rollAllDice()
    {
        ArrayList<Dice> array = getDiceArray();
        for(Dice dice : array)
        {
            if(dice.getReroll())
            {
                dice.rollDice();
                dice.setReroll(false, ""); //resets all reroll flags to false
            }
        }
        
        return array;
    }
    
    public int checkFrequency(String diceValue)
    { 
        int count = 0;
        for(Dice dice : getDiceArray())
            if(dice.getDiceString().equals(diceValue))
                count++;
        
        return count;
    }
    
    public String printAllDice()
    {
        String output = "";
        int c = 0;
        for(Dice dice : getDiceArray())
        {
            output = output + "Dice " + c + ": " + dice.getDiceString() + "\n";
            System.out.println("Dice " + c + ": " + dice.getDiceString());
            c++;
        }
        output = output + "\n";
        System.out.println();
        return output;
    }
    
}
