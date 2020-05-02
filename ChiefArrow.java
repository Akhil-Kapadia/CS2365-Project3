package cs2365_project3;

public class ChiefArrow 
{
    private String Owner = "";
    
    public void TakeArrow(String Name)
    {
        if(Owner.equals(""))
        {
            Owner = Name;
        }
    }
    
    public String UseArrow()
    {
        return Owner;
    }
}
