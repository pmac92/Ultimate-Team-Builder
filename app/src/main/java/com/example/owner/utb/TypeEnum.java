package com.example.owner.utb;

import java.util.ArrayList;
import java.util.Arrays;

public enum TypeEnum {
	NORMAL,FIRE,WATER,ELECTRIC,GRASS,ICE,
	FIGHTING,POISON,GROUND,FLYING,PSYCHIC,BUG,
	ROCK,GHOST,DARK,STEEL,FAIRY, DRAGON, NONE;

   /* ArrayList<TypeEnum> normWeak = new ArrayList<TypeEnum>(Arrays.asList(TypeEnum.FIGHTING));
    ArrayList<TypeEnum> normResist = new ArrayList<TypeEnum>();
    ArrayList<TypeEnum> fireWeak = new ArrayList<TypeEnum>(Arrays.asList(TypeEnum.GROUND,TypeEnum.ROCK,TypeEnum.WATER));
    ArrayList<TypeEnum> fireResist = new ArrayList<TypeEnum>(Arrays.asList(TypeEnum.BUG,TypeEnum.FAIRY,TypeEnum.FIRE,TypeEnum.GRASS,TypeEnum.ICE,TypeEnum.STEEL));
    ArrayList<TypeEnum> waterWeak = new ArrayList<TypeEnum>(Arrays.asList(TypeEnum.ELECTRIC,TypeEnum.GRASS));
    ArrayList<TypeEnum> waterResist = new ArrayList<TypeEnum>(Arrays.asList(TypeEnum.FIRE,TypeEnum.ICE,TypeEnum.STEEL,TypeEnum.WATER));

    public ArrayList<TypeEnum> getWeaknesses(TypeEnum type){
        if(type == TypeEnum.NORMAL) return normWeak;
        else if(type == TypeEnum.WATER) return waterWeak;
        else return fireWeak;
    }*/
}
