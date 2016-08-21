package com.example.billy.kilamonsta;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Billy on 7/20/2016.
 */
public class PlayerDetailAdapter extends CursorAdapter {

    public static class ViewHolder {
        public ImageView iconView;
        public TextView playerNameView;
        public TextView playerMonsterView;
        public TextView playerStatView;
        public TextView playerSpecialEventView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.player_stat_icon);
            playerNameView = (TextView) view.findViewById(R.id.player_name);
            playerMonsterView=(TextView)view.findViewById(R.id.player_monster);
            playerSpecialEventView =(TextView)view.findViewById(R.id.special_event);
            playerStatView = (TextView) view.findViewById(R.id.player_status);
        }
    }

    private ArrayList<PlayerStats> listData;
    LayoutInflater layoutInflater;

    public PlayerDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

/*    public int getCount() {
        return listData.size();
    }*/

/*    public Object getItem(int position) {
        return this.listData.get(position);
    }*/

    public long getItemId(int position) {
        return position;
    }


    public void bindView(View view, Context context, Cursor cursor){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());

        String name = cursor.getString(GameBoardFragment.COL_NAME);
        viewHolder.playerNameView.setText(name);

        String monster = cursor.getString(GameBoardFragment.COL_MONSTER);
        viewHolder.playerMonsterView.setText(monster);

        String status = cursor.getString(GameBoardFragment.COL_STATUS);
        viewHolder.playerStatView.setText(status);

        String icon = cursor.getString(GameBoardFragment.COL_ICON);
        viewHolder.iconView.setImageResource(PlayerStats.getIcon(icon));

        String event_tag = cursor.getString(GameBoardFragment.COL_EVENT_TAG);
        viewHolder.playerSpecialEventView.setText(event_tag);

        if(status.equals("dead")){
            //iconView.setAlpha(0.5f);
            view.setAlpha(0.2f);
        }else if(status.equals("infected")){
            viewHolder.iconView.setColorFilter(0xAAA0Af00);//, PorterDuff.Mode.LIGHTEN);
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = R.layout.player_stats_view;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }











/*    @Override
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
    }*/
}
