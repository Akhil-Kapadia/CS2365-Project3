package cs2365_project3;

import java.util.ArrayList;
import java.util.Scanner;

public class Turn {

	//Arraylist of all players in the game for this turn.
	private ArrayList<Player> player = new ArrayList();
	//Index of the player's whose turn it is in the arraylist.
	private int currentPlayer;
	//The total amount of arrows left in the stack
	private int arrowStack;
	//Name of current Player from player class.
	private String name;
        
        private boolean gameOver = false;
        int[] roles = {0,0,0,0}; //used to keep track of how many of each role is left alive for checking game over
        private int winCond;
        
        boolean expansion;
        boolean DoA;
	
        private ArrayList<Player> deadList = new ArrayList(0); //arraylist of dead players
        
	Scanner scan = new Scanner(System.in);
	
	PlayerDecisionMaking user;
	AIDecisionMaking ai;
        DiceController diceHandler;
	
	public Turn(ArrayList<Player> tableSeating, ArrayList<Player> deadList, int arrowPile, int current, int[] roles, boolean expansion, boolean DoA)
	{
            this.currentPlayer = current;
            this.player = tableSeating;
            this.deadList = deadList;
            this.arrowStack = arrowPile;
            this.name = player.get(current).getCharacterName();
            
            this.expansion = expansion;
            this.DoA = DoA;
                        
            this.user = new PlayerDecisionMaking(player);
            this.ai = new AIDecisionMaking();
            this.diceHandler = new DiceController();
            this.roles = roles;
        }
        	
	/**
	 * Method returns the amount of arrows left over after the turn ends.
	 * @return	Integer	Arrows left over after turn ends.
	 */
	public int getArrowStack()
	{
            return arrowStack;
	}
        
        public ArrayList<Player> getTableSeating()
        {
            return new ArrayList<>(player);
        }
        
        public ArrayList<Player> getDeadList()
        {
            return new ArrayList<>(deadList);
        }
	
	/**
	 * Method adds an arrow to the current player if there's enough in the stack.
	 * Also updates the stack count.
	 */
	public void indianArrow()
	{
		if(arrowStack > 1) //if there is an arrow left to take
		{
			player.get(currentPlayer).setArrowCount(1); //add an arrow to the player
			arrowStack--; //decrease arrow stack by one
                        System.out.println(player.get(currentPlayer).getCharacterName() + " took an arrow, they have " + 
                                player.get(currentPlayer).getArrowCount() + ". Arrow pile at " + getArrowStack());
                        System.out.println();
                        if(arrowStack == 0) //if that taken arrow was last one in pile, start indian attack
                            indianAttack();
		}

	}
        
        public void setGameOver()
        {
            if(winCondition() != 0)
                this.gameOver = true; //game is over
            else
                this.gameOver = false; //game is not over
        }
        
        public boolean getGameOver()
        {
            return this.gameOver;
        }
        
        public void setWinCond()
        {
            this.winCond = winCondition();
        }
        
        public int getWinCond()
        {
            return this.winCond;
        }
        
        public void indianAttack()
        {
            for(Player p : player)
            {
                p.TakeDamage(p.getArrowCount()); //player takes damaged based on number of arrows they have
                System.out.println(p.getCharacterName() + " took " + p.getArrowCount() + " damage. Current HP: " + p.getHealth());
                p.setArrowCount(0); //reset players arrow count to zero
                checkPlayerDeath(p); //check since player took damage
            }

            arrowStack = 9; //reset arrow pile to 9
            System.out.println();
        }
	
