package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class POISONType extends Type{

    public POISONType(){
        super.name = TypeEnum.POISON;
        //Resistances
        super.resistMatrix[1][2] = 1;
        super.resistMatrix[3][2] = 1;
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[11][2] = 1;
        super.resistMatrix[17][2] = 1;
        //Weaknesses
        super.resistMatrix[4][4] = 1;
        super.resistMatrix[13][4] = 1;
    }
}
