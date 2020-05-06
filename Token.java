package cs2365_project3;

import java.util.Random;

/**
 * Token class that creates a list of 4 possible tokens beer, shoot1, shoot2, and dynamite 
 * @author 
 */
public class Token {
    Random rand = new Random();
    
    private int[] tokenList = {0,0,0,0};
    
    /**
     * Method that creates the tokens and adds them to tokenList array
     */
    public void createTokens()
    {
        int beer = 3;
        int shootOne = 5;
        int shootTwo = 5;
        int dynamite = 2;
        this.tokenList[0] = beer;
        this.tokenList[1] = shootOne;
        this.tokenList[2] = shootTwo;
        this.tokenList[3] = dynamite;
    }
    
    /**
     * Method to randomly chose a token then remove it from the list 
     * @return randomIndex integer
     */
    public int getToken()
    {
        int randomIndex = rand.nextInt(4);
        for(;;)
        {
            if(this.tokenList[randomIndex] > 0)
            {
                this.tokenList[randomIndex]--;
                break;
            }
            else
                randomIndex = rand.nextInt(4);
        }
        
        return randomIndex;
    }
    
    /**
     * Method adds tokens to token array
     * @param tokens integer array 
     */
    public void addToken(int[] tokens)
    {
        this.tokenList[0]+=tokens[0];
        this.tokenList[1]+=tokens[1];
        this.tokenList[2]+=tokens[2];
        this.tokenList[3]+=tokens[3];
    }
    
}
