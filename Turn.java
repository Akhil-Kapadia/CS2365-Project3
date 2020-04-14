package cs2365_project3;

import java.util.ArrayList;
import java.util.Scanner;

class Turn{
    
    Scanner scan = new Scanner(System.in);
    
    Player currentPlayer;
    private ArrayList<Player> tableSeating;
    ArrayList<Dice> diceArray;
    private int arrowPile;
    
    private ArrayList<String> turnResult;    
    
    public Turn(Player CP, ArrayList<Player> TS, ArrayList<Dice> DA, int AP)
    {
        this.currentPlayer = CP;
        this.tableSeating = TS;
        this.diceArray = DA;
        this.arrowPile = AP;
    }
    
    public ArrayList<Player> getTableSeating()
    {
        return new ArrayList<>(tableSeating);
    }
    
    public void setTableSeating(ArrayList<Player> newSeating)
    {
        this.tableSeating = newSeating;
    }
    
    public void setArrowPile(int totalArrows)
    {
        this.arrowPile = totalArrows;
    }
    
    public int getArrowPile()
    {
        return this.arrowPile;
    }
    
    public void decreaseArrowPile()
    {
        this.arrowPile--;
    }
    
    public void addTurnResult(String output)
    {
        System.out.println(output);
        this.turnResult.add(output);
    }
    
    public ArrayList<String> getTurnResult()
    {
        return new ArrayList<>(turnResult);
    }
    
    public void playTurn() //most of the user controlled stuff will turn into the PlayerControl class but I'm lazy
    {
        boolean[] diceReroll = {true, true, true, true, true}; //starts off as true since all dice will be rolled first time
        
        printGameStatus(); //as a check before the start of each turn
        
        addTurnResult(currentPlayer.getCharacterName() + " Turn Start! - Player " + currentPlayer.getPlayerIndex());
        addTurnResult("--------------------------------------------------------");
        
        rollAllDice(diceReroll);
        
        if(currentPlayer.getUser()) //let user make decisions for rerolling
        {
            while(currentPlayer.CanReroll())
            {
                int i;
                for(i = 0; i < 5; i++)
                {
                    System.out.println("Would you like to reroll dice " + (i+1) + "? (y or n)");
                    String input = scan.nextLine();
                    if(input.equals("y") && diceArray.get(i).getDiceInt() != 2) //cant reroll dynamite
                        diceReroll[i] = true;
                    else
                        diceReroll[i] = false;
                }

                rollAllDice(diceReroll);
                
                //arrows get resolved right away
                for(Dice dice : diceArray)
                {
                    if(dice.getDiceInt() == 0)
                        resolveArrow();
                }
                
                currentPlayer.usedReroll();
            }
            
            currentPlayer.setRerolls(2); //reset rerolls to 2 after turn is over
            
        }
        else //let AI make decisions for rerolling
        {
            
        }
        
        //start resolving dice
        for(Dice dice : diceArray)
            {
                if(checkFrequency(1) >= 3)
                    resolveDynamite();
                if(dice.getDiceInt() == 2)
                    resolveShootOne();
                if(dice.getDiceInt() == 3)
                    resolveShootTwo();
                if(dice.getDiceInt() == 4)
                    resolveBeer();
                if(checkFrequency(5) >= 3)
                    resolveGatling();
            }
    }
    
    public void rollAllDice(boolean[] diceReroll)
    {
        int diceIndex = 1;
        addTurnResult("Dice Roll");
        for(Dice dice : diceArray)
        {
            if(diceReroll[diceIndex-1])
                dice.rollDice();
            addTurnResult("Dice " + diceIndex + ": " + dice.getDiceInt() + " = " + dice.getDiceString());
            diceIndex++;
        }
        addTurnResult("");
    }
    
    //helper functions for dice resolution
    public int checkFrequency(int diceValue)
    { 
        int count = 0;
        for(Dice dice : diceArray)
            if(dice.getDiceInt() == diceValue)
                count++;
        
        return count;
    }
    
