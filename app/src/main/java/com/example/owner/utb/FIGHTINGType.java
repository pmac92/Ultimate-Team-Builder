package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class FIGHTINGType extends Type {

    public FIGHTINGType(){
        super.name = TypeEnum.FIGHTING;
        //Resistances
        super.resistMatrix[5][2] = 1;
        super.resistMatrix[6][2] = 1;
        super.resistMatrix[16][2] = 1;
        //Weaknesses
        super.resistMatrix[2][4] = 1;
        super.resistMatrix[13][4] = 1;
        super.resistMatrix[17][4] = 1;
    }
}
