package cs2365_project3;

/**
 * Chief Indian Arrow class just tracks who holds the Chief Indian Arrow
 * @author Demetrios Mihaltses
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
        }
    }
    
    /**
    * Method for when the Chief Indian Arrow is used, this method clears the 
    * Ownership
    * @return Owner String, The Name of the Current Owner
    */
    public String UseArrow()
    {
        return Owner;
    }
}
