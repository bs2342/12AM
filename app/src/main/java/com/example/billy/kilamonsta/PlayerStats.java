package com.example.billy.kilamonsta;

/**
 * Created by Billy on 7/21/2016.
 */
public class PlayerStats {
    private String playerName;
    private String playerStat;
    private String playerMonster;
    private String specialEventTag;
    private String statIcon;
    private int ID;
    public PlayerStats(){
        this.playerName=null;
        this.playerStat=null;
        this.specialEventTag=null;
        this.statIcon=null;
        this.ID = -1;
    }
    public PlayerStats(String Name, String monster, String stat, String icon, String specialEventTag, int ID) {
        this.playerName =Name;
        this.playerMonster=monster;
        this.playerStat = stat;
        this.specialEventTag=specialEventTag;
        this.statIcon = icon;
        this.ID = ID;
    }

    public String getPlayerName(){
        return playerName;
    }
    public String getPlayerMonster() {return playerMonster;}
    public String getPlayerStat(){
        return playerStat;
    }
    public String getSpecialEventTag(){
        return specialEventTag;
    }
    public String getStatIcon(){
        return statIcon;
    }
    public int getID(){return ID;}
    public void setPlayerName(String playerName){
        this.playerName=playerName;
    }
    public void setPlayerMonster(String monster){this.playerMonster=monster;}
    public void setPlayerStat(String playerStat){
        this.playerStat=playerStat;
    }
    public void setPlayerID(int ID){this.ID=ID;}

    public void setStatIcon(String statIcon){
        this.statIcon=statIcon;
    }
    public void setSpecialEventTag(String specialEventTag){this.specialEventTag=specialEventTag;}
    public static int getIcon(String statIconId){
        String statIcon = statIconId.toLowerCase();
        if (statIcon.equals("boogeyman")){
            return R.drawable.boogeyman_icon;
        }
        else if(statIcon.equals("werewolf")){
            return R.drawable.werewolf_icon;
        }
        else if(statIcon.equals("clown")){
            return R.drawable.clown_icon;
        }
        else if(statIcon.equals("zombie")){
            return R.drawable.zombie_icon;
        }
        else if(statIcon.equals("spirit")){
            return R.mipmap.ic_launcher;//drawable.spirit_icon;
        }
        else{
            return R.mipmap.ic_launcher;//drawable.spirit_icon;
        }
        //return -1;
    }

}
