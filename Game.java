package cs2365_project3;

import java.util.Random;

public class Game {
    Random rand = new Random();
    
    int sher = 1;
    int depu, outl, reneg;
    int roles[] = {sher, reneg, outl, depu};
    
    private int totalPlayers;
    private ArrayList<Player> tableSeating = new ArrayList<>(total_players);
    
    public void setTotalPlayers()
    {
        this.totalPlayers = rand.nextInt(5) + 4;
    }
    
    public int getTotalPlayers()
    {
        return this.totalPlayers;
    }
    
    public void gameSetup()
    {
        //dice creation
        for(int d = 0; d < 5; d++)
            dice[d] = createDice(); //this will change based on how the dice code works
        
        //player creation
        setTotalPlayers();
        switch (getTotalPlayers()) { //number of roles changes based on number of players
            case 4:
                roles[1] = 1;
                roles[2] = 2;
                roles[3] = 0;
                reneg = 1;
                outl = 2;
                depu = 0;
                break;
            case 5:
                roles[1] = 1;
                roles[2] = 2;
                roles[3] = 1;
                reneg = 1;
                outl = 2;
                depu = 1;
                break;
            case 6:
                roles[1] = 1;
                roles[2] = 3;
                roles[3] = 1;
                reneg = 1;
                outl = 3;
                depu = 1;
                break;
            case 7:
                roles[1] = 1;
                roles[2] = 3;
                roles[3] = 2;
                reneg = 1;
                outl = 3;
                depu = 2;
                break;
            default:
                roles[1] = 2;
                roles[2] = 3;
                roles[3] = 2;
                reneg = 2;
                outl = 3;
                depu = 2;
                break;
        }
        
        for(int index = 0; index < getTotalPlayers(); index++) //add all the players to the list
            tableSeating.add(createPlayer(index));
    }
    
    public Player createPlayer(int index)
    {
        Player newPlayer = new Player();
        
        int role = rand.nextInt(5);
        for(;;) //loops infintely until you can add that role into the game
        {
            if(role == 0 && sher != 0) //if you can add a sheriff
                break;       
            if(role == 1 && reneg != 0) //if you can add a renegade
                break;        
            if(role == 2 && outl != 0) //if you can add a outlaw
                break;        
            if(role == 3 && depu != 0) //if you can add a deputy
                break;
            
            role = rand.nextInt(5); //nothing could be added so generate a new number
        }
        newPlayer.setRole(role); //valid role has been found, give it to the player
        
        int HP = rand.nextInt(4) + 6; //set hp to random value for now, will change based on character
        
         switch (role){
            case 0: //player is a sheriff 
                    newPlayer.setHealth(HP + 2); //sheriff gets boost to max HP
                    newPlayer.setArrowCount(0);
                    newPlayer.setIndex(index);
                    sher--; //decrememnt the number of avalible spots for that role
                    break;
            case 1: //player is a renegade
                    newPlayer.setHealth(HP);
                    newPlayer.setArrowCount(0);
                    newPlayer.setIndex(index);
                    reneg--;
                    break;
            case 2: //player is an outlaw 
                    newPlayer.setHealth(HP);
                    newPlayer.setArrowCount(0);
                    newPlayer.setIndex(index);
                    outl--;
                    break;
            case 3: //player is a deputy 
                    newPlayer.setHealth(HP);
                    newPlayer.setArrowCount(0);
                    newPlayer.setIndex(index);
                    depu--;
                    break;
            default: 
                    break;
        }
    
        return newPlayer;
    }
    
    public void createDice()
    {
        
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
    
    public boolean checkGameOver(Player deadPlayer)
    {
        if(winCondition() != 0)
            return true; //game is over
        else
            return false; //game is not over
    }
    
    public boolean checkPlayerDeath(Player damagedPlayer)
    {
        if(damagedPlayer.getHealth() == 0)
        {
            tableSeating.remove(new Player(damagedPlayer)); //remove the player from the seating
            return true;
        }
        else
            return false;
    }
    
    public String checkPlayerRole(Player player)
    {
        return player.getRole();
    }
}
