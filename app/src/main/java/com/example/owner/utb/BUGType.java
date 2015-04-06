package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class BUGType extends Type{

    public BUGType(){
        super.name = TypeEnum.BUG;
        //Resistances
        super.resistMatrix[1][2] = 1;
        super.resistMatrix[4][2] = 1;
        super.resistMatrix[11][2] = 1;
        //Weaknesses
        super.resistMatrix[2][4] = 1;
        super.resistMatrix[5][4] = 1;
        super.resistMatrix[9][4] = 1;
    }
}
