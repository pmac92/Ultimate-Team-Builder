package com.example.owner.utb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 5/4/2015.
 */
public class PokeListAdapter extends ArrayAdapter<Pokemon> {

    ImageView type1;
    ImageView type2;
    
    public PokeListAdapter(Context context, ArrayList<Pokemon> pokemon) {
        super(context, 0, pokemon);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pokemon pokemon = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokelistview, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView stats = (TextView) convertView.findViewById(R.id.stats);

        type1 = (ImageView) convertView.findViewById(R.id.type1);
        type2 = (ImageView) convertView.findViewById(R.id.type2);

        // Populate the data into the template view using the data object
        name.setText(pokemon.name.toString());
        stats.setText(pokemon.hp + "/" + pokemon.atk +"/" + pokemon.def + "/" + pokemon.spA +"/" +
                pokemon.spD + "/" + pokemon.spe);

        setType(pokemon.type1, type1);
        setType(pokemon.type2, type2);

        // Return the completed view to render on screen
        return convertView;
    }


    private void setType(Type type, ImageView set){
        if(type.name == TypeEnum.BUG) set.setBackgroundResource(R.drawable.bug);
        else if(type.name == TypeEnum.DARK) set.setBackgroundResource(R.drawable.dark);
        else if(type.name == TypeEnum.DRAGON) set.setBackgroundResource(R.drawable.dragon);
        else if(type.name == TypeEnum.ELECTRIC) set.setBackgroundResource(R.drawable.electric);
        else if(type.name == TypeEnum.FAIRY) set.setBackgroundResource(R.drawable.fairy);
        else if(type.name == TypeEnum.FIRE) set.setBackgroundResource(R.drawable.fire);
        else if(type.name == TypeEnum.FIGHTING) set.setBackgroundResource(R.drawable.fighting);
        else if(type.name == TypeEnum.FLYING) set.setBackgroundResource(R.drawable.flying);
        else if(type.name == TypeEnum.GHOST) set.setBackgroundResource(R.drawable.ghost);
        else if(type.name == TypeEnum.GRASS) set.setBackgroundResource(R.drawable.grass);
        else if(type.name == TypeEnum.GROUND) set.setBackgroundResource(R.drawable.ground);
        else if(type.name == TypeEnum.ICE) set.setBackgroundResource(R.drawable.ice);
        else if(type.name == TypeEnum.NORMAL) set.setBackgroundResource(R.drawable.normal);
        else if(type.name == TypeEnum.POISON) set.setBackgroundResource(R.drawable.poison);
        else if(type.name == TypeEnum.PSYCHIC) set.setBackgroundResource(R.drawable.psychic);
        else if(type.name == TypeEnum.ROCK) set.setBackgroundResource(R.drawable.rock);
        else if(type.name == TypeEnum.STEEL) set.setBackgroundResource(R.drawable.steel);
        else if(type.name == TypeEnum.WATER) set.setBackgroundResource(R.drawable.water);
        else set.setBackgroundResource(R.drawable.none);

    }
}
