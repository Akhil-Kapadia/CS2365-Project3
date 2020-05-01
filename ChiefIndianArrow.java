package cs2365_project3;

public class ChiefIndianArrow 
{
    private String Owner;
    
    public void TakeArrow(String Name)
    {
        if(Owner.equals(""))
        {
            Owner = Name;
        }
    }
    
    public void UseArrow()
    {
        Owner = "";
    }
}
