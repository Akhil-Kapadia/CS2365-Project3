import java.util.ArrayList;
import java.util.Scanner;

public class Turn {

	//Arraylist of all players in the game for this turn.
	private ArrayList player = new ArrayList();
	//ArrayList of all the dices that are set for this round.
	private ArrayList<Dice> dices;
	//Index of the player's whose turn it is in the arraylist.
	private int currentPlayer;
	//The total amount of arrows left in the stack
	private int arrowStack;
	//Array of integer corresponding to Dice faces, sorted in priority order.
	private int diceRoll[];
	//Name of current Player from player class.
	private String name;
	
	Scanner scan = new Scanner(System.in);
	
	//Are these objs passed in from Game class??
	PlayerDecisionMaking user = new PlayerDecisionMaking(player);
	AIDecisionMakin ai = new AIDecisionMaking();//No construtor?
	
	public Turn(ArrayList<Player> tableSeating, ArrayList<Dice> dices, int arrowPile, int current)
	{
		setCurrentPlayer(current);
		setPlayers(tableSeating);
		setArrowStack(arrowPile);
		name = getName(current);
		this.dices = dices;
		
		user = new PlayerDecisionMaking(player);
		ai = new AIDecisionMaking();//No construtor?
		playTurn();
		
		
	}
	
	
	/**
	 * Method sets the local player Arraylist equal to the ArrayList of Player
	 * objects from the game class.
	 * @param list	ArrayList of Player Objects
	 */
	public void setPlayers(ArrayList<Player> list)
	{
		//Creates a shallow copy of Player array used in Game (Pointers basically).
		player = (ArrayList)list.clone();
	}
	
	/**
	 * Sets the current players whose turn it is. This is used to reference
	 * the player Arraylist.
	 * @param index	Integer
	 */
	public void setCurrentPlayer(int index)
	{
		currentPlayer=index;
	}
	
	/**
	 * Method gets the amount of arrows in the stack inside the game class, and
	 * sets it locally for use.
	 * @param arrows	Integer.
	 */
	public void setArrowStack(int arrows)
	{
		arrowStack=arrows;
	}
	
	/**
	 * Method returns the amount of arrows left over after the turn ends.
	 * @return	Integer	Arrows left over after turn ends.
	 */
	public int getArrowStack()
	{
		return arrowStack;
	}
	
	private String getName(int index)
	{
		return player.get(index).getCharacterName();
	}
	
	
	/**
	 * Method adds an arrow to the current player if theres enough in the stack.
	 * Also updates the stack count.
	 */
	public void indianArrow()
	{
		if(arrowStack>1)
		{
			player.get(currentPlayer).setArrowCount(1);
			arrowStack--;
		}
	}
	
	/**
	 * Method enacts the Bull's Eye x1 dice, and handled the special cases when
	 * character's abilities interfere with the dice function.
	 * If no special cases, the method determing whether the player to the left
	 * or the right has worse favor, and shoots them dealing 1HP damage.
	 * 
	 * @param jack boolean Used to tell if his ability has been used.
	 */
	public void bullsEyex1()
	{
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
		int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (right != player.size()-1) ? right+1 : 0;
		int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;
		
		if(player.get(currentPlayer).getUser())
		{
			if ( name == "CALAMITY JANET")
			{
				target = user.chooseShoot(player.get(left),player.get(right),
										  player.get(leftx2),player.get(rightx2));
			}else
				target = user.chooseShoot(player.get(left),player.get(right));
		}
		else
		{
			//AI...IDK what demetrios code is doing, so I based it off Jacobs.
		}
			if ( name == "CALAMITY JANET")
			{
				target = ai.chooseShoot(player.get(left),player.get(right),
									    player.get(leftx2),player.get(rightx2));
			}else
				target = ai.chooseShoot(player.get(left),player.get(right));
		}
		
