package com.example.owner.utb;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    public ArrayList<Pokemon> pokeList = new ArrayList<Pokemon>();
    ArrayAdapter<Pokemon> arrayAdapter;
    private ListView lv;
    EditText inputSearch;
    ImageButton activeSlot;
    int activeSlotNum;
    Pokemon selectedPokemon;
    Pokemon[] team = new Pokemon[6];

    BUGType BUG = new BUGType();
    DARKType DARK = new DARKType();
    DRAGONType DRAGON = new DRAGONType();
    ELECTRICType ELECTRIC = new ELECTRICType();
    FAIRYType FAIRY = new FAIRYType();
    FIGHTINGType FIGHTING = new FIGHTINGType();
    FIREType FIRE = new FIREType();
    FLYINGType FLYING = new FLYINGType();
    GHOSTType GHOST = new GHOSTType();
    GRASSType GRASS = new GRASSType();
    GROUNDType GROUND = new GROUNDType();
    ICEType ICE = new ICEType();
    NORMALType NORMAL = new NORMALType();
    POISONType POISON = new POISONType();
    PSYCHICType PSYCHIC = new PSYCHICType();
    ROCKType ROCK = new ROCKType();
    STEELType STEEL = new STEELType();
    WATERType WATER = new WATERType();
    NONEType NONE = new NONEType();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createEmptyTeam();

        lv = (ListView) findViewById(R.id.pokeList);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.setText("Search");
        initList();

         arrayAdapter = new ArrayAdapter<Pokemon>(
                this,
                android.R.layout.simple_list_item_1,
                pokeList);

        lv.setAdapter(arrayAdapter);
        lv.setTextFilterEnabled(true);


        //Slot0
        final ImageButton slot0 = (ImageButton) findViewById(R.id.slot0);
        slot0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot0);
                activeSlotNum = 0;
                initSearch();
            }
        });

        //Slot1
        final ImageButton slot1 = (ImageButton) findViewById(R.id.slot1);
        slot1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot1);
                activeSlotNum = 1;
                initSearch();
            }
        });

        //Slot2
        final ImageButton slot2 = (ImageButton) findViewById(R.id.slot2);
        slot2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot2);
                activeSlotNum = 2;
                initSearch();
            }
        });

        //Slot3
        final ImageButton slot3 = (ImageButton) findViewById(R.id.slot3);
        slot3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot3);
                activeSlotNum = 3;
                initSearch();
            }
        });

        //Slot4
        final ImageButton slot4 = (ImageButton) findViewById(R.id.slot4);
        slot4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot4);
                activeSlotNum = 4;
                initSearch();
            }
        });

        //Slot5
        final ImageButton slot5 = (ImageButton) findViewById(R.id.slot5);
        slot5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activeSlot = (ImageButton) findViewById(R.id.slot5);
                activeSlotNum = 5;
                initSearch();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //String number = ((TextView)view).getText().toString();
                //activeSlot.setBackgroundResource(R.drawable.pokeball2);

                //Select pokemon from list
                selectedPokemon = (Pokemon) parent.getAdapter().getItem(position);
                String picName = selectedPokemon.name.toString().toLowerCase();

                //Add selected pokemon to team list
                team[activeSlotNum] = selectedPokemon;

                findViewById(R.id.pokeList).setVisibility(View.GONE);
                findViewById(R.id.inputSearch).setVisibility(View.GONE);
                findViewById(R.id.resistChart).setVisibility(View.VISIBLE);

                typeEval();

                //Set selected pokemon's image
                Resources r = getResources();
                int picId = r.getIdentifier(picName, "drawable", "com.example.owner.utb");
                activeSlot.setBackgroundResource(picId);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initSearch(){
        findViewById(R.id.pokeList).setVisibility(View.VISIBLE);
        findViewById(R.id.inputSearch).setVisibility(View.VISIBLE);
        findViewById(R.id.resistChart).setVisibility(View.GONE);


        //Search Bar
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.arrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void typeEval(){

        int [][] teamResistMatrix = new int[18] [6];
        String[][] stringMatrix = new String [18][6];

        //Table Slots

        TextView normZero = (TextView) findViewById(R.id.normZero);
        TextView normFourth = (TextView) findViewById(R.id.normFourth);
        TextView normHalf = (TextView) findViewById(R.id.normHalf);
        TextView normOne = (TextView) findViewById(R.id.normOne);
        TextView normTwo = (TextView) findViewById(R.id.normTwo);
        TextView normFour = (TextView) findViewById(R.id.normFour);

        TextView fightZero = (TextView) findViewById(R.id.fightZero);
        TextView fightFourth = (TextView) findViewById(R.id.fightFourth);
        TextView fightHalf = (TextView) findViewById(R.id.fightHalf);
        TextView fightOne = (TextView) findViewById(R.id.fightOne);
        TextView fightTwo = (TextView) findViewById(R.id.fightTwo);
        TextView fightFour = (TextView) findViewById(R.id.fightFour);

        TextView flyZero = (TextView) findViewById(R.id.flyZero);
        TextView flyFourth = (TextView) findViewById(R.id.flyFourth);
        TextView flyHalf = (TextView) findViewById(R.id.flyHalf);
        TextView flyOne = (TextView) findViewById(R.id.flyOne);
        TextView flyTwo = (TextView) findViewById(R.id.flyTwo);
        TextView flyFour = (TextView) findViewById(R.id.flyFour);

        TextView psnZero = (TextView) findViewById(R.id.psnZero);
        TextView psnFourth = (TextView) findViewById(R.id.psnFourth);
        TextView psnHalf = (TextView) findViewById(R.id.psnHalf);
        TextView psnOne = (TextView) findViewById(R.id.psnOne);
        TextView psnTwo = (TextView) findViewById(R.id.psnTwo);
        TextView psnFour = (TextView) findViewById(R.id.psnFour);

        TextView grndZero = (TextView) findViewById(R.id.grndZero);
        TextView grndFourth = (TextView) findViewById(R.id.grndFourth);
        TextView grndHalf = (TextView) findViewById(R.id.grndHalf);
        TextView grndOne = (TextView) findViewById(R.id.grndOne);
        TextView grndTwo = (TextView) findViewById(R.id.grndTwo);
        TextView grndFour = (TextView) findViewById(R.id.grndFour);

        TextView rockZero = (TextView) findViewById(R.id.rockZero);
        TextView rockFourth = (TextView) findViewById(R.id.rockFourth);
        TextView rockHalf = (TextView) findViewById(R.id.rockHalf);
        TextView rockOne = (TextView) findViewById(R.id.rockOne);
        TextView rockTwo = (TextView) findViewById(R.id.rockTwo);
        TextView rockFour = (TextView) findViewById(R.id.rockFour);

        TextView ghostZero = (TextView) findViewById(R.id.ghostZero);
        TextView ghostFourth = (TextView) findViewById(R.id.ghostFourth);
        TextView ghostHalf = (TextView) findViewById(R.id.ghostHalf);
        TextView ghostOne = (TextView) findViewById(R.id.ghostOne);
        TextView ghostTwo = (TextView) findViewById(R.id.ghostTwo);
        TextView ghostFour = (TextView) findViewById(R.id.ghostFour);

        TextView steelZero = (TextView) findViewById(R.id.steelZero);
        TextView steelFourth = (TextView) findViewById(R.id.steelFourth);
        TextView steelHalf = (TextView) findViewById(R.id.steelHalf);
        TextView steelOne = (TextView) findViewById(R.id.steelOne);
        TextView steelTwo = (TextView) findViewById(R.id.steelTwo);
        TextView steelFour = (TextView) findViewById(R.id.steelFour);

        TextView bugZero = (TextView) findViewById(R.id.bugZero);
        TextView bugFourth = (TextView) findViewById(R.id.bugFourth);
        TextView bugHalf = (TextView) findViewById(R.id.bugHalf);
        TextView bugOne = (TextView) findViewById(R.id.bugOne);
        TextView bugTwo = (TextView) findViewById(R.id.bugTwo);
        TextView bugFour = (TextView) findViewById(R.id.bugFour);

        TextView waterZero = (TextView) findViewById(R.id.waterZero);
        TextView waterFourth = (TextView) findViewById(R.id.waterFourth);
        TextView waterHalf = (TextView) findViewById(R.id.waterHalf);
        TextView waterOne = (TextView) findViewById(R.id.waterOne);
        TextView waterTwo = (TextView) findViewById(R.id.waterTwo);
        TextView waterFour = (TextView) findViewById(R.id.waterFour);

        TextView fireZero = (TextView) findViewById(R.id.fireZero);
        TextView fireFourth = (TextView) findViewById(R.id.fireFourth);
        TextView fireHalf = (TextView) findViewById(R.id.fireHalf);
        TextView fireOne = (TextView) findViewById(R.id.fireOne);
        TextView fireTwo = (TextView) findViewById(R.id.fireTwo);
        TextView fireFour = (TextView) findViewById(R.id.fireFour);

        TextView grsZero = (TextView) findViewById(R.id.grsZero);
        TextView grsFourth = (TextView) findViewById(R.id.grsFourth);
        TextView grsHalf = (TextView) findViewById(R.id.grsHalf);
        TextView grsOne = (TextView) findViewById(R.id.grsOne);
        TextView grsTwo = (TextView) findViewById(R.id.grsTwo);
        TextView grsFour = (TextView) findViewById(R.id.grsFour);

        TextView elecZero = (TextView) findViewById(R.id.elecZero);
        TextView elecFourth = (TextView) findViewById(R.id.elecFourth);
        TextView elecHalf = (TextView) findViewById(R.id.elecHalf);
        TextView elecOne = (TextView) findViewById(R.id.elecOne);
        TextView elecTwo = (TextView) findViewById(R.id.elecTwo);
        TextView elecFour = (TextView) findViewById(R.id.elecFour);

        TextView psyZero = (TextView) findViewById(R.id.psyZero);
        TextView psyFourth = (TextView) findViewById(R.id.psyFourth);
        TextView psyHalf = (TextView) findViewById(R.id.psyHalf);
        TextView psyOne = (TextView) findViewById(R.id.psyOne);
        TextView psyTwo = (TextView) findViewById(R.id.psyTwo);
        TextView psyFour = (TextView) findViewById(R.id.psyFour);

        TextView iceZero = (TextView) findViewById(R.id.iceZero);
        TextView iceFourth = (TextView) findViewById(R.id.iceFourth);
        TextView iceHalf = (TextView) findViewById(R.id.iceHalf);
        TextView iceOne = (TextView) findViewById(R.id.iceOne);
        TextView iceTwo = (TextView) findViewById(R.id.iceTwo);
        TextView iceFour = (TextView) findViewById(R.id.iceFour);

        TextView drgZero = (TextView) findViewById(R.id.drgZero);
        TextView drgFourth = (TextView) findViewById(R.id.drgFourth);
        TextView drgHalf = (TextView) findViewById(R.id.drgHalf);
        TextView drgOne = (TextView) findViewById(R.id.drgOne);
        TextView drgTwo = (TextView) findViewById(R.id.drgTwo);
        TextView drgFour = (TextView) findViewById(R.id.drgFour);

        TextView fryZero = (TextView) findViewById(R.id.fryZero);
        TextView fryFourth = (TextView) findViewById(R.id.fryFourth);
        TextView fryHalf = (TextView) findViewById(R.id.fryHalf);
        TextView fryOne = (TextView) findViewById(R.id.fryOne);
        TextView fryTwo = (TextView) findViewById(R.id.fryTwo);
        TextView fryFour = (TextView) findViewById(R.id.fryFour);

        TextView darkZero = (TextView) findViewById(R.id.darkZero);
        TextView darkFourth = (TextView) findViewById(R.id.darkFourth);
        TextView darkHalf = (TextView) findViewById(R.id.darkHalf);
        TextView darkOne = (TextView) findViewById(R.id.darkOne);
        TextView darkTwo = (TextView) findViewById(R.id.darkTwo);
        TextView darkFour = (TextView) findViewById(R.id.darkFour);



        for(int i=0;i<team.length;i++) {
            int[][] combinedResist = new int[18][6];

            //Add Matricies
            if (team[i].type2 != NONE) {
                for (int j = 0; j < 18; j++) {
                    for (int k = 0; k < 6; k++) {
                        combinedResist[j][k] = team[i].type1.resistMatrix[j][k] + team[i].type2.resistMatrix[j][k];
                        System.out.println("combinedResist[" + j + "][" + k + "] = " + combinedResist[j][k]);
                    }
                    //Checking for Neutral Cancellation, *1/4, *4

                    //No Effect
                    if (combinedResist[j][0] > 0) {
                        combinedResist[j][0] = 1;
                        for (int l = 1; l < 6; l++) {
                            combinedResist[j][l] = 0;
                        }
                    }

                    // *1/4
                    else if (combinedResist[j][2] == 2) {
                        combinedResist[j][2] = 0;
                        combinedResist[j][1] = 1;
                    }

                    // *4
                    else if (combinedResist[j][4] == 2) {
                        combinedResist[j][4] = 0;
                        combinedResist[j][5] = 1;
                    }

                    //Neutral out
                    else if (combinedResist[j][2] == 1 && combinedResist[j][4] == 1) {
                        combinedResist[j][2] = 0;
                        combinedResist[j][4] = 0;
                        combinedResist[j][3] = 0;
                    }

                    //Neutral
                    else if (combinedResist[j][0] == 0 && combinedResist[j][1] == 0 &&
                            combinedResist[j][2] == 0 && combinedResist[j][3] == 0 &&
                            combinedResist[j][4] == 0 && combinedResist[j][5] == 0) {
                        combinedResist[j][3] = 1;
                    }
                }

                //Accumulate all Pokemon on team
                for (int m = 0; m < 18; m++) {
                    for (int n = 0; n < 6; n++) {
                        teamResistMatrix[m][n] = teamResistMatrix[m][n] + combinedResist[m][n];
                    }
                }

            }
            else {
                for (int m = 0; m < 18; m++) {
                    for (int n = 0; n < 6; n++) {
                        int num = team[i].type1.resistMatrix[m][n];
                        teamResistMatrix[m][n] = teamResistMatrix[m][n] + num;
                    }
                }
            }

        }

        //CONVERT MATRIX TO STRING HERE
        for (int m = 0; m < 18; m++) {
            for (int n = 0; n < 6; n++) {
                stringMatrix[m][n] = String.valueOf(teamResistMatrix[m][n]);
            }
        }

        //Display table
        normZero.setText(stringMatrix[0][0]);
        normFourth.setText(stringMatrix[0][1]);
        normHalf.setText(stringMatrix[0][2]);
        normOne.setText(stringMatrix[0][3]);
        normTwo.setText(stringMatrix[0][4]);
        normFour.setText(stringMatrix[0][5]);

        fightZero.setText(stringMatrix[1][0]);
        fightFourth.setText(stringMatrix[1][1]);
        fightHalf.setText(stringMatrix[1][2]);
        fightOne.setText(stringMatrix[1][3]);
        fightTwo.setText(stringMatrix[1][4]);
        fightFour.setText(stringMatrix[1][5]);

        flyZero.setText(stringMatrix[2][0]);
        flyFourth.setText(stringMatrix[2][1]);
        flyHalf.setText(stringMatrix[2][2]);
        flyOne.setText(stringMatrix[2][3]);
        flyTwo.setText(stringMatrix[2][4]);
        flyFour.setText(stringMatrix[2][5]);

        psnZero.setText(stringMatrix[3][0]);
        psnFourth.setText(stringMatrix[3][1]);
        psnHalf.setText(stringMatrix[3][2]);
        psnOne.setText(stringMatrix[3][3]);
        psnTwo.setText(stringMatrix[3][4]);
        psnFour.setText(stringMatrix[3][5]);

        grndZero.setText(stringMatrix[4][0]);
        grndFourth.setText(stringMatrix[4][1]);
        grndHalf.setText(stringMatrix[4][2]);
        grndOne.setText(stringMatrix[4][3]);
        grndTwo.setText(stringMatrix[4][4]);
        grndFour.setText(stringMatrix[4][5]);

        rockZero.setText(stringMatrix[5][0]);
        rockFourth.setText(stringMatrix[5][1]);
        rockHalf.setText(stringMatrix[5][2]);
        rockOne.setText(stringMatrix[5][3]);
        rockTwo.setText(stringMatrix[5][4]);
        rockFour.setText(stringMatrix[5][5]);

        bugZero.setText(stringMatrix[6][0]);
        bugFourth.setText(stringMatrix[6][1]);
        bugHalf.setText(stringMatrix[6][2]);
        bugOne.setText(stringMatrix[6][3]);
        bugTwo.setText(stringMatrix[6][4]);
        bugFour.setText(stringMatrix[6][5]);

        ghostZero.setText(stringMatrix[7][0]);
        ghostFourth.setText(stringMatrix[7][1]);
        ghostHalf.setText(stringMatrix[7][2]);
        ghostOne.setText(stringMatrix[7][3]);
        ghostTwo.setText(stringMatrix[7][4]);
        ghostFour.setText(stringMatrix[7][5]);

        steelZero.setText(stringMatrix[8][0]);
        steelFourth.setText(stringMatrix[8][1]);
        steelHalf.setText(stringMatrix[8][2]);
        steelOne.setText(stringMatrix[8][3]);
        steelTwo.setText(stringMatrix[8][4]);
        steelFour.setText(stringMatrix[8][5]);

        fireZero.setText(stringMatrix[9][0]);
        fireFourth.setText(stringMatrix[9][1]);
        fireHalf.setText(stringMatrix[9][2]);
        fireOne.setText(stringMatrix[9][3]);
        fireTwo.setText(stringMatrix[9][4]);
        fireFour.setText(stringMatrix[9][5]);

        waterZero.setText(stringMatrix[10][0]);
        waterFourth.setText(stringMatrix[10][1]);
        waterHalf.setText(stringMatrix[10][2]);
        waterOne.setText(stringMatrix[10][3]);
        waterTwo.setText(stringMatrix[10][4]);
        waterFour.setText(stringMatrix[10][5]);

        grsZero.setText(stringMatrix[11][0]);
        grsFourth.setText(stringMatrix[11][1]);
        grsHalf.setText(stringMatrix[11][2]);
        grsOne.setText(stringMatrix[11][3]);
        grsTwo.setText(stringMatrix[11][4]);
        grsFour.setText(stringMatrix[11][5]);

        elecZero.setText(stringMatrix[12][0]);
        elecFourth.setText(stringMatrix[12][1]);
        elecHalf.setText(stringMatrix[12][2]);
        elecOne.setText(stringMatrix[12][3]);
        elecTwo.setText(stringMatrix[12][4]);
        elecFour.setText(stringMatrix[12][5]);

        psyZero.setText(stringMatrix[13][0]);
        psyFourth.setText(stringMatrix[13][1]);
        psyHalf.setText(stringMatrix[13][2]);
        psyOne.setText(stringMatrix[13][3]);
        psyTwo.setText(stringMatrix[13][4]);
        psyFour.setText(stringMatrix[13][5]);

        iceZero.setText(stringMatrix[14][0]);
        iceFourth.setText(stringMatrix[14][1]);
        iceHalf.setText(stringMatrix[14][2]);
        iceOne.setText(stringMatrix[14][3]);
        iceTwo.setText(stringMatrix[14][4]);
        iceFour.setText(stringMatrix[14][5]);

        drgZero.setText(stringMatrix[15][0]);
        drgFourth.setText(stringMatrix[15][1]);
        drgHalf.setText(stringMatrix[15][2]);
        drgOne.setText(stringMatrix[15][3]);
        drgTwo.setText(stringMatrix[15][4]);
        drgFour.setText(stringMatrix[15][5]);

        darkZero.setText(stringMatrix[16][0]);
        darkFourth.setText(stringMatrix[16][1]);
        darkHalf.setText(stringMatrix[16][2]);
        darkOne.setText(stringMatrix[16][3]);
        darkTwo.setText(stringMatrix[16][4]);
        darkFour.setText(stringMatrix[16][5]);

        fryZero.setText(stringMatrix[17][0]);
        fryFourth.setText(stringMatrix[17][1]);
        fryHalf.setText(stringMatrix[17][2]);
        fryOne.setText(stringMatrix[17][3]);
        fryTwo.setText(stringMatrix[17][4]);
        fryFour.setText(stringMatrix[17][5]);

    }

    public void createEmptyTeam(){
        team[0] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
        team[1] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
        team[2] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
        team[3] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
        team[4] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
        team[5] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0);
    }

    public void initList()
    {
        pokeList.add(new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,0,0,0,0,0,0));
        pokeList.add(new Pokemon(Names.Bulbasaur,1,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,45,49,49,45,65,6));
        pokeList.add(new Pokemon(Names.Ivysaur,2,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,60,62,63,60,80,8));
        pokeList.add(new Pokemon(Names.Venusaur,3,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,80,82,83,80,100,10));
        pokeList.add(new Pokemon(Names.Charmander,4,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,39,52,43,65,60,5));
        pokeList.add(new Pokemon(Names.Charmeleon,5,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,58,64,58,80,80,6));
        pokeList.add(new Pokemon(Names.Charizard,6,FIRE,FLYING,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,78,84,78,100,109,8));
        pokeList.add(new Pokemon(Names.Squirtle,7,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,44,48,65,43,50,6));
        pokeList.add(new Pokemon(Names.Wartortle,8,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,59,63,80,58,65,8));
        pokeList.add(new Pokemon(Names.Blastoise,9,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,79,83,100,78,85,10));
        pokeList.add(new Pokemon(Names.Caterpie,10,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,45,30,35,45,20,2));
        pokeList.add(new Pokemon(Names.Metapod,11,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,50,20,55,30,25,2));
        pokeList.add(new Pokemon(Names.Butterfree,12,BUG,FLYING,AbilityEnum.COMPOUNDEYES,AbilityEnum.TINTEDLENS,AbilityEnum.NONE,60,45,50,70,90,8));
        pokeList.add(new Pokemon(Names.Weedle,13,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,40,35,30,50,20,2));
        pokeList.add(new Pokemon(Names.Kakuna,14,BUG,POISON,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,45,25,50,35,25,2));
        pokeList.add(new Pokemon(Names.Beedrill,15,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.SNIPER,AbilityEnum.NONE,65,90,40,75,45,8));
        pokeList.add(new Pokemon(Names.Pidgey,16,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,40,45,40,56,35,3));
        pokeList.add(new Pokemon(Names.Pidgeotto,17,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,63,60,55,71,50,5));
        pokeList.add(new Pokemon(Names.Pidgeot,18,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,83,80,75,101,70,7));
        pokeList.add(new Pokemon(Names.Rattata,19,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.GUTS,AbilityEnum.HUSTLE,30,56,35,72,25,3));
        pokeList.add(new Pokemon(Names.Raticate,20,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.GUTS,AbilityEnum.HUSTLE,55,81,60,97,50,7));
        pokeList.add(new Pokemon(Names.Spearow,21,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SNIPER,AbilityEnum.NONE,40,60,30,70,31,3));
        pokeList.add(new Pokemon(Names.Fearow,22,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SNIPER,AbilityEnum.NONE,65,90,65,100,61,6));
        pokeList.add(new Pokemon(Names.Ekans,23,POISON,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SHEDSKIN,AbilityEnum.UNNERVE,35,60,44,55,40,5));
        pokeList.add(new Pokemon(Names.Arbok,24,POISON,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SHEDSKIN,AbilityEnum.UNNERVE,60,85,69,80,65,7));
        pokeList.add(new Pokemon(Names.Pikachu,25,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,35,55,40,90,50,5));
        pokeList.add(new Pokemon(Names.Raichu,26,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,60,90,55,110,90,8));
        pokeList.add(new Pokemon(Names.Sandshrew,27,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.SANDRUSH,AbilityEnum.NONE,50,75,85,40,20,3));
        pokeList.add(new Pokemon(Names.Sandslash,28,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.SANDRUSH,AbilityEnum.NONE,75,100,110,65,45,5));
        pokeList.add(new Pokemon(Names.Nidoran_M,29,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,55,47,52,41,40,4));
        pokeList.add(new Pokemon(Names.Nidorina,30,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,70,62,67,56,55,5));
        pokeList.add(new Pokemon(Names.Nidoqueen,31,POISON,GROUND,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.SHEERFORCE,90,92,87,76,75,8));
        pokeList.add(new Pokemon(Names.Nidoran_F,32,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,46,57,40,50,40,4));
        pokeList.add(new Pokemon(Names.Nidorino,33,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,61,72,57,65,55,5));
        pokeList.add(new Pokemon(Names.Nidoking,34,POISON,GROUND,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.SHEERFORCE,81,102,77,85,85,7));
        pokeList.add(new Pokemon(Names.Clefairy,35,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.FRIENDGUARD,70,45,48,35,60,6));
        pokeList.add(new Pokemon(Names.Clefable,36,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.UNAWARE,95,70,73,60,95,9));
        pokeList.add(new Pokemon(Names.Vulpix,37,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.DROUGHT,AbilityEnum.NONE,38,41,40,65,50,6));
        pokeList.add(new Pokemon(Names.Ninetales,38,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.DROUGHT,AbilityEnum.NONE,73,76,75,100,81,10));
        pokeList.add(new Pokemon(Names.Jigglypuff,39,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRIENDGUARD,115,45,20,20,45,2));
        pokeList.add(new Pokemon(Names.Wigglytuff,40,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRISK,140,70,45,45,85,5));
        pokeList.add(new Pokemon(Names.Zubat,41,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,40,45,35,55,30,4));
        pokeList.add(new Pokemon(Names.Golbat,42,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,75,80,70,90,65,7));
        pokeList.add(new Pokemon(Names.Oddish,43,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.RUNAWAY,AbilityEnum.NONE,45,50,55,30,75,6));
        pokeList.add(new Pokemon(Names.Gloom,44,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.STENCH,AbilityEnum.NONE,60,65,70,40,85,7));
        pokeList.add(new Pokemon(Names.Vileplume,45,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.EFFECTSPORE,AbilityEnum.NONE,75,80,85,50,110,9));
        pokeList.add(new Pokemon(Names.Paras,46,BUG,GRASS,AbilityEnum.EFFECTSPORE,AbilityEnum.DRYSKIN,AbilityEnum.DAMP,35,70,55,25,45,5));
        pokeList.add(new Pokemon(Names.Parasect,47,BUG,GRASS,AbilityEnum.EFFECTSPORE,AbilityEnum.DRYSKIN,AbilityEnum.DAMP,60,95,80,30,60,8));
        pokeList.add(new Pokemon(Names.Venonat,48,BUG,POISON,AbilityEnum.COMPOUNDEYES,AbilityEnum.TINTEDLENS,AbilityEnum.RUNAWAY,60,55,50,45,40,5));
        pokeList.add(new Pokemon(Names.Venomoth,49,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.TINTEDLENS,AbilityEnum.WONDERSKIN,70,65,60,90,90,7));
        pokeList.add(new Pokemon(Names.Diglett,50,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.ARENATRAP,AbilityEnum.SANDFORCE,10,55,25,95,35,4));
        pokeList.add(new Pokemon(Names.Dugtrio,51,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.ARENATRAP,AbilityEnum.SANDFORCE,35,80,50,120,50,7));
        pokeList.add(new Pokemon(Names.Meowth,52,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.TECHNICIAN,AbilityEnum.UNNERVE,40,45,35,90,40,4));
        pokeList.add(new Pokemon(Names.Persian,53,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.TECHNICIAN,AbilityEnum.UNNERVE,65,70,60,115,65,6));
        pokeList.add(new Pokemon(Names.Psyduck,54,WATER,NONE,AbilityEnum.DAMP,AbilityEnum.CLOUDNINE,AbilityEnum.SWIFTSWIM,50,52,48,55,65,5));
        pokeList.add(new Pokemon(Names.Golduck,55,WATER,NONE,AbilityEnum.DAMP,AbilityEnum.CLOUDNINE,AbilityEnum.SWIFTSWIM,80,82,78,85,95,8));
        pokeList.add(new Pokemon(Names.Mankey,56,FIGHTING,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.ANGERPOINT,AbilityEnum.DEFIANT,40,80,35,70,35,4));
        pokeList.add(new Pokemon(Names.Primeape,57,FIGHTING,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.ANGERPOINT,AbilityEnum.DEFIANT,65,105,60,95,60,7));
        pokeList.add(new Pokemon(Names.Growlithe,58,FIRE,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FLASHFIRE,AbilityEnum.JUSTIFIED,55,70,45,60,70,5));
        pokeList.add(new Pokemon(Names.Arcanine,59,FIRE,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FLASHFIRE,AbilityEnum.JUSTIFIED,90,110,80,95,100,8));
        pokeList.add(new Pokemon(Names.Poliwag,60,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,40,50,40,90,40,4));
        pokeList.add(new Pokemon(Names.Poliwhirl,61,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,65,65,65,90,50,5));
        pokeList.add(new Pokemon(Names.Poliwrath,62,WATER,FIGHTING,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,90,95,95,70,70,9));
        pokeList.add(new Pokemon(Names.Abra,63,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,25,20,15,90,105,5));
        pokeList.add(new Pokemon(Names.Kadabra,64,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,40,35,30,105,120,7));
        pokeList.add(new Pokemon(Names.Alakazam,65,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,55,50,45,120,135,9));
        pokeList.add(new Pokemon(Names.Machop,66,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,70,80,50,35,35,3));
        pokeList.add(new Pokemon(Names.Machoke,67,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,80,100,70,45,50,6));
        pokeList.add(new Pokemon(Names.Machamp,68,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,90,130,80,55,65,8));
        pokeList.add(new Pokemon(Names.Bellsprout,69,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,50,75,35,40,70,3));
        pokeList.add(new Pokemon(Names.Weepinbell,70,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,65,90,50,55,85,4));
        pokeList.add(new Pokemon(Names.Victreebel,71,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,80,105,65,70,100,7));
        pokeList.add(new Pokemon(Names.Tentacool,72,WATER,POISON,AbilityEnum.CLEARBODY,AbilityEnum.LIQUIDOOZE,AbilityEnum.RAINDISH,40,40,35,70,50,10));
        pokeList.add(new Pokemon(Names.Tentacruel,73,WATER,POISON,AbilityEnum.CLEARBODY,AbilityEnum.LIQUIDOOZE,AbilityEnum.RAINDISH,80,70,65,100,80,12));
        pokeList.add(new Pokemon(Names.Geodude,74,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,40,80,100,20,30,3));
        pokeList.add(new Pokemon(Names.Graveler,75,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,55,95,115,35,45,4));
        pokeList.add(new Pokemon(Names.Golem,76,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,80,120,130,45,55,6));
        pokeList.add(new Pokemon(Names.Ponyta,77,FIRE,NONE,AbilityEnum.RUNAWAY,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,50,85,55,90,65,6));
        pokeList.add(new Pokemon(Names.Rapidash,78,FIRE,NONE,AbilityEnum.RUNAWAY,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,65,100,70,105,80,8));
        pokeList.add(new Pokemon(Names.Slowpoke,79,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,90,65,65,15,40,4));
        pokeList.add(new Pokemon(Names.Slowbro,80,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,95,75,110,30,100,8));
        pokeList.add(new Pokemon(Names.Magnemite,81,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,25,35,70,45,95,5));
        pokeList.add(new Pokemon(Names.Magneton,82,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,50,60,95,70,120,7));
        pokeList.add(new Pokemon(Names.Farfetchd,83,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.INNERFOCUS,AbilityEnum.DEFIANT,52,65,55,60,58,6));
        pokeList.add(new Pokemon(Names.Doduo,84,NORMAL,FLYING,AbilityEnum.RUNAWAY,AbilityEnum.EARLYBIRD,AbilityEnum.TANGLEDFEET,35,85,45,75,35,3));
        pokeList.add(new Pokemon(Names.Dodrio,85,NORMAL,FLYING,AbilityEnum.RUNAWAY,AbilityEnum.EARLYBIRD,AbilityEnum.TANGLEDFEET,60,110,70,100,60,6));
        pokeList.add(new Pokemon(Names.Seel,86,WATER,NONE,AbilityEnum.THICKFAT,AbilityEnum.HYDRATION,AbilityEnum.ICEBODY,65,45,55,45,45,7));
        pokeList.add(new Pokemon(Names.Dewgong,87,WATER,ICE,AbilityEnum.THICKFAT,AbilityEnum.HYDRATION,AbilityEnum.ICEBODY,90,70,80,70,70,9));
        pokeList.add(new Pokemon(Names.Grimer,88,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.POISONTOUCH,80,80,50,25,40,5));
        pokeList.add(new Pokemon(Names.Muk,89,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.POISONTOUCH,105,105,75,50,65,10));
        pokeList.add(new Pokemon(Names.Shellder,90,WATER,NONE,AbilityEnum.SHELLARMOR,AbilityEnum.SKILLLINK,AbilityEnum.OVERCOAT,30,65,100,40,45,2));
        pokeList.add(new Pokemon(Names.Cloyster,91,WATER,ICE,AbilityEnum.SHELLARMOR,AbilityEnum.SKILLLINK,AbilityEnum.OVERCOAT,50,95,180,70,85,4));
        pokeList.add(new Pokemon(Names.Gastly,92,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,30,35,30,80,100,3));
        pokeList.add(new Pokemon(Names.Haunter,93,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,45,50,45,95,115,5));
        pokeList.add(new Pokemon(Names.Gengar,94,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,60,65,60,110,130,7));
        pokeList.add(new Pokemon(Names.Onix,95,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.WEAKARMOR,35,45,160,70,30,4));
        pokeList.add(new Pokemon(Names.Drowzee,96,PSYCHIC,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FOREWARN,AbilityEnum.INNERFOCUS,60,48,45,42,43,9));
        pokeList.add(new Pokemon(Names.Hypno,97,PSYCHIC,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FOREWARN,AbilityEnum.INNERFOCUS,85,73,70,67,73,11));
        pokeList.add(new Pokemon(Names.Krabby,98,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.SHEERFORCE,30,105,90,50,25,2));
        pokeList.add(new Pokemon(Names.Kingler,99,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.SHEERFORCE,55,130,115,75,50,5));
        pokeList.add(new Pokemon(Names.Voltorb,100,ELECTRIC,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.STATIC,AbilityEnum.AFTERMATH,40,30,50,100,55,5));
        pokeList.add(new Pokemon(Names.Electrode,101,ELECTRIC,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.STATIC,AbilityEnum.AFTERMATH,60,50,70,140,80,8));
        pokeList.add(new Pokemon(Names.Exeggcute,102,GRASS,PSYCHIC,AbilityEnum.CHLOROPHYLL,AbilityEnum.HARVEST,AbilityEnum.NONE,60,40,80,40,60,4));
        pokeList.add(new Pokemon(Names.Exeggutor,103,GRASS,PSYCHIC,AbilityEnum.CHLOROPHYLL,AbilityEnum.HARVEST,AbilityEnum.NONE,95,95,85,55,125,6));
        pokeList.add(new Pokemon(Names.Cubone,104,GROUND,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.LIGHTNINGROD,AbilityEnum.BATTLEARMOR,50,50,95,35,40,5));
        pokeList.add(new Pokemon(Names.Marowak,105,GROUND,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.LIGHTNINGROD,AbilityEnum.BATTLEARMOR,60,80,110,45,50,8));
        pokeList.add(new Pokemon(Names.Hitmonlee,106,FIGHTING,NONE,AbilityEnum.LIMBER,AbilityEnum.RECKLESS,AbilityEnum.UNBURDEN,50,120,53,87,35,11));
        pokeList.add(new Pokemon(Names.Hitmonchan,107,FIGHTING,NONE,AbilityEnum.KEENEYE,AbilityEnum.IRONFIST,AbilityEnum.INNERFOCUS,50,105,79,76,35,11));
        pokeList.add(new Pokemon(Names.Lickitung,108,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.OBLIVIOUS,AbilityEnum.CLOUDNINE,90,55,75,30,60,7));
        pokeList.add(new Pokemon(Names.Koffing,109,POISON,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,40,65,95,35,60,4));
        pokeList.add(new Pokemon(Names.Weezing,110,POISON,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,65,90,120,60,85,7));
        pokeList.add(new Pokemon(Names.Rhyhorn,111,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.ROCKHEAD,AbilityEnum.RECKLESS,80,85,95,25,30,3));
        pokeList.add(new Pokemon(Names.Rhydon,112,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.ROCKHEAD,AbilityEnum.RECKLESS,105,130,120,40,45,4));
        pokeList.add(new Pokemon(Names.Chansey,113,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.HEALER,250,5,5,50,35,10));
        pokeList.add(new Pokemon(Names.Tangela,114,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.REGENERATOR,65,55,115,60,100,4));
        pokeList.add(new Pokemon(Names.Kangaskhan,115,NORMAL,NONE,AbilityEnum.EARLYBIRD,AbilityEnum.SCRAPPY,AbilityEnum.INNERFOCUS,105,95,80,90,40,8));
        pokeList.add(new Pokemon(Names.Horsea,116,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.SNIPER,AbilityEnum.DAMP,30,40,70,60,70,2));
        pokeList.add(new Pokemon(Names.Seadra,117,WATER,NONE,AbilityEnum.POISONPOINT,AbilityEnum.SNIPER,AbilityEnum.DAMP,55,65,95,85,95,4));
        pokeList.add(new Pokemon(Names.Goldeen,118,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.LIGHTNINGROD,45,67,60,63,35,5));
        pokeList.add(new Pokemon(Names.Seaking,119,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.LIGHTNINGROD,80,92,65,68,65,8));
        pokeList.add(new Pokemon(Names.Staryu,120,WATER,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.NATURALCURE,AbilityEnum.ANALYTIC,30,45,55,85,70,5));
        pokeList.add(new Pokemon(Names.Starmie,121,WATER,PSYCHIC,AbilityEnum.ILLUMINATE,AbilityEnum.NATURALCURE,AbilityEnum.ANALYTIC,60,75,85,115,100,8));
        pokeList.add(new Pokemon(Names.Mr_Mime,122,PSYCHIC,FAIRY,AbilityEnum.SOUNDPROOF,AbilityEnum.FILTER,AbilityEnum.TECHNICIAN,40,45,65,90,100,12));
        pokeList.add(new Pokemon(Names.Scyther,123,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.STEADFAST,70,110,80,105,55,8));
        pokeList.add(new Pokemon(Names.Jynx,124,ICE,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.FOREWARN,AbilityEnum.DRYSKIN,65,50,35,95,115,9));
        pokeList.add(new Pokemon(Names.Electabuzz,125,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,65,83,57,105,95,8));
        pokeList.add(new Pokemon(Names.Magmar,126,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,65,95,57,93,100,8));
        pokeList.add(new Pokemon(Names.Pinsir,127,BUG,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.MOLDBREAKER,AbilityEnum.MOXIE,65,125,100,85,55,7));
        pokeList.add(new Pokemon(Names.Tauros,128,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.ANGERPOINT,AbilityEnum.SHEERFORCE,75,100,95,110,40,7));
        pokeList.add(new Pokemon(Names.Magikarp,129,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.RATTLED,AbilityEnum.NONE,20,10,55,80,15,2));
        pokeList.add(new Pokemon(Names.Gyarados,130,WATER,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.NONE,95,125,79,81,60,10));
        pokeList.add(new Pokemon(Names.Lapras,131,WATER,ICE,AbilityEnum.WATERABSORB,AbilityEnum.SHELLARMOR,AbilityEnum.HYDRATION,130,85,80,60,85,9));
        pokeList.add(new Pokemon(Names.Ditto,132,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.IMPOSTER,AbilityEnum.NONE,48,48,48,48,48,4));
        pokeList.add(new Pokemon(Names.Eevee,133,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.ADAPTABILITY,AbilityEnum.ANTICIPATION,55,55,50,55,45,6));
        pokeList.add(new Pokemon(Names.Vaporeon,134,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.WATERABSORB,AbilityEnum.HYDRATION,130,65,60,65,110,9));
        pokeList.add(new Pokemon(Names.Jolteon,135,ELECTRIC,NONE,AbilityEnum.VOLTABSORB,AbilityEnum.VOLTABSORB,AbilityEnum.QUICKFEET,65,65,60,130,110,9));
        pokeList.add(new Pokemon(Names.Flareon,136,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.FLASHFIRE,AbilityEnum.GUTS,65,130,60,65,95,11));
        pokeList.add(new Pokemon(Names.Porygon,137,NORMAL,NONE,AbilityEnum.TRACE,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,65,60,70,40,85,7));
        pokeList.add(new Pokemon(Names.Omanyte,138,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,35,40,100,35,90,5));
        pokeList.add(new Pokemon(Names.Omastar,139,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,70,60,125,55,115,7));
        pokeList.add(new Pokemon(Names.Kabuto,140,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.BATTLEARMOR,AbilityEnum.WEAKARMOR,30,80,90,55,55,4));
        pokeList.add(new Pokemon(Names.Kabutops,141,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.BATTLEARMOR,AbilityEnum.WEAKARMOR,60,115,105,80,65,7));
        pokeList.add(new Pokemon(Names.Aerodactyl,142,ROCK,FLYING,AbilityEnum.ROCKHEAD,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,80,105,65,130,60,7));
        pokeList.add(new Pokemon(Names.Snorlax,143,NORMAL,NONE,AbilityEnum.IMMUNITY,AbilityEnum.THICKFAT,AbilityEnum.GLUTTONY,160,110,65,30,65,11));
        pokeList.add(new Pokemon(Names.Articuno,144,ICE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.SNOWCLOAK,AbilityEnum.NONE,90,85,100,85,95,12));
        pokeList.add(new Pokemon(Names.Zapdos,145,ELECTRIC,FLYING,AbilityEnum.PRESSURE,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,90,90,85,100,125,9));
        pokeList.add(new Pokemon(Names.Moltres,146,FIRE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.FLAMEBODY,AbilityEnum.NONE,90,100,90,90,125,8));
        pokeList.add(new Pokemon(Names.Dratini,147,DRAGON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.MARVELSCALE,AbilityEnum.NONE,41,64,45,50,50,5));
        pokeList.add(new Pokemon(Names.Dragonair,148,DRAGON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.MARVELSCALE,AbilityEnum.NONE,61,84,65,70,70,7));
        pokeList.add(new Pokemon(Names.Dragonite,149,DRAGON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.MULTISCALE,AbilityEnum.NONE,91,134,95,80,100,10));
        pokeList.add(new Pokemon(Names.Mewtwo,150,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,AbilityEnum.NONE,106,110,90,130,154,9));
        pokeList.add(new Pokemon(Names.Mew,151,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Chikorita,152,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,45,49,65,45,49,6));
        pokeList.add(new Pokemon(Names.Bayleef,153,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,60,62,80,60,63,8));
        pokeList.add(new Pokemon(Names.Meganium,154,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,80,82,100,80,83,10));
        pokeList.add(new Pokemon(Names.Cyndaquil,155,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,39,52,43,65,60,5));
        pokeList.add(new Pokemon(Names.Quilava,156,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,58,64,58,80,80,6));
        pokeList.add(new Pokemon(Names.Typhlosion,157,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,78,84,78,100,109,8));
        pokeList.add(new Pokemon(Names.Totodile,158,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,50,65,64,43,44,4));
        pokeList.add(new Pokemon(Names.Croconaw,159,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,65,80,80,58,59,6));
        pokeList.add(new Pokemon(Names.Feraligatr,160,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,85,105,100,78,79,8));
        pokeList.add(new Pokemon(Names.Sentret,161,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.FRISK,35,46,34,20,35,4));
        pokeList.add(new Pokemon(Names.Furret,162,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.FRISK,85,76,64,90,45,5));
        pokeList.add(new Pokemon(Names.Hoothoot,163,NORMAL,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.KEENEYE,AbilityEnum.TINTEDLENS,60,30,30,50,36,5));
        pokeList.add(new Pokemon(Names.Noctowl,164,NORMAL,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.KEENEYE,AbilityEnum.TINTEDLENS,100,50,50,70,76,9));
        pokeList.add(new Pokemon(Names.Ledyba,165,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.EARLYBIRD,AbilityEnum.RATTLED,40,20,30,55,40,8));
        pokeList.add(new Pokemon(Names.Ledian,166,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.EARLYBIRD,AbilityEnum.IRONFIST,55,35,50,85,55,11));
        pokeList.add(new Pokemon(Names.Spinarak,167,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.INSOMNIA,AbilityEnum.SNIPER,40,60,40,30,40,4));
        pokeList.add(new Pokemon(Names.Ariados,168,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.INSOMNIA,AbilityEnum.SNIPER,70,90,70,40,60,6));
        pokeList.add(new Pokemon(Names.Crobat,169,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,85,90,80,130,70,8));
        pokeList.add(new Pokemon(Names.Chinchou,170,WATER,ELECTRIC,AbilityEnum.VOLTABSORB,AbilityEnum.ILLUMINATE,AbilityEnum.WATERABSORB,75,38,38,67,56,5));
        pokeList.add(new Pokemon(Names.Lanturn,171,WATER,ELECTRIC,AbilityEnum.VOLTABSORB,AbilityEnum.ILLUMINATE,AbilityEnum.WATERABSORB,125,58,58,67,76,7));
        pokeList.add(new Pokemon(Names.Pichu,172,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,20,40,15,60,35,3));
        pokeList.add(new Pokemon(Names.Cleffa,173,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.FRIENDGUARD,50,25,28,15,45,5));
        pokeList.add(new Pokemon(Names.Igglybuff,174,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRIENDGUARD,90,30,15,15,40,2));
        pokeList.add(new Pokemon(Names.Togepi,175,FAIRY,NONE,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,35,20,65,20,40,6));
        pokeList.add(new Pokemon(Names.Togetic,176,FAIRY,FLYING,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,55,40,85,40,80,10));
        pokeList.add(new Pokemon(Names.Natu,177,PSYCHIC,FLYING,AbilityEnum.SYNCHRONIZE,AbilityEnum.EARLYBIRD,AbilityEnum.MAGICBOUNCE,40,50,45,70,70,4));
        pokeList.add(new Pokemon(Names.Xatu,178,PSYCHIC,FLYING,AbilityEnum.SYNCHRONIZE,AbilityEnum.EARLYBIRD,AbilityEnum.MAGICBOUNCE,65,75,70,95,95,7));
        pokeList.add(new Pokemon(Names.Mareep,179,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,55,40,40,35,65,4));
        pokeList.add(new Pokemon(Names.Flaaffy,180,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,70,55,55,45,80,6));
        pokeList.add(new Pokemon(Names.Ampharos,181,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,90,75,85,55,115,9));
        pokeList.add(new Pokemon(Names.Bellossom,182,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.HEALER,AbilityEnum.NONE,75,80,95,50,90,10));
        pokeList.add(new Pokemon(Names.Marill,183,WATER,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,70,20,50,40,20,5));
        pokeList.add(new Pokemon(Names.Azumarill,184,WATER,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,100,50,80,60,50,8));
        pokeList.add(new Pokemon(Names.Sudowoodo,185,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.RATTLED,70,100,115,30,30,6));
        pokeList.add(new Pokemon(Names.Politoed,186,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.DRIZZLE,90,75,75,70,90,10));
        pokeList.add(new Pokemon(Names.Hoppip,187,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,35,35,40,50,35,5));
        pokeList.add(new Pokemon(Names.Skiploom,188,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,55,45,50,80,45,6));
        pokeList.add(new Pokemon(Names.Jumpluff,189,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,75,55,70,110,55,9));
        pokeList.add(new Pokemon(Names.Aipom,190,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.PICKUP,AbilityEnum.SKILLLINK,55,70,55,85,40,5));
        pokeList.add(new Pokemon(Names.Sunkern,191,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.EARLYBIRD,30,30,30,30,30,3));
        pokeList.add(new Pokemon(Names.Sunflora,192,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.EARLYBIRD,75,75,55,30,105,8));
        pokeList.add(new Pokemon(Names.Yanma,193,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRISK,65,65,45,95,75,4));
        pokeList.add(new Pokemon(Names.Wooper,194,WATER,GROUND,AbilityEnum.DAMP,AbilityEnum.WATERABSORB,AbilityEnum.UNAWARE,55,45,45,15,25,2));
        pokeList.add(new Pokemon(Names.Quagsire,195,WATER,GROUND,AbilityEnum.DAMP,AbilityEnum.WATERABSORB,AbilityEnum.UNAWARE,95,85,85,35,65,6));
        pokeList.add(new Pokemon(Names.Espeon,196,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.SYNCHRONIZE,AbilityEnum.MAGICBOUNCE,65,65,60,110,130,9));
        pokeList.add(new Pokemon(Names.Umbreon,197,DARK,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,95,65,110,65,60,13));
        pokeList.add(new Pokemon(Names.Murkrow,198,DARK,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.SUPERLUCK,AbilityEnum.PRANKSTER,60,85,42,91,85,4));
        pokeList.add(new Pokemon(Names.Slowking,199,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,95,75,80,30,100,11));
        pokeList.add(new Pokemon(Names.Misdreavus,200,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,60,60,60,85,85,8));
        pokeList.add(new Pokemon(Names.Unown,201,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,48,72,48,48,72,4));
        pokeList.add(new Pokemon(Names.Wobbuffet,202,PSYCHIC,NONE,AbilityEnum.SHADOWTAG,AbilityEnum.TELEPATHY,AbilityEnum.NONE,190,33,58,33,33,5));
        pokeList.add(new Pokemon(Names.Girafarig,203,NORMAL,PSYCHIC,AbilityEnum.INNERFOCUS,AbilityEnum.EARLYBIRD,AbilityEnum.SAPSIPPER,70,80,65,85,90,6));
        pokeList.add(new Pokemon(Names.Pineco,204,BUG,NONE,AbilityEnum.STURDY,AbilityEnum.OVERCOAT,AbilityEnum.NONE,50,65,90,15,35,3));
        pokeList.add(new Pokemon(Names.Forretress,205,BUG,STEEL,AbilityEnum.STURDY,AbilityEnum.OVERCOAT,AbilityEnum.NONE,75,90,140,40,60,6));
        pokeList.add(new Pokemon(Names.Dunsparce,206,NORMAL,NONE,AbilityEnum.SERENEGRACE,AbilityEnum.RUNAWAY,AbilityEnum.RATTLED,100,70,70,45,65,6));
        pokeList.add(new Pokemon(Names.Gligar,207,GROUND,FLYING,AbilityEnum.HYPERCUTTER,AbilityEnum.SANDVEIL,AbilityEnum.IMMUNITY,65,75,105,85,35,6));
        pokeList.add(new Pokemon(Names.Steelix,208,STEEL,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SHEERFORCE,75,85,200,30,55,6));
        pokeList.add(new Pokemon(Names.Snubbull,209,FAIRY,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.RUNAWAY,AbilityEnum.RATTLED,60,80,50,30,40,4));
        pokeList.add(new Pokemon(Names.Granbull,210,FAIRY,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.QUICKFEET,AbilityEnum.RATTLED,90,120,75,45,60,6));
        pokeList.add(new Pokemon(Names.Qwilfish,211,WATER,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWIFTSWIM,AbilityEnum.INTIMIDATE,65,95,75,85,55,5));
        pokeList.add(new Pokemon(Names.Scizor,212,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.LIGHTMETAL,70,130,100,65,55,8));
        pokeList.add(new Pokemon(Names.Shuckle,213,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.GLUTTONY,AbilityEnum.CONTRARY,20,10,230,5,10,23));
        pokeList.add(new Pokemon(Names.Heracross,214,BUG,FIGHTING,AbilityEnum.SWARM,AbilityEnum.GUTS,AbilityEnum.MOXIE,80,125,75,85,40,9));
        pokeList.add(new Pokemon(Names.Sneasel,215,DARK,ICE,AbilityEnum.INNERFOCUS,AbilityEnum.KEENEYE,AbilityEnum.PICKPOCKET,55,95,55,115,35,7));
        pokeList.add(new Pokemon(Names.Teddiursa,216,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.QUICKFEET,AbilityEnum.HONEYGATHER,60,80,50,40,50,5));
        pokeList.add(new Pokemon(Names.Ursaring,217,NORMAL,NONE,AbilityEnum.GUTS,AbilityEnum.QUICKFEET,AbilityEnum.UNNERVE,90,130,75,55,75,7));
        pokeList.add(new Pokemon(Names.Slugma,218,FIRE,NONE,AbilityEnum.MAGMAARMOR,AbilityEnum.FLAMEBODY,AbilityEnum.WEAKARMOR,40,40,40,20,70,4));
        pokeList.add(new Pokemon(Names.Magcargo,219,FIRE,ROCK,AbilityEnum.MAGMAARMOR,AbilityEnum.FLAMEBODY,AbilityEnum.WEAKARMOR,50,50,120,30,80,8));
        pokeList.add(new Pokemon(Names.Swinub,220,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,50,50,40,50,30,3));
        pokeList.add(new Pokemon(Names.Piloswine,221,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,100,100,80,50,60,6));
        pokeList.add(new Pokemon(Names.Corsola,222,WATER,ROCK,AbilityEnum.HUSTLE,AbilityEnum.NATURALCURE,AbilityEnum.REGENERATOR,55,55,85,35,65,8));
        pokeList.add(new Pokemon(Names.Remoraid,223,WATER,NONE,AbilityEnum.HUSTLE,AbilityEnum.SNIPER,AbilityEnum.MOODY,35,65,35,65,65,3));
        pokeList.add(new Pokemon(Names.Octillery,224,WATER,NONE,AbilityEnum.SUCTIONCUPS,AbilityEnum.SNIPER,AbilityEnum.MOODY,75,105,75,45,105,7));
        pokeList.add(new Pokemon(Names.Delibird,225,ICE,FLYING,AbilityEnum.VITALSPIRIT,AbilityEnum.HUSTLE,AbilityEnum.INSOMNIA,45,55,45,75,65,4));
        pokeList.add(new Pokemon(Names.Mantine,226,WATER,FLYING,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERABSORB,AbilityEnum.WATERVEIL,65,40,70,70,80,14));
        pokeList.add(new Pokemon(Names.Skarmory,227,STEEL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.STURDY,AbilityEnum.WEAKARMOR,65,80,140,70,40,7));
        pokeList.add(new Pokemon(Names.Houndour,228,DARK,FIRE,AbilityEnum.EARLYBIRD,AbilityEnum.FLASHFIRE,AbilityEnum.UNNERVE,45,60,30,65,80,5));
        pokeList.add(new Pokemon(Names.Houndoom,229,DARK,FIRE,AbilityEnum.EARLYBIRD,AbilityEnum.FLASHFIRE,AbilityEnum.UNNERVE,75,90,50,95,110,8));
        pokeList.add(new Pokemon(Names.Kingdra,230,WATER,DRAGON,AbilityEnum.SWIFTSWIM,AbilityEnum.SNIPER,AbilityEnum.DAMP,75,95,95,85,95,9));
        pokeList.add(new Pokemon(Names.Phanpy,231,GROUND,NONE,AbilityEnum.PICKUP,AbilityEnum.SANDVEIL,AbilityEnum.NONE,90,60,60,40,40,4));
        pokeList.add(new Pokemon(Names.Donphan,232,GROUND,NONE,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,AbilityEnum.NONE,90,120,120,50,60,6));
        pokeList.add(new Pokemon(Names.Porygon2,233,NORMAL,NONE,AbilityEnum.TRACE,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,85,80,90,60,105,9));
        pokeList.add(new Pokemon(Names.Stantler,234,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FRISK,AbilityEnum.SAPSIPPER,73,95,62,85,85,6));
        pokeList.add(new Pokemon(Names.Smeargle,235,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.TECHNICIAN,AbilityEnum.MOODY,55,20,35,75,20,4));
        pokeList.add(new Pokemon(Names.Tyrogue,236,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.STEADFAST,AbilityEnum.VITALSPIRIT,35,35,35,35,35,3));
        pokeList.add(new Pokemon(Names.Hitmontop,237,FIGHTING,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.TECHNICIAN,AbilityEnum.STEADFAST,50,95,95,70,35,11));
        pokeList.add(new Pokemon(Names.Smoochum,238,ICE,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.FOREWARN,AbilityEnum.HYDRATION,45,30,15,65,85,6));
        pokeList.add(new Pokemon(Names.Elekid,239,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,45,63,37,95,65,5));
        pokeList.add(new Pokemon(Names.Magby,240,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,45,75,37,83,70,5));
        pokeList.add(new Pokemon(Names.Miltank,241,NORMAL,NONE,AbilityEnum.THICKFAT,AbilityEnum.SCRAPPY,AbilityEnum.SAPSIPPER,95,80,105,100,40,7));
        pokeList.add(new Pokemon(Names.Blissey,242,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.HEALER,255,10,10,55,75,13));
        pokeList.add(new Pokemon(Names.Raikou,243,ELECTRIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.VOLTABSORB,AbilityEnum.NONE,90,85,75,115,115,10));
        pokeList.add(new Pokemon(Names.Entei,244,FIRE,NONE,AbilityEnum.PRESSURE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,115,115,85,100,90,7));
        pokeList.add(new Pokemon(Names.Suicune,245,WATER,NONE,AbilityEnum.PRESSURE,AbilityEnum.WATERABSORB,AbilityEnum.NONE,100,75,115,85,90,11));
        pokeList.add(new Pokemon(Names.Larvitar,246,ROCK,GROUND,AbilityEnum.GUTS,AbilityEnum.SANDVEIL,AbilityEnum.NONE,50,64,50,41,45,5));
        pokeList.add(new Pokemon(Names.Pupitar,247,ROCK,GROUND,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,70,84,70,51,65,7));
        pokeList.add(new Pokemon(Names.Tyranitar,248,ROCK,DARK,AbilityEnum.SANDSTREAM,AbilityEnum.UNNERVE,AbilityEnum.NONE,100,134,110,61,95,10));
        pokeList.add(new Pokemon(Names.Lugia,249,PSYCHIC,FLYING,AbilityEnum.PRESSURE,AbilityEnum.MULTISCALE,AbilityEnum.NONE,106,90,130,110,90,15));
        pokeList.add(new Pokemon(Names.Ho_Oh,250,FIRE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,106,130,90,90,110,15));
        pokeList.add(new Pokemon(Names.Celebi,251,PSYCHIC,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Treecko,252,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,40,45,35,70,65,5));
        pokeList.add(new Pokemon(Names.Grovyle,253,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,50,65,45,95,85,6));
        pokeList.add(new Pokemon(Names.Sceptile,254,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,70,85,65,120,105,8));
        pokeList.add(new Pokemon(Names.Torchic,255,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,45,60,40,45,70,5));
        pokeList.add(new Pokemon(Names.Combusken,256,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,60,85,60,55,85,6));
        pokeList.add(new Pokemon(Names.Blaziken,257,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,80,120,70,80,110,7));
        pokeList.add(new Pokemon(Names.Mudkip,258,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,50,70,50,40,50,5));
        pokeList.add(new Pokemon(Names.Marshtomp,259,WATER,GROUND,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,70,85,70,50,60,7));
        pokeList.add(new Pokemon(Names.Swampert,260,WATER,GROUND,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,100,110,90,60,85,9));
        pokeList.add(new Pokemon(Names.Poochyena,261,DARK,NONE,AbilityEnum.RUNAWAY,AbilityEnum.QUICKFEET,AbilityEnum.RATTLED,35,55,35,35,30,3));
        pokeList.add(new Pokemon(Names.Mightyena,262,DARK,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.QUICKFEET,AbilityEnum.MOXIE,70,90,70,70,60,6));
        pokeList.add(new Pokemon(Names.Zigzagoon,263,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.GLUTTONY,AbilityEnum.QUICKFEET,38,30,41,60,30,4));
        pokeList.add(new Pokemon(Names.Linoone,264,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.GLUTTONY,AbilityEnum.QUICKFEET,78,70,61,100,50,6));
        pokeList.add(new Pokemon(Names.Wurmple,265,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,45,45,35,20,20,3));
        pokeList.add(new Pokemon(Names.Silcoon,266,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,50,35,55,15,25,2));
        pokeList.add(new Pokemon(Names.Beautifly,267,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.RIVALRY,AbilityEnum.NONE,60,70,50,65,100,5));
        pokeList.add(new Pokemon(Names.Cascoon,268,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,50,35,55,15,25,2));
        pokeList.add(new Pokemon(Names.Dustox,269,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.NONE,60,50,70,65,50,9));
        pokeList.add(new Pokemon(Names.Lotad,270,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,40,30,30,30,40,5));
        pokeList.add(new Pokemon(Names.Lombre,271,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,60,50,50,50,60,7));
        pokeList.add(new Pokemon(Names.Ludicolo,272,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,80,70,70,70,90,10));
        pokeList.add(new Pokemon(Names.Seedot,273,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,40,40,50,30,30,3));
        pokeList.add(new Pokemon(Names.Nuzleaf,274,GRASS,DARK,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,70,70,40,60,60,4));
        pokeList.add(new Pokemon(Names.Shiftry,275,GRASS,DARK,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,90,100,60,80,90,6));
        pokeList.add(new Pokemon(Names.Taillow,276,NORMAL,FLYING,AbilityEnum.GUTS,AbilityEnum.SCRAPPY,AbilityEnum.NONE,40,55,30,85,30,3));
        pokeList.add(new Pokemon(Names.Swellow,277,NORMAL,FLYING,AbilityEnum.GUTS,AbilityEnum.SCRAPPY,AbilityEnum.NONE,60,85,60,125,50,5));
        pokeList.add(new Pokemon(Names.Wingull,278,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RAINDISH,AbilityEnum.NONE,40,30,30,85,55,3));
        pokeList.add(new Pokemon(Names.Pelipper,279,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RAINDISH,AbilityEnum.NONE,60,50,100,65,85,7));
        pokeList.add(new Pokemon(Names.Ralts,280,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,28,25,25,40,45,3));
        pokeList.add(new Pokemon(Names.Kirlia,281,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,38,35,35,50,65,5));
        pokeList.add(new Pokemon(Names.Gardevoir,282,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,68,65,65,80,125,11));
        pokeList.add(new Pokemon(Names.Surskit,283,BUG,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.NONE,40,30,32,65,50,5));
        pokeList.add(new Pokemon(Names.Masquerain,284,BUG,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.UNNERVE,AbilityEnum.NONE,70,60,62,60,80,8));
        pokeList.add(new Pokemon(Names.Shroomish,285,GRASS,NONE,AbilityEnum.EFFECTSPORE,AbilityEnum.POISONHEAL,AbilityEnum.QUICKFEET,60,40,60,35,40,6));
        pokeList.add(new Pokemon(Names.Breloom,286,GRASS,FIGHTING,AbilityEnum.EFFECTSPORE,AbilityEnum.POISONHEAL,AbilityEnum.TECHNICIAN,60,130,80,70,60,6));
        pokeList.add(new Pokemon(Names.Slakoth,287,NORMAL,NONE,AbilityEnum.TRUANT,AbilityEnum.NONE,AbilityEnum.NONE,60,60,60,30,35,3));
        pokeList.add(new Pokemon(Names.Vigoroth,288,NORMAL,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,AbilityEnum.NONE,80,80,80,90,55,5));
        pokeList.add(new Pokemon(Names.Slaking,289,NORMAL,NONE,AbilityEnum.TRUANT,AbilityEnum.NONE,AbilityEnum.NONE,150,160,100,100,95,6));
        pokeList.add(new Pokemon(Names.Nincada,290,BUG,GROUND,AbilityEnum.COMPOUNDEYES,AbilityEnum.RUNAWAY,AbilityEnum.NONE,31,45,90,40,30,3));
        pokeList.add(new Pokemon(Names.Ninjask,291,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,61,90,45,160,50,5));
        pokeList.add(new Pokemon(Names.Shedinja,292,BUG,GHOST,AbilityEnum.WONDERGUARD,AbilityEnum.NONE,AbilityEnum.NONE,1,90,45,40,30,3));
        pokeList.add(new Pokemon(Names.Whismur,293,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.RATTLED,AbilityEnum.NONE,64,51,23,28,51,2));
        pokeList.add(new Pokemon(Names.Loudred,294,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.SCRAPPY,AbilityEnum.NONE,84,71,43,48,71,4));
        pokeList.add(new Pokemon(Names.Exploud,295,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.SCRAPPY,AbilityEnum.NONE,104,91,63,68,91,7));
        pokeList.add(new Pokemon(Names.Makuhita,296,FIGHTING,NONE,AbilityEnum.THICKFAT,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,72,60,30,25,20,3));
        pokeList.add(new Pokemon(Names.Hariyama,297,FIGHTING,NONE,AbilityEnum.THICKFAT,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,144,120,60,50,40,6));
        pokeList.add(new Pokemon(Names.Azurill,298,NORMAL,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,50,20,40,20,20,4));
        pokeList.add(new Pokemon(Names.Nosepass,299,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.MAGNETPULL,AbilityEnum.SANDFORCE,30,45,135,30,45,9));
        pokeList.add(new Pokemon(Names.Skitty,300,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.NORMALIZE,AbilityEnum.WONDERSKIN,50,45,45,50,35,3));
        pokeList.add(new Pokemon(Names.Delcatty,301,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.NORMALIZE,AbilityEnum.WONDERSKIN,70,65,65,70,55,5));
        pokeList.add(new Pokemon(Names.Sableye,302,DARK,GHOST,AbilityEnum.KEENEYE,AbilityEnum.STALL,AbilityEnum.PRANKSTER,50,75,75,50,65,6));
        pokeList.add(new Pokemon(Names.Mawile,303,STEEL,FAIRY,AbilityEnum.HYPERCUTTER,AbilityEnum.INTIMIDATE,AbilityEnum.SHEERFORCE,50,85,85,50,55,5));
        pokeList.add(new Pokemon(Names.Aron,304,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,50,70,100,30,40,4));
        pokeList.add(new Pokemon(Names.Lairon,305,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,60,90,140,40,50,5));
        pokeList.add(new Pokemon(Names.Aggron,306,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,70,110,180,50,60,6));
        pokeList.add(new Pokemon(Names.Meditite,307,FIGHTING,PSYCHIC,AbilityEnum.PUREPOWER,AbilityEnum.TELEPATHY,AbilityEnum.NONE,30,40,55,60,40,5));
        pokeList.add(new Pokemon(Names.Medicham,308,FIGHTING,PSYCHIC,AbilityEnum.PUREPOWER,AbilityEnum.TELEPATHY,AbilityEnum.NONE,60,60,75,80,60,7));
        pokeList.add(new Pokemon(Names.Electrike,309,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.MINUS,40,45,40,65,65,4));
        pokeList.add(new Pokemon(Names.Manectric,310,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.MINUS,70,75,60,105,105,6));
        pokeList.add(new Pokemon(Names.Plusle,311,ELECTRIC,NONE,AbilityEnum.PLUS,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,60,50,40,95,85,7));
        pokeList.add(new Pokemon(Names.Minun,312,ELECTRIC,NONE,AbilityEnum.MINUS,AbilityEnum.VOLTABSORB,AbilityEnum.NONE,60,40,50,95,75,8));
        pokeList.add(new Pokemon(Names.Volbeat,313,BUG,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.SWARM,AbilityEnum.PRANKSTER,65,73,55,85,47,7));
        pokeList.add(new Pokemon(Names.Illumise,314,BUG,NONE,AbilityEnum.OBLIVIOUS,AbilityEnum.TINTEDLENS,AbilityEnum.PRANKSTER,65,47,55,85,73,7));
        pokeList.add(new Pokemon(Names.Roselia,315,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.LEAFGUARD,50,60,45,65,100,8));
        pokeList.add(new Pokemon(Names.Gulpin,316,POISON,NONE,AbilityEnum.LIQUIDOOZE,AbilityEnum.STICKYHOLD,AbilityEnum.GLUTTONY,70,43,53,40,43,5));
        pokeList.add(new Pokemon(Names.Swalot,317,POISON,NONE,AbilityEnum.LIQUIDOOZE,AbilityEnum.STICKYHOLD,AbilityEnum.GLUTTONY,100,73,83,55,73,8));
        pokeList.add(new Pokemon(Names.Carvanha,318,WATER,DARK,AbilityEnum.ROUGHSKIN,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,45,90,20,65,65,2));
        pokeList.add(new Pokemon(Names.Sharpedo,319,WATER,DARK,AbilityEnum.ROUGHSKIN,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,70,120,40,95,95,4));
        pokeList.add(new Pokemon(Names.Wailmer,320,WATER,NONE,AbilityEnum.WATERVEIL,AbilityEnum.OBLIVIOUS,AbilityEnum.PRESSURE,130,70,35,60,70,3));
        pokeList.add(new Pokemon(Names.Wailord,321,WATER,NONE,AbilityEnum.WATERVEIL,AbilityEnum.OBLIVIOUS,AbilityEnum.PRESSURE,170,90,45,60,90,4));
        pokeList.add(new Pokemon(Names.Numel,322,FIRE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SIMPLE,AbilityEnum.OWNTEMPO,60,60,40,35,65,4));
        pokeList.add(new Pokemon(Names.Camerupt,323,FIRE,GROUND,AbilityEnum.MAGMAARMOR,AbilityEnum.SOLIDROCK,AbilityEnum.ANGERPOINT,70,100,70,40,105,7));
        pokeList.add(new Pokemon(Names.Torkoal,324,FIRE,NONE,AbilityEnum.WHITESMOKE,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,70,85,140,20,85,7));
        pokeList.add(new Pokemon(Names.Spoink,325,PSYCHIC,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.GLUTTONY,60,25,35,60,70,8));
        pokeList.add(new Pokemon(Names.Grumpig,326,PSYCHIC,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.GLUTTONY,80,45,65,80,90,11));
        pokeList.add(new Pokemon(Names.Spinda,327,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.TANGLEDFEET,AbilityEnum.CONTRARY,60,60,60,60,60,6));
        pokeList.add(new Pokemon(Names.Trapinch,328,GROUND,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.ARENATRAP,AbilityEnum.SHEERFORCE,45,100,45,10,45,4));
        pokeList.add(new Pokemon(Names.Vibrava,329,GROUND,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,50,70,50,70,50,5));
        pokeList.add(new Pokemon(Names.Flygon,330,GROUND,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,80,100,80,100,80,8));
        pokeList.add(new Pokemon(Names.Cacnea,331,GRASS,NONE,AbilityEnum.SANDVEIL,AbilityEnum.WATERABSORB,AbilityEnum.NONE,50,85,40,35,85,4));
        pokeList.add(new Pokemon(Names.Cacturne,332,GRASS,DARK,AbilityEnum.SANDVEIL,AbilityEnum.WATERABSORB,AbilityEnum.NONE,70,115,60,55,115,6));
        pokeList.add(new Pokemon(Names.Swablu,333,NORMAL,FLYING,AbilityEnum.NATURALCURE,AbilityEnum.CLOUDNINE,AbilityEnum.NONE,45,40,60,50,40,7));
        pokeList.add(new Pokemon(Names.Altaria,334,DRAGON,FLYING,AbilityEnum.NATURALCURE,AbilityEnum.CLOUDNINE,AbilityEnum.NONE,75,70,90,80,70,10));
        pokeList.add(new Pokemon(Names.Zangoose,335,NORMAL,NONE,AbilityEnum.IMMUNITY,AbilityEnum.TOXICBOOST,AbilityEnum.NONE,73,115,60,90,60,6));
        pokeList.add(new Pokemon(Names.Seviper,336,POISON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,73,100,60,65,100,6));
        pokeList.add(new Pokemon(Names.Lunatone,337,ROCK,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,70,55,65,70,95,8));
        pokeList.add(new Pokemon(Names.Solrock,338,ROCK,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,70,95,85,70,55,6));
        pokeList.add(new Pokemon(Names.Barboach,339,WATER,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.ANTICIPATION,AbilityEnum.HYDRATION,50,48,43,60,46,4));
        pokeList.add(new Pokemon(Names.Whiscash,340,WATER,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.ANTICIPATION,AbilityEnum.HYDRATION,110,78,73,60,76,7));
        pokeList.add(new Pokemon(Names.Corphish,341,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.ADAPTABILITY,43,80,65,35,50,3));
        pokeList.add(new Pokemon(Names.Crawdaunt,342,WATER,DARK,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.ADAPTABILITY,63,120,85,55,90,5));
        pokeList.add(new Pokemon(Names.Baltoy,343,GROUND,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,40,40,55,55,40,7));
        pokeList.add(new Pokemon(Names.Claydol,344,GROUND,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,60,70,105,75,70,12));
        pokeList.add(new Pokemon(Names.Lileep,345,ROCK,GRASS,AbilityEnum.SUCTIONCUPS,AbilityEnum.STORMDRAIN,AbilityEnum.NONE,66,41,77,23,61,8));
        pokeList.add(new Pokemon(Names.Cradily,346,ROCK,GRASS,AbilityEnum.SUCTIONCUPS,AbilityEnum.STORMDRAIN,AbilityEnum.NONE,86,81,97,43,81,10));
        pokeList.add(new Pokemon(Names.Anorith,347,ROCK,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,45,95,50,75,40,5));
        pokeList.add(new Pokemon(Names.Armaldo,348,ROCK,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,75,125,100,45,70,8));
        pokeList.add(new Pokemon(Names.Feebas,349,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.ADAPTABILITY,AbilityEnum.NONE,20,15,20,80,10,5));
        pokeList.add(new Pokemon(Names.Milotic,350,WATER,NONE,AbilityEnum.MARVELSCALE,AbilityEnum.COMPETITIVE,AbilityEnum.CUTECHARM,95,60,79,81,100,12));
        pokeList.add(new Pokemon(Names.Castform,351,NORMAL,NONE,AbilityEnum.FORECAST,AbilityEnum.NONE,AbilityEnum.NONE,70,70,70,70,70,7));
        pokeList.add(new Pokemon(Names.Kecleon,352,NORMAL,NONE,AbilityEnum.COLORCHANGE,AbilityEnum.PROTEAN,AbilityEnum.NONE,60,90,70,40,60,12));
        pokeList.add(new Pokemon(Names.Shuppet,353,GHOST,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FRISK,AbilityEnum.CURSEDBODY,44,75,35,45,63,3));
        pokeList.add(new Pokemon(Names.Banette,354,GHOST,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FRISK,AbilityEnum.CURSEDBODY,64,115,65,65,83,6));
        pokeList.add(new Pokemon(Names.Duskull,355,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.FRISK,AbilityEnum.NONE,20,40,90,25,30,9));
        pokeList.add(new Pokemon(Names.Dusclops,356,GHOST,NONE,AbilityEnum.PRESSURE,AbilityEnum.FRISK,AbilityEnum.NONE,40,70,130,25,60,13));
        pokeList.add(new Pokemon(Names.Tropius,357,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.HARVEST,99,68,83,51,72,8));
        pokeList.add(new Pokemon(Names.Chimecho,358,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,65,50,70,65,95,8));
        pokeList.add(new Pokemon(Names.Absol,359,DARK,NONE,AbilityEnum.PRESSURE,AbilityEnum.SUPERLUCK,AbilityEnum.JUSTIFIED,65,130,60,75,75,6));
        pokeList.add(new Pokemon(Names.Wynaut,360,PSYCHIC,NONE,AbilityEnum.SHADOWTAG,AbilityEnum.TELEPATHY,AbilityEnum.NONE,95,23,48,23,23,4));
        pokeList.add(new Pokemon(Names.Snorunt,361,ICE,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.ICEBODY,AbilityEnum.MOODY,50,50,50,50,50,5));
        pokeList.add(new Pokemon(Names.Glalie,362,ICE,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.ICEBODY,AbilityEnum.MOODY,80,80,80,80,80,8));
        pokeList.add(new Pokemon(Names.Spheal,363,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,70,40,50,25,55,5));
        pokeList.add(new Pokemon(Names.Sealeo,364,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,90,60,70,45,75,7));
        pokeList.add(new Pokemon(Names.Walrein,365,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,110,80,90,65,95,9));
        pokeList.add(new Pokemon(Names.Clamperl,366,WATER,NONE,AbilityEnum.SHELLARMOR,AbilityEnum.RATTLED,AbilityEnum.NONE,35,64,85,32,74,5));
        pokeList.add(new Pokemon(Names.Huntail,367,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,55,104,105,52,94,7));
        pokeList.add(new Pokemon(Names.Gorebyss,368,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.NONE,55,84,105,52,114,7));
        pokeList.add(new Pokemon(Names.Relicanth,369,WATER,ROCK,AbilityEnum.SWIFTSWIM,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,100,90,130,55,45,6));
        pokeList.add(new Pokemon(Names.Luvdisc,370,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.NONE,43,30,55,97,40,6));
        pokeList.add(new Pokemon(Names.Bagon,371,DRAGON,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,45,75,60,50,40,3));
        pokeList.add(new Pokemon(Names.Shelgon,372,DRAGON,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.OVERCOAT,AbilityEnum.NONE,65,95,100,50,60,5));
        pokeList.add(new Pokemon(Names.Salamence,373,DRAGON,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.NONE,95,135,80,100,110,8));
        pokeList.add(new Pokemon(Names.Beldum,374,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,40,55,80,30,35,6));
        pokeList.add(new Pokemon(Names.Metang,375,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,60,75,100,50,55,8));
        pokeList.add(new Pokemon(Names.Metagross,376,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,80,135,130,70,95,9));
        pokeList.add(new Pokemon(Names.Regirock,377,ROCK,NONE,AbilityEnum.CLEARBODY,AbilityEnum.STURDY,AbilityEnum.NONE,80,100,200,50,50,10));
        pokeList.add(new Pokemon(Names.Regice,378,ICE,NONE,AbilityEnum.CLEARBODY,AbilityEnum.ICEBODY,AbilityEnum.NONE,80,50,100,50,100,20));
        pokeList.add(new Pokemon(Names.Registeel,379,STEEL,NONE,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,80,75,150,50,75,15));
        pokeList.add(new Pokemon(Names.Latias,380,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,80,80,90,110,110,13));
        pokeList.add(new Pokemon(Names.Latios,381,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,80,90,80,110,130,11));
        pokeList.add(new Pokemon(Names.Kyogre,382,WATER,NONE,AbilityEnum.DRIZZLE,AbilityEnum.NONE,AbilityEnum.NONE,100,100,90,90,150,14));
        pokeList.add(new Pokemon(Names.Groudon,383,GROUND,NONE,AbilityEnum.DROUGHT,AbilityEnum.NONE,AbilityEnum.NONE,100,150,140,90,100,9));
        pokeList.add(new Pokemon(Names.Rayquaza,384,DRAGON,FLYING,AbilityEnum.AIRLOCK,AbilityEnum.NONE,AbilityEnum.NONE,105,150,90,95,150,9));
        pokeList.add(new Pokemon(Names.Jirachi,385,STEEL,PSYCHIC,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Deoxys,386,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,50,150,50,150,150,5));
        pokeList.add(new Pokemon(Names.Turtwig,387,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,55,68,64,31,45,5));
        pokeList.add(new Pokemon(Names.Grotle,388,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,75,89,85,36,55,6));
        pokeList.add(new Pokemon(Names.Torterra,389,GRASS,GROUND,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,95,109,105,56,75,8));
        pokeList.add(new Pokemon(Names.Chimchar,390,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,44,58,44,61,58,4));
        pokeList.add(new Pokemon(Names.Monferno,391,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,64,78,52,81,78,5));
        pokeList.add(new Pokemon(Names.Infernape,392,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,76,104,71,108,104,7));
        pokeList.add(new Pokemon(Names.Piplup,393,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,53,51,53,40,61,5));
        pokeList.add(new Pokemon(Names.Prinplup,394,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,64,66,68,50,81,7));
        pokeList.add(new Pokemon(Names.Empoleon,395,WATER,STEEL,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,84,86,88,60,111,10));
        pokeList.add(new Pokemon(Names.Starly,396,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RECKLESS,AbilityEnum.NONE,40,55,30,60,30,3));
        pokeList.add(new Pokemon(Names.Staravia,397,NORMAL,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.RECKLESS,AbilityEnum.NONE,55,75,50,80,40,4));
        pokeList.add(new Pokemon(Names.Staraptor,398,NORMAL,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.RECKLESS,AbilityEnum.NONE,85,120,70,100,50,6));
        pokeList.add(new Pokemon(Names.Bidoof,399,NORMAL,NONE,AbilityEnum.SIMPLE,AbilityEnum.UNAWARE,AbilityEnum.MOODY,59,45,40,31,35,4));
        pokeList.add(new Pokemon(Names.Bibarel,400,NORMAL,WATER,AbilityEnum.SIMPLE,AbilityEnum.UNAWARE,AbilityEnum.MOODY,79,85,60,71,55,6));
        pokeList.add(new Pokemon(Names.Kricketot,401,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.RUNAWAY,AbilityEnum.NONE,37,25,41,25,25,4));
        pokeList.add(new Pokemon(Names.Kricketune,402,BUG,NONE,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.NONE,77,85,51,65,55,5));
        pokeList.add(new Pokemon(Names.Shinx,403,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,45,65,34,45,40,3));
        pokeList.add(new Pokemon(Names.Luxio,404,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,60,85,49,60,60,4));
        pokeList.add(new Pokemon(Names.Luxray,405,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,80,120,79,70,95,7));
        pokeList.add(new Pokemon(Names.Budew,406,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.LEAFGUARD,40,30,35,55,50,7));
        pokeList.add(new Pokemon(Names.Roserade,407,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.TECHNICIAN,60,70,65,90,125,10));
        pokeList.add(new Pokemon(Names.Cranidos,408,ROCK,NONE,AbilityEnum.MOLDBREAKER,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,67,125,40,58,30,3));
        pokeList.add(new Pokemon(Names.Rampardos,409,ROCK,NONE,AbilityEnum.MOLDBREAKER,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,97,165,60,58,65,5));
        pokeList.add(new Pokemon(Names.Shieldon,410,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,30,42,118,30,42,8));
        pokeList.add(new Pokemon(Names.Bastiodon,411,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,60,52,168,30,47,13));
        pokeList.add(new Pokemon(Names.Burmy,412,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.OVERCOAT,AbilityEnum.NONE,40,29,45,36,29,4));
        pokeList.add(new Pokemon(Names.Wormadam,413,BUG,GRASS,AbilityEnum.ANTICIPATION,AbilityEnum.OVERCOAT,AbilityEnum.NONE,60,59,85,36,79,10));
        pokeList.add(new Pokemon(Names.Mothim,414,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.TINTEDLENS,AbilityEnum.NONE,70,94,50,66,94,5));
        pokeList.add(new Pokemon(Names.Combee,415,BUG,FLYING,AbilityEnum.HONEYGATHER,AbilityEnum.HUSTLE,AbilityEnum.NONE,30,30,42,70,30,4));
        pokeList.add(new Pokemon(Names.Vespiquen,416,BUG,FLYING,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,AbilityEnum.NONE,70,80,102,40,80,10));
        pokeList.add(new Pokemon(Names.Pachirisu,417,ELECTRIC,NONE,AbilityEnum.RUNAWAY,AbilityEnum.PICKUP,AbilityEnum.VOLTABSORB,60,45,70,95,45,9));
        pokeList.add(new Pokemon(Names.Buizel,418,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,55,65,35,85,60,3));
        pokeList.add(new Pokemon(Names.Floatzel,419,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,85,105,55,115,85,5));
        pokeList.add(new Pokemon(Names.Cherubi,420,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,AbilityEnum.NONE,45,35,45,35,62,5));
        pokeList.add(new Pokemon(Names.Cherrim,421,GRASS,NONE,AbilityEnum.FLOWERGIFT,AbilityEnum.NONE,AbilityEnum.NONE,70,60,70,85,87,7));
        pokeList.add(new Pokemon(Names.Shellos,422,WATER,NONE,AbilityEnum.STICKYHOLD,AbilityEnum.STORMDRAIN,AbilityEnum.SANDFORCE,76,48,48,34,57,6));
        pokeList.add(new Pokemon(Names.Gastrodon,423,WATER,GROUND,AbilityEnum.STICKYHOLD,AbilityEnum.STORMDRAIN,AbilityEnum.SANDFORCE,111,83,68,39,92,8));
        pokeList.add(new Pokemon(Names.Ambipom,424,NORMAL,NONE,AbilityEnum.TECHNICIAN,AbilityEnum.PICKUP,AbilityEnum.SKILLLINK,75,100,66,115,60,6));
        pokeList.add(new Pokemon(Names.Drifloon,425,GHOST,FLYING,AbilityEnum.AFTERMATH,AbilityEnum.UNBURDEN,AbilityEnum.FLAREBOOST,90,50,34,70,60,4));
        pokeList.add(new Pokemon(Names.Drifblim,426,GHOST,FLYING,AbilityEnum.AFTERMATH,AbilityEnum.UNBURDEN,AbilityEnum.FLAREBOOST,150,80,44,80,90,5));
        pokeList.add(new Pokemon(Names.Buneary,427,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KLUTZ,AbilityEnum.LIMBER,55,66,44,85,44,5));
        pokeList.add(new Pokemon(Names.Lopunny,428,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.KLUTZ,AbilityEnum.LIMBER,65,76,84,105,54,9));
        pokeList.add(new Pokemon(Names.Mismagius,429,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,60,60,60,105,105,10));
        pokeList.add(new Pokemon(Names.Honchkrow,430,DARK,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.SUPERLUCK,AbilityEnum.MOXIE,100,125,52,71,105,5));
        pokeList.add(new Pokemon(Names.Glameow,431,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.OWNTEMPO,AbilityEnum.KEENEYE,49,55,42,85,42,3));
        pokeList.add(new Pokemon(Names.Purugly,432,NORMAL,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.DEFIANT,71,82,64,112,64,5));
        pokeList.add(new Pokemon(Names.Chingling,433,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,45,30,50,45,65,5));
        pokeList.add(new Pokemon(Names.Stunky,434,POISON,DARK,AbilityEnum.STENCH,AbilityEnum.AFTERMATH,AbilityEnum.KEENEYE,63,63,47,74,41,4));
        pokeList.add(new Pokemon(Names.Skuntank,435,POISON,DARK,AbilityEnum.STENCH,AbilityEnum.AFTERMATH,AbilityEnum.KEENEYE,103,93,67,84,71,6));
        pokeList.add(new Pokemon(Names.Bronzor,436,STEEL,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.HEATPROOF,AbilityEnum.HEAVYMETAL,57,24,86,23,24,8));
        pokeList.add(new Pokemon(Names.Bronzong,437,STEEL,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.HEATPROOF,AbilityEnum.HEAVYMETAL,67,89,116,33,79,11));
        pokeList.add(new Pokemon(Names.Bonsly,438,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.RATTLED,50,80,95,10,10,4));
        pokeList.add(new Pokemon(Names.Mime_Jr,439,PSYCHIC,FAIRY,AbilityEnum.SOUNDPROOF,AbilityEnum.FILTER,AbilityEnum.TECHNICIAN,20,25,45,60,70,9));
        pokeList.add(new Pokemon(Names.Happiny,440,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.FRIENDGUARD,100,5,5,30,15,6));
        pokeList.add(new Pokemon(Names.Chatot,441,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,76,65,45,91,92,4));
        pokeList.add(new Pokemon(Names.Spiritomb,442,GHOST,DARK,AbilityEnum.PRESSURE,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,50,92,108,35,92,10));
        pokeList.add(new Pokemon(Names.Gible,443,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,58,70,45,42,40,4));
        pokeList.add(new Pokemon(Names.Gabite,444,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,68,90,65,82,50,5));
        pokeList.add(new Pokemon(Names.Garchomp,445,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,108,130,95,102,80,8));
        pokeList.add(new Pokemon(Names.Munchlax,446,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.THICKFAT,AbilityEnum.GLUTTONY,135,85,40,5,40,8));
        pokeList.add(new Pokemon(Names.Riolu,447,FIGHTING,NONE,AbilityEnum.STEADFAST,AbilityEnum.INNERFOCUS,AbilityEnum.PRANKSTER,40,70,40,60,35,4));
        pokeList.add(new Pokemon(Names.Lucario,448,FIGHTING,STEEL,AbilityEnum.STEADFAST,AbilityEnum.INNERFOCUS,AbilityEnum.JUSTIFIED,70,110,70,90,115,7));
        pokeList.add(new Pokemon(Names.Hippopotas,449,GROUND,NONE,AbilityEnum.SANDSTREAM,AbilityEnum.SANDFORCE,AbilityEnum.NONE,68,72,78,32,38,4));
        pokeList.add(new Pokemon(Names.Hippowdon,450,GROUND,NONE,AbilityEnum.SANDSTREAM,AbilityEnum.SANDFORCE,AbilityEnum.NONE,108,112,118,47,68,7));
        pokeList.add(new Pokemon(Names.Skorupi,451,POISON,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SNIPER,AbilityEnum.KEENEYE,40,50,90,65,30,5));
        pokeList.add(new Pokemon(Names.Drapion,452,POISON,DARK,AbilityEnum.BATTLEARMOR,AbilityEnum.SNIPER,AbilityEnum.KEENEYE,70,90,110,95,60,7));
        pokeList.add(new Pokemon(Names.Croagunk,453,POISON,FIGHTING,AbilityEnum.ANTICIPATION,AbilityEnum.DRYSKIN,AbilityEnum.POISONTOUCH,48,61,40,50,61,4));
        pokeList.add(new Pokemon(Names.Toxicroak,454,POISON,FIGHTING,AbilityEnum.ANTICIPATION,AbilityEnum.DRYSKIN,AbilityEnum.POISONTOUCH,83,106,65,85,86,6));
        pokeList.add(new Pokemon(Names.Carnivine,455,GRASS,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,74,100,72,46,90,7));
        pokeList.add(new Pokemon(Names.Finneon,456,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.STORMDRAIN,AbilityEnum.WATERVEIL,49,49,56,66,49,6));
        pokeList.add(new Pokemon(Names.Lumineon,457,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.STORMDRAIN,AbilityEnum.WATERVEIL,69,69,76,91,69,8));
        pokeList.add(new Pokemon(Names.Mantyke,458,WATER,FLYING,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERABSORB,AbilityEnum.WATERVEIL,45,20,50,50,60,12));
        pokeList.add(new Pokemon(Names.Snover,459,GRASS,ICE,AbilityEnum.SNOWWARNING,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,60,62,50,40,62,6));
        pokeList.add(new Pokemon(Names.Abomasnow,460,GRASS,ICE,AbilityEnum.SNOWWARNING,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,90,92,75,60,92,8));
        pokeList.add(new Pokemon(Names.Weavile,461,DARK,ICE,AbilityEnum.PRESSURE,AbilityEnum.PICKPOCKET,AbilityEnum.NONE,70,120,65,125,45,8));
        pokeList.add(new Pokemon(Names.Magnezone,462,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,70,70,115,60,130,9));
        pokeList.add(new Pokemon(Names.Lickilicky,463,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.OBLIVIOUS,AbilityEnum.CLOUDNINE,110,85,95,50,80,9));
        pokeList.add(new Pokemon(Names.Rhyperior,464,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.SOLIDROCK,AbilityEnum.RECKLESS,115,140,130,40,55,5));
        pokeList.add(new Pokemon(Names.Tangrowth,465,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.REGENERATOR,100,100,125,50,110,5));
        pokeList.add(new Pokemon(Names.Electivire,466,ELECTRIC,NONE,AbilityEnum.MOTORDRIVE,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,75,123,67,95,95,8));
        pokeList.add(new Pokemon(Names.Magmortar,467,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,75,95,67,83,125,9));
        pokeList.add(new Pokemon(Names.Togekiss,468,FAIRY,FLYING,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,85,50,95,80,120,11));
        pokeList.add(new Pokemon(Names.Yanmega,469,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.TINTEDLENS,AbilityEnum.FRISK,86,76,86,95,116,5));
        pokeList.add(new Pokemon(Names.Leafeon,470,GRASS,NONE,AbilityEnum.LEAFGUARD,AbilityEnum.LEAFGUARD,AbilityEnum.CHLOROPHYLL,65,110,130,95,60,6));
        pokeList.add(new Pokemon(Names.Glaceon,471,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.SNOWCLOAK,AbilityEnum.ICEBODY,65,60,110,65,130,9));
        pokeList.add(new Pokemon(Names.Gliscor,472,GROUND,FLYING,AbilityEnum.HYPERCUTTER,AbilityEnum.SANDVEIL,AbilityEnum.POISONHEAL,75,95,125,95,45,7));
        pokeList.add(new Pokemon(Names.Mamoswine,473,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,110,130,80,80,70,6));
        pokeList.add(new Pokemon(Names.Porygon_Z,474,NORMAL,NONE,AbilityEnum.ADAPTABILITY,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,85,80,70,90,135,7));
        pokeList.add(new Pokemon(Names.Gallade,475,PSYCHIC,FIGHTING,AbilityEnum.STEADFAST,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,68,125,65,80,65,11));
        pokeList.add(new Pokemon(Names.Probopass,476,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.MAGNETPULL,AbilityEnum.SANDFORCE,60,55,145,40,75,15));
        pokeList.add(new Pokemon(Names.Dusknoir,477,GHOST,NONE,AbilityEnum.PRESSURE,AbilityEnum.FRISK,AbilityEnum.NONE,45,100,135,45,65,13));
        pokeList.add(new Pokemon(Names.Froslass,478,ICE,GHOST,AbilityEnum.SNOWCLOAK,AbilityEnum.CURSEDBODY,AbilityEnum.NONE,70,80,70,110,80,7));
        pokeList.add(new Pokemon(Names.Rotom,479,ELECTRIC,GHOST,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,50,50,77,91,95,7));
        pokeList.add(new Pokemon(Names.Uxie,480,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,75,75,130,95,75,13));
        pokeList.add(new Pokemon(Names.Mesprit,481,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,80,105,105,80,105,10));
        pokeList.add(new Pokemon(Names.Azelf,482,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,75,125,70,115,125,7));
        pokeList.add(new Pokemon(Names.Dialga,483,STEEL,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,100,120,120,90,150,10));
        pokeList.add(new Pokemon(Names.Palkia,484,WATER,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,90,120,100,100,150,12));
        pokeList.add(new Pokemon(Names.Heatran,485,FIRE,STEEL,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.NONE,91,90,106,77,130,10));
        pokeList.add(new Pokemon(Names.Regigigas,486,NORMAL,NONE,AbilityEnum.SLOWSTART,AbilityEnum.NONE,AbilityEnum.NONE,110,160,110,100,80,11));
        pokeList.add(new Pokemon(Names.Giratina,487,GHOST,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,150,100,120,90,100,12));
        pokeList.add(new Pokemon(Names.Cresselia,488,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,120,70,120,85,75,13));
        pokeList.add(new Pokemon(Names.Phione,489,WATER,NONE,AbilityEnum.HYDRATION,AbilityEnum.NONE,AbilityEnum.NONE,80,80,80,80,80,8));
        pokeList.add(new Pokemon(Names.Manaphy,490,WATER,NONE,AbilityEnum.HYDRATION,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Darkrai,491,DARK,NONE,AbilityEnum.BADDREAMS,AbilityEnum.NONE,AbilityEnum.NONE,70,90,90,125,135,9));
        pokeList.add(new Pokemon(Names.Shaymin,492,GRASS,NONE,AbilityEnum.NATURALCURE,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Arceus,493,NORMAL,NONE,AbilityEnum.MULTITYPE,AbilityEnum.NONE,AbilityEnum.NONE,120,120,120,120,120,12));
        pokeList.add(new Pokemon(Names.Victini,494,PSYCHIC,FIRE,AbilityEnum.VICTORYSTAR,AbilityEnum.NONE,AbilityEnum.NONE,100,100,100,100,100,10));
        pokeList.add(new Pokemon(Names.Snivy,495,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,45,45,55,63,45,5));
        pokeList.add(new Pokemon(Names.Servine,496,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,60,60,75,83,60,7));
        pokeList.add(new Pokemon(Names.Serperior,497,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,75,75,95,113,75,9));
        pokeList.add(new Pokemon(Names.Tepig,498,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.THICKFAT,AbilityEnum.NONE,65,63,45,45,45,4));
        pokeList.add(new Pokemon(Names.Pignite,499,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.THICKFAT,AbilityEnum.NONE,90,93,55,55,70,5));
        pokeList.add(new Pokemon(Names.Emboar,500,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.RECKLESS,AbilityEnum.NONE,110,123,65,65,100,6));
        pokeList.add(new Pokemon(Names.Oshawott,501,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,55,55,45,45,63,4));
        pokeList.add(new Pokemon(Names.Dewott,502,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,75,75,60,60,83,6));
        pokeList.add(new Pokemon(Names.Samurott,503,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,95,100,85,70,108,7));
        pokeList.add(new Pokemon(Names.Patrat,504,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.ANALYTIC,45,55,39,42,35,3));
        pokeList.add(new Pokemon(Names.Watchog,505,NORMAL,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.KEENEYE,AbilityEnum.ANALYTIC,60,85,69,77,60,6));
        pokeList.add(new Pokemon(Names.Lillipup,506,NORMAL,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.PICKUP,AbilityEnum.RUNAWAY,45,60,45,55,25,4));
        pokeList.add(new Pokemon(Names.Herdier,507,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SANDRUSH,AbilityEnum.SCRAPPY,65,80,65,60,35,6));
        pokeList.add(new Pokemon(Names.Stoutland,508,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SANDRUSH,AbilityEnum.SCRAPPY,85,110,90,80,45,9));
        pokeList.add(new Pokemon(Names.Purrloin,509,DARK,NONE,AbilityEnum.LIMBER,AbilityEnum.UNBURDEN,AbilityEnum.PRANKSTER,41,50,37,66,50,3));
        pokeList.add(new Pokemon(Names.Liepard,510,DARK,NONE,AbilityEnum.LIMBER,AbilityEnum.UNBURDEN,AbilityEnum.PRANKSTER,64,88,50,106,88,5));
        pokeList.add(new Pokemon(Names.Pansage,511,GRASS,NONE,AbilityEnum.GLUTTONY,AbilityEnum.OVERGROW,AbilityEnum.NONE,50,53,48,64,53,4));
        pokeList.add(new Pokemon(Names.Simisage,512,GRASS,NONE,AbilityEnum.GLUTTONY,AbilityEnum.OVERGROW,AbilityEnum.NONE,75,98,63,101,98,6));
        pokeList.add(new Pokemon(Names.Pansear,513,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.BLAZE,AbilityEnum.NONE,50,53,48,64,53,4));
        pokeList.add(new Pokemon(Names.Simisear,514,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.BLAZE,AbilityEnum.NONE,75,98,63,101,98,6));
        pokeList.add(new Pokemon(Names.Panpour,515,WATER,NONE,AbilityEnum.GLUTTONY,AbilityEnum.TORRENT,AbilityEnum.NONE,50,53,48,64,53,4));
        pokeList.add(new Pokemon(Names.Simipour,516,WATER,NONE,AbilityEnum.GLUTTONY,AbilityEnum.TORRENT,AbilityEnum.NONE,75,98,63,101,98,6));
        pokeList.add(new Pokemon(Names.Munna,517,PSYCHIC,NONE,AbilityEnum.FOREWARN,AbilityEnum.SYNCHRONIZE,AbilityEnum.TELEPATHY,76,25,45,24,67,5));
        pokeList.add(new Pokemon(Names.Musharna,518,PSYCHIC,NONE,AbilityEnum.FOREWARN,AbilityEnum.SYNCHRONIZE,AbilityEnum.TELEPATHY,116,55,85,29,107,9));
        pokeList.add(new Pokemon(Names.Pidove,519,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,50,55,50,43,36,3));
        pokeList.add(new Pokemon(Names.Tranquill,520,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,62,77,62,65,50,4));
        pokeList.add(new Pokemon(Names.Unfezant,521,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,80,115,80,93,65,5));
        pokeList.add(new Pokemon(Names.Blitzle,522,ELECTRIC,NONE,AbilityEnum.LIGHTNINGROD,AbilityEnum.MOTORDRIVE,AbilityEnum.SAPSIPPER,45,60,32,76,50,3));
        pokeList.add(new Pokemon(Names.Zebstrika,523,ELECTRIC,NONE,AbilityEnum.LIGHTNINGROD,AbilityEnum.MOTORDRIVE,AbilityEnum.SAPSIPPER,75,100,63,116,80,6));
        pokeList.add(new Pokemon(Names.Roggenrola,524,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,55,75,85,15,25,2));
        pokeList.add(new Pokemon(Names.Boldore,525,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,70,105,105,20,50,4));
        pokeList.add(new Pokemon(Names.Gigalith,526,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,85,135,130,25,60,8));
        pokeList.add(new Pokemon(Names.Woobat,527,PSYCHIC,FLYING,AbilityEnum.UNAWARE,AbilityEnum.KLUTZ,AbilityEnum.SIMPLE,55,45,43,72,55,4));
        pokeList.add(new Pokemon(Names.Swoobat,528,PSYCHIC,FLYING,AbilityEnum.UNAWARE,AbilityEnum.KLUTZ,AbilityEnum.SIMPLE,67,57,55,114,77,5));
        pokeList.add(new Pokemon(Names.Drilbur,529,GROUND,NONE,AbilityEnum.SANDRUSH,AbilityEnum.SANDFORCE,AbilityEnum.MOLDBREAKER,60,85,40,68,30,4));
        pokeList.add(new Pokemon(Names.Excadrill,530,GROUND,STEEL,AbilityEnum.SANDRUSH,AbilityEnum.SANDFORCE,AbilityEnum.MOLDBREAKER,110,135,60,88,50,6));
        pokeList.add(new Pokemon(Names.Audino,531,NORMAL,NONE,AbilityEnum.HEALER,AbilityEnum.REGENERATOR,AbilityEnum.KLUTZ,103,60,86,50,60,8));
        pokeList.add(new Pokemon(Names.Timburr,532,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,75,80,55,35,25,3));
        pokeList.add(new Pokemon(Names.Gurdurr,533,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,85,105,85,40,40,5));
        pokeList.add(new Pokemon(Names.Conkeldurr,534,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,105,140,95,45,55,6));
        pokeList.add(new Pokemon(Names.Tympole,535,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.WATERABSORB,50,50,40,64,50,4));
        pokeList.add(new Pokemon(Names.Palpitoad,536,WATER,GROUND,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.WATERABSORB,75,65,55,69,65,5));
        pokeList.add(new Pokemon(Names.Seismitoad,537,WATER,GROUND,AbilityEnum.SWIFTSWIM,AbilityEnum.POISONTOUCH,AbilityEnum.WATERABSORB,105,95,75,74,85,7));
        pokeList.add(new Pokemon(Names.Throh,538,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.INNERFOCUS,AbilityEnum.MOLDBREAKER,120,100,85,45,30,8));
        pokeList.add(new Pokemon(Names.Sawk,539,FIGHTING,NONE,AbilityEnum.STURDY,AbilityEnum.INNERFOCUS,AbilityEnum.MOLDBREAKER,75,125,75,85,30,7));
        pokeList.add(new Pokemon(Names.Sewaddle,540,BUG,GRASS,AbilityEnum.SWARM,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,45,53,70,42,40,6));
        pokeList.add(new Pokemon(Names.Swadloon,541,BUG,GRASS,AbilityEnum.LEAFGUARD,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,55,63,90,42,50,8));
        pokeList.add(new Pokemon(Names.Leavanny,542,BUG,GRASS,AbilityEnum.SWARM,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,75,103,80,92,70,8));
        pokeList.add(new Pokemon(Names.Venipede,543,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,30,45,59,57,30,3));
        pokeList.add(new Pokemon(Names.Whirlipede,544,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,40,55,99,47,40,7));
        pokeList.add(new Pokemon(Names.Scolipede,545,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,60,100,89,112,55,6));
        pokeList.add(new Pokemon(Names.Cottonee,546,GRASS,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.INFILTRATOR,AbilityEnum.CHLOROPHYLL,40,27,60,66,37,5));
        pokeList.add(new Pokemon(Names.Whimsicott,547,GRASS,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.INFILTRATOR,AbilityEnum.CHLOROPHYLL,60,67,85,116,77,7));
        pokeList.add(new Pokemon(Names.Petilil,548,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.OWNTEMPO,AbilityEnum.LEAFGUARD,45,35,50,30,70,5));
        pokeList.add(new Pokemon(Names.Lilligant,549,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.OWNTEMPO,AbilityEnum.LEAFGUARD,70,60,75,90,110,7));
        pokeList.add(new Pokemon(Names.Basculin,550,WATER,NONE,AbilityEnum.RECKLESS,AbilityEnum.ADAPTABILITY,AbilityEnum.MOLDBREAKER,70,92,65,98,80,5));
        pokeList.add(new Pokemon(Names.Sandile,551,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,50,72,35,65,35,3));
        pokeList.add(new Pokemon(Names.Krokorok,552,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,60,82,45,74,45,4));
        pokeList.add(new Pokemon(Names.Krookodile,553,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,95,117,80,92,65,7));
        pokeList.add(new Pokemon(Names.Darumaka,554,FIRE,NONE,AbilityEnum.HUSTLE,AbilityEnum.INNERFOCUS,AbilityEnum.NONE,70,90,45,50,15,4));
        pokeList.add(new Pokemon(Names.Darmanitan,555,FIRE,NONE,AbilityEnum.SHEERFORCE,AbilityEnum.ZENMODE,AbilityEnum.NONE,105,140,55,95,30,5));
        pokeList.add(new Pokemon(Names.Maractus,556,GRASS,NONE,AbilityEnum.WATERABSORB,AbilityEnum.CHLOROPHYLL,AbilityEnum.STORMDRAIN,75,86,67,60,106,6));
        pokeList.add(new Pokemon(Names.Dwebble,557,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,50,65,85,55,35,3));
        pokeList.add(new Pokemon(Names.Crustle,558,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,70,95,125,45,65,7));
        pokeList.add(new Pokemon(Names.Scraggy,559,DARK,FIGHTING,AbilityEnum.SHEDSKIN,AbilityEnum.MOXIE,AbilityEnum.INTIMIDATE,50,75,70,48,35,7));
        pokeList.add(new Pokemon(Names.Scrafty,560,DARK,FIGHTING,AbilityEnum.SHEDSKIN,AbilityEnum.MOXIE,AbilityEnum.INTIMIDATE,65,90,115,58,45,11));
        pokeList.add(new Pokemon(Names.Sigilyph,561,PSYCHIC,FLYING,AbilityEnum.WONDERSKIN,AbilityEnum.MAGICGUARD,AbilityEnum.TINTEDLENS,72,58,80,97,103,8));
        pokeList.add(new Pokemon(Names.Yamask,562,GHOST,NONE,AbilityEnum.MUMMY,AbilityEnum.NONE,AbilityEnum.NONE,38,30,85,30,55,6));
        pokeList.add(new Pokemon(Names.Cofagrigus,563,GHOST,NONE,AbilityEnum.MUMMY,AbilityEnum.NONE,AbilityEnum.NONE,58,50,145,30,95,10));
        pokeList.add(new Pokemon(Names.Tirtouga,564,WATER,ROCK,AbilityEnum.SOLIDROCK,AbilityEnum.STURDY,AbilityEnum.SWIFTSWIM,54,78,103,22,53,4));
        pokeList.add(new Pokemon(Names.Carracosta,565,WATER,ROCK,AbilityEnum.SOLIDROCK,AbilityEnum.STURDY,AbilityEnum.SWIFTSWIM,74,108,133,32,83,6));
        pokeList.add(new Pokemon(Names.Archen,566,ROCK,FLYING,AbilityEnum.DEFEATIST,AbilityEnum.NONE,AbilityEnum.NONE,55,112,45,70,74,4));
        pokeList.add(new Pokemon(Names.Archeops,567,ROCK,FLYING,AbilityEnum.DEFEATIST,AbilityEnum.NONE,AbilityEnum.NONE,75,140,65,110,112,6));
        pokeList.add(new Pokemon(Names.Trubbish,568,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.AFTERMATH,50,50,62,65,40,6));
        pokeList.add(new Pokemon(Names.Garbodor,569,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.WEAKARMOR,AbilityEnum.AFTERMATH,80,95,82,75,60,8));
        pokeList.add(new Pokemon(Names.Zorua,570,DARK,NONE,AbilityEnum.ILLUSION,AbilityEnum.NONE,AbilityEnum.NONE,40,65,40,65,80,4));
        pokeList.add(new Pokemon(Names.Zoroark,571,DARK,NONE,AbilityEnum.ILLUSION,AbilityEnum.NONE,AbilityEnum.NONE,60,105,60,105,120,6));
        pokeList.add(new Pokemon(Names.Minccino,572,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.TECHNICIAN,AbilityEnum.SKILLLINK,55,50,40,75,40,4));
        pokeList.add(new Pokemon(Names.Cinccino,573,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.TECHNICIAN,AbilityEnum.SKILLLINK,75,95,60,115,65,6));
        pokeList.add(new Pokemon(Names.Gothita,574,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,45,30,50,45,55,6));
        pokeList.add(new Pokemon(Names.Gothorita,575,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,60,45,70,55,75,8));
        pokeList.add(new Pokemon(Names.Gothitelle,576,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,70,55,95,65,95,11));
        pokeList.add(new Pokemon(Names.Solosis,577,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,45,30,40,20,105,5));
        pokeList.add(new Pokemon(Names.Duosion,578,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,65,40,50,30,125,6));
        pokeList.add(new Pokemon(Names.Reuniclus,579,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,110,65,75,30,125,8));
        pokeList.add(new Pokemon(Names.Ducklett,580,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.BIGPECKS,AbilityEnum.HYDRATION,62,44,50,55,44,5));
        pokeList.add(new Pokemon(Names.Swanna,581,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.BIGPECKS,AbilityEnum.HYDRATION,75,87,63,98,87,6));
        pokeList.add(new Pokemon(Names.Vanillite,582,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,36,50,50,44,65,6));
        pokeList.add(new Pokemon(Names.Vanillish,583,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,51,65,65,59,80,7));
        pokeList.add(new Pokemon(Names.Vanilluxe,584,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,71,95,85,79,110,9));
        pokeList.add(new Pokemon(Names.Deerling,585,NORMAL,GRASS,AbilityEnum.CHLOROPHYLL,AbilityEnum.SAPSIPPER,AbilityEnum.SERENEGRACE,60,60,50,75,40,5));
        pokeList.add(new Pokemon(Names.Sawsbuck,586,NORMAL,GRASS,AbilityEnum.CHLOROPHYLL,AbilityEnum.SAPSIPPER,AbilityEnum.SERENEGRACE,80,100,70,95,60,7));
        pokeList.add(new Pokemon(Names.Emolga,587,ELECTRIC,FLYING,AbilityEnum.STATIC,AbilityEnum.MOTORDRIVE,AbilityEnum.NONE,55,75,60,103,75,6));
        pokeList.add(new Pokemon(Names.Karrablast,588,BUG,NONE,AbilityEnum.SWARM,AbilityEnum.SHEDSKIN,AbilityEnum.NOGUARD,50,75,45,60,40,4));
        pokeList.add(new Pokemon(Names.Escavalier,589,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.SHELLARMOR,AbilityEnum.OVERCOAT,70,135,105,20,60,10));
        pokeList.add(new Pokemon(Names.Foongus,590,GRASS,POISON,AbilityEnum.EFFECTSPORE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,69,55,45,15,55,5));
        pokeList.add(new Pokemon(Names.Amoonguss,591,GRASS,POISON,AbilityEnum.EFFECTSPORE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,114,85,70,30,85,8));
        pokeList.add(new Pokemon(Names.Frillish,592,WATER,GHOST,AbilityEnum.WATERABSORB,AbilityEnum.CURSEDBODY,AbilityEnum.DAMP,55,40,50,40,65,8));
        pokeList.add(new Pokemon(Names.Jellicent,593,WATER,GHOST,AbilityEnum.WATERABSORB,AbilityEnum.CURSEDBODY,AbilityEnum.DAMP,100,60,70,60,85,10));
        pokeList.add(new Pokemon(Names.Alomomola,594,WATER,NONE,AbilityEnum.HEALER,AbilityEnum.HYDRATION,AbilityEnum.REGENERATOR,165,75,80,65,40,4));
        pokeList.add(new Pokemon(Names.Joltik,595,BUG,ELECTRIC,AbilityEnum.COMPOUNDEYES,AbilityEnum.UNNERVE,AbilityEnum.SWARM,50,47,50,65,57,5));
        pokeList.add(new Pokemon(Names.Galvantula,596,BUG,ELECTRIC,AbilityEnum.COMPOUNDEYES,AbilityEnum.UNNERVE,AbilityEnum.SWARM,70,77,60,108,97,6));
        pokeList.add(new Pokemon(Names.Ferroseed,597,GRASS,STEEL,AbilityEnum.IRONBARBS,AbilityEnum.NONE,AbilityEnum.NONE,44,50,91,10,24,8));
        pokeList.add(new Pokemon(Names.Ferrothorn,598,GRASS,STEEL,AbilityEnum.IRONBARBS,AbilityEnum.ANTICIPATION,AbilityEnum.NONE,74,94,131,20,54,11));
        pokeList.add(new Pokemon(Names.Klink,599,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,40,55,70,30,45,6));
        pokeList.add(new Pokemon(Names.Klang,600,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,60,80,95,50,70,8));
        pokeList.add(new Pokemon(Names.Klinklang,601,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,60,100,115,90,70,8));
        pokeList.add(new Pokemon(Names.Tynamo,602,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,35,55,40,60,45,4));
        pokeList.add(new Pokemon(Names.Eelektrik,603,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,65,85,70,40,75,7));
        pokeList.add(new Pokemon(Names.Eelektross,604,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,85,115,80,50,105,8));
        pokeList.add(new Pokemon(Names.Elgyem,605,PSYCHIC,NONE,AbilityEnum.TELEPATHY,AbilityEnum.SYNCHRONIZE,AbilityEnum.ANALYTIC,55,55,55,30,85,5));
        pokeList.add(new Pokemon(Names.Beheeyem,606,PSYCHIC,NONE,AbilityEnum.TELEPATHY,AbilityEnum.SYNCHRONIZE,AbilityEnum.ANALYTIC,75,75,75,40,125,9));
        pokeList.add(new Pokemon(Names.Litwick,607,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,50,30,55,20,65,5));
        pokeList.add(new Pokemon(Names.Lampent,608,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,60,40,60,55,95,6));
        pokeList.add(new Pokemon(Names.Chandelure,609,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,60,55,90,80,145,9));
        pokeList.add(new Pokemon(Names.Axew,610,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,46,87,60,57,30,4));
        pokeList.add(new Pokemon(Names.Fraxure,611,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,66,117,70,67,40,5));
        pokeList.add(new Pokemon(Names.Haxorus,612,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,76,147,90,97,60,7));
        pokeList.add(new Pokemon(Names.Cubchoo,613,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.RATTLED,AbilityEnum.NONE,55,70,40,40,60,4));
        pokeList.add(new Pokemon(Names.Beartic,614,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,95,110,80,50,70,8));
        pokeList.add(new Pokemon(Names.Cryogonal,615,ICE,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,70,50,30,105,95,13));
        pokeList.add(new Pokemon(Names.Shelmet,616,BUG,NONE,AbilityEnum.HYDRATION,AbilityEnum.SHELLARMOR,AbilityEnum.OVERCOAT,50,40,85,25,40,6));
        pokeList.add(new Pokemon(Names.Accelgor,617,BUG,NONE,AbilityEnum.HYDRATION,AbilityEnum.STICKYHOLD,AbilityEnum.UNBURDEN,80,70,40,145,100,6));
        pokeList.add(new Pokemon(Names.Stunfisk,618,GROUND,ELECTRIC,AbilityEnum.STATIC,AbilityEnum.LIMBER,AbilityEnum.SANDVEIL,109,66,84,32,81,9));
        pokeList.add(new Pokemon(Names.Mienfoo,619,FIGHTING,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.REGENERATOR,AbilityEnum.RECKLESS,45,85,50,65,55,5));
        pokeList.add(new Pokemon(Names.Mienshao,620,FIGHTING,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.REGENERATOR,AbilityEnum.RECKLESS,65,125,60,105,95,6));
        pokeList.add(new Pokemon(Names.Druddigon,621,DRAGON,NONE,AbilityEnum.ROUGHSKIN,AbilityEnum.SHEERFORCE,AbilityEnum.MOLDBREAKER,77,120,90,48,60,9));
        pokeList.add(new Pokemon(Names.Golett,622,GROUND,GHOST,AbilityEnum.IRONFIST,AbilityEnum.KLUTZ,AbilityEnum.NOGUARD,59,74,50,35,35,5));
        pokeList.add(new Pokemon(Names.Golurk,623,GROUND,GHOST,AbilityEnum.IRONFIST,AbilityEnum.KLUTZ,AbilityEnum.NOGUARD,89,124,80,55,55,8));
        pokeList.add(new Pokemon(Names.Pawniard,624,DARK,STEEL,AbilityEnum.DEFIANT,AbilityEnum.INNERFOCUS,AbilityEnum.PRESSURE,45,85,70,60,40,4));
        pokeList.add(new Pokemon(Names.Bisharp,625,DARK,STEEL,AbilityEnum.DEFIANT,AbilityEnum.INNERFOCUS,AbilityEnum.PRESSURE,65,125,100,70,60,7));
        pokeList.add(new Pokemon(Names.Bouffalant,626,NORMAL,NONE,AbilityEnum.RECKLESS,AbilityEnum.SAPSIPPER,AbilityEnum.SOUNDPROOF,95,110,95,55,40,9));
        pokeList.add(new Pokemon(Names.Rufflet,627,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SHEERFORCE,AbilityEnum.HUSTLE,70,83,50,60,37,5));
        pokeList.add(new Pokemon(Names.Braviary,628,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SHEERFORCE,AbilityEnum.DEFIANT,100,123,75,80,57,7));
        pokeList.add(new Pokemon(Names.Vullaby,629,DARK,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.OVERCOAT,AbilityEnum.WEAKARMOR,70,55,75,60,45,6));
        pokeList.add(new Pokemon(Names.Mandibuzz,630,DARK,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.OVERCOAT,AbilityEnum.WEAKARMOR,110,65,105,80,55,9));
        pokeList.add(new Pokemon(Names.Heatmor,631,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.FLASHFIRE,AbilityEnum.WHITESMOKE,85,97,66,65,105,6));
        pokeList.add(new Pokemon(Names.Durant,632,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.HUSTLE,AbilityEnum.TRUANT,58,109,112,109,48,4));
        pokeList.add(new Pokemon(Names.Deino,633,DARK,DRAGON,AbilityEnum.HUSTLE,AbilityEnum.NONE,AbilityEnum.NONE,52,65,50,38,45,5));
        pokeList.add(new Pokemon(Names.Zweilous,634,DARK,DRAGON,AbilityEnum.HUSTLE,AbilityEnum.NONE,AbilityEnum.NONE,72,85,70,58,65,7));
        pokeList.add(new Pokemon(Names.Hydreigon,635,DARK,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,92,105,90,98,125,9));
        pokeList.add(new Pokemon(Names.Larvesta,636,BUG,FIRE,AbilityEnum.FLAMEBODY,AbilityEnum.SWARM,AbilityEnum.NONE,55,85,55,60,50,5));
        pokeList.add(new Pokemon(Names.Volcarona,637,BUG,FIRE,AbilityEnum.FLAMEBODY,AbilityEnum.SWARM,AbilityEnum.NONE,85,60,65,100,135,10));
        pokeList.add(new Pokemon(Names.Cobalion,638,STEEL,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,91,90,129,108,90,7));
        pokeList.add(new Pokemon(Names.Terrakion,639,ROCK,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,91,129,90,108,72,9));
        pokeList.add(new Pokemon(Names.Virizion,640,GRASS,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,91,90,72,108,90,12));
        pokeList.add(new Pokemon(Names.Tornadus,641,FLYING,NONE,AbilityEnum.PRANKSTER,AbilityEnum.DEFIANT,AbilityEnum.NONE,79,115,70,111,125,8));
        pokeList.add(new Pokemon(Names.Thundurus,642,ELECTRIC,FLYING,AbilityEnum.PRANKSTER,AbilityEnum.DEFIANT,AbilityEnum.NONE,79,115,70,111,125,8));
        pokeList.add(new Pokemon(Names.Reshiram,643,DRAGON,FIRE,AbilityEnum.TURBOBLAZE,AbilityEnum.NONE,AbilityEnum.NONE,100,120,100,90,150,12));
        pokeList.add(new Pokemon(Names.Zekrom,644,DRAGON,ELECTRIC,AbilityEnum.TERAVOLT,AbilityEnum.NONE,AbilityEnum.NONE,100,150,120,90,120,10));
        pokeList.add(new Pokemon(Names.Landorus,645,GROUND,FLYING,AbilityEnum.SANDFORCE,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,89,125,90,101,115,8));
        pokeList.add(new Pokemon(Names.Kyurem,646,DRAGON,ICE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,125,130,90,95,130,9));
        pokeList.add(new Pokemon(Names.Keldeo,647,WATER,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,91,72,90,108,129,9));
        pokeList.add(new Pokemon(Names.Meloetta,648,NORMAL,PSYCHIC,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,100,77,77,90,128,12));
        pokeList.add(new Pokemon(Names.Genesect,649,BUG,STEEL,AbilityEnum.DOWNLOAD,AbilityEnum.NONE,AbilityEnum.NONE,71,120,95,99,120,9));
        pokeList.add(new Pokemon(Names.Chespin,650,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,56,61,65,38,48,4));
        pokeList.add(new Pokemon(Names.Quilladin,651,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,61,78,95,57,56,5));
        pokeList.add(new Pokemon(Names.Chesnaught,652,GRASS,FIGHTING,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,88,107,122,64,74,7));
        pokeList.add(new Pokemon(Names.Fennekin,653,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,40,45,40,60,62,6));
        pokeList.add(new Pokemon(Names.Braixen,654,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,59,59,58,73,90,7));
        pokeList.add(new Pokemon(Names.Delphox,655,FIRE,PSYCHIC,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,75,69,72,104,114,10));
        pokeList.add(new Pokemon(Names.Froakie,656,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,41,56,40,71,62,4));
        pokeList.add(new Pokemon(Names.Frogadier,657,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,54,63,52,97,83,5));
        pokeList.add(new Pokemon(Names.Greninja,658,WATER,DARK,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,72,95,67,122,103,7));
        pokeList.add(new Pokemon(Names.Bunnelby,659,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.CHEEKPOUCH,AbilityEnum.HUGEPOWER,38,36,38,57,32,3));
        pokeList.add(new Pokemon(Names.Diggersby,660,NORMAL,GROUND,AbilityEnum.PICKUP,AbilityEnum.CHEEKPOUCH,AbilityEnum.HUGEPOWER,85,56,77,78,50,7));
        pokeList.add(new Pokemon(Names.Fletchling,661,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.GALEWINGS,AbilityEnum.NONE,45,50,43,62,40,3));
        pokeList.add(new Pokemon(Names.Fletchinder,662,FIRE,FLYING,AbilityEnum.FLAMEBODY,AbilityEnum.GALEWINGS,AbilityEnum.NONE,62,73,55,84,56,5));
        pokeList.add(new Pokemon(Names.Talonflame,663,FIRE,FLYING,AbilityEnum.FLAMEBODY,AbilityEnum.GALEWINGS,AbilityEnum.NONE,78,81,71,126,74,6));
        pokeList.add(new Pokemon(Names.Scatterbug,664,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRIENDGUARD,38,35,40,35,27,2));
        pokeList.add(new Pokemon(Names.Spewpa,665,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.FRIENDGUARD,AbilityEnum.NONE,45,22,60,29,27,3));
        pokeList.add(new Pokemon(Names.Vivillon,666,BUG,FLYING,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRIENDGUARD,80,52,50,89,90,5));
        pokeList.add(new Pokemon(Names.Litleo,667,FIRE,NORMAL,AbilityEnum.RIVALRY,AbilityEnum.UNNERVE,AbilityEnum.MOXIE,62,50,58,72,73,5));
        pokeList.add(new Pokemon(Names.Pyroar,668,FIRE,NORMAL,AbilityEnum.RIVALRY,AbilityEnum.UNNERVE,AbilityEnum.MOXIE,86,68,72,106,109,6));
        pokeList.add(new Pokemon(Names.Flabebe,669,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,44,38,39,42,61,7));
        pokeList.add(new Pokemon(Names.Floette,670,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,54,45,47,52,75,9));
        pokeList.add(new Pokemon(Names.Florges,671,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,78,65,68,75,112,15));
        pokeList.add(new Pokemon(Names.Skiddo,672,GRASS,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.GRASSPELT,AbilityEnum.NONE,66,65,48,52,62,5));
        pokeList.add(new Pokemon(Names.Gogoat,673,GRASS,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.GRASSPELT,AbilityEnum.NONE,123,100,62,68,97,8));
        pokeList.add(new Pokemon(Names.Pancham,674,FIGHTING,NONE,AbilityEnum.IRONFIST,AbilityEnum.MOLDBREAKER,AbilityEnum.SCRAPPY,67,82,62,43,46,4));
        pokeList.add(new Pokemon(Names.Pangoro,675,FIGHTING,DARK,AbilityEnum.IRONFIST,AbilityEnum.MOLDBREAKER,AbilityEnum.SCRAPPY,95,124,78,58,69,7));
        pokeList.add(new Pokemon(Names.Furfrou,676,NORMAL,NONE,AbilityEnum.FURCOAT,AbilityEnum.NONE,AbilityEnum.NONE,75,80,60,102,65,9));
        pokeList.add(new Pokemon(Names.Espurr,677,PSYCHIC,NONE,AbilityEnum.KEENEYE,AbilityEnum.INFILTRATOR,AbilityEnum.OWNTEMPO,62,48,54,68,63,6));
        pokeList.add(new Pokemon(Names.Meowstic,678,PSYCHIC,NONE,AbilityEnum.KEENEYE,AbilityEnum.INFILTRATOR,AbilityEnum.PRANKSTER,74,48,76,104,83,8));
        pokeList.add(new Pokemon(Names.Honedge,679,STEEL,GHOST,AbilityEnum.NOGUARD,AbilityEnum.NONE,AbilityEnum.NONE,45,80,100,28,35,3));
        pokeList.add(new Pokemon(Names.Doublade,680,STEEL,GHOST,AbilityEnum.NOGUARD,AbilityEnum.NONE,AbilityEnum.NONE,59,110,150,35,45,4));
        pokeList.add(new Pokemon(Names.Aegislash,681,STEEL,GHOST,AbilityEnum.STANCECHANGE,AbilityEnum.NONE,AbilityEnum.NONE,60,50,150,60,50,15));
        pokeList.add(new Pokemon(Names.Spritzee,682,FAIRY,NONE,AbilityEnum.HEALER,AbilityEnum.AROMAVEIL,AbilityEnum.NONE,78,52,60,23,63,6));
        pokeList.add(new Pokemon(Names.Aromatisse,683,FAIRY,NONE,AbilityEnum.HEALER,AbilityEnum.AROMAVEIL,AbilityEnum.NONE,101,72,72,29,99,8));
        pokeList.add(new Pokemon(Names.Swirlix,684,FAIRY,NONE,AbilityEnum.SWEETVEIL,AbilityEnum.UNBURDEN,AbilityEnum.NONE,62,48,66,49,59,5));
        pokeList.add(new Pokemon(Names.Slurpuff,685,FAIRY,NONE,AbilityEnum.SWEETVEIL,AbilityEnum.UNBURDEN,AbilityEnum.NONE,82,80,86,72,85,7));
        pokeList.add(new Pokemon(Names.Inkay,686,DARK,PSYCHIC,AbilityEnum.CONTRARY,AbilityEnum.SUCTIONCUPS,AbilityEnum.INFILTRATOR,53,54,53,45,37,4));
        pokeList.add(new Pokemon(Names.Malamar,687,DARK,PSYCHIC,AbilityEnum.CONTRARY,AbilityEnum.SUCTIONCUPS,AbilityEnum.INFILTRATOR,86,92,88,73,68,7));
        pokeList.add(new Pokemon(Names.Binacle,688,ROCK,WATER,AbilityEnum.TOUGHCLAWS,AbilityEnum.SNIPER,AbilityEnum.PICKPOCKET,42,52,67,50,39,5));
        pokeList.add(new Pokemon(Names.Barbaracle,689,ROCK,WATER,AbilityEnum.TOUGHCLAWS,AbilityEnum.SNIPER,AbilityEnum.PICKPOCKET,72,105,115,54,86,6));
        pokeList.add(new Pokemon(Names.Skrelp,690,POISON,WATER,AbilityEnum.POISONPOINT,AbilityEnum.POISONTOUCH,AbilityEnum.ADAPTABILITY,50,60,60,30,60,6));
        pokeList.add(new Pokemon(Names.Dragalge,691,POISON,DRAGON,AbilityEnum.POISONPOINT,AbilityEnum.POISONTOUCH,AbilityEnum.ADAPTABILITY,65,75,90,44,97,12));
        pokeList.add(new Pokemon(Names.Clauncher,692,WATER,NONE,AbilityEnum.MEGALAUNCHER,AbilityEnum.NONE,AbilityEnum.NONE,50,53,62,44,58,6));
        pokeList.add(new Pokemon(Names.Clawitzer,693,WATER,NONE,AbilityEnum.MEGALAUNCHER,AbilityEnum.NONE,AbilityEnum.NONE,71,73,88,59,120,8));
        pokeList.add(new Pokemon(Names.Helioptile,694,ELECTRIC,NORMAL,AbilityEnum.DRYSKIN,AbilityEnum.SANDVEIL,AbilityEnum.SOLARPOWER,44,38,33,70,61,4));
        pokeList.add(new Pokemon(Names.Heliolisk,695,ELECTRIC,NORMAL,AbilityEnum.DRYSKIN,AbilityEnum.SANDVEIL,AbilityEnum.SOLARPOWER,62,55,52,109,109,9));
        pokeList.add(new Pokemon(Names.Tyrunt,696,ROCK,DRAGON,AbilityEnum.STRONGJAW,AbilityEnum.STURDY,AbilityEnum.NONE,58,89,77,48,45,4));
        pokeList.add(new Pokemon(Names.Tyrantrum,697,ROCK,DRAGON,AbilityEnum.STRONGJAW,AbilityEnum.ROCKHEAD,AbilityEnum.NONE,82,121,119,71,69,5));
        pokeList.add(new Pokemon(Names.Amaura,698,ROCK,ICE,AbilityEnum.REFRIGERATE,AbilityEnum.SNOWWARNING,AbilityEnum.NONE,77,59,50,46,67,6));
        pokeList.add(new Pokemon(Names.Aurorus,699,ROCK,ICE,AbilityEnum.REFRIGERATE,AbilityEnum.SNOWWARNING,AbilityEnum.NONE,123,77,72,58,99,9));
        pokeList.add(new Pokemon(Names.Sylveon,700,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.CUTECHARM,AbilityEnum.PIXILATE,95,65,65,60,110,13));
        pokeList.add(new Pokemon(Names.Hawlucha,701,FIGHTING,FLYING,AbilityEnum.UNBURDEN,AbilityEnum.LIMBER,AbilityEnum.MOLDBREAKER,78,92,75,118,74,6));
        pokeList.add(new Pokemon(Names.Dedenne,702,ELECTRIC,FAIRY,AbilityEnum.CHEEKPOUCH,AbilityEnum.PICKUP,AbilityEnum.PLUS,67,58,57,101,81,6));
        pokeList.add(new Pokemon(Names.Carbink,703,ROCK,FAIRY,AbilityEnum.CLEARBODY,AbilityEnum.STURDY,AbilityEnum.NONE,50,50,150,50,50,15));
        pokeList.add(new Pokemon(Names.Goomy,704,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,45,50,35,40,55,7));
        pokeList.add(new Pokemon(Names.Sliggoo,705,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,68,75,53,60,83,11));
        pokeList.add(new Pokemon(Names.Goodra,706,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,90,100,70,80,110,15));
        pokeList.add(new Pokemon(Names.Klefki,707,STEEL,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.MAGICIAN,AbilityEnum.NONE,57,80,91,75,80,8));
        pokeList.add(new Pokemon(Names.Phantump,708,GHOST,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.FRISK,AbilityEnum.HARVEST,43,70,48,38,50,6));
        pokeList.add(new Pokemon(Names.Trevenant,709,GHOST,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.FRISK,AbilityEnum.HARVEST,85,110,76,56,65,8));
        pokeList.add(new Pokemon(Names.Pumpkaboo,710,GHOST,GRASS,AbilityEnum.PICKUP,AbilityEnum.FRISK,AbilityEnum.INSOMNIA,59,66,70,41,44,5));
        pokeList.add(new Pokemon(Names.Gourgeist,711,GHOST,GRASS,AbilityEnum.PICKUP,AbilityEnum.FRISK,AbilityEnum.INSOMNIA,85,100,122,54,58,7));
        pokeList.add(new Pokemon(Names.Bergmite,712,ICE,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.ICEBODY,AbilityEnum.STURDY,55,69,85,28,32,3));
        pokeList.add(new Pokemon(Names.Avalugg,713,ICE,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.ICEBODY,AbilityEnum.STURDY,95,117,184,28,44,4));
        pokeList.add(new Pokemon(Names.Noibat,714,FLYING,DRAGON,AbilityEnum.FRISK,AbilityEnum.INFILTRATOR,AbilityEnum.TELEPATHY,40,30,35,55,45,4));
        pokeList.add(new Pokemon(Names.Noivern,715,FLYING,DRAGON,AbilityEnum.FRISK,AbilityEnum.INFILTRATOR,AbilityEnum.TELEPATHY,85,70,80,123,97,8));
        pokeList.add(new Pokemon(Names.Xerneas,716,FAIRY,NONE,AbilityEnum.FAIRYAURA,AbilityEnum.NONE,AbilityEnum.NONE,126,131,95,99,131,9));
        pokeList.add(new Pokemon(Names.Yveltal,717,DARK,FLYING,AbilityEnum.DARKAURA,AbilityEnum.NONE,AbilityEnum.NONE,126,131,95,99,131,9));
        pokeList.add(new Pokemon(Names.Zygarde,718,DRAGON,GROUND,AbilityEnum.AURABREAK,AbilityEnum.NONE,AbilityEnum.NONE,108,100,121,95,81,9));
        pokeList.add(new Pokemon(Names.Diancie,719,ROCK,FAIRY,AbilityEnum.CLEARBODY,AbilityEnum.NONE,AbilityEnum.NONE,50,100,150,50,100,15));
        pokeList.add(new Pokemon(Names.Hoopa,720,PSYCHIC,GHOST,AbilityEnum.MAGICIAN,AbilityEnum.NONE,AbilityEnum.NONE,80,110,60,70,150,13));
        pokeList.add(new Pokemon(Names.Volcanion,721,FIRE,WATER,AbilityEnum.WATERABSORB,AbilityEnum.NONE,AbilityEnum.NONE,80,110,120,70,130,9));

    }
}
