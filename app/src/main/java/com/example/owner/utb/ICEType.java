package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class ICEType extends Type{

    public ICEType(){
        super.name = TypeEnum.ICE;
        //Resistances
        super.resistMatrix[14][2] = 1;
        //Weaknesses
        super.resistMatrix[1][4] = 1;
        super.resistMatrix[5][4] = 1;
        super.resistMatrix[8][4] = 1;
        super.resistMatrix[9][4] = 1;
    }
}
