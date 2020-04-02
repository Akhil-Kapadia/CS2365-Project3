import java.util.ArrayList;

public class Turn {

	//Arraylist of all players in the game for this turn.
	private ArrayList player = new ArrayList();
	//Index of the player's whose turn it is in the arraylist.
	private int currentPlayer;
	//The total amount of arrows left in the stack
	private int arrowStack;
	//Array of integer corresponding to Dice faces, sorted in priority order.
	private int diceRoll[];
	//Name of current Player from player class.
	private String name;
	
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
	public void bullsEyex1(boolean jack)
	{
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
		int right = (currentPlayer!=player.size()-1) ? right+1 : 0 ;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (currentPlayer < player.size()-2) ? currentPlayer +2:
			(currentPlayer == player.size()-1) ? 0 : 1;
		int leftx2 = (currentPlayer>1) ? currentPlayer - 2 :  
		   (currentPlayer==1) ? player.size()-1 : player.size()-2 ;
		
		if (name == "ROSE DOOLAN" || name == "CALAMITY JANET")
		{

			/*Choose which ever has the worst favor here.
			 * Choose between left and leftx2,
			 * Choose between right and rightx2
			 * then set left or right equal to it. 
			 */
		}
		
		/*
		 * Put code Determing whether left/right has better favor here.
		 * Then set target equal to that.
		 */

		if (name == "SLAB THE KILLER")
		{
			int beer=0;
			for (int i : diceRoll)
				 beer = (diceRoll[i]==5) ? beer+1 : beer;
			if(beer!=0 && !jack)
				player.get(target).TakeDamage(2);
		}else
		{
			player.get(target).TakeDamage(1);
		}
	}
	
	/**
	 * Method enacts the Bull's Eye x2 dice, and handled the special cases when
	 * character's abilities interfere with the dice function.
	 * If no special cases, the method determing whether the player to the left
	 * or the right has worse favor, and shoots them dealing 2HP damage.
	 * 
	 * @param jack boolean Used to tell if his ability has been used.
	 */
	public void bullsEyex2(boolean jack)
	{
		String character = player.get(currentPlayer).getCharacterName();
		//Sets the left and right index of currentPlayer. If current is 0, then
		//left is that last person.
		int target;
		int right = (currentPlayer!=player.size()-1) ? rightx1+1 : 0;
		int left = (currentPlayer!=0) ? currentPlayer - 1 :  player.size()-1;
		int rightx2 = (currentPlayer < player.size()-2) ? currentPlayer + 2 :
					(currentPlayer == player.size()-1) ? 0 : 1;
		int leftx2 = (currentPlayer>1) ? currentPlayer - 2 :  
				   (currentPlayer==1) ? player.size()-1 : player.size()-2 ;
		int rightx3 = (rightx2!=player.size()-1) ? rightx2+1 : 0;
		int leftx3 = (leftx2!=0) ? leftx2-1 : player.size()-1;

		if( name == "CALAMITY JANET")
		{
			/*Choose which ever has the worst favor here.
			 * Choose between left and leftx2,
			 * Choose between right and rightx2
			 * then set left or right equal to it. 
			 */
		}
		
		if(name == "ROSE DOOLAN")
		{
			/*Choose which ever has the worst favor here.
			 * Choose between leftx2 and leftx3,
			 * Choose between rightx2 and rightx3
			 * then set left or right equal to it. 
			 */
		}
		
		/*
		 * Put code Determing whether left/right has better favor here.
		 * Then set target equal to that.
		 */
				   
		if (name == "SLAB THE KILLER")
		{
			int beer =0;
			for (int i : diceRoll)
				beer = (diceRoll[i]==5) ? beer+1 :beer;
			if(beer!=0 && !jack)
				player.get(target).TakeDamage(4);
		}else
		{
			player.get(target).TakeDamage(2);
		}
		
		if(player.get(target).getCharacterName().equals("EL GRINGO"))
			indianArrow();
	}
	
	/**
	 * Method for the beer dice. If current player is at max health, then HP
	 * is added to the person with the highest favor. If not at max health 
	 * then add to his own. Also handles Jesse Jones ability.
	 */
	public void beer()
	{
		int health = player.get(currentPlayer).getHealth();
		int target;
		if(health < player.get(currentPlayer).getMaxHealth())
		{
			if (name == "JESSE JONES" && health < 4)
				player.get(currentPlayer).setHealth(2);
			player.get(currentPlayer).setHealth(1);
		}
		else
		{
			/*
			 * Find whoever has the most favor and give them health.
			 * Set target = to the index position.
			 */
			player.get(target).setHealth(1);
		}
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
	 * Constructor. Create Dice and rolls. Execute dice actions.
	 */
	public Turn()
	{
		//Object of Dice class. Contains the roll for this turn.
		Dice dices = new Dice();
		name = getName(currentPlayer);
		//Count of dynamite dices.
		int dynamite = 0;
		//Count of gatling dices.
		int gatling = 0;
		//See if Jack used his ability yet.
		boolean jack=false;
		//Sends player obj over. Check for Black Jack here.
		dices.setPlayer();
		//Sends sorted array of Dice values.
		diceRoll = dices.getRoll();
		
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
				bullsEyex1(jack);
				jack=true;
				break;
			case 4:
				bullsEyex2(jack);
				jack=true;
				break;
			case 5: 
				beer();
				break;
			case 6:
				gatling++;
				if(name == "WILLY THE KID" && gatling == 2)
					gatlingGun();
				else if(gatling>=3)
					gatlingGun();
				break;
			}
			
		}
		
	}
}
