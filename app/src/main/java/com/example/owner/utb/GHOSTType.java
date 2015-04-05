package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class GHOSTType extends Type{

    public GHOSTType(){
        super.name = TypeEnum.GHOST;
        //Resistances
        super.resistMatrix[3][2] = 1;
        super.resistMatrix[6][2] = 1;
        //Weaknesses
        super.resistMatrix[7][4] = 1;
        super.resistMatrix[16][4] = 1;
        //No Effect
        super.resistMatrix[0][0] = 1;
        super.resistMatrix[1][0] = 1;
    }
}
