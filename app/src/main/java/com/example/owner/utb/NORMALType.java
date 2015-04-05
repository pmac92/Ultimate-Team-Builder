package com.example.owner.utb;

/**
 * Created by Owner on 4/3/2015.
 */
public class NORMALType extends Type {

    public NORMALType(){
        super.name = TypeEnum.NORMAL;
        super.resistMatrix[1][4] = 1;
        super.resistMatrix[7][0] = 1;
    }
}
