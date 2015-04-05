package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class GROUNDType extends Type{

    public GROUNDType(){
        super.name = TypeEnum.GROUND;
        //Resistances
        super.resistMatrix[3][2] = 1;
        super.resistMatrix[5][2] = 1;

        //Weaknesses
        super.resistMatrix[10][4] = 1;
        super.resistMatrix[11][4] = 1;
        super.resistMatrix[14][4] = 1;

        //No Effect
        super.resistMatrix[12][0] = 1;
    }
}