    public void indiansAttack()
    {
        addTurnResult("Indians Attack!");
        for(Player player : getTableSeating())
        {
            addTurnResult(player.getCharacterName() + " took " + player.getArrowCount() + " damage.");
            player.TakeDamage(player.getArrowCount());
            player.setArrowCount(0);
        }
        
        setArrowPile(9);
    }
    
    //start resolving dice
    public void resolveArrow()
    {
        currentPlayer.setArrowCount(1);
        decreaseArrowPile();
        addTurnResult("Take an arrow. Current arrow pile: " + getArrowPile());
        if(getArrowPile() == 0)
            indiansAttack();
    }
    
    public void resolveDynamite()
    {
        currentPlayer.TakeDamage(3);
        addTurnResult("Dynamite! Take 3 damage.");
    }
    
    public void resolveShootOne()
    {
        int shootIndex = getTableSeating().indexOf(currentPlayer);
        if(currentPlayer.getUser()) //allow user to choose
        {
            System.out.println("Would you like to shoot who is to your left or right (l or r)");
            String input = scan.nextLine();
            if(input.equals("l"))
            {
                if(shootIndex-- < 0) //if going to left gives negative index, loop around to start
                    shootIndex = getTableSeating().size() + (shootIndex - 1);
                else
                    shootIndex--;
            }
            else
                shootIndex = (shootIndex + 1) % (getTableSeating().size() - 1);
            
            getTableSeating().get(shootIndex).TakeDamage(1);
        }
        else //allow AI to choose
        {
            
        }
        addTurnResult("Shot " + getTableSeating().get(shootIndex).getCharacterName() + " for 1 damage.");
    }
    
    public void resolveShootTwo()
    {
        int shootIndex = getTableSeating().indexOf(currentPlayer);
        if(currentPlayer.getUser()) //allow user to choose
        {
            System.out.println("Would you like to shoot who is to your twice left or twice right (l or r)");
            String input = scan.nextLine();
            if(input.equals("l"))
            {
                if(shootIndex - 2 < 0) //if going to left gives negative index, loop around to start
                    shootIndex = getTableSeating().size() + (shootIndex - 2);
                else
                    shootIndex--;
            }
            else
                shootIndex = (shootIndex + 2) % (getTableSeating().size() - 1);
            
            getTableSeating().get(shootIndex).TakeDamage(1);
        }
        else //allow AI to choose
        {
            
        }
        addTurnResult("Shot " + getTableSeating().get(shootIndex).getCharacterName() + " for 1 damage.");
    }
    
    public void resolveBeer()
    {
        if(currentPlayer.getUser()) //allow user to choose
        {
            for(;;)
            {
                System.out.println("What player would you like to heal (0-" + (getTableSeating().size() - 1) + ")");
                int input = scan.nextInt();
                if(getTableSeating().get(input).isFullHealth())
                    System.out.println("That person is full health already, please select someone else");
                else
                {
                    addTurnResult("Healed " + getTableSeating().get(input).getCharacterName() + " for 1 HP.");
                    getTableSeating().get(input).addHealth();
                    break;
                }
            }            
        }
        else //allow AI to choose
        {
            
        }
    }
    
    public void resolveGatling()
    {
        for(Player player : getTableSeating())
        {
            if(!(player.equals(currentPlayer)))
                player.TakeDamage(1);
        }
        addTurnResult("Gatling! Everyone but " + currentPlayer.getCharacterName() + " takes 1 damage.");
    }
    
    public void printGameStatus()
    {
        for(Player player : getTableSeating())
        {
            System.out.println("Name: " + player.getCharacterName());
            System.out.println("Ability: " + player.getAbility());
            System.out.println("Health: " + player.getHealth());
            System.out.println("Role: " + player.getRole());
            System.out.println("Arrows: " + player.getArrowCount());
            System.out.println("Index: " + player.getPlayerIndex());
            System.out.println("");
        }
    }

}