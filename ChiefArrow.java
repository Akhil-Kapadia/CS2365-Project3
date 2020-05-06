package cs2365_project3;

/**
 * The Chief Arrow class is responsible for assigning the Chief Arrow to a player.
 * @author Demetrios Mihaltses
 * Collaborators: Jacob Strickland
 */
public class ChiefArrow 
{
    //A String for the character Name of who holds the Chief Indian Arrow
    private String Owner = "";
    
    /**
    * Method that set's the ownership of the Arrow
    * @param Name String, The Name of the New Owner
    */
    public void TakeArrow(String Name)
    {
        if(Owner.equals(""))
        {
            Owner = Name;
            //System.out.println(Name + " is now the owner of the chief arrow.\n");
        }
    }
    
    /**
    * Method to check who owns the Chief Arrow
    * @return Owner String, The Name of the Current Owner
    */
    public String UseArrow()
    {
        return Owner;
    }
}
