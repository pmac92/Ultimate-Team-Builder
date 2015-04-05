package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class WATERType extends Type{

    public WATERType(){
        super.name = TypeEnum.WATER;
        //Resistances
        super.resistMatrix[8][2] = 1;
        super.resistMatrix[9][2] = 1;
        super.resistMatrix[10][2] = 1;
        super.resistMatrix[14][2] = 1;
        //Weaknesses
        super.resistMatrix[11][4] = 1;
        super.resistMatrix[12][4] = 1;
    }
}
