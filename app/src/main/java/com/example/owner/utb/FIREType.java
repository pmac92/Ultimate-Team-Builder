package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class FIREType extends Type{

    public FIREType(){
        super.name = TypeEnum.FIRE;
        //Resistances
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[8][2] = 1;
        super.resistMatrix[11][2] = 1;
        super.resistMatrix[14][2] = 1;
        super.resistMatrix[17][2] = 1;
        //Weaknesses
        super.resistMatrix[4][4] = 1;
        super.resistMatrix[5][4] = 1;
        super.resistMatrix[10][4] = 1;
    }
}
