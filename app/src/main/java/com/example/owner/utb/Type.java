package com.example.owner.utb;

import java.util.ArrayList;

/**
 * Created by Owner on 4/3/2015.
 */
public class Type {

    TypeEnum name;
    public int [][] resistMatrix= new int [18][6];

    //Norm 0, Fight 1, Fly 2, Poison 3, Ground 4, Rock 5, Bug 6, Ghost 7, Steel 8, Fire 9,
    //Water 10, Grass 11, Elec 12, Psy 13, Ice 14, Dragon 15, Dark 16, Fairy 17
/*    public int [] noEffect = new int[18];
    public int [] resist = new int[18];
    public int [] weak = new int[18];*/

/*    public Type(TypeEnum name, int[] noEffect, int [] resist, int [] weak){
        this.name = name;
        this.noEffect = noEffect;
        this.resist = resist;
        this.weak = weak;
    }*/

    public Type()
    {

    }
}
