package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class STEELType extends Type{

    public STEELType(){
        super.name = TypeEnum.STEEL;
        //Resistances
        super.resistMatrix[0][2] = 1;
        super.resistMatrix[2][2] = 1;
        super.resistMatrix[5][2] = 1;
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[8][2] = 1;
        super.resistMatrix[11][2] = 1;
        super.resistMatrix[13][2] = 1;
        super.resistMatrix[14][2] = 1;
        super.resistMatrix[15][2] = 1;
        super.resistMatrix[17][2] = 1;
        //Weaknesses
        super.resistMatrix[1][4] = 1;
        super.resistMatrix[4][4] = 1;
        super.resistMatrix[9][4] = 1;
        //No Effect
        super.resistMatrix[3][0] = 1;
    }
}
