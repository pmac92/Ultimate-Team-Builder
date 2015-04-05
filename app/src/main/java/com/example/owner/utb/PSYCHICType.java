package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class PSYCHICType extends Type{

    public PSYCHICType(){
        super.name = TypeEnum.PSYCHIC;
        //Resistances
        super.resistMatrix[1][2] = 1;
        super.resistMatrix[13][2] = 1;

        //Weaknesses
        super.resistMatrix[6][4] = 1;
        super.resistMatrix[7][4] = 1;
        super.resistMatrix[16][4] = 1;
    }
}