	/**
	 * Method enacts the Bull's Eye x1 dice, and handled the special cases when
	 * character's abilities interfere with the dice function.
	 * If no special cases, the method determining whether the player to the left
	 * or the right has worse favor, and shoots them dealing 1HP damage.
	 * 
	 * @param jack boolean Used to tell if his ability has been used.
	 */
	public void bullsEyex1()
	{
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
                Player targetPlayer;
		int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (right != player.size()-1) ? right+1 : 0;
		int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;
		
		if(player.get(currentPlayer).getUser())
		{
			if ( name.equals("CALAMITY JANET"))
			{
				target = user.chooseShoot(player.get(left),player.get(right),
										  player.get(leftx2),player.get(rightx2));
			}else
				target = user.chooseShoot(player.get(left),player.get(right));
                        
                    player.get(target).TakeDamage(1);
                    System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                    + ". Current HP: " + player.get(target).getHealth());
                    checkPlayerDeath(player.get(target)); //check since player took damage
		}
		else
		{
                    targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Shoot person two over left or right", player);
                    System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                    + ". Current HP: " + targetPlayer.getHealth());
                    targetPlayer.TakeDamage(1);
                    checkPlayerDeath(targetPlayer); //check since player took damage
                               
		}
                System.out.println();

	}
	
	/**
	 * Method enacts the Bull's Eye x2 dice, and handled the special cases when
	 * character's abilities interfere with the dice function.
	 * If no special cases, the method determining whether the player to the left
	 * or the right has worse favor, and shoots them dealing 2HP damage.
	 * 
	 * @param jack boolean Used to tell if his ability has been used.
	 */
	public void bullsEyex2()
	{
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
                Player targetPlayer;
		int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (right != player.size()-1) ? right+1 : 0;
		int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;
				
		if(player.get(currentPlayer).getUser())
		{
			if ( name.equals("CALAMITY JANET"))
			{
				target = user.chooseShoot(player.get(left),player.get(right),
										  player.get(leftx2),player.get(rightx2));
			}
                        else
				target = user.chooseShoot(player.get(left),player.get(right));
                        
                    player.get(target).TakeDamage(1);
                    System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + player.get(target).getCharacterName() 
                    + ". Current HP: " + player.get(target).getHealth());
                    checkPlayerDeath(player.get(target)); //check since player took damage
		}
		else
		{
                    targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Shoot person two over left or right", player);
                    targetPlayer.TakeDamage(1);
                    System.out.println(player.get(currentPlayer).getCharacterName() + " shot " + targetPlayer.getCharacterName() 
                    + ". Current HP: " + targetPlayer.getHealth());
                    checkPlayerDeath(targetPlayer); //check since player took damage
		}
                
                
                System.out.println();
				
	}
	
	/**
	 * Method for the beer dice. If current player is at max health, then HP
	 * is added to the person with the highest favor. If not at max health 
	 * then add to his own. Also handles Jesse Jones ability.
	 */
	public void beer()
	{
            int targetIndex;
            Player targetPlayer = player.get(currentPlayer);
            if(player.get(currentPlayer).getUser())
            {
                    targetIndex = user.chooseHeal();
                    for(Player p : player)
                    {
                        if(p.getPlayerIndex() == targetIndex)
                            targetPlayer = p;
                    }
            }
            else
                targetPlayer = ai.getHighestFavor(player.get(currentPlayer), "Beer", player);
            
            //handle jesse jones (if less than or equal to 4 hp and healing self, double healing)
            if (name.equals("JESSE JONES") && targetPlayer.equals(player.get(currentPlayer)) && player.get(currentPlayer).getHealth() <= 4)
		targetPlayer.addHealth(2);
            else if(!(targetPlayer.getRole().equals("Zombie"))) //zombies cannot be healed
                targetPlayer.addHealth(1);
            if(!(targetPlayer.getRole().equals("Zombie"))) //only print out the message if someone was actually healed
            {
                System.out.println(player.get(currentPlayer).getCharacterName() + " healed " + targetPlayer.getCharacterName() 
                        + ". Current HP: " + targetPlayer.getHealth());
                System.out.println();
            }

	}
	
	public void gatlingGun()
	{
            System.out.println("Gatling Gun!");
		for(Player obj : player) //each player takes one damage unless paul regret
		{
			String person = obj.getCharacterName();
			if(!person.equals(name) || !person.equals("PAUL REGRET"))
			{
                            System.out.println(person + "takes 1 damage. Current HP: " + obj.getHealth());
				obj.TakeDamage(1);
			}
                        checkPlayerDeath(obj); //check death since player took damage
		}
		//remove arrows from current player and add them to the stack.
		arrowStack += player.get(currentPlayer).getArrowCount();
		player.get(currentPlayer).setArrowCount(-player.get(currentPlayer).getArrowCount());
                
                System.out.println();
	}

	/**
	 * Method determining roll of dice for this turn. Sets an arrayList of dice 
	 * indexes to what the final roll should look like for this turn.
	 */
	public void setDiceRoll()
	{
		int count = 1;
		//Check if player is the User or an AI
                diceHandler.rollAllDice();
                System.out.println("Roll " + count + ": ");
                diceHandler.printAllDice();
                count++;
                
		if(player.get(currentPlayer).getUser())
		{
			//Reroll as many times as possible. If user replies no, breaks loop.
			while(player.get(currentPlayer).CanReroll())
			{
				System.out.println("Would you like to reroll any dice? (y or n)");
				String input = scan.nextLine();
				if(input.equals("y"))
				{
                                    
					//Change the flags to roll. Add black Jack Ability in DiceController?
					diceHandler.setDiceArray(user.chooseReroll(diceHandler.getDiceArray()));
                                        
					diceHandler.rollAllDice();
                                        System.out.println("Roll " + count + ": ");
                                        diceHandler.printAllDice();
                                        count++;
                                        player.get(currentPlayer).usedReroll();
                                        //need to handle arrows as they come up
                                        for(Dice dice : diceHandler.getDiceArray())
                                        {
                                            if(dice.getDiceInt() == 0)
                                                indianArrow();
                                        }
                                        //if three dynamite, cant reroll anymore
                                        if(diceHandler.checkFrequency(1) >= 3)
                                            player.get(currentPlayer).setRerolls(0);
				}
                                else
					break;
			}
		}
                else	//AI handles the rerolls.
		{
                    while(player.get(currentPlayer).CanReroll())
                    {
			diceHandler.setDiceArray(ai.RerollHandler(player.get(currentPlayer), diceHandler.getDiceArray(), player));
                        diceHandler.rollAllDice();
                        System.out.println("Roll " + count + ": ");
                        diceHandler.printAllDice();
                        count++;
                        player.get(currentPlayer).usedReroll();
                        //need to handle arrows as they come up
                        for(Dice dice : diceHandler.getDiceArray())
                        {
                            if(dice.getDiceInt() == 0)
                                indianArrow();
                        }
                        //if three dynamite, cant reroll anymore
                        if(diceHandler.checkFrequency(1) >= 3)
                            player.get(currentPlayer).setRerolls(0);
                    }
		}
		
                diceHandler.setDiceArray(diceHandler.sortDiceArray());
	}
	
	/**
	 * Handles the actual play of the turn.
	 */
	public void playTurn()
	{
                //roll the dice
		setDiceRoll();
                
                player.get(currentPlayer).setRerolls(2);
		
                //resolve the dice in correct order, arrows are already handled as they are rolled
                if(diceHandler.checkFrequency(1) >= 3)
                {
                    player.get(currentPlayer).TakeDamage(1);
                    checkPlayerDeath(player.get(currentPlayer)); //check since player took damage
                    if(gameOver)
                    {
                        winCond = winCondition();
                        return; //breakout since game is over
                    }
                }

		for(Dice dice : diceHandler.getDiceArray())
		{
			switch(dice.getDiceInt())
			{
			case 2:	//Bull's Eye x1
				bullsEyex2();
                                if(gameOver)
                                {
                                    winCond = winCondition();
                                    return; //breakout since game is over
                                }
				break;
			case 3:
                                if(gameOver)
                                {
                                    winCond = winCondition();
                                    return; //breakout since game is over
                                }
				bullsEyex2();
				break;
			case 4: 
				beer();
				break;
			}
			
		}
                
                if(diceHandler.checkFrequency(5) >= 3)
                {
                    gatlingGun();
                    if(gameOver)
                    {
                        winCond = winCondition();
                        return; //breakout since game is over
                    }
                }
		
	}
        
    public int winCondition()
    {
        if(roles[0] == 0 && roles[1] == 1 && roles[2] == 0 && roles[3] == 0)
            return 1; //everyone else died, single renegade wins
        else if(roles[1] == 0 && roles[2] == 0)
            return 2; //outlaws and renegade(s) died, sheriff wins
        else if(roles[0] == 0)
            return 3; //sheriff died, outlaws win
        else
            return 0; //game is not over
    }
        
    public boolean checkPlayerDeath(Player damagedPlayer)
    {
        if(damagedPlayer.getHealth() <= 0)
        {
            String role = damagedPlayer.getRole();
            //decrement the current amount of the role of the dead player that exists in the game
            if(role.equals("Sheriff"))
                roles[0]--;
            else if(role.equals("Renegade"))
                roles[1]--;
            else if(role.equals("Outlaw"))
                roles[2]--;
            else
                roles[3]--;
            
            System.out.println("Player " + damagedPlayer.getPlayerIndex() + " is dead, their role was " + role);
            
            deadList.add(damagedPlayer);
            player.remove(damagedPlayer); //remove the player from the seating
            setGameOver();
            
            return true;
        }
        else
            return false;
    }
}