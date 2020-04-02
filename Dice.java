
public class Dice {

	/*
	 * Short Description on all dice types. Remember that Dice roles have a 
	 * a priority in which they must be used / played.
	 * 1. Arrow dice (take an arrow from the pile) (automatic reroll) Resolved
	 * immediately)
	 * 2. Dynamite dice (if >= 3 dynamite blow up) (cannot reroll)
	 * 3. Shoot person one over left or right
	 * 4. Shoot person two over, left or right
	 * 5. Beer dice (heal). Can not go above max HP
	 * 6. Gatling dice (if >= 3 gatling shoot) 
	 * 
	 */
	
	//Randomly assigned number that corresponds to dice. #s listed above.
	private int dice_index;
	
	public void setDice(int dice)
	{
		dice_index = dice;
	}
	
	/**
	 * Method Gets the textual Face value of the dice, given that setDice has 
	 * already been done.
	 * 
	 * @return String Provides Dice Face Value
	 */
	public String getDiceFace()
	{
		String diceName[] = {"Indian Arrow", "Dynamite", "Bull's Eye x1",
							"Bull's Eye x2", "Beer", "Gatling Gun"};
		return diceName[dice_index-1];
	}
	
	/**
	 * Method returns the dice index ranging from 1 to 6
	 * @return Integer Range: 1<=Dice_index<=6
	 */
	public int getDice()
	{
		return dice_index;
	}
	
	/**
	 * Method requires that prior conditions are already handled in game class.
	 *  IE means that rerolls have already  been done and they're are none left.
	 *  Also means that we haven't run out of arrow cards yet.
	 * @param obj	Object of the Player class. 
	 */
	public void indianArrow(Player obj)
	{
		//Missing set methods. Also Demetrios fix your naming conventions
		obj.setArrowCount(obj.getArrowCount()+1);
	}
	/**
	 * Method is called when >=3 dynamite die are rolled. Requires that other 
	 * conditions be met else where.
	 * Character that affect this Dice: Black Jack.
	 * @param obj	Player Object of the Player class
	 */
	public void dynamite(Player obj)
	{
		//Missing methods again
		obj.setHealth(obj.getHealth()-1);
		if (obj.getCharacter()=="")
	}
	
	/**
	 * Method requires that the player obj to the r/l of the current player be 
	 * passed as a parameter. Character that affect this dice are: Rose Dolan
	 * Slab the Killer, Calamity Janet.
	 * @param obj Object of the player Class
	 */
	public void bullsEyeOne(Player obj)
	{
		obj.setHealth(obj.getHealth()-1);
	}
	
	
 
	//Constructor Class
	public Dice()
	{

	}
}
