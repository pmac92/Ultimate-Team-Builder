package com.example.owner.utb;

/**
 * Created by Owner on 3/28/2015.
 */
public class Pokemon {

    Names name;
    int dexNum;
    TypeEnum type1;
    TypeEnum type2;
    AbilityEnum ab1;
    AbilityEnum ab2;
    AbilityEnum hiddenAb;
    int hp;
    int atk;
    int def;
    int spA;
    int spD;
    int spe;
    boolean canHaveItem;


    public Pokemon(Names name, int dexNum, TypeEnum type1, TypeEnum type2, AbilityEnum ab1, AbilityEnum ab2, AbilityEnum hiddenAb,
                   int hp, int atk, int def, int spA, int spD, int spe){

        this.name = name;
        this.dexNum = dexNum;
        this.type1 = type1;
        this.type2 = type2;
        this.ab1 = ab1;
        this.ab2 = ab2;
        this.hiddenAb = hiddenAb;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spA = spA;
        this.spD = spD;
        this.spe = spe;
    }

    @Override
    public String toString(){
        return name.toString();
    }
}
