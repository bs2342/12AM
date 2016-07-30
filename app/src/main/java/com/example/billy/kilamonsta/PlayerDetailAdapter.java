package com.example.billy.kilamonsta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Billy on 7/20/2016.
 */
public class PlayerDetailAdapter extends BaseAdapter {

    public static class ViewHolder {
        public ImageView iconView;
        public TextView playerNameView;
        public TextView playerMonsterView;
        public TextView playerStatView;
        public TextView playerSpecialEventView;


 /*       public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.player_stat_icon);
            playerNameView = (TextView) view.findViewById(R.id.player_name);
            playerStatView = (TextView) view.findViewById(R.id.player_status);
        }*/
    }

    private ArrayList<PlayerStats> listData;
    LayoutInflater layoutInflater;

    public PlayerDetailAdapter(Context context, ArrayList<PlayerStats> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listData.size();
    }

    public Object getItem(int position) {
        return this.listData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.player_stats_view, parent, false);
            viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        }
       else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.iconView=(ImageView)convertView.findViewById(R.id.player_stat_icon);
        viewHolder.playerNameView=(TextView)convertView.findViewById(R.id.player_name);
        viewHolder.playerMonsterView=(TextView)convertView.findViewById(R.id.player_monster);
        viewHolder.playerStatView=(TextView)convertView.findViewById(R.id.player_status);
        viewHolder.playerSpecialEventView =(TextView)convertView.findViewById(R.id.special_event);

        viewHolder.playerNameView.setText(listData.get(position).getPlayerName());
        viewHolder.playerMonsterView.setText(listData.get(position).getPlayerMonster());
        viewHolder.playerStatView.setText(listData.get(position).getPlayerStat());
        viewHolder.playerSpecialEventView.setText(listData.get(position).getSpecialEventTag());

        viewHolder.iconView.setImageResource(listData.get(position).getIcon());
        //if player is dead then make row transparent
        if(listData.get(position).getPlayerStat().equals("dead")){
            //iconView.setAlpha(0.5f);
            convertView.setAlpha(0.2f);
        }else if(listData.get(position).getPlayerStat().equals("infected")){
            viewHolder.iconView.setColorFilter(0xAAA0Af00);//, PorterDuff.Mode.LIGHTEN);
        }

        return convertView;
    }
}
