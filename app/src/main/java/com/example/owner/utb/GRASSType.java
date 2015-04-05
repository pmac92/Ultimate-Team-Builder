package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class GRASSType extends Type{

    public GRASSType(){
        super.name = TypeEnum.GRASS;
        //Resistances
        super.resistMatrix[4][2] = 1;
        super.resistMatrix[10][2] = 1;
        super.resistMatrix[11][2] = 1;
        super.resistMatrix[12][2] = 1;
        //Weaknesses
        super.resistMatrix[2][4] = 1;
        super.resistMatrix[3][4] = 1;
        super.resistMatrix[6][4] = 1;
        super.resistMatrix[9][4] = 1;
        super.resistMatrix[14][4] = 1;
    }
}
