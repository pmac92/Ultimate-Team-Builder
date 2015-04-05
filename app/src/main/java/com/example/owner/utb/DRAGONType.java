package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class DRAGONType extends Type{

    public DRAGONType(){
        super.name = TypeEnum.DRAGON;
        //Resistances
        super.resistMatrix[9][2] = 1;
        super.resistMatrix[10][2] = 1;
        super.resistMatrix[11][2] = 1;
        //Weaknesses
        super.resistMatrix[14][4] = 1;
        super.resistMatrix[15][4] = 1;
        super.resistMatrix[17][4] = 1;
    }
}