		player.get(target).TakeDamage(1);

	}
	
	/**
	 * Method enacts the Bull's Eye x2 dice, and handled the special cases when
	 * character's abilities interfere with the dice function.
	 * If no special cases, the method determing whether the player to the left
	 * or the right has worse favor, and shoots them dealing 2HP damage.
	 * 
	 * @param jack boolean Used to tell if his ability has been used.
	 */
	public void bullsEyex2()
	{
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
		int right = (currentPlayer!=player.size()-1) ? currentPlayer+1 : 0 ;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (right != player.size()-1) ? right+1 : 0;
		int leftx2 = (left !=0 ) ? left - 1 :  player.size() -1;
				
		if(player.get(currentPlayer).getUser())
		{
			if ( name == "CALAMITY JANET")
			{
				target = user.chooseShoot(player.get(left),player.get(right),
										  player.get(leftx2),player.get(rightx2));
			}else
				target = user.chooseShoot(player.get(left),player.get(right));
		}
		else
		{
			//AI...IDK what demetrios code is doing, so I based it off Jacobs.
		}
			if ( name == "CALAMITY JANET")
			{
				target = ai.chooseShoot(player.get(left),player.get(right),
									    player.get(leftx2),player.get(rightx2));
			}else
				target = ai.chooseShoot(player.get(left),player.get(right));
		}
				
		player.get(target).TakeDamage(1);
	}
	
	/**
	 * Method for the beer dice. If current player is at max health, then HP
	 * is added to the person with the highest favor. If not at max health 
	 * then add to his own. Also handles Jesse Jones ability.
	 */
	public void beer()
	{
		int target;
		int health = player.get(currentPlayer).getHealth();
		int target;
		if(health < player.get(currentPlayer).getMaxHealth())
		{
			if (name == "JESSE JONES" && health < 4)
				player.get(currentPlayer).addHealth(2);	//change addhealth to include integer please.
			player.get(currentPlayer).addHealth(1);
		}
		else
		{
			if(player.getUser())
				target=user.chooseHeal();
			else
				target=ai.chooseHeal();
		player.get(target).addHealth(1);
	}
	
	public void gatlingGun()
	{
		for(Player obj : player)
		{
			String person = obj.getCharacterName();
			if(!person.equals(name) || !person.equals("PAUL REGRET"))
			{
				obj.TakeDamage(1);
			}
		}
		//remove arrows from current player and add them to the stack.
		arrowStack += player.getArrowCount();
		player.setArrowCount(-player.getArrowCount());
	}

	/**
	 * Method determing roll of dice for this turn. Sets an arrayList of dice 
	 * indexes to what the final roll should look like for this turn.
	 */
	public void setDiceRoll()
	{
		int count =0;
		//Check if player is the User or an AI
		if(player.getUser())
		{
			//Reroll as many times as possible. If user replies no, breaks loop.
			while(player.CanReroll())
			{
				System.out.println("Would you like to reroll any dice? (y or n)");
				String input = scan.nextLine();
				if(input.equals("y"))
				{
					//Change the flags to roll. Add black Jack Ability in DiceController?
					dices = user.chooseReroll(dices);
					for(Dice x : dices)
						x.RollDice();//Roll the dices.
				}else
					break;
			}
		}else	//AI handles the rerolls.
		{
			dices = ai.RerollHandler(player.get(currentPlayer), dices, player);
		}
		
		for(Dice x : dices)
		{
			diceRoll[count] = x.getDiceInt();	//set the diceroll to dice values.
			count++;
			//I would also sort the dice array here, but I can't call DiceController
			//if you're doing that from Game.
		}
	}
	
	/**
	 * Handles the actual play of the turn.
	 */
	public void playTurn()
	{
	
		//Count of dynamite.
		int dynamite = 0;
		//Count of gatling dices.
		int gatling = 0;


		setDiceRoll();
		
		endTurn:
		for(int die : diceRoll)
		{
			switch(diceRoll[die])
			{
			case 1:	//Indian Arrow
				indianArrow();
				break;
			case 2:	//Dynamite
				dynamite++;
				if(dynamite==3)
				{
					player.get(currentPlayer).TakeDamage(1);
					break endturn;
				}
				break;
			case 3:	//Bull's Eye x1
				bullsEyex2();
				break;
			case 4:
				bullsEyex2();
				break;
			case 5: 
				beer();
				break;
			case 6:
				gatling++;
				if(gatling>=3)
					gatlingGun();
				break;
			}
			
		}
		
	}
}
