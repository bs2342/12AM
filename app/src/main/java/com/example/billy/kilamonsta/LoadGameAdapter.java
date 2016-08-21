package com.example.billy.kilamonsta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Billy on 8/17/2016.
 */
public class LoadGameAdapter extends BaseAdapter {

    public static class ViewHolder {
        public TextView game_name_view;
        public TextView player_name_view;
    }

    private ArrayList<String> game_names, player_names;
    LayoutInflater layoutInflater;

    public LoadGameAdapter(Context context, ArrayList<String> game_names, ArrayList<String> player_names) {
        this.game_names = game_names;
        this.player_names = player_names;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return game_names.size();
    }

    public Object getItem(int position) {
        return this.game_names.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.load_game_view, parent, false);
            viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.game_name_view=(TextView)convertView.findViewById(R.id.game_name_value);
        viewHolder.player_name_view =(TextView)convertView.findViewById(R.id.player_names);

        viewHolder.game_name_view.setText(game_names.get(position));
        viewHolder.player_name_view.setText(player_names.get(position));

       /* viewHolder.iconView.setImageResource(listData.get(position).getIcon());
        //if player is dead then make row transparent
        if(listData.get(position).getPlayerStat().equals("dead")){
            //iconView.setAlpha(0.5f);
            convertView.setAlpha(0.2f);
        }else if(listData.get(position).getPlayerStat().equals("infected")){
            viewHolder.iconView.setColorFilter(0xAAA0Af00);//, PorterDuff.Mode.LIGHTEN);
        }*/

        return convertView;
    }
}
