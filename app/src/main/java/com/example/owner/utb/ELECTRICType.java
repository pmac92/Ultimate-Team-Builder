package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class ELECTRICType extends Type{

    public ELECTRICType(){
        super.name = TypeEnum.ELECTRIC;
        //Resistances
        super.resistMatrix[2][2] = 1;
        super.resistMatrix[8][2] = 1;
        super.resistMatrix[12][2] = 1;
        //Weaknesses
        super.resistMatrix[4][4] = 1;
    }
}
