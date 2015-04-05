package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class ROCKType extends Type{

    public ROCKType(){
        super.name = TypeEnum.ROCK;
        //Resistances
        super.resistMatrix[0][2] = 1;
        super.resistMatrix[2][2] = 1;
        super.resistMatrix[3][2] = 1;
        super.resistMatrix[9][2] = 1;
        //Weaknesses
        super.resistMatrix[1][4] = 1;
        super.resistMatrix[4][4] = 1;
        super.resistMatrix[8][4] = 1;
        super.resistMatrix[10][4] = 1;
        super.resistMatrix[11][4] = 1;
    }
}
