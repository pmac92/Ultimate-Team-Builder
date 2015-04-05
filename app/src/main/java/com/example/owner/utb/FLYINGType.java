package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class FLYINGType extends Type{

    public FLYINGType(){
        super.name = TypeEnum.FLYING;
        //Resistances
        super.resistMatrix[1][2] = 1;
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[11][2] = 1;
        //Weaknesses
        super.resistMatrix[5][4] = 1;
        super.resistMatrix[12][4] = 1;
        super.resistMatrix[14][4] = 1;
        //No Effect
        super.resistMatrix[4][0] = 1;
    }
}
