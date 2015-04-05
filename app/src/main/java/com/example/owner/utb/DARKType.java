package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class DARKType extends Type{

    public DARKType(){
        super.name = TypeEnum.DARK;
        //Resistances
        super.resistMatrix[7][2] = 1;
        super.resistMatrix[16][2] = 1;
        //Weaknesses
        super.resistMatrix[1][4] = 1;
        super.resistMatrix[6][4] = 1;
        super.resistMatrix[17][4] = 1;
        //No Effect
        super.resistMatrix[13][0] = 1;
    }
}
