package com.example.billy.kilamonsta;

/**
 * Created by Billy on 7/21/2016.
 */
public class PlayerStats {
    private String playerName;
    private String playerStat;
    private String playerMonster;
    private String specialEventTag;
    private int statIcon;
    public PlayerStats(){
        this.playerName=null;
        this.playerStat=null;
        this.specialEventTag=null;
        this.statIcon=0;
    }
    public PlayerStats(String Name, String monster, String stat, int icon, String specialEventTag) {
        this.playerName =Name;
        this.playerMonster=monster;
        this.playerStat = stat;
        this.specialEventTag=specialEventTag;
        this.statIcon = 0;
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
    public int getStatIcon(){
        return statIcon;
    }
    public void setPlayerName(String playerName){
        this.playerName=playerName;
    }
    public void setPlayerMonster(String monster){this.playerMonster=monster;}
    public void setPlayerStat(String playerStat){
        this.playerStat=playerStat;
    }

    public void setStatIcon(int statIcon){
        this.statIcon=statIcon;
    }
    public void setSpecialEventTag(String specialEventTag){this.specialEventTag=specialEventTag;}
    public int getIcon(){
        int statIconId = this.getStatIcon();
        if (statIconId == 1){
            return R.drawable.boogeyman_icon;
        }
        else if(statIconId == 2){
            return R.drawable.werewolf_icon;
        }
        else if(statIconId == 3){
            return R.drawable.clown_icon;
        }
        else if(statIconId == 4){
            return R.drawable.zombie_icon;
        }
        else if(statIconId == 5){
            return R.drawable.spirit_icon;
        }

        return -1;
    }

}
