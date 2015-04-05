package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class FAIRYType extends Type{

    public FAIRYType(){
        super.name = TypeEnum.FAIRY;
        //Resistances
        super.resistMatrix[1][2] = 1;
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[16][2] = 1;
        //Weaknesses
        super.resistMatrix[3][4] = 1;
        super.resistMatrix[8][4] = 1;
        //No Effect
        super.resistMatrix[15][0] = 1;
    }
}
