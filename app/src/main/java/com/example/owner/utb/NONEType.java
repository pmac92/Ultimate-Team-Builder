package com.example.owner.utb;

/**
 * Created by Owner on 4/4/2015.
 */
public class NONEType extends Type{

    public NONEType (){

        super.name = TypeEnum.NONE;
        for (int m = 0; m < 18; m++) {
            for (int n = 0; n < 6; n++) {
                super.resistMatrix[m][n] = 0;
            }
        }
    }
}
