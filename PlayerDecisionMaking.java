package cs2365_project3;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerDecisionMaking {
    
    ArrayList<Player> tableSeating;
    
    Scanner scan = new Scanner(System.in);
    
    public PlayerDecisionMaking(ArrayList<Player> tableSeating)
    {
        this.tableSeating = tableSeating;
    }
    
    public int chooseShoot(Player player1, Player player2)
    {
        System.out.println("Please choose which player you would like to shoot");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(tableSeating.get(i).equals(player1) || tableSeating.get(i).equals(player2))
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        return scan.nextInt();
    }
    
    //method overloading to allow janet's ability
    public int chooseShoot(Player player1, Player player2, Player player3, Player player4)
    {
        System.out.println("Please choose which player you would like to shoot");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(tableSeating.get(i).equals(player1) || tableSeating.get(i).equals(player2) 
                || tableSeating.get(i).equals(player3) || tableSeating.get(i).equals(player4))
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        return scan.nextInt();
    }
    
    public int chooseHeal()
    {
        System.out.println("Please choose who you would like to heal");
        for(int i = 0; i < tableSeating.size(); i++)
        {
            if(!(tableSeating.get(i).isFullHealth())) //if player not full HP, print them out
                System.out.println(i + ": " + tableSeating.get(i).getCharacterName());
        }
        return scan.nextInt(); 
    }
    
    public ArrayList<Dice> chooseReroll(ArrayList<Dice> diceArray)
    {
        System.out.println("Please enter the dice you would like to reroll (ex: 1345):");
        for(int i = 0; i < diceArray.size(); i++)
            System.out.println(i + ": " + diceArray.get(i).getDiceString());
        String input = scan.nextLine();
        for(int j = 0; j < input.length(); j++)
            diceArray.get((int)input.charAt(j)-48).setReroll(true);
        return diceArray;
    }
    
}
