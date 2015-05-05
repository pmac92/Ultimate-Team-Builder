package com.example.owner.utb;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
    PokeListAdapter arrayAdapter;
    private ListView lv;
    EditText inputSearch;
    ImageButton activeSlot;
    int activeSlotNum;
    Pokemon selectedPokemon;
    Pokemon[] team = new Pokemon[6];
    int matW = 6;
    int matH = 18;
    int [] teamType = new int[18];

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

    boolean bugWeak,darkWeak,dragonWeak,electricWeak,fairyWeak,fightingWeak,fireWeak,flyingWeak,
            ghostWeak,grassWeak,groundWeak,iceWeak,normalWeak,poisonWeak,
            psychicWeak,rockWeak,steelWeak,waterWeak;
    boolean evaluateOnlyTypeWeaknesses = true;
    ArrayList<Boolean> weakArrayBool = new ArrayList<Boolean>(Arrays.asList(bugWeak,darkWeak,dragonWeak,electricWeak,
            fairyWeak,fightingWeak,fireWeak,flyingWeak,ghostWeak,grassWeak,groundWeak,iceWeak,normalWeak,poisonWeak,
            psychicWeak,rockWeak,steelWeak,waterWeak));
    ArrayList<Type> weakArrayType = new ArrayList<Type>();
    ArrayList<Pokemon> offensiveThreats = new ArrayList<Pokemon>();
    ArrayList<Pokemon> wallThreats = new ArrayList<Pokemon>();
    ArrayAdapter<Pokemon> offThreatAdapt;
    ArrayAdapter<Pokemon> wallThreatAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createEmptyTeam();

        lv = (ListView) findViewById(R.id.pokeList);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.setText("Search");
        initList();

         arrayAdapter = new PokeListAdapter(
                this,
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


        //typeChartTab
        final TextView typeChartTab = (TextView) findViewById(R.id.typeChartTab);
        typeChartTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.analysisSection).setVisibility(View.GONE);
                findViewById(R.id.resistChart).setVisibility(View.VISIBLE);
            }
        });

        //Anaysis Tab
        final TextView analysisTab = (TextView) findViewById(R.id.analysisTab);
        analysisTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.resistChart).setVisibility(View.GONE);
                findViewById(R.id.analysisSection).setVisibility(View.VISIBLE);
                analyzeTeamStats();
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
        findViewById(R.id.analysisSection).setVisibility(View.GONE);


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

        int [][] teamResistMatrix = new int[matH] [matW];
        String[][] stringMatrix = new String [matH][matW];

        //Table Slots

        TextView normLabel = (TextView) findViewById(R.id.norm);
        TextView normZero = (TextView) findViewById(R.id.normZero);
        TextView normFourth = (TextView) findViewById(R.id.normFourth);
        TextView normHalf = (TextView) findViewById(R.id.normHalf);
        TextView normOne = (TextView) findViewById(R.id.normOne);
        TextView normTwo = (TextView) findViewById(R.id.normTwo);
        TextView normFour = (TextView) findViewById(R.id.normFour);

        TextView fightLabel = (TextView) findViewById(R.id.fight);
        TextView fightZero = (TextView) findViewById(R.id.fightZero);
        TextView fightFourth = (TextView) findViewById(R.id.fightFourth);
        TextView fightHalf = (TextView) findViewById(R.id.fightHalf);
        TextView fightOne = (TextView) findViewById(R.id.fightOne);
        TextView fightTwo = (TextView) findViewById(R.id.fightTwo);
        TextView fightFour = (TextView) findViewById(R.id.fightFour);

        TextView flyLabel = (TextView) findViewById(R.id.fly);
        TextView flyZero = (TextView) findViewById(R.id.flyZero);
        TextView flyFourth = (TextView) findViewById(R.id.flyFourth);
        TextView flyHalf = (TextView) findViewById(R.id.flyHalf);
        TextView flyOne = (TextView) findViewById(R.id.flyOne);
        TextView flyTwo = (TextView) findViewById(R.id.flyTwo);
        TextView flyFour = (TextView) findViewById(R.id.flyFour);

        TextView psnLabel = (TextView) findViewById(R.id.psn);
        TextView psnZero = (TextView) findViewById(R.id.psnZero);
        TextView psnFourth = (TextView) findViewById(R.id.psnFourth);
        TextView psnHalf = (TextView) findViewById(R.id.psnHalf);
        TextView psnOne = (TextView) findViewById(R.id.psnOne);
        TextView psnTwo = (TextView) findViewById(R.id.psnTwo);
        TextView psnFour = (TextView) findViewById(R.id.psnFour);

        TextView grndLabel = (TextView) findViewById(R.id.grnd);
        TextView grndZero = (TextView) findViewById(R.id.grndZero);
        TextView grndFourth = (TextView) findViewById(R.id.grndFourth);
        TextView grndHalf = (TextView) findViewById(R.id.grndHalf);
        TextView grndOne = (TextView) findViewById(R.id.grndOne);
        TextView grndTwo = (TextView) findViewById(R.id.grndTwo);
        TextView grndFour = (TextView) findViewById(R.id.grndFour);

        TextView rockLabel = (TextView) findViewById(R.id.rock);
        TextView rockZero = (TextView) findViewById(R.id.rockZero);
        TextView rockFourth = (TextView) findViewById(R.id.rockFourth);
        TextView rockHalf = (TextView) findViewById(R.id.rockHalf);
        TextView rockOne = (TextView) findViewById(R.id.rockOne);
        TextView rockTwo = (TextView) findViewById(R.id.rockTwo);
        TextView rockFour = (TextView) findViewById(R.id.rockFour);

        TextView ghostLabel = (TextView) findViewById(R.id.ghost);
        TextView ghostZero = (TextView) findViewById(R.id.ghostZero);
        TextView ghostFourth = (TextView) findViewById(R.id.ghostFourth);
        TextView ghostHalf = (TextView) findViewById(R.id.ghostHalf);
        TextView ghostOne = (TextView) findViewById(R.id.ghostOne);
        TextView ghostTwo = (TextView) findViewById(R.id.ghostTwo);
        TextView ghostFour = (TextView) findViewById(R.id.ghostFour);

        TextView steelLabel = (TextView) findViewById(R.id.steel);
        TextView steelZero = (TextView) findViewById(R.id.steelZero);
        TextView steelFourth = (TextView) findViewById(R.id.steelFourth);
        TextView steelHalf = (TextView) findViewById(R.id.steelHalf);
        TextView steelOne = (TextView) findViewById(R.id.steelOne);
        TextView steelTwo = (TextView) findViewById(R.id.steelTwo);
        TextView steelFour = (TextView) findViewById(R.id.steelFour);

        TextView bugLabel = (TextView) findViewById(R.id.bug);
        TextView bugZero = (TextView) findViewById(R.id.bugZero);
        TextView bugFourth = (TextView) findViewById(R.id.bugFourth);
        TextView bugHalf = (TextView) findViewById(R.id.bugHalf);
        TextView bugOne = (TextView) findViewById(R.id.bugOne);
        TextView bugTwo = (TextView) findViewById(R.id.bugTwo);
        TextView bugFour = (TextView) findViewById(R.id.bugFour);

        TextView waterLabel = (TextView) findViewById(R.id.water);
        TextView waterZero = (TextView) findViewById(R.id.waterZero);
        TextView waterFourth = (TextView) findViewById(R.id.waterFourth);
        TextView waterHalf = (TextView) findViewById(R.id.waterHalf);
        TextView waterOne = (TextView) findViewById(R.id.waterOne);
        TextView waterTwo = (TextView) findViewById(R.id.waterTwo);
        TextView waterFour = (TextView) findViewById(R.id.waterFour);

        TextView fireLabel = (TextView) findViewById(R.id.fire);
        TextView fireZero = (TextView) findViewById(R.id.fireZero);
        TextView fireFourth = (TextView) findViewById(R.id.fireFourth);
        TextView fireHalf = (TextView) findViewById(R.id.fireHalf);
        TextView fireOne = (TextView) findViewById(R.id.fireOne);
        TextView fireTwo = (TextView) findViewById(R.id.fireTwo);
        TextView fireFour = (TextView) findViewById(R.id.fireFour);

        TextView grsLabel = (TextView) findViewById(R.id.grs);
        TextView grsZero = (TextView) findViewById(R.id.grsZero);
        TextView grsFourth = (TextView) findViewById(R.id.grsFourth);
        TextView grsHalf = (TextView) findViewById(R.id.grsHalf);
        TextView grsOne = (TextView) findViewById(R.id.grsOne);
        TextView grsTwo = (TextView) findViewById(R.id.grsTwo);
        TextView grsFour = (TextView) findViewById(R.id.grsFour);

        TextView elecLabel = (TextView) findViewById(R.id.elec);
        TextView elecZero = (TextView) findViewById(R.id.elecZero);
        TextView elecFourth = (TextView) findViewById(R.id.elecFourth);
        TextView elecHalf = (TextView) findViewById(R.id.elecHalf);
        TextView elecOne = (TextView) findViewById(R.id.elecOne);
        TextView elecTwo = (TextView) findViewById(R.id.elecTwo);
        TextView elecFour = (TextView) findViewById(R.id.elecFour);

        TextView psyLabel = (TextView) findViewById(R.id.psy);
        TextView psyZero = (TextView) findViewById(R.id.psyZero);
        TextView psyFourth = (TextView) findViewById(R.id.psyFourth);
        TextView psyHalf = (TextView) findViewById(R.id.psyHalf);
        TextView psyOne = (TextView) findViewById(R.id.psyOne);
        TextView psyTwo = (TextView) findViewById(R.id.psyTwo);
        TextView psyFour = (TextView) findViewById(R.id.psyFour);

        TextView iceLabel = (TextView) findViewById(R.id.ice);
        TextView iceZero = (TextView) findViewById(R.id.iceZero);
        TextView iceFourth = (TextView) findViewById(R.id.iceFourth);
        TextView iceHalf = (TextView) findViewById(R.id.iceHalf);
        TextView iceOne = (TextView) findViewById(R.id.iceOne);
        TextView iceTwo = (TextView) findViewById(R.id.iceTwo);
        TextView iceFour = (TextView) findViewById(R.id.iceFour);

        TextView drgLabel = (TextView) findViewById(R.id.drg);
        TextView drgZero = (TextView) findViewById(R.id.drgZero);
        TextView drgFourth = (TextView) findViewById(R.id.drgFourth);
        TextView drgHalf = (TextView) findViewById(R.id.drgHalf);
        TextView drgOne = (TextView) findViewById(R.id.drgOne);
        TextView drgTwo = (TextView) findViewById(R.id.drgTwo);
        TextView drgFour = (TextView) findViewById(R.id.drgFour);

        TextView fryLabel = (TextView) findViewById(R.id.fry);
        TextView fryZero = (TextView) findViewById(R.id.fryZero);
        TextView fryFourth = (TextView) findViewById(R.id.fryFourth);
        TextView fryHalf = (TextView) findViewById(R.id.fryHalf);
        TextView fryOne = (TextView) findViewById(R.id.fryOne);
        TextView fryTwo = (TextView) findViewById(R.id.fryTwo);
        TextView fryFour = (TextView) findViewById(R.id.fryFour);

        TextView darkLabel = (TextView) findViewById(R.id.dark);
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
                for (int j = 0; j < matH; j++) {
                    for (int k = 0; k < matW; k++) {
                        combinedResist[j][k] = team[i].type1.resistMatrix[j][k] + team[i].type2.resistMatrix[j][k];
                        //System.out.println("combinedResist[" + j + "][" + k + "] = " + combinedResist[j][k]);
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
                for (int m = 0; m < matH; m++) {
                    for (int n = 0; n < matW; n++) {
                        teamResistMatrix[m][n] = teamResistMatrix[m][n] + combinedResist[m][n];
                    }
                }

            }
            else {
                for (int m = 0; m < matH; m++) {
                    for (int n = 0; n < matW; n++) {
                        int num = team[i].type1.resistMatrix[m][n];
                        teamResistMatrix[m][n] = teamResistMatrix[m][n] + num;
                    }
                }
            }

        }
        //Change Row colors
        normalWeak = rowEval(sumWeak(teamResistMatrix, 0),sumRes(teamResistMatrix, 0), normLabel,1);
        fightingWeak = rowEval(sumWeak(teamResistMatrix, 1),sumRes(teamResistMatrix,1), fightLabel,0);
        flyingWeak = rowEval(sumWeak(teamResistMatrix, 2),sumRes(teamResistMatrix,2), flyLabel,1);
        poisonWeak = rowEval(sumWeak(teamResistMatrix, 3),sumRes(teamResistMatrix,3), psnLabel,0);
        groundWeak = rowEval(sumWeak(teamResistMatrix, 4),sumRes(teamResistMatrix,4), grndLabel,1);
        rockWeak = rowEval(sumWeak(teamResistMatrix, 5),sumRes(teamResistMatrix,5), rockLabel,0);
        bugWeak = rowEval(sumWeak(teamResistMatrix, 6),sumRes(teamResistMatrix,6), bugLabel,1);
        ghostWeak = rowEval(sumWeak(teamResistMatrix, 7),sumRes(teamResistMatrix,7), ghostLabel,0);
        steelWeak = rowEval(sumWeak(teamResistMatrix, 8),sumRes(teamResistMatrix,8), steelLabel,1);
        fireWeak = rowEval(sumWeak(teamResistMatrix, 9),sumRes(teamResistMatrix,9), fireLabel,0);
        waterWeak = rowEval(sumWeak(teamResistMatrix, 10),sumRes(teamResistMatrix,10), waterLabel,1);
        grassWeak = rowEval(sumWeak(teamResistMatrix, 11),sumRes(teamResistMatrix,11), grsLabel,0);
        electricWeak = rowEval(sumWeak(teamResistMatrix, 12),sumRes(teamResistMatrix,12), elecLabel,1);
        psychicWeak = rowEval(sumWeak(teamResistMatrix, 13),sumRes(teamResistMatrix,13), psyLabel,0);
        iceWeak = rowEval(sumWeak(teamResistMatrix, 14),sumRes(teamResistMatrix,14), iceLabel,1);
        dragonWeak = rowEval(sumWeak(teamResistMatrix, 15),sumRes(teamResistMatrix,15), drgLabel,0);
        darkWeak = rowEval(sumWeak(teamResistMatrix, 16),sumRes(teamResistMatrix,16), darkLabel,1);
        fairyWeak = rowEval(sumWeak(teamResistMatrix, 17),sumRes(teamResistMatrix,17), fryLabel,0);



        //CONVERT MATRIX TO STRING HERE
        for (int m = 0; m < 18; m++) {
            for (int n = 0; n < 6; n++) {
                String value = String.valueOf(teamResistMatrix[m][n]);
                if(value.equals("0")) stringMatrix[m][n] = "";
                else stringMatrix[m][n] = value;
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
        team[0] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
        team[1] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
        team[2] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
        team[3] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
        team[4] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
        team[5] = new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0);
    }

    private boolean rowEval(int weaknesses, int resistances, TextView tv, int rowColor){
        if(weaknesses > resistances){
            tv.setBackgroundColor(Color.parseColor("#EE796C")); //red
            return true;
        }
        if (weaknesses < resistances) tv.setBackgroundColor(Color.parseColor("#6CEE6C")); //green
        else if (weaknesses == resistances){
            if (rowColor == 0) tv.setBackgroundColor(Color.parseColor("#BEE1E8")); //row color
            if (rowColor == 1) tv.setBackgroundColor(Color.parseColor("#B3B3B3"));
        }

        if(resistances < 3 || weaknesses >= resistances) return true;

        return false;
    }

    private int sumRes(int[][] matrix, int row){
        return matrix[row][0]+matrix[row][1]+matrix[row][2];
    }

    private int sumWeak(int[][] matrix, int row){
        return matrix[row][4]+matrix[row][5];
    }

    private void analyzeTeamStats(){
        int maxHp=0,maxAtk=0,maxDef=0,maxSpA=0,maxSpD=0,maxSpe=0;

        offensiveThreats.clear();
        wallThreats.clear();
        weakArrayType.clear();

        if(bugWeak == true) weakArrayType.add(BUG);
        if(darkWeak == true) weakArrayType.add(DARK);
        if(dragonWeak == true) weakArrayType.add(DRAGON);
        if(electricWeak == true) weakArrayType.add(ELECTRIC);
        if(fairyWeak == true) weakArrayType.add(FAIRY);
        if(fightingWeak == true) weakArrayType.add(FIGHTING);
        if(fireWeak == true) weakArrayType.add(FIRE);
        if(flyingWeak == true) weakArrayType.add(FLYING);
        if(ghostWeak == true) weakArrayType.add(GHOST);
        if(grassWeak == true) weakArrayType.add(GRASS);
        if(groundWeak == true) weakArrayType.add(GROUND);
        if(iceWeak == true) weakArrayType.add(ICE);
        if(normalWeak == true) weakArrayType.add(NORMAL);
        if(poisonWeak == true) weakArrayType.add(POISON);
        if(psychicWeak == true) weakArrayType.add(PSYCHIC);
        if(rockWeak == true) weakArrayType.add(ROCK);
        if(steelWeak == true) weakArrayType.add(STEEL);
        if(waterWeak == true) weakArrayType.add(WATER);

        //Get Max Stats and types
        for(int i=0;i<team.length;i++){
            if(team[i].hp > maxHp) maxHp = team[i].hp;
            if(team[i].atk > maxAtk) maxAtk = team[i].atk;
            if(team[i].def > maxDef) maxDef = team[i].def;
            if(team[i].spA > maxSpA) maxSpA = team[i].spA;
            if(team[i].spD > maxSpD) maxSpD = team[i].spD;
            if(team[i].spe > maxSpe) maxSpe = team[i].spe;

            Type type1 = team[i].type1;
            Type type2 = team[i].type1;

            if(type1 == NORMAL || type2 == NORMAL){
                teamType[7]--; //ghost
                teamType[5]--; //rock
                teamType[8]--; //steel
            }
            if(type1 == FIGHTING || type2 == FIGHTING){
                teamType[16]++;
                teamType[14]++;
                teamType[0]++;
                teamType[5]++;
                teamType[8]++;
                teamType[6]--;
                teamType[17]--;
                teamType[2]--;
                teamType[3]--;
                teamType[13]--;
                teamType[7]--;
            }
            if(type1 == FLYING || type2 == FLYING){
                teamType[6]++;
                teamType[1]++;
                teamType[11]++;
                teamType[12]--;
                teamType[5]--;
                teamType[8]--;
            }
            if(type1 == POISON || type2 == POISON){
                teamType[17]++;
                teamType[11]++;
                teamType[3]--;
                teamType[4]--;
                teamType[5]--;
                teamType[7]--;
                teamType[8]--;
            }
            if(type1 == GROUND || type2 == GROUND) {
                teamType[12]++;
                teamType[9]++;
                teamType[3]++;
                teamType[5]++;
                teamType[8]++;
                teamType[6]--;
                teamType[11]--;
                teamType[2]--;
            }
            if(type1 == ROCK || type2 == ROCK){
                teamType[6]++;
                teamType[9]++;
                teamType[2]++;
                teamType[14]++;
                teamType[1]--;
                teamType[4]--;
            }
            if(type1 == BUG || type2 == BUG){
                teamType[16]++;
                teamType[11]++;
                teamType[13]++;
                teamType[17]--;
                teamType[1]--;
                teamType[9]--;
                teamType[2]--;
                teamType[7]--;
                teamType[3]--;
                teamType[8]--;
            }
            if(type1 == GHOST || type2 == GHOST){
                teamType[7]++;
                teamType[13]++;
                teamType[16]--;
                teamType[0]--;
            }
            if(type1 == STEEL || type2 == STEEL){
                teamType[17]++;
                teamType[14]++;
                teamType[5]++;
                teamType[12]--;
                teamType[9]--;
                teamType[8]--;
                teamType[10]--;
            }
            if(type1 == FIRE || type2 == FIRE){
                teamType[6]++;
                teamType[11]++;
                teamType[14]++;
                teamType[8]++;
                teamType[15]--;
                teamType[9]--;
                teamType[5]--;
                teamType[10]--;
            }
            if(type1 == WATER || type2 == WATER){
                teamType[9]++;
                teamType[4]++;
                teamType[5]++;
                teamType[15]--;
                teamType[11]--;
                teamType[10]--;
            }
            if(type1 == GRASS || type2 == GRASS){
                teamType[4]++;
                teamType[5]++;
                teamType[10]++;
                teamType[15]--;
                teamType[6]--;
                teamType[9]--;
                teamType[2]--;
                teamType[11]--;
                teamType[3]--;
                teamType[8]--;
            }
            if(type1 == ELECTRIC || type2 == ELECTRIC){
                teamType[2]++;
                teamType[10]++;
                teamType[15]--;
                teamType[12]--;
                teamType[11]--;
                teamType[4]--;
            }
            if(type1 == PSYCHIC || type2 == PSYCHIC){
                teamType[1]++;
                teamType[3]++;
                teamType[13]--;
                teamType[8]--;
                teamType[16]--;
            }
            if(type1 == ICE || type2 == ICE){
                teamType[15]++;
                teamType[2]++;
                teamType[11]++;
                teamType[4]++;
                teamType[9]--;
                teamType[14]--;
                teamType[8]--;
                teamType[10]--;
            }
            if(type1 == DRAGON || type2 == DRAGON){
                teamType[15]++;
                teamType[8]--;
                teamType[17]--;
            }
            if(type1 == DARK || type2 == DARK){
                teamType[7]++;
                teamType[13]++;
                teamType[16]--;
                teamType[17]--;
                teamType[1]--;
            }
            if(type1 == FAIRY || type2 == FAIRY){
                teamType[16]++;
                teamType[15]++;
                teamType[1]++;
                teamType[9]--;
                teamType[3]--;
                teamType[8]--;
            }
        }

        ArrayList<Type> wallTypes = new ArrayList<Type>();
        if(teamType[0] < -2) wallTypes.add(NORMAL);
        if(teamType[1] < -2) wallTypes.add(FIGHTING);
        if(teamType[2] < -2) wallTypes.add(FLYING);
        if(teamType[3] < -2) wallTypes.add(POISON);
        if(teamType[4] < -2) wallTypes.add(GROUND);
        if(teamType[5] < -2) wallTypes.add(ROCK);
        if(teamType[6] < -2) wallTypes.add(BUG);
        if(teamType[7] < -2) wallTypes.add(GHOST);
        if(teamType[8] < -2) wallTypes.add(STEEL);
        if(teamType[9] < -2) wallTypes.add(FIRE);
        if(teamType[10] < -2) wallTypes.add(WATER);
        if(teamType[11] < -2) wallTypes.add(GRASS);
        if(teamType[12] < -2) wallTypes.add(ELECTRIC);
        if(teamType[13] < -2) wallTypes.add(PSYCHIC);
        if(teamType[14] < -2) wallTypes.add(ICE);
        if(teamType[15] < -2) wallTypes.add(DRAGON);
        if(teamType[16] < -2) wallTypes.add(DARK);
        if(teamType[17] < -2) wallTypes.add(FAIRY);
        for(int i=0;i<teamType.length;i++) {
            System.out.println(teamType[i] + ",");
        }
        //Stat and Type Check
        for(int i = 0; i< pokeList.size(); i++){
            Pokemon currentPoke = pokeList.get(i);
            if((currentPoke.atk >= maxDef && currentPoke.spe >= maxSpe) ||
                    (currentPoke.spA >= maxSpD && currentPoke.spe >= maxSpe))
            {
                for(Type t : weakArrayType){
                    if(currentPoke.type1.name == t.name || currentPoke.type2.name == t.name) offensiveThreats.add(pokeList.get(i));
                }

            }
            if(currentPoke.def >= maxAtk || currentPoke.spD >= maxSpA){
                if(wallTypes.contains(currentPoke.type1) || wallTypes.contains(currentPoke.type2)) wallThreats.add(pokeList.get(i));
            }
        }

        //offThreatAdapt = new ArrayAdapter<Pokemon>(this,android.R.layout.simple_list_item_1,offensiveThreats);
        //wallThreatAdapt = new ArrayAdapter<Pokemon>(this,android.R.layout.simple_list_item_1,wallThreats);

        String offThreatString = "";
        String wallThreatString = "";

        for(Pokemon p : offensiveThreats){
            offThreatString += "-" + p.name.toString() + "\n";
        }

        for(Pokemon p : wallThreats){
            wallThreatString += "-" + p.name.toString() + "\n";
        }

        TextView ot = (TextView) findViewById(R.id.offthreatstv);
        TextView wt = (TextView) findViewById(R.id.wallthreatstv);
        ot.setText(offThreatString);
        wt.setText(wallThreatString);

        for(int i=0;i<teamType.length;i++){
            teamType[i]=0;
        }

    }

    public void initList()
    {
        pokeList.add(new Pokemon(Names.NONE,0,NONE,NONE,AbilityEnum.NONE,AbilityEnum.NONE,AbilityEnum.NONE,false,0,0,0,0,0,0));
        pokeList.add(new Pokemon(Names.Bulbasaur,1,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,true,45,49,49,65,65,45));
        pokeList.add(new Pokemon(Names.Ivysaur,2,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,true,60,62,63,80,80,60));
        pokeList.add(new Pokemon(Names.Venusaur,3,GRASS,POISON,AbilityEnum.OVERGROW,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,true,80,82,83,100,100,80));
        pokeList.add(new Pokemon(Names.Venusaur_Mega,3,GRASS,POISON,AbilityEnum.THICKFAT,AbilityEnum.NONE,AbilityEnum.NONE,false,80,100,123,122,120,80));
        pokeList.add(new Pokemon(Names.Charmander,4,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,true,39,52,43,60,50,65));
        pokeList.add(new Pokemon(Names.Charmeleon,5,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,true,58,64,58,80,65,80));
        pokeList.add(new Pokemon(Names.Charizard,6,FIRE,FLYING,AbilityEnum.BLAZE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,true,78,84,78,109,85,100));
        pokeList.add(new Pokemon(Names.Charizard_Mega_X,6,FIRE,DRAGON,AbilityEnum.TOUGHCLAWS,AbilityEnum.NONE,AbilityEnum.NONE,false,78,130,111,130,85,100));
        pokeList.add(new Pokemon(Names.Charizard_Mega_Y,6,FIRE,FLYING,AbilityEnum.DROUGHT,AbilityEnum.NONE,AbilityEnum.NONE,false,78,104,78,159,115,100));
        pokeList.add(new Pokemon(Names.Squirtle,7,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,44,48,65,50,64,43));
        pokeList.add(new Pokemon(Names.Wartortle,8,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,59,63,80,65,80,58));
        pokeList.add(new Pokemon(Names.Blastoise,9,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,79,83,100,85,105,78));
        pokeList.add(new Pokemon(Names.Blastoise_Mega,9,WATER,NONE,AbilityEnum.MEGALAUNCHER,AbilityEnum.NONE,AbilityEnum.NONE,false,79,103,120,135,115,78));
        pokeList.add(new Pokemon(Names.Caterpie,10,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,45,30,35,20,20,45));
        pokeList.add(new Pokemon(Names.Metapod,11,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,true,50,20,55,25,25,30));
        pokeList.add(new Pokemon(Names.Butterfree,12,BUG,FLYING,AbilityEnum.COMPOUNDEYES,AbilityEnum.TINTEDLENS,AbilityEnum.NONE,true,60,45,50,90,80,70));
        pokeList.add(new Pokemon(Names.Weedle,13,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,40,35,30,20,20,50));
        pokeList.add(new Pokemon(Names.Kakuna,14,BUG,POISON,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,true,45,25,50,25,25,35));
        pokeList.add(new Pokemon(Names.Beedrill,15,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.SNIPER,AbilityEnum.NONE,true,65,90,40,45,80,75));
        pokeList.add(new Pokemon(Names.Beedrill_Mega,15,BUG,POISON,AbilityEnum.ADAPTABILITY,AbilityEnum.NONE,AbilityEnum.NONE,false,65,150,40,15,80,145));
        pokeList.add(new Pokemon(Names.Pidgey,16,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,true,40,45,40,35,35,56));
        pokeList.add(new Pokemon(Names.Pidgeotto,17,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,true,63,60,55,50,50,71));
        pokeList.add(new Pokemon(Names.Pidgeot,18,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,true,83,80,75,70,70,101));
        pokeList.add(new Pokemon(Names.Pidgeot_Mega,18,NORMAL,FLYING,AbilityEnum.NOGUARD,AbilityEnum.NONE,AbilityEnum.NONE,false,83,80,80,135,80,121));
        pokeList.add(new Pokemon(Names.Rattata,19,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.GUTS,AbilityEnum.HUSTLE,true,30,56,35,25,35,72));
        pokeList.add(new Pokemon(Names.Raticate,20,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.GUTS,AbilityEnum.HUSTLE,true,55,81,60,50,70,97));
        pokeList.add(new Pokemon(Names.Spearow,21,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SNIPER,AbilityEnum.NONE,true,40,60,30,31,31,70));
        pokeList.add(new Pokemon(Names.Fearow,22,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SNIPER,AbilityEnum.NONE,true,65,90,65,61,61,100));
        pokeList.add(new Pokemon(Names.Ekans,23,POISON,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SHEDSKIN,AbilityEnum.UNNERVE,true,35,60,44,40,54,55));
        pokeList.add(new Pokemon(Names.Arbok,24,POISON,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SHEDSKIN,AbilityEnum.UNNERVE,true,60,85,69,65,79,80));
        pokeList.add(new Pokemon(Names.Pikachu,25,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,true,35,55,40,50,50,90));
        pokeList.add(new Pokemon(Names.Raichu,26,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,true,60,90,55,90,80,110));
        pokeList.add(new Pokemon(Names.Sandshrew,27,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.SANDRUSH,AbilityEnum.NONE,true,50,75,85,20,30,40));
        pokeList.add(new Pokemon(Names.Sandslash,28,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.SANDRUSH,AbilityEnum.NONE,true,75,100,110,45,55,65));
        pokeList.add(new Pokemon(Names.Nidoran_F,29,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,true,55,47,52,40,40,41));
        pokeList.add(new Pokemon(Names.Nidorina,30,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,true,70,62,67,55,55,56));
        pokeList.add(new Pokemon(Names.Nidoqueen,31,POISON,GROUND,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.SHEERFORCE,true,90,92,87,75,85,76));
        pokeList.add(new Pokemon(Names.Nidoran_M,32,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,true,46,57,40,40,40,50));
        pokeList.add(new Pokemon(Names.Nidorino,33,POISON,NONE,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.HUSTLE,true,61,72,57,55,55,65));
        pokeList.add(new Pokemon(Names.Nidoking,34,POISON,GROUND,AbilityEnum.POISONPOINT,AbilityEnum.RIVALRY,AbilityEnum.SHEERFORCE,true,81,102,77,85,75,85));
        pokeList.add(new Pokemon(Names.Clefairy,35,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.FRIENDGUARD,true,70,45,48,60,65,35));
        pokeList.add(new Pokemon(Names.Clefable,36,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.UNAWARE,true,95,70,73,95,90,60));
        pokeList.add(new Pokemon(Names.Vulpix,37,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.DROUGHT,AbilityEnum.NONE,true,38,41,40,50,65,65));
        pokeList.add(new Pokemon(Names.Ninetales,38,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.DROUGHT,AbilityEnum.NONE,true,73,76,75,81,100,100));
        pokeList.add(new Pokemon(Names.Jigglypuff,39,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRIENDGUARD,true,115,45,20,45,25,20));
        pokeList.add(new Pokemon(Names.Wigglytuff,40,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRISK,true,140,70,45,85,50,45));
        pokeList.add(new Pokemon(Names.Zubat,41,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,40,45,35,30,40,55));
        pokeList.add(new Pokemon(Names.Golbat,42,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,75,80,70,65,75,90));
        pokeList.add(new Pokemon(Names.Oddish,43,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,45,50,55,75,65,30));
        pokeList.add(new Pokemon(Names.Gloom,44,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.STENCH,AbilityEnum.NONE,true,60,65,70,85,75,40));
        pokeList.add(new Pokemon(Names.Vileplume,45,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.EFFECTSPORE,AbilityEnum.NONE,true,75,80,85,110,90,50));
        pokeList.add(new Pokemon(Names.Paras,46,BUG,GRASS,AbilityEnum.EFFECTSPORE,AbilityEnum.DRYSKIN,AbilityEnum.DAMP,true,35,70,55,45,55,25));
        pokeList.add(new Pokemon(Names.Parasect,47,BUG,GRASS,AbilityEnum.EFFECTSPORE,AbilityEnum.DRYSKIN,AbilityEnum.DAMP,true,60,95,80,60,80,30));
        pokeList.add(new Pokemon(Names.Venonat,48,BUG,POISON,AbilityEnum.COMPOUNDEYES,AbilityEnum.TINTEDLENS,AbilityEnum.RUNAWAY,true,60,55,50,40,55,45));
        pokeList.add(new Pokemon(Names.Venomoth,49,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.TINTEDLENS,AbilityEnum.WONDERSKIN,true,70,65,60,90,75,90));
        pokeList.add(new Pokemon(Names.Diglett,50,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.ARENATRAP,AbilityEnum.SANDFORCE,true,10,55,25,35,45,95));
        pokeList.add(new Pokemon(Names.Dugtrio,51,GROUND,NONE,AbilityEnum.SANDVEIL,AbilityEnum.ARENATRAP,AbilityEnum.SANDFORCE,true,35,80,50,50,70,120));
        pokeList.add(new Pokemon(Names.Meowth,52,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.TECHNICIAN,AbilityEnum.UNNERVE,true,40,45,35,40,40,90));
        pokeList.add(new Pokemon(Names.Persian,53,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.TECHNICIAN,AbilityEnum.UNNERVE,true,65,70,60,65,65,115));
        pokeList.add(new Pokemon(Names.Psyduck,54,WATER,NONE,AbilityEnum.DAMP,AbilityEnum.CLOUDNINE,AbilityEnum.SWIFTSWIM,true,50,52,48,65,50,55));
        pokeList.add(new Pokemon(Names.Golduck,55,WATER,NONE,AbilityEnum.DAMP,AbilityEnum.CLOUDNINE,AbilityEnum.SWIFTSWIM,true,80,82,78,95,80,85));
        pokeList.add(new Pokemon(Names.Mankey,56,FIGHTING,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.ANGERPOINT,AbilityEnum.DEFIANT,true,40,80,35,35,45,70));
        pokeList.add(new Pokemon(Names.Primeape,57,FIGHTING,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.ANGERPOINT,AbilityEnum.DEFIANT,true,65,105,60,60,70,95));
        pokeList.add(new Pokemon(Names.Growlithe,58,FIRE,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FLASHFIRE,AbilityEnum.JUSTIFIED,true,55,70,45,70,50,60));
        pokeList.add(new Pokemon(Names.Arcanine,59,FIRE,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FLASHFIRE,AbilityEnum.JUSTIFIED,true,90,110,80,100,80,95));
        pokeList.add(new Pokemon(Names.Poliwag,60,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,true,40,50,40,40,40,90));
        pokeList.add(new Pokemon(Names.Poliwhirl,61,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,true,65,65,65,50,50,90));
        pokeList.add(new Pokemon(Names.Poliwrath,62,WATER,FIGHTING,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.SWIFTSWIM,true,90,95,95,70,90,70));
        pokeList.add(new Pokemon(Names.Abra,63,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,true,25,20,15,105,55,90));
        pokeList.add(new Pokemon(Names.Kadabra,64,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,true,40,35,30,120,70,105));
        pokeList.add(new Pokemon(Names.Alakazam,65,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,AbilityEnum.MAGICGUARD,true,55,50,45,135,95,120));
        pokeList.add(new Pokemon(Names.Alakazam_Mega,65,PSYCHIC,NONE,AbilityEnum.TRACE,AbilityEnum.NONE,AbilityEnum.NONE,false,55,50,65,175,95,150));
        pokeList.add(new Pokemon(Names.Machop,66,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,true,70,80,50,35,35,35));
        pokeList.add(new Pokemon(Names.Machoke,67,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,true,80,100,70,50,60,45));
        pokeList.add(new Pokemon(Names.Machamp,68,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.NOGUARD,AbilityEnum.STEADFAST,true,90,130,80,65,85,55));
        pokeList.add(new Pokemon(Names.Bellsprout,69,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,true,50,75,35,70,30,40));
        pokeList.add(new Pokemon(Names.Weepinbell,70,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,true,65,90,50,85,45,55));
        pokeList.add(new Pokemon(Names.Victreebel,71,GRASS,POISON,AbilityEnum.CHLOROPHYLL,AbilityEnum.GLUTTONY,AbilityEnum.NONE,true,80,105,65,100,70,70));
        pokeList.add(new Pokemon(Names.Tentacool,72,WATER,POISON,AbilityEnum.CLEARBODY,AbilityEnum.LIQUIDOOZE,AbilityEnum.RAINDISH,true,40,40,35,50,100,70));
        pokeList.add(new Pokemon(Names.Tentacruel,73,WATER,POISON,AbilityEnum.CLEARBODY,AbilityEnum.LIQUIDOOZE,AbilityEnum.RAINDISH,true,80,70,65,80,120,100));
        pokeList.add(new Pokemon(Names.Geodude,74,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,true,40,80,100,30,30,20));
        pokeList.add(new Pokemon(Names.Graveler,75,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,true,55,95,115,45,45,35));
        pokeList.add(new Pokemon(Names.Golem,76,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,true,80,120,130,55,65,45));
        pokeList.add(new Pokemon(Names.Ponyta,77,FIRE,NONE,AbilityEnum.RUNAWAY,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,true,50,85,55,65,65,90));
        pokeList.add(new Pokemon(Names.Rapidash,78,FIRE,NONE,AbilityEnum.RUNAWAY,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,true,65,100,70,80,80,105));
        pokeList.add(new Pokemon(Names.Slowpoke,79,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,true,90,65,65,40,40,15));
        pokeList.add(new Pokemon(Names.Slowbro,80,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,true,95,75,110,100,80,30));
        pokeList.add(new Pokemon(Names.Slowbro_Mega,80,WATER,PSYCHIC,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,AbilityEnum.NONE,false,95,75,180,130,80,30));
        pokeList.add(new Pokemon(Names.Magnemite,81,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,true,25,35,70,95,55,45));
        pokeList.add(new Pokemon(Names.Magneton,82,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,true,50,60,95,120,70,70));
        pokeList.add(new Pokemon(Names.Farfetchd,83,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.INNERFOCUS,AbilityEnum.DEFIANT,true,52,65,55,58,62,60));
        pokeList.add(new Pokemon(Names.Doduo,84,NORMAL,FLYING,AbilityEnum.RUNAWAY,AbilityEnum.EARLYBIRD,AbilityEnum.TANGLEDFEET,true,35,85,45,35,35,75));
        pokeList.add(new Pokemon(Names.Dodrio,85,NORMAL,FLYING,AbilityEnum.RUNAWAY,AbilityEnum.EARLYBIRD,AbilityEnum.TANGLEDFEET,true,60,110,70,60,60,100));
        pokeList.add(new Pokemon(Names.Seel,86,WATER,NONE,AbilityEnum.THICKFAT,AbilityEnum.HYDRATION,AbilityEnum.ICEBODY,true,65,45,55,45,70,45));
        pokeList.add(new Pokemon(Names.Dewgong,87,WATER,ICE,AbilityEnum.THICKFAT,AbilityEnum.HYDRATION,AbilityEnum.ICEBODY,true,90,70,80,70,95,70));
        pokeList.add(new Pokemon(Names.Grimer,88,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.POISONTOUCH,true,80,80,50,40,50,25));
        pokeList.add(new Pokemon(Names.Muk,89,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.POISONTOUCH,true,105,105,75,65,100,50));
        pokeList.add(new Pokemon(Names.Shellder,90,WATER,NONE,AbilityEnum.SHELLARMOR,AbilityEnum.SKILLLINK,AbilityEnum.OVERCOAT,true,30,65,100,45,25,40));
        pokeList.add(new Pokemon(Names.Cloyster,91,WATER,ICE,AbilityEnum.SHELLARMOR,AbilityEnum.SKILLLINK,AbilityEnum.OVERCOAT,true,50,95,180,85,45,70));
        pokeList.add(new Pokemon(Names.Gastly,92,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,30,35,30,100,35,80));
        pokeList.add(new Pokemon(Names.Haunter,93,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,45,50,45,115,55,95));
        pokeList.add(new Pokemon(Names.Gengar,94,GHOST,POISON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,60,65,60,130,75,110));
        pokeList.add(new Pokemon(Names.Gengar_Mega,94,GHOST,POISON,AbilityEnum.SHADOWTAG,AbilityEnum.NONE,AbilityEnum.NONE,false,60,65,80,170,95,130));
        pokeList.add(new Pokemon(Names.Onix,95,ROCK,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.WEAKARMOR,true,35,45,160,30,45,70));
        pokeList.add(new Pokemon(Names.Drowzee,96,PSYCHIC,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FOREWARN,AbilityEnum.INNERFOCUS,true,60,48,45,43,90,42));
        pokeList.add(new Pokemon(Names.Hypno,97,PSYCHIC,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FOREWARN,AbilityEnum.INNERFOCUS,true,85,73,70,73,115,67));
        pokeList.add(new Pokemon(Names.Krabby,98,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.SHEERFORCE,true,30,105,90,25,25,50));
        pokeList.add(new Pokemon(Names.Kingler,99,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.SHEERFORCE,true,55,130,115,50,50,75));
        pokeList.add(new Pokemon(Names.Voltorb,100,ELECTRIC,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.STATIC,AbilityEnum.AFTERMATH,true,40,30,50,55,55,100));
        pokeList.add(new Pokemon(Names.Electrode,101,ELECTRIC,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.STATIC,AbilityEnum.AFTERMATH,true,60,50,70,80,80,140));
        pokeList.add(new Pokemon(Names.Exeggcute,102,GRASS,PSYCHIC,AbilityEnum.CHLOROPHYLL,AbilityEnum.HARVEST,AbilityEnum.NONE,true,60,40,80,60,45,40));
        pokeList.add(new Pokemon(Names.Exeggutor,103,GRASS,PSYCHIC,AbilityEnum.CHLOROPHYLL,AbilityEnum.HARVEST,AbilityEnum.NONE,true,95,95,85,125,65,55));
        pokeList.add(new Pokemon(Names.Cubone,104,GROUND,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.LIGHTNINGROD,AbilityEnum.BATTLEARMOR,true,50,50,95,40,50,35));
        pokeList.add(new Pokemon(Names.Marowak,105,GROUND,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.LIGHTNINGROD,AbilityEnum.BATTLEARMOR,true,60,80,110,50,80,45));
        pokeList.add(new Pokemon(Names.Hitmonlee,106,FIGHTING,NONE,AbilityEnum.LIMBER,AbilityEnum.RECKLESS,AbilityEnum.UNBURDEN,true,50,120,53,35,110,87));
        pokeList.add(new Pokemon(Names.Hitmonchan,107,FIGHTING,NONE,AbilityEnum.KEENEYE,AbilityEnum.IRONFIST,AbilityEnum.INNERFOCUS,true,50,105,79,35,110,76));
        pokeList.add(new Pokemon(Names.Lickitung,108,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.OBLIVIOUS,AbilityEnum.CLOUDNINE,true,90,55,75,60,75,30));
        pokeList.add(new Pokemon(Names.Koffing,109,POISON,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,40,65,95,60,45,35));
        pokeList.add(new Pokemon(Names.Weezing,110,POISON,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,65,90,120,85,70,60));
        pokeList.add(new Pokemon(Names.Rhyhorn,111,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.ROCKHEAD,AbilityEnum.RECKLESS,true,80,85,95,30,30,25));
        pokeList.add(new Pokemon(Names.Rhydon,112,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.ROCKHEAD,AbilityEnum.RECKLESS,true,105,130,120,45,45,40));
        pokeList.add(new Pokemon(Names.Chansey,113,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.HEALER,true,250,5,5,35,105,50));
        pokeList.add(new Pokemon(Names.Tangela,114,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.REGENERATOR,true,65,55,115,100,40,60));
        pokeList.add(new Pokemon(Names.Kangaskhan,115,NORMAL,NONE,AbilityEnum.EARLYBIRD,AbilityEnum.SCRAPPY,AbilityEnum.INNERFOCUS,true,105,95,80,40,80,90));
        pokeList.add(new Pokemon(Names.Kangaskhan_Mega,115,NORMAL,NONE,AbilityEnum.PARENTALBOND,AbilityEnum.NONE,AbilityEnum.NONE,false,105,125,100,60,100,100));
        pokeList.add(new Pokemon(Names.Horsea,116,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.SNIPER,AbilityEnum.DAMP,true,30,40,70,70,25,60));
        pokeList.add(new Pokemon(Names.Seadra,117,WATER,NONE,AbilityEnum.POISONPOINT,AbilityEnum.SNIPER,AbilityEnum.DAMP,true,55,65,95,95,45,85));
        pokeList.add(new Pokemon(Names.Goldeen,118,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.LIGHTNINGROD,true,45,67,60,35,50,63));
        pokeList.add(new Pokemon(Names.Seaking,119,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.LIGHTNINGROD,true,80,92,65,65,80,68));
        pokeList.add(new Pokemon(Names.Staryu,120,WATER,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.NATURALCURE,AbilityEnum.ANALYTIC,true,30,45,55,70,55,85));
        pokeList.add(new Pokemon(Names.Starmie,121,WATER,PSYCHIC,AbilityEnum.ILLUMINATE,AbilityEnum.NATURALCURE,AbilityEnum.ANALYTIC,true,60,75,85,100,85,115));
        pokeList.add(new Pokemon(Names.Mr_Mime,122,PSYCHIC,FAIRY,AbilityEnum.SOUNDPROOF,AbilityEnum.FILTER,AbilityEnum.TECHNICIAN,true,40,45,65,100,120,90));
        pokeList.add(new Pokemon(Names.Scyther,123,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.STEADFAST,true,70,110,80,55,80,105));
        pokeList.add(new Pokemon(Names.Jynx,124,ICE,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.FOREWARN,AbilityEnum.DRYSKIN,true,65,50,35,115,95,95));
        pokeList.add(new Pokemon(Names.Electabuzz,125,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,65,83,57,95,85,105));
        pokeList.add(new Pokemon(Names.Magmar,126,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,65,95,57,100,85,93));
        pokeList.add(new Pokemon(Names.Pinsir,127,BUG,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.MOLDBREAKER,AbilityEnum.MOXIE,true,65,125,100,55,70,85));
        pokeList.add(new Pokemon(Names.Pinsir_Mega,127,BUG,FLYING,AbilityEnum.AERILATE,AbilityEnum.NONE,AbilityEnum.NONE,false,65,155,120,65,90,105));
        pokeList.add(new Pokemon(Names.Tauros,128,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.ANGERPOINT,AbilityEnum.SHEERFORCE,true,75,100,95,40,70,110));
        pokeList.add(new Pokemon(Names.Magikarp,129,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.RATTLED,AbilityEnum.NONE,true,20,10,55,15,20,80));
        pokeList.add(new Pokemon(Names.Gyarados,130,WATER,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.NONE,true,95,125,79,60,100,81));
        pokeList.add(new Pokemon(Names.Gyarados,130,WATER,DARK,AbilityEnum.MOLDBREAKER,AbilityEnum.NONE,AbilityEnum.NONE,false,95,155,109,70,130,81));
        pokeList.add(new Pokemon(Names.Lapras,131,WATER,ICE,AbilityEnum.WATERABSORB,AbilityEnum.SHELLARMOR,AbilityEnum.HYDRATION,true,130,85,80,85,95,60));
        pokeList.add(new Pokemon(Names.Ditto,132,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.IMPOSTER,AbilityEnum.NONE,true,48,48,48,48,48,48));
        pokeList.add(new Pokemon(Names.Eevee,133,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.ADAPTABILITY,AbilityEnum.ANTICIPATION,true,55,55,50,45,65,55));
        pokeList.add(new Pokemon(Names.Vaporeon,134,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.WATERABSORB,AbilityEnum.HYDRATION,true,130,65,60,110,95,65));
        pokeList.add(new Pokemon(Names.Jolteon,135,ELECTRIC,NONE,AbilityEnum.VOLTABSORB,AbilityEnum.VOLTABSORB,AbilityEnum.QUICKFEET,true,65,65,60,110,95,130));
        pokeList.add(new Pokemon(Names.Flareon,136,FIRE,NONE,AbilityEnum.FLASHFIRE,AbilityEnum.FLASHFIRE,AbilityEnum.GUTS,true,65,130,60,95,110,65));
        pokeList.add(new Pokemon(Names.Porygon,137,NORMAL,NONE,AbilityEnum.TRACE,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,true,65,60,70,85,75,40));
        pokeList.add(new Pokemon(Names.Omanyte,138,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,true,35,40,100,90,55,35));
        pokeList.add(new Pokemon(Names.Omastar,139,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,true,70,60,125,115,70,55));
        pokeList.add(new Pokemon(Names.Kabuto,140,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.BATTLEARMOR,AbilityEnum.WEAKARMOR,true,30,80,90,55,45,55));
        pokeList.add(new Pokemon(Names.Kabutops,141,ROCK,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.BATTLEARMOR,AbilityEnum.WEAKARMOR,true,60,115,105,65,70,80));
        pokeList.add(new Pokemon(Names.Aerodactyl,142,ROCK,FLYING,AbilityEnum.ROCKHEAD,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,true,80,105,65,60,75,130));
        pokeList.add(new Pokemon(Names.Aerodactyl_Mega,142,ROCK,FLYING,AbilityEnum.TOUGHCLAWS,AbilityEnum.NONE,AbilityEnum.NONE,false,80,135,85,70,95,150));
        pokeList.add(new Pokemon(Names.Snorlax,143,NORMAL,NONE,AbilityEnum.IMMUNITY,AbilityEnum.THICKFAT,AbilityEnum.GLUTTONY,true,160,110,65,65,110,30));
        pokeList.add(new Pokemon(Names.Articuno,144,ICE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.SNOWCLOAK,AbilityEnum.NONE,true,90,85,100,95,125,85));
        pokeList.add(new Pokemon(Names.Zapdos,145,ELECTRIC,FLYING,AbilityEnum.PRESSURE,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,true,90,90,85,125,90,100));
        pokeList.add(new Pokemon(Names.Moltres,146,FIRE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.FLAMEBODY,AbilityEnum.NONE,true,90,100,90,125,85,90));
        pokeList.add(new Pokemon(Names.Dratini,147,DRAGON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.MARVELSCALE,AbilityEnum.NONE,true,41,64,45,50,50,50));
        pokeList.add(new Pokemon(Names.Dragonair,148,DRAGON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.MARVELSCALE,AbilityEnum.NONE,true,61,84,65,70,70,70));
        pokeList.add(new Pokemon(Names.Dragonite,149,DRAGON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.MULTISCALE,AbilityEnum.NONE,true,91,134,95,100,100,80));
        pokeList.add(new Pokemon(Names.Mewtwo,150,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,AbilityEnum.NONE,true,106,110,90,154,90,130));
        pokeList.add(new Pokemon(Names.Mewtwo_Mega_X,150,PSYCHIC,FIGHTING,AbilityEnum.STEADFAST,AbilityEnum.NONE,AbilityEnum.NONE,false,106,190,100,154,100,130));
        pokeList.add(new Pokemon(Names.Mewtwo_Mega_Y,150,PSYCHIC,NONE,AbilityEnum.INSOMNIA,AbilityEnum.NONE,AbilityEnum.NONE,false,106,150,70,194,120,140));
        pokeList.add(new Pokemon(Names.Mew,151,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Chikorita,152,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,true,45,49,65,49,65,45));
        pokeList.add(new Pokemon(Names.Bayleef,153,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,true,60,62,80,63,80,60));
        pokeList.add(new Pokemon(Names.Meganium,154,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.LEAFGUARD,AbilityEnum.NONE,true,80,82,100,83,100,80));
        pokeList.add(new Pokemon(Names.Cyndaquil,155,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,true,39,52,43,60,50,65));
        pokeList.add(new Pokemon(Names.Quilava,156,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,true,58,64,58,80,65,80));
        pokeList.add(new Pokemon(Names.Typhlosion,157,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,true,78,84,78,109,85,100));
        pokeList.add(new Pokemon(Names.Totodile,158,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,50,65,64,44,48,43));
        pokeList.add(new Pokemon(Names.Croconaw,159,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,65,80,80,59,63,58));
        pokeList.add(new Pokemon(Names.Feraligatr,160,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,85,105,100,79,83,78));
        pokeList.add(new Pokemon(Names.Sentret,161,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.FRISK,true,35,46,34,35,45,20));
        pokeList.add(new Pokemon(Names.Furret,162,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.FRISK,true,85,76,64,45,55,90));
        pokeList.add(new Pokemon(Names.Hoothoot,163,NORMAL,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.KEENEYE,AbilityEnum.TINTEDLENS,true,60,30,30,36,56,50));
        pokeList.add(new Pokemon(Names.Noctowl,164,NORMAL,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.KEENEYE,AbilityEnum.TINTEDLENS,true,100,50,50,76,96,70));
        pokeList.add(new Pokemon(Names.Ledyba,165,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.EARLYBIRD,AbilityEnum.RATTLED,true,40,20,30,40,80,55));
        pokeList.add(new Pokemon(Names.Ledian,166,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.EARLYBIRD,AbilityEnum.IRONFIST,true,55,35,50,55,110,85));
        pokeList.add(new Pokemon(Names.Spinarak,167,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.INSOMNIA,AbilityEnum.SNIPER,true,40,60,40,40,40,30));
        pokeList.add(new Pokemon(Names.Ariados,168,BUG,POISON,AbilityEnum.SWARM,AbilityEnum.INSOMNIA,AbilityEnum.SNIPER,true,70,90,70,60,60,40));
        pokeList.add(new Pokemon(Names.Crobat,169,POISON,FLYING,AbilityEnum.INNERFOCUS,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,85,90,80,70,80,130));
        pokeList.add(new Pokemon(Names.Chinchou,170,WATER,ELECTRIC,AbilityEnum.VOLTABSORB,AbilityEnum.ILLUMINATE,AbilityEnum.WATERABSORB,true,75,38,38,56,56,67));
        pokeList.add(new Pokemon(Names.Lanturn,171,WATER,ELECTRIC,AbilityEnum.VOLTABSORB,AbilityEnum.ILLUMINATE,AbilityEnum.WATERABSORB,true,125,58,58,76,76,67));
        pokeList.add(new Pokemon(Names.Pichu,172,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,true,20,40,15,35,35,60));
        pokeList.add(new Pokemon(Names.Cleffa,173,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.MAGICGUARD,AbilityEnum.FRIENDGUARD,true,50,25,28,45,55,15));
        pokeList.add(new Pokemon(Names.Igglybuff,174,NORMAL,FAIRY,AbilityEnum.CUTECHARM,AbilityEnum.COMPETITIVE,AbilityEnum.FRIENDGUARD,true,90,30,15,40,20,15));
        pokeList.add(new Pokemon(Names.Togepi,175,FAIRY,NONE,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,true,35,20,65,40,65,20));
        pokeList.add(new Pokemon(Names.Togetic,176,FAIRY,FLYING,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,true,55,40,85,80,105,40));
        pokeList.add(new Pokemon(Names.Natu,177,PSYCHIC,FLYING,AbilityEnum.SYNCHRONIZE,AbilityEnum.EARLYBIRD,AbilityEnum.MAGICBOUNCE,true,40,50,45,70,45,70));
        pokeList.add(new Pokemon(Names.Xatu,178,PSYCHIC,FLYING,AbilityEnum.SYNCHRONIZE,AbilityEnum.EARLYBIRD,AbilityEnum.MAGICBOUNCE,true,65,75,70,95,70,95));
        pokeList.add(new Pokemon(Names.Mareep,179,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,true,55,40,40,65,45,35));
        pokeList.add(new Pokemon(Names.Flaaffy,180,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,true,70,55,55,80,60,45));
        pokeList.add(new Pokemon(Names.Ampharos,181,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.PLUS,AbilityEnum.NONE,true,90,75,85,115,90,55));
        pokeList.add(new Pokemon(Names.Ampharos_Mega,181,ELECTRIC,DRAGON,AbilityEnum.MOLDBREAKER,AbilityEnum.NONE,AbilityEnum.NONE,false,90,95,105,165,110,45));
        pokeList.add(new Pokemon(Names.Bellossom,182,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.HEALER,AbilityEnum.NONE,true,75,80,95,90,100,50));
        pokeList.add(new Pokemon(Names.Marill,183,WATER,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,true,70,20,50,20,50,40));
        pokeList.add(new Pokemon(Names.Azumarill,184,WATER,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,true,100,50,80,50,80,60));
        pokeList.add(new Pokemon(Names.Sudowoodo,185,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.RATTLED,true,70,100,115,30,65,30));
        pokeList.add(new Pokemon(Names.Politoed,186,WATER,NONE,AbilityEnum.WATERABSORB,AbilityEnum.DAMP,AbilityEnum.DRIZZLE,true,90,75,75,90,100,70));
        pokeList.add(new Pokemon(Names.Hoppip,187,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,true,35,35,40,35,55,50));
        pokeList.add(new Pokemon(Names.Skiploom,188,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,true,55,45,50,45,65,80));
        pokeList.add(new Pokemon(Names.Jumpluff,189,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.INFILTRATOR,true,75,55,70,55,95,110));
        pokeList.add(new Pokemon(Names.Aipom,190,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.PICKUP,AbilityEnum.SKILLLINK,true,55,70,55,40,55,85));
        pokeList.add(new Pokemon(Names.Sunkern,191,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.EARLYBIRD,true,30,30,30,30,30,30));
        pokeList.add(new Pokemon(Names.Sunflora,192,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.EARLYBIRD,true,75,75,55,105,85,30));
        pokeList.add(new Pokemon(Names.Yanma,193,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRISK,true,65,65,45,75,45,95));
        pokeList.add(new Pokemon(Names.Wooper,194,WATER,GROUND,AbilityEnum.DAMP,AbilityEnum.WATERABSORB,AbilityEnum.UNAWARE,true,55,45,45,25,25,15));
        pokeList.add(new Pokemon(Names.Quagsire,195,WATER,GROUND,AbilityEnum.DAMP,AbilityEnum.WATERABSORB,AbilityEnum.UNAWARE,true,95,85,85,65,65,35));
        pokeList.add(new Pokemon(Names.Espeon,196,PSYCHIC,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.SYNCHRONIZE,AbilityEnum.MAGICBOUNCE,true,65,65,60,130,95,110));
        pokeList.add(new Pokemon(Names.Umbreon,197,DARK,NONE,AbilityEnum.SYNCHRONIZE,AbilityEnum.SYNCHRONIZE,AbilityEnum.INNERFOCUS,true,95,65,110,60,130,65));
        pokeList.add(new Pokemon(Names.Murkrow,198,DARK,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.SUPERLUCK,AbilityEnum.PRANKSTER,true,60,85,42,85,42,91));
        pokeList.add(new Pokemon(Names.Slowking,199,WATER,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.OWNTEMPO,AbilityEnum.REGENERATOR,true,95,75,80,100,110,30));
        pokeList.add(new Pokemon(Names.Misdreavus,200,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,60,60,60,85,85,85));
        pokeList.add(new Pokemon(Names.Unown,201,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,48,72,48,72,48,48));
        pokeList.add(new Pokemon(Names.Wobbuffet,202,PSYCHIC,NONE,AbilityEnum.SHADOWTAG,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,190,33,58,33,58,33));
        pokeList.add(new Pokemon(Names.Girafarig,203,NORMAL,PSYCHIC,AbilityEnum.INNERFOCUS,AbilityEnum.EARLYBIRD,AbilityEnum.SAPSIPPER,true,70,80,65,90,65,85));
        pokeList.add(new Pokemon(Names.Pineco,204,BUG,NONE,AbilityEnum.STURDY,AbilityEnum.OVERCOAT,AbilityEnum.NONE,true,50,65,90,35,35,15));
        pokeList.add(new Pokemon(Names.Forretress,205,BUG,STEEL,AbilityEnum.STURDY,AbilityEnum.OVERCOAT,AbilityEnum.NONE,true,75,90,140,60,60,40));
        pokeList.add(new Pokemon(Names.Dunsparce,206,NORMAL,NONE,AbilityEnum.SERENEGRACE,AbilityEnum.RUNAWAY,AbilityEnum.RATTLED,true,100,70,70,65,65,45));
        pokeList.add(new Pokemon(Names.Gligar,207,GROUND,FLYING,AbilityEnum.HYPERCUTTER,AbilityEnum.SANDVEIL,AbilityEnum.IMMUNITY,true,65,75,105,35,65,85));
        pokeList.add(new Pokemon(Names.Steelix,208,STEEL,GROUND,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,AbilityEnum.SHEERFORCE,true,75,85,200,55,65,30));
        pokeList.add(new Pokemon(Names.Steelix_Mega,208,STEEL,GROUND,AbilityEnum.SANDFORCE,AbilityEnum.NONE,AbilityEnum.NONE,false,75,125,230,55,95,30));
        pokeList.add(new Pokemon(Names.Snubbull,209,FAIRY,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.RUNAWAY,AbilityEnum.RATTLED,true,60,80,50,40,40,30));
        pokeList.add(new Pokemon(Names.Granbull,210,FAIRY,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.QUICKFEET,AbilityEnum.RATTLED,true,90,120,75,60,60,45));
        pokeList.add(new Pokemon(Names.Qwilfish,211,WATER,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWIFTSWIM,AbilityEnum.INTIMIDATE,true,65,95,75,55,55,85));
        pokeList.add(new Pokemon(Names.Scizor,212,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.LIGHTMETAL,true,70,130,100,55,80,65));
        pokeList.add(new Pokemon(Names.Scizor_Mega,212,BUG,STEEL,AbilityEnum.TECHNICIAN,AbilityEnum.NONE,AbilityEnum.NONE,false,70,150,140,65,100,75));
        pokeList.add(new Pokemon(Names.Shuckle,213,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.GLUTTONY,AbilityEnum.CONTRARY,true,20,10,230,10,230,5));
        pokeList.add(new Pokemon(Names.Heracross,214,BUG,FIGHTING,AbilityEnum.SWARM,AbilityEnum.GUTS,AbilityEnum.MOXIE,true,80,125,75,40,95,85));
        pokeList.add(new Pokemon(Names.Heracross_Mega,214,BUG,FIGHTING,AbilityEnum.SKILLLINK,AbilityEnum.NONE,AbilityEnum.NONE,false,80,185,115,40,105,75));
        pokeList.add(new Pokemon(Names.Sneasel,215,DARK,ICE,AbilityEnum.INNERFOCUS,AbilityEnum.KEENEYE,AbilityEnum.PICKPOCKET,true,55,95,55,35,75,115));
        pokeList.add(new Pokemon(Names.Teddiursa,216,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.QUICKFEET,AbilityEnum.HONEYGATHER,true,60,80,50,50,50,40));
        pokeList.add(new Pokemon(Names.Ursaring,217,NORMAL,NONE,AbilityEnum.GUTS,AbilityEnum.QUICKFEET,AbilityEnum.UNNERVE,true,90,130,75,75,75,55));
        pokeList.add(new Pokemon(Names.Slugma,218,FIRE,NONE,AbilityEnum.MAGMAARMOR,AbilityEnum.FLAMEBODY,AbilityEnum.WEAKARMOR,true,40,40,40,70,40,20));
        pokeList.add(new Pokemon(Names.Magcargo,219,FIRE,ROCK,AbilityEnum.MAGMAARMOR,AbilityEnum.FLAMEBODY,AbilityEnum.WEAKARMOR,true,50,50,120,80,80,30));
        pokeList.add(new Pokemon(Names.Swinub,220,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,true,50,50,40,30,30,50));
        pokeList.add(new Pokemon(Names.Piloswine,221,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,true,100,100,80,60,60,50));
        pokeList.add(new Pokemon(Names.Corsola,222,WATER,ROCK,AbilityEnum.HUSTLE,AbilityEnum.NATURALCURE,AbilityEnum.REGENERATOR,true,55,55,85,65,85,35));
        pokeList.add(new Pokemon(Names.Remoraid,223,WATER,NONE,AbilityEnum.HUSTLE,AbilityEnum.SNIPER,AbilityEnum.MOODY,true,35,65,35,65,35,65));
        pokeList.add(new Pokemon(Names.Octillery,224,WATER,NONE,AbilityEnum.SUCTIONCUPS,AbilityEnum.SNIPER,AbilityEnum.MOODY,true,75,105,75,105,75,45));
        pokeList.add(new Pokemon(Names.Delibird,225,ICE,FLYING,AbilityEnum.VITALSPIRIT,AbilityEnum.HUSTLE,AbilityEnum.INSOMNIA,true,45,55,45,65,45,75));
        pokeList.add(new Pokemon(Names.Mantine,226,WATER,FLYING,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERABSORB,AbilityEnum.WATERVEIL,true,65,40,70,80,140,70));
        pokeList.add(new Pokemon(Names.Skarmory,227,STEEL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.STURDY,AbilityEnum.WEAKARMOR,true,65,80,140,40,70,70));
        pokeList.add(new Pokemon(Names.Houndour,228,DARK,FIRE,AbilityEnum.EARLYBIRD,AbilityEnum.FLASHFIRE,AbilityEnum.UNNERVE,true,45,60,30,80,50,65));
        pokeList.add(new Pokemon(Names.Houndoom,229,DARK,FIRE,AbilityEnum.EARLYBIRD,AbilityEnum.FLASHFIRE,AbilityEnum.UNNERVE,true,75,90,50,110,80,95));
        pokeList.add(new Pokemon(Names.Houndoom_Mega,229,DARK,FIRE,AbilityEnum.SOLARPOWER,AbilityEnum.NONE,AbilityEnum.NONE,false,75,90,90,140,90,115));
        pokeList.add(new Pokemon(Names.Kingdra,230,WATER,DRAGON,AbilityEnum.SWIFTSWIM,AbilityEnum.SNIPER,AbilityEnum.DAMP,true,75,95,95,95,95,85));
        pokeList.add(new Pokemon(Names.Phanpy,231,GROUND,NONE,AbilityEnum.PICKUP,AbilityEnum.SANDVEIL,AbilityEnum.NONE,true,90,60,60,40,40,40));
        pokeList.add(new Pokemon(Names.Donphan,232,GROUND,NONE,AbilityEnum.STURDY,AbilityEnum.SANDVEIL,AbilityEnum.NONE,true,90,120,120,60,60,50));
        pokeList.add(new Pokemon(Names.Porygon2,233,NORMAL,NONE,AbilityEnum.TRACE,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,true,85,80,90,105,95,60));
        pokeList.add(new Pokemon(Names.Stantler,234,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.FRISK,AbilityEnum.SAPSIPPER,true,73,95,62,85,65,85));
        pokeList.add(new Pokemon(Names.Smeargle,235,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.TECHNICIAN,AbilityEnum.MOODY,true,55,20,35,20,45,75));
        pokeList.add(new Pokemon(Names.Tyrogue,236,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.STEADFAST,AbilityEnum.VITALSPIRIT,true,35,35,35,35,35,35));
        pokeList.add(new Pokemon(Names.Hitmontop,237,FIGHTING,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.TECHNICIAN,AbilityEnum.STEADFAST,true,50,95,95,35,110,70));
        pokeList.add(new Pokemon(Names.Smoochum,238,ICE,PSYCHIC,AbilityEnum.OBLIVIOUS,AbilityEnum.FOREWARN,AbilityEnum.HYDRATION,true,45,30,15,85,65,65));
        pokeList.add(new Pokemon(Names.Elekid,239,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,45,63,37,65,55,95));
        pokeList.add(new Pokemon(Names.Magby,240,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,45,75,37,70,55,83));
        pokeList.add(new Pokemon(Names.Miltank,241,NORMAL,NONE,AbilityEnum.THICKFAT,AbilityEnum.SCRAPPY,AbilityEnum.SAPSIPPER,true,95,80,105,40,70,100));
        pokeList.add(new Pokemon(Names.Blissey,242,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.HEALER,true,255,10,10,75,135,55));
        pokeList.add(new Pokemon(Names.Raikou,243,ELECTRIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.VOLTABSORB,AbilityEnum.NONE,true,90,85,75,115,100,115));
        pokeList.add(new Pokemon(Names.Entei,244,FIRE,NONE,AbilityEnum.PRESSURE,AbilityEnum.FLASHFIRE,AbilityEnum.NONE,true,115,115,85,90,75,100));
        pokeList.add(new Pokemon(Names.Suicune,245,WATER,NONE,AbilityEnum.PRESSURE,AbilityEnum.WATERABSORB,AbilityEnum.NONE,true,100,75,115,90,115,85));
        pokeList.add(new Pokemon(Names.Larvitar,246,ROCK,GROUND,AbilityEnum.GUTS,AbilityEnum.SANDVEIL,AbilityEnum.NONE,true,50,64,50,45,50,41));
        pokeList.add(new Pokemon(Names.Pupitar,247,ROCK,GROUND,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,true,70,84,70,65,70,51));
        pokeList.add(new Pokemon(Names.Tyranitar,248,ROCK,DARK,AbilityEnum.SANDSTREAM,AbilityEnum.UNNERVE,AbilityEnum.NONE,true,100,134,110,95,100,61));
        pokeList.add(new Pokemon(Names.Tyranitar_Mega,248,ROCK,DARK,AbilityEnum.SANDSTREAM,AbilityEnum.NONE,AbilityEnum.NONE,false,100,164,150,95,120,71));
        pokeList.add(new Pokemon(Names.Lugia,249,PSYCHIC,FLYING,AbilityEnum.PRESSURE,AbilityEnum.MULTISCALE,AbilityEnum.NONE,true,106,90,130,90,154,110));
        pokeList.add(new Pokemon(Names.Ho_Oh,250,FIRE,FLYING,AbilityEnum.PRESSURE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,true,106,130,90,110,154,90));
        pokeList.add(new Pokemon(Names.Celebi,251,PSYCHIC,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Treecko,252,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,true,40,45,35,65,55,70));
        pokeList.add(new Pokemon(Names.Grovyle,253,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,true,50,65,45,85,65,95));
        pokeList.add(new Pokemon(Names.Sceptile,254,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.UNBURDEN,AbilityEnum.NONE,true,70,85,65,105,85,120));
        pokeList.add(new Pokemon(Names.Sceptile_Mega,254,GRASS,DRAGON,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,AbilityEnum.NONE,false,70,110,75,145,85,145));
        pokeList.add(new Pokemon(Names.Torchic,255,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,true,45,60,40,70,50,45));
        pokeList.add(new Pokemon(Names.Combusken,256,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,true,60,85,60,85,60,55));
        pokeList.add(new Pokemon(Names.Blaziken,257,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,true,80,120,70,110,70,80));
        pokeList.add(new Pokemon(Names.Blaziken_Mega,257,FIRE,FIGHTING,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,AbilityEnum.NONE,false,80,160,80,130,80,100));
        pokeList.add(new Pokemon(Names.Mudkip,258,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,true,50,70,50,50,50,40));
        pokeList.add(new Pokemon(Names.Marshtomp,259,WATER,GROUND,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,true,70,85,70,60,70,50));
        pokeList.add(new Pokemon(Names.Swampert,260,WATER,GROUND,AbilityEnum.TORRENT,AbilityEnum.DAMP,AbilityEnum.NONE,true,100,110,90,85,90,60));
        pokeList.add(new Pokemon(Names.Swampert_Mega,260,WATER,GROUND,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,AbilityEnum.NONE,false,100,150,100,95,100,70));
        pokeList.add(new Pokemon(Names.Poochyena,261,DARK,NONE,AbilityEnum.RUNAWAY,AbilityEnum.QUICKFEET,AbilityEnum.RATTLED,true,35,55,35,30,30,35));
        pokeList.add(new Pokemon(Names.Mightyena,262,DARK,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.QUICKFEET,AbilityEnum.MOXIE,true,70,90,70,60,60,70));
        pokeList.add(new Pokemon(Names.Zigzagoon,263,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.GLUTTONY,AbilityEnum.QUICKFEET,true,38,30,41,30,41,60));
        pokeList.add(new Pokemon(Names.Linoone,264,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.GLUTTONY,AbilityEnum.QUICKFEET,true,78,70,61,50,61,100));
        pokeList.add(new Pokemon(Names.Wurmple,265,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,45,45,35,20,30,20));
        pokeList.add(new Pokemon(Names.Silcoon,266,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,true,50,35,55,25,25,15));
        pokeList.add(new Pokemon(Names.Beautifly,267,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.RIVALRY,AbilityEnum.NONE,true,60,70,50,100,50,65));
        pokeList.add(new Pokemon(Names.Cascoon,268,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.NONE,AbilityEnum.NONE,true,50,35,55,25,25,15));
        pokeList.add(new Pokemon(Names.Dustox,269,BUG,POISON,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.NONE,true,60,50,70,50,90,65));
        pokeList.add(new Pokemon(Names.Lotad,270,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,true,40,30,30,40,50,30));
        pokeList.add(new Pokemon(Names.Lombre,271,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,true,60,50,50,60,70,50));
        pokeList.add(new Pokemon(Names.Ludicolo,272,WATER,GRASS,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.OWNTEMPO,true,80,70,70,90,100,70));
        pokeList.add(new Pokemon(Names.Seedot,273,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,true,40,40,50,30,30,30));
        pokeList.add(new Pokemon(Names.Nuzleaf,274,GRASS,DARK,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,true,70,70,40,60,40,60));
        pokeList.add(new Pokemon(Names.Shiftry,275,GRASS,DARK,AbilityEnum.CHLOROPHYLL,AbilityEnum.EARLYBIRD,AbilityEnum.PICKPOCKET,true,90,100,60,90,60,80));
        pokeList.add(new Pokemon(Names.Taillow,276,NORMAL,FLYING,AbilityEnum.GUTS,AbilityEnum.SCRAPPY,AbilityEnum.NONE,true,40,55,30,30,30,85));
        pokeList.add(new Pokemon(Names.Swellow,277,NORMAL,FLYING,AbilityEnum.GUTS,AbilityEnum.SCRAPPY,AbilityEnum.NONE,true,60,85,60,50,50,125));
        pokeList.add(new Pokemon(Names.Wingull,278,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,40,30,30,55,30,85));
        pokeList.add(new Pokemon(Names.Pelipper,279,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,60,50,100,85,70,65));
        pokeList.add(new Pokemon(Names.Ralts,280,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,true,28,25,25,45,35,40));
        pokeList.add(new Pokemon(Names.Kirlia,281,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,true,38,35,35,65,55,50));
        pokeList.add(new Pokemon(Names.Gardevoir,282,PSYCHIC,FAIRY,AbilityEnum.SYNCHRONIZE,AbilityEnum.TRACE,AbilityEnum.TELEPATHY,true,68,65,65,125,115,80));
        pokeList.add(new Pokemon(Names.Gardevoir_Mega,282,PSYCHIC,FAIRY,AbilityEnum.PIXILATE,AbilityEnum.NONE,AbilityEnum.NONE,false,68,85,65,165,135,100));
        pokeList.add(new Pokemon(Names.Surskit,283,BUG,WATER,AbilityEnum.SWIFTSWIM,AbilityEnum.RAINDISH,AbilityEnum.NONE,true,40,30,32,50,52,65));
        pokeList.add(new Pokemon(Names.Masquerain,284,BUG,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.UNNERVE,AbilityEnum.NONE,true,70,60,62,80,82,60));
        pokeList.add(new Pokemon(Names.Shroomish,285,GRASS,NONE,AbilityEnum.EFFECTSPORE,AbilityEnum.POISONHEAL,AbilityEnum.QUICKFEET,true,60,40,60,40,60,35));
        pokeList.add(new Pokemon(Names.Breloom,286,GRASS,FIGHTING,AbilityEnum.EFFECTSPORE,AbilityEnum.POISONHEAL,AbilityEnum.TECHNICIAN,true,60,130,80,60,60,70));
        pokeList.add(new Pokemon(Names.Slakoth,287,NORMAL,NONE,AbilityEnum.TRUANT,AbilityEnum.NONE,AbilityEnum.NONE,true,60,60,60,35,35,30));
        pokeList.add(new Pokemon(Names.Vigoroth,288,NORMAL,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,AbilityEnum.NONE,true,80,80,80,55,55,90));
        pokeList.add(new Pokemon(Names.Slaking,289,NORMAL,NONE,AbilityEnum.TRUANT,AbilityEnum.NONE,AbilityEnum.NONE,true,150,160,100,95,65,100));
        pokeList.add(new Pokemon(Names.Nincada,290,BUG,GROUND,AbilityEnum.COMPOUNDEYES,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,31,45,90,30,30,40));
        pokeList.add(new Pokemon(Names.Ninjask,291,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,61,90,45,50,50,160));
        pokeList.add(new Pokemon(Names.Shedinja,292,BUG,GHOST,AbilityEnum.WONDERGUARD,AbilityEnum.NONE,AbilityEnum.NONE,true,1,90,45,30,30,40));
        pokeList.add(new Pokemon(Names.Whismur,293,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.RATTLED,AbilityEnum.NONE,true,64,51,23,51,23,28));
        pokeList.add(new Pokemon(Names.Loudred,294,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.SCRAPPY,AbilityEnum.NONE,true,84,71,43,71,43,48));
        pokeList.add(new Pokemon(Names.Exploud,295,NORMAL,NONE,AbilityEnum.SOUNDPROOF,AbilityEnum.SCRAPPY,AbilityEnum.NONE,true,104,91,63,91,73,68));
        pokeList.add(new Pokemon(Names.Makuhita,296,FIGHTING,NONE,AbilityEnum.THICKFAT,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,true,72,60,30,20,30,25));
        pokeList.add(new Pokemon(Names.Hariyama,297,FIGHTING,NONE,AbilityEnum.THICKFAT,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,true,144,120,60,40,60,50));
        pokeList.add(new Pokemon(Names.Azurill,298,NORMAL,FAIRY,AbilityEnum.THICKFAT,AbilityEnum.HUGEPOWER,AbilityEnum.SAPSIPPER,true,50,20,40,20,40,20));
        pokeList.add(new Pokemon(Names.Nosepass,299,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.MAGNETPULL,AbilityEnum.SANDFORCE,true,30,45,135,45,90,30));
        pokeList.add(new Pokemon(Names.Skitty,300,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.NORMALIZE,AbilityEnum.WONDERSKIN,true,50,45,45,35,35,50));
        pokeList.add(new Pokemon(Names.Delcatty,301,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.NORMALIZE,AbilityEnum.WONDERSKIN,true,70,65,65,55,55,70));
        pokeList.add(new Pokemon(Names.Sableye,302,DARK,GHOST,AbilityEnum.KEENEYE,AbilityEnum.STALL,AbilityEnum.PRANKSTER,true,50,75,75,65,65,50));
        pokeList.add(new Pokemon(Names.Sableye_Mega,302,DARK,GHOST,AbilityEnum.MAGICBOUNCE,AbilityEnum.NONE,AbilityEnum.NONE,false,50,85,125,85,115,20));
        pokeList.add(new Pokemon(Names.Mawile,303,STEEL,FAIRY,AbilityEnum.HYPERCUTTER,AbilityEnum.INTIMIDATE,AbilityEnum.SHEERFORCE,true,50,85,85,55,55,50));
        pokeList.add(new Pokemon(Names.Mawile_Mega,303,STEEL,FAIRY,AbilityEnum.HUGEPOWER,AbilityEnum.NONE,AbilityEnum.NONE,false,50,105,125,55,95,50));
        pokeList.add(new Pokemon(Names.Aron,304,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,true,50,70,100,40,40,30));
        pokeList.add(new Pokemon(Names.Lairon,305,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,true,60,90,140,50,50,40));
        pokeList.add(new Pokemon(Names.Aggron,306,STEEL,ROCK,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.HEAVYMETAL,true,70,110,180,60,60,50));
        pokeList.add(new Pokemon(Names.Aggron_Mega,306,STEEL,NONE,AbilityEnum.FILTER,AbilityEnum.NONE,AbilityEnum.NONE,false,70,140,230,60,80,50));
        pokeList.add(new Pokemon(Names.Meditite,307,FIGHTING,PSYCHIC,AbilityEnum.PUREPOWER,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,30,40,55,40,55,60));
        pokeList.add(new Pokemon(Names.Medicham,308,FIGHTING,PSYCHIC,AbilityEnum.PUREPOWER,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,60,60,75,60,75,80));
        pokeList.add(new Pokemon(Names.Medicham_Mega,308,FIGHTING,PSYCHIC,AbilityEnum.PUREPOWER,AbilityEnum.NONE,AbilityEnum.NONE,false,60,100,85,80,85,100));
        pokeList.add(new Pokemon(Names.Electrike,309,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.MINUS,true,40,45,40,65,40,65));
        pokeList.add(new Pokemon(Names.Manectric,310,ELECTRIC,NONE,AbilityEnum.STATIC,AbilityEnum.LIGHTNINGROD,AbilityEnum.MINUS,true,70,75,60,105,60,105));
        pokeList.add(new Pokemon(Names.Manectric_Mega,310,ELECTRIC,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.NONE,AbilityEnum.NONE,false,70,75,80,135,80,135));
        pokeList.add(new Pokemon(Names.Plusle,311,ELECTRIC,NONE,AbilityEnum.PLUS,AbilityEnum.LIGHTNINGROD,AbilityEnum.NONE,true,60,50,40,85,75,95));
        pokeList.add(new Pokemon(Names.Minun,312,ELECTRIC,NONE,AbilityEnum.MINUS,AbilityEnum.VOLTABSORB,AbilityEnum.NONE,true,60,40,50,75,85,95));
        pokeList.add(new Pokemon(Names.Volbeat,313,BUG,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.SWARM,AbilityEnum.PRANKSTER,true,65,73,55,47,75,85));
        pokeList.add(new Pokemon(Names.Illumise,314,BUG,NONE,AbilityEnum.OBLIVIOUS,AbilityEnum.TINTEDLENS,AbilityEnum.PRANKSTER,true,65,47,55,73,75,85));
        pokeList.add(new Pokemon(Names.Roselia,315,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.LEAFGUARD,true,50,60,45,100,80,65));
        pokeList.add(new Pokemon(Names.Gulpin,316,POISON,NONE,AbilityEnum.LIQUIDOOZE,AbilityEnum.STICKYHOLD,AbilityEnum.GLUTTONY,true,70,43,53,43,53,40));
        pokeList.add(new Pokemon(Names.Swalot,317,POISON,NONE,AbilityEnum.LIQUIDOOZE,AbilityEnum.STICKYHOLD,AbilityEnum.GLUTTONY,true,100,73,83,73,83,55));
        pokeList.add(new Pokemon(Names.Carvanha,318,WATER,DARK,AbilityEnum.ROUGHSKIN,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,true,45,90,20,65,20,65));
        pokeList.add(new Pokemon(Names.Sharpedo,319,WATER,DARK,AbilityEnum.ROUGHSKIN,AbilityEnum.SPEEDBOOST,AbilityEnum.NONE,true,70,120,40,95,40,95));
        pokeList.add(new Pokemon(Names.Sharpedo_Mega,319,WATER,DARK,AbilityEnum.STRONGJAW,AbilityEnum.NONE,AbilityEnum.NONE,false,70,140,70,110,65,105));
        pokeList.add(new Pokemon(Names.Wailmer,320,WATER,NONE,AbilityEnum.WATERVEIL,AbilityEnum.OBLIVIOUS,AbilityEnum.PRESSURE,true,130,70,35,70,35,60));
        pokeList.add(new Pokemon(Names.Wailord,321,WATER,NONE,AbilityEnum.WATERVEIL,AbilityEnum.OBLIVIOUS,AbilityEnum.PRESSURE,true,170,90,45,90,45,60));
        pokeList.add(new Pokemon(Names.Numel,322,FIRE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SIMPLE,AbilityEnum.OWNTEMPO,true,60,60,40,65,45,35));
        pokeList.add(new Pokemon(Names.Camerupt,323,FIRE,GROUND,AbilityEnum.MAGMAARMOR,AbilityEnum.SOLIDROCK,AbilityEnum.ANGERPOINT,true,70,100,70,105,75,40));
        pokeList.add(new Pokemon(Names.Camerupt_Mega,323,FIRE,GROUND,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,AbilityEnum.NONE,false,70,120,100,145,105,20));
        pokeList.add(new Pokemon(Names.Torkoal,324,FIRE,NONE,AbilityEnum.WHITESMOKE,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,70,85,140,85,70,20));
        pokeList.add(new Pokemon(Names.Spoink,325,PSYCHIC,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.GLUTTONY,true,60,25,35,70,80,60));
        pokeList.add(new Pokemon(Names.Grumpig,326,PSYCHIC,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.GLUTTONY,true,80,45,65,90,110,80));
        pokeList.add(new Pokemon(Names.Spinda,327,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.TANGLEDFEET,AbilityEnum.CONTRARY,true,60,60,60,60,60,60));
        pokeList.add(new Pokemon(Names.Trapinch,328,GROUND,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.ARENATRAP,AbilityEnum.SHEERFORCE,true,45,100,45,45,45,10));
        pokeList.add(new Pokemon(Names.Vibrava,329,GROUND,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,70,50,50,50,70));
        pokeList.add(new Pokemon(Names.Flygon,330,GROUND,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,80,100,80,80,80,100));
        pokeList.add(new Pokemon(Names.Cacnea,331,GRASS,NONE,AbilityEnum.SANDVEIL,AbilityEnum.WATERABSORB,AbilityEnum.NONE,true,50,85,40,85,40,35));
        pokeList.add(new Pokemon(Names.Cacturne,332,GRASS,DARK,AbilityEnum.SANDVEIL,AbilityEnum.WATERABSORB,AbilityEnum.NONE,true,70,115,60,115,60,55));
        pokeList.add(new Pokemon(Names.Swablu,333,NORMAL,FLYING,AbilityEnum.NATURALCURE,AbilityEnum.CLOUDNINE,AbilityEnum.NONE,true,45,40,60,40,75,50));
        pokeList.add(new Pokemon(Names.Altaria,334,DRAGON,FLYING,AbilityEnum.NATURALCURE,AbilityEnum.CLOUDNINE,AbilityEnum.NONE,true,75,70,90,70,105,80));
        pokeList.add(new Pokemon(Names.Altaria_Mega,334,DRAGON,FAIRY,AbilityEnum.PIXILATE,AbilityEnum.NONE,AbilityEnum.NONE,false,75,110,110,110,105,80));
        pokeList.add(new Pokemon(Names.Zangoose,335,NORMAL,NONE,AbilityEnum.IMMUNITY,AbilityEnum.TOXICBOOST,AbilityEnum.NONE,true,73,115,60,60,60,90));
        pokeList.add(new Pokemon(Names.Seviper,336,POISON,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,73,100,60,100,60,65));
        pokeList.add(new Pokemon(Names.Lunatone,337,ROCK,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,70,55,65,95,85,70));
        pokeList.add(new Pokemon(Names.Solrock,338,ROCK,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,70,95,85,55,65,70));
        pokeList.add(new Pokemon(Names.Barboach,339,WATER,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.ANTICIPATION,AbilityEnum.HYDRATION,true,50,48,43,46,41,60));
        pokeList.add(new Pokemon(Names.Whiscash,340,WATER,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.ANTICIPATION,AbilityEnum.HYDRATION,true,110,78,73,76,71,60));
        pokeList.add(new Pokemon(Names.Corphish,341,WATER,NONE,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.ADAPTABILITY,true,43,80,65,50,35,35));
        pokeList.add(new Pokemon(Names.Crawdaunt,342,WATER,DARK,AbilityEnum.HYPERCUTTER,AbilityEnum.SHELLARMOR,AbilityEnum.ADAPTABILITY,true,63,120,85,90,55,55));
        pokeList.add(new Pokemon(Names.Baltoy,343,GROUND,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,40,40,55,40,70,55));
        pokeList.add(new Pokemon(Names.Claydol,344,GROUND,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,60,70,105,70,120,75));
        pokeList.add(new Pokemon(Names.Lileep,345,ROCK,GRASS,AbilityEnum.SUCTIONCUPS,AbilityEnum.STORMDRAIN,AbilityEnum.NONE,true,66,41,77,61,87,23));
        pokeList.add(new Pokemon(Names.Cradily,346,ROCK,GRASS,AbilityEnum.SUCTIONCUPS,AbilityEnum.STORMDRAIN,AbilityEnum.NONE,true,86,81,97,81,107,43));
        pokeList.add(new Pokemon(Names.Anorith,347,ROCK,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,true,45,95,50,40,50,75));
        pokeList.add(new Pokemon(Names.Armaldo,348,ROCK,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,true,75,125,100,70,80,45));
        pokeList.add(new Pokemon(Names.Feebas,349,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.ADAPTABILITY,AbilityEnum.NONE,true,20,15,20,10,55,80));
        pokeList.add(new Pokemon(Names.Milotic,350,WATER,NONE,AbilityEnum.MARVELSCALE,AbilityEnum.COMPETITIVE,AbilityEnum.CUTECHARM,true,95,60,79,100,125,81));
        pokeList.add(new Pokemon(Names.Castform,351,NORMAL,NONE,AbilityEnum.FORECAST,AbilityEnum.NONE,AbilityEnum.NONE,true,70,70,70,70,70,70));
        pokeList.add(new Pokemon(Names.Kecleon,352,NORMAL,NONE,AbilityEnum.COLORCHANGE,AbilityEnum.PROTEAN,AbilityEnum.NONE,true,60,90,70,60,120,40));
        pokeList.add(new Pokemon(Names.Shuppet,353,GHOST,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FRISK,AbilityEnum.CURSEDBODY,true,44,75,35,63,33,45));
        pokeList.add(new Pokemon(Names.Banette,354,GHOST,NONE,AbilityEnum.INSOMNIA,AbilityEnum.FRISK,AbilityEnum.CURSEDBODY,true,64,115,65,83,63,65));
        pokeList.add(new Pokemon(Names.Banette_Mega,354,GHOST,NONE,AbilityEnum.PRANKSTER,AbilityEnum.NONE,AbilityEnum.NONE,false,64,165,75,93,83,75));
        pokeList.add(new Pokemon(Names.Duskull,355,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.FRISK,AbilityEnum.NONE,true,20,40,90,30,90,25));
        pokeList.add(new Pokemon(Names.Dusclops,356,GHOST,NONE,AbilityEnum.PRESSURE,AbilityEnum.FRISK,AbilityEnum.NONE,true,40,70,130,60,130,25));
        pokeList.add(new Pokemon(Names.Tropius,357,GRASS,FLYING,AbilityEnum.CHLOROPHYLL,AbilityEnum.SOLARPOWER,AbilityEnum.HARVEST,true,99,68,83,72,87,51));
        pokeList.add(new Pokemon(Names.Chimecho,358,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,65,50,70,95,80,65));
        pokeList.add(new Pokemon(Names.Absol,359,DARK,NONE,AbilityEnum.PRESSURE,AbilityEnum.SUPERLUCK,AbilityEnum.JUSTIFIED,true,65,130,60,75,60,75));
        pokeList.add(new Pokemon(Names.Absol_Mega,359,DARK,NONE,AbilityEnum.MAGICBOUNCE,AbilityEnum.NONE,AbilityEnum.NONE,false,65,150,60,115,60,115));
        pokeList.add(new Pokemon(Names.Wynaut,360,PSYCHIC,NONE,AbilityEnum.SHADOWTAG,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,95,23,48,23,48,23));
        pokeList.add(new Pokemon(Names.Snorunt,361,ICE,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.ICEBODY,AbilityEnum.MOODY,true,50,50,50,50,50,50));
        pokeList.add(new Pokemon(Names.Glalie,362,ICE,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.ICEBODY,AbilityEnum.MOODY,true,80,80,80,80,80,80));
        pokeList.add(new Pokemon(Names.Glalie_Mega,362,ICE,NONE,AbilityEnum.REFRIGERATE,AbilityEnum.NONE,AbilityEnum.NONE,false,80,120,80,120,80,100));
        pokeList.add(new Pokemon(Names.Spheal,363,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,true,70,40,50,55,50,25));
        pokeList.add(new Pokemon(Names.Sealeo,364,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,true,90,60,70,75,70,45));
        pokeList.add(new Pokemon(Names.Walrein,365,ICE,WATER,AbilityEnum.THICKFAT,AbilityEnum.ICEBODY,AbilityEnum.OBLIVIOUS,true,110,80,90,95,90,65));
        pokeList.add(new Pokemon(Names.Clamperl,366,WATER,NONE,AbilityEnum.SHELLARMOR,AbilityEnum.RATTLED,AbilityEnum.NONE,true,35,64,85,74,55,32));
        pokeList.add(new Pokemon(Names.Huntail,367,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,true,55,104,105,94,75,52));
        pokeList.add(new Pokemon(Names.Gorebyss,368,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.NONE,true,55,84,105,114,75,52));
        pokeList.add(new Pokemon(Names.Relicanth,369,WATER,ROCK,AbilityEnum.SWIFTSWIM,AbilityEnum.ROCKHEAD,AbilityEnum.STURDY,true,100,90,130,45,65,55));
        pokeList.add(new Pokemon(Names.Luvdisc,370,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.NONE,true,43,30,55,40,65,97));
        pokeList.add(new Pokemon(Names.Bagon,371,DRAGON,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,45,75,60,40,30,50));
        pokeList.add(new Pokemon(Names.Shelgon,372,DRAGON,NONE,AbilityEnum.ROCKHEAD,AbilityEnum.OVERCOAT,AbilityEnum.NONE,true,65,95,100,60,50,50));
        pokeList.add(new Pokemon(Names.Salamence,373,DRAGON,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.NONE,true,95,135,80,110,80,100));
        pokeList.add(new Pokemon(Names.Salamence_Mega,373,DRAGON,FLYING,AbilityEnum.AERILATE,AbilityEnum.NONE,AbilityEnum.NONE,false,95,145,130,120,90,120));
        pokeList.add(new Pokemon(Names.Beldum,374,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,true,40,55,80,35,60,30));
        pokeList.add(new Pokemon(Names.Metang,375,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,true,60,75,100,55,80,50));
        pokeList.add(new Pokemon(Names.Metagross,376,STEEL,PSYCHIC,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,true,80,135,130,95,90,70));
        pokeList.add(new Pokemon(Names.Metagross_Mega,376,STEEL,PSYCHIC,AbilityEnum.TOUGHCLAWS,AbilityEnum.NONE,AbilityEnum.NONE,false,80,145,150,105,110,110));
        pokeList.add(new Pokemon(Names.Regirock,377,ROCK,NONE,AbilityEnum.CLEARBODY,AbilityEnum.STURDY,AbilityEnum.NONE,true,80,100,200,50,100,50));
        pokeList.add(new Pokemon(Names.Regice,378,ICE,NONE,AbilityEnum.CLEARBODY,AbilityEnum.ICEBODY,AbilityEnum.NONE,true,80,50,100,100,200,50));
        pokeList.add(new Pokemon(Names.Registeel,379,STEEL,NONE,AbilityEnum.CLEARBODY,AbilityEnum.LIGHTMETAL,AbilityEnum.NONE,true,80,75,150,75,150,50));
        pokeList.add(new Pokemon(Names.Latias,380,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,80,80,90,110,130,110));
        pokeList.add(new Pokemon(Names.Latias_Mega,380,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,false,80,100,120,140,150,110));
        pokeList.add(new Pokemon(Names.Latios,381,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,80,90,80,130,110,110));
        pokeList.add(new Pokemon(Names.Latios_Mega,381,DRAGON,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,false,80,130,100,160,120,110));
        pokeList.add(new Pokemon(Names.Kyogre,382,WATER,NONE,AbilityEnum.DRIZZLE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,90,150,140,90));
        pokeList.add(new Pokemon(Names.Kyogre_Primal,382,WATER,NONE,AbilityEnum.PRIMORDIALSEA,AbilityEnum.NONE,AbilityEnum.NONE,false,100,150,90,180,160,90));
        pokeList.add(new Pokemon(Names.Groudon,383,GROUND,NONE,AbilityEnum.DROUGHT,AbilityEnum.NONE,AbilityEnum.NONE,true,100,150,140,100,90,90));
        pokeList.add(new Pokemon(Names.Groudon_Primal,383,GROUND,FIRE,AbilityEnum.DESOLATELAND,AbilityEnum.NONE,AbilityEnum.NONE,false,100,180,160,150,90,90));
        pokeList.add(new Pokemon(Names.Rayquaza,384,DRAGON,FLYING,AbilityEnum.AIRLOCK,AbilityEnum.NONE,AbilityEnum.NONE,true,105,150,90,150,90,95));
        pokeList.add(new Pokemon(Names.Rayquaza_Mega,384,DRAGON,FLYING,AbilityEnum.DELTASTREAM,AbilityEnum.NONE,AbilityEnum.NONE,true,105,180,100,180,100,115));
        pokeList.add(new Pokemon(Names.Jirachi,385,STEEL,PSYCHIC,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Deoxys_Normal,386,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,150,50,150,50,150));
        pokeList.add(new Pokemon(Names.Deoxys_Attack,386,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,180,20,180,20,150));
        pokeList.add(new Pokemon(Names.Deoxys_Defense,386,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,70,160,70,160,90));
        pokeList.add(new Pokemon(Names.Deoxys_Speed,386,PSYCHIC,NONE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,95,90,95,90,180));
        pokeList.add(new Pokemon(Names.Turtwig,387,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,55,68,64,45,55,31));
        pokeList.add(new Pokemon(Names.Grotle,388,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,75,89,85,55,65,36));
        pokeList.add(new Pokemon(Names.Torterra,389,GRASS,GROUND,AbilityEnum.OVERGROW,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,95,109,105,75,85,56));
        pokeList.add(new Pokemon(Names.Chimchar,390,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,true,44,58,44,58,44,61));
        pokeList.add(new Pokemon(Names.Monferno,391,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,true,64,78,52,78,52,81));
        pokeList.add(new Pokemon(Names.Infernape,392,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.IRONFIST,AbilityEnum.NONE,true,76,104,71,104,71,108));
        pokeList.add(new Pokemon(Names.Piplup,393,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,true,53,51,53,61,56,40));
        pokeList.add(new Pokemon(Names.Prinplup,394,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,true,64,66,68,81,76,50));
        pokeList.add(new Pokemon(Names.Empoleon,395,WATER,STEEL,AbilityEnum.TORRENT,AbilityEnum.DEFIANT,AbilityEnum.NONE,true,84,86,88,111,101,60));
        pokeList.add(new Pokemon(Names.Starly,396,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.RECKLESS,AbilityEnum.NONE,true,40,55,30,30,30,60));
        pokeList.add(new Pokemon(Names.Staravia,397,NORMAL,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.RECKLESS,AbilityEnum.NONE,true,55,75,50,40,40,80));
        pokeList.add(new Pokemon(Names.Staraptor,398,NORMAL,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.RECKLESS,AbilityEnum.NONE,true,85,120,70,50,60,100));
        pokeList.add(new Pokemon(Names.Bidoof,399,NORMAL,NONE,AbilityEnum.SIMPLE,AbilityEnum.UNAWARE,AbilityEnum.MOODY,true,59,45,40,35,40,31));
        pokeList.add(new Pokemon(Names.Bibarel,400,NORMAL,WATER,AbilityEnum.SIMPLE,AbilityEnum.UNAWARE,AbilityEnum.MOODY,true,79,85,60,55,60,71));
        pokeList.add(new Pokemon(Names.Kricketot,401,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.RUNAWAY,AbilityEnum.NONE,true,37,25,41,25,41,25));
        pokeList.add(new Pokemon(Names.Kricketune,402,BUG,NONE,AbilityEnum.SWARM,AbilityEnum.TECHNICIAN,AbilityEnum.NONE,true,77,85,51,55,51,65));
        pokeList.add(new Pokemon(Names.Shinx,403,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,true,45,65,34,40,34,45));
        pokeList.add(new Pokemon(Names.Luxio,404,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,true,60,85,49,60,49,60));
        pokeList.add(new Pokemon(Names.Luxray,405,ELECTRIC,NONE,AbilityEnum.RIVALRY,AbilityEnum.INTIMIDATE,AbilityEnum.GUTS,true,80,120,79,95,79,70));
        pokeList.add(new Pokemon(Names.Budew,406,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.LEAFGUARD,true,40,30,35,50,70,55));
        pokeList.add(new Pokemon(Names.Roserade,407,GRASS,POISON,AbilityEnum.NATURALCURE,AbilityEnum.POISONPOINT,AbilityEnum.TECHNICIAN,true,60,70,65,125,105,90));
        pokeList.add(new Pokemon(Names.Cranidos,408,ROCK,NONE,AbilityEnum.MOLDBREAKER,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,67,125,40,30,30,58));
        pokeList.add(new Pokemon(Names.Rampardos,409,ROCK,NONE,AbilityEnum.MOLDBREAKER,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,97,165,60,65,50,58));
        pokeList.add(new Pokemon(Names.Shieldon,410,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,true,30,42,118,42,88,30));
        pokeList.add(new Pokemon(Names.Bastiodon,411,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,true,60,52,168,47,138,30));
        pokeList.add(new Pokemon(Names.Burmy,412,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.OVERCOAT,AbilityEnum.NONE,true,40,29,45,29,45,36));
        pokeList.add(new Pokemon(Names.Wormadam,413,BUG,GRASS,AbilityEnum.ANTICIPATION,AbilityEnum.OVERCOAT,AbilityEnum.NONE,true,60,59,85,79,105,36));
        pokeList.add(new Pokemon(Names.Mothim,414,BUG,FLYING,AbilityEnum.SWARM,AbilityEnum.TINTEDLENS,AbilityEnum.NONE,true,70,94,50,94,50,66));
        pokeList.add(new Pokemon(Names.Combee,415,BUG,FLYING,AbilityEnum.HONEYGATHER,AbilityEnum.HUSTLE,AbilityEnum.NONE,true,30,30,42,30,42,70));
        pokeList.add(new Pokemon(Names.Vespiquen,416,BUG,FLYING,AbilityEnum.PRESSURE,AbilityEnum.UNNERVE,AbilityEnum.NONE,true,70,80,102,80,102,40));
        pokeList.add(new Pokemon(Names.Pachirisu,417,ELECTRIC,NONE,AbilityEnum.RUNAWAY,AbilityEnum.PICKUP,AbilityEnum.VOLTABSORB,true,60,45,70,45,90,95));
        pokeList.add(new Pokemon(Names.Buizel,418,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,true,55,65,35,60,30,85));
        pokeList.add(new Pokemon(Names.Floatzel,419,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERVEIL,AbilityEnum.NONE,true,85,105,55,85,50,115));
        pokeList.add(new Pokemon(Names.Cherubi,420,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.NONE,AbilityEnum.NONE,true,45,35,45,62,53,35));
        pokeList.add(new Pokemon(Names.Cherrim,421,GRASS,NONE,AbilityEnum.FLOWERGIFT,AbilityEnum.NONE,AbilityEnum.NONE,true,70,60,70,87,78,85));
        pokeList.add(new Pokemon(Names.Shellos,422,WATER,NONE,AbilityEnum.STICKYHOLD,AbilityEnum.STORMDRAIN,AbilityEnum.SANDFORCE,true,76,48,48,57,62,34));
        pokeList.add(new Pokemon(Names.Gastrodon,423,WATER,GROUND,AbilityEnum.STICKYHOLD,AbilityEnum.STORMDRAIN,AbilityEnum.SANDFORCE,true,111,83,68,92,82,39));
        pokeList.add(new Pokemon(Names.Ambipom,424,NORMAL,NONE,AbilityEnum.TECHNICIAN,AbilityEnum.PICKUP,AbilityEnum.SKILLLINK,true,75,100,66,60,66,115));
        pokeList.add(new Pokemon(Names.Drifloon,425,GHOST,FLYING,AbilityEnum.AFTERMATH,AbilityEnum.UNBURDEN,AbilityEnum.FLAREBOOST,true,90,50,34,60,44,70));
        pokeList.add(new Pokemon(Names.Drifblim,426,GHOST,FLYING,AbilityEnum.AFTERMATH,AbilityEnum.UNBURDEN,AbilityEnum.FLAREBOOST,true,150,80,44,90,54,80));
        pokeList.add(new Pokemon(Names.Buneary,427,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KLUTZ,AbilityEnum.LIMBER,true,55,66,44,44,56,85));
        pokeList.add(new Pokemon(Names.Lopunny,428,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.KLUTZ,AbilityEnum.LIMBER,true,65,76,84,54,96,105));
        pokeList.add(new Pokemon(Names.Lopunny_Mega,428,NORMAL,FIGHTING,AbilityEnum.SCRAPPY,AbilityEnum.NONE,AbilityEnum.NONE,false,65,136,94,54,96,135));
        pokeList.add(new Pokemon(Names.Mismagius,429,GHOST,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,60,60,60,105,105,105));
        pokeList.add(new Pokemon(Names.Honchkrow,430,DARK,FLYING,AbilityEnum.INSOMNIA,AbilityEnum.SUPERLUCK,AbilityEnum.MOXIE,true,100,125,52,105,52,71));
        pokeList.add(new Pokemon(Names.Glameow,431,NORMAL,NONE,AbilityEnum.LIMBER,AbilityEnum.OWNTEMPO,AbilityEnum.KEENEYE,true,49,55,42,42,37,85));
        pokeList.add(new Pokemon(Names.Purugly,432,NORMAL,NONE,AbilityEnum.THICKFAT,AbilityEnum.OWNTEMPO,AbilityEnum.DEFIANT,true,71,82,64,64,59,112));
        pokeList.add(new Pokemon(Names.Chingling,433,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,45,30,50,65,50,45));
        pokeList.add(new Pokemon(Names.Stunky,434,POISON,DARK,AbilityEnum.STENCH,AbilityEnum.AFTERMATH,AbilityEnum.KEENEYE,true,63,63,47,41,41,74));
        pokeList.add(new Pokemon(Names.Skuntank,435,POISON,DARK,AbilityEnum.STENCH,AbilityEnum.AFTERMATH,AbilityEnum.KEENEYE,true,103,93,67,71,61,84));
        pokeList.add(new Pokemon(Names.Bronzor,436,STEEL,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.HEATPROOF,AbilityEnum.HEAVYMETAL,true,57,24,86,24,86,23));
        pokeList.add(new Pokemon(Names.Bronzong,437,STEEL,PSYCHIC,AbilityEnum.LEVITATE,AbilityEnum.HEATPROOF,AbilityEnum.HEAVYMETAL,true,67,89,116,79,116,33));
        pokeList.add(new Pokemon(Names.Bonsly,438,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.ROCKHEAD,AbilityEnum.RATTLED,true,50,80,95,10,45,10));
        pokeList.add(new Pokemon(Names.Mime_Jr,439,PSYCHIC,FAIRY,AbilityEnum.SOUNDPROOF,AbilityEnum.FILTER,AbilityEnum.TECHNICIAN,true,20,25,45,70,90,60));
        pokeList.add(new Pokemon(Names.Happiny,440,NORMAL,NONE,AbilityEnum.NATURALCURE,AbilityEnum.SERENEGRACE,AbilityEnum.FRIENDGUARD,true,100,5,5,15,65,30));
        pokeList.add(new Pokemon(Names.Chatot,441,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.TANGLEDFEET,AbilityEnum.BIGPECKS,true,76,65,45,92,42,91));
        pokeList.add(new Pokemon(Names.Spiritomb,442,GHOST,DARK,AbilityEnum.PRESSURE,AbilityEnum.INFILTRATOR,AbilityEnum.NONE,true,50,92,108,92,108,35));
        pokeList.add(new Pokemon(Names.Gible,443,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,true,58,70,45,40,45,42));
        pokeList.add(new Pokemon(Names.Gabite,444,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,true,68,90,65,50,55,82));
        pokeList.add(new Pokemon(Names.Garchomp,445,DRAGON,GROUND,AbilityEnum.SANDVEIL,AbilityEnum.ROUGHSKIN,AbilityEnum.NONE,true,108,130,95,80,85,102));
        pokeList.add(new Pokemon(Names.Garchomp_Mega,445,DRAGON,GROUND,AbilityEnum.SANDFORCE,AbilityEnum.NONE,AbilityEnum.NONE,false,108,170,115,120,95,92));
        pokeList.add(new Pokemon(Names.Munchlax,446,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.THICKFAT,AbilityEnum.GLUTTONY,true,135,85,40,40,85,5));
        pokeList.add(new Pokemon(Names.Riolu,447,FIGHTING,NONE,AbilityEnum.STEADFAST,AbilityEnum.INNERFOCUS,AbilityEnum.PRANKSTER,true,40,70,40,35,40,60));
        pokeList.add(new Pokemon(Names.Lucario,448,FIGHTING,STEEL,AbilityEnum.STEADFAST,AbilityEnum.INNERFOCUS,AbilityEnum.JUSTIFIED,true,70,110,70,115,70,90));
        pokeList.add(new Pokemon(Names.Lucario_Mega,448,FIGHTING,STEEL,AbilityEnum.ADAPTABILITY,AbilityEnum.NONE,AbilityEnum.NONE,false,70,145,88,140,70,112));
        pokeList.add(new Pokemon(Names.Hippopotas,449,GROUND,NONE,AbilityEnum.SANDSTREAM,AbilityEnum.SANDFORCE,AbilityEnum.NONE,true,68,72,78,38,42,32));
        pokeList.add(new Pokemon(Names.Hippowdon,450,GROUND,NONE,AbilityEnum.SANDSTREAM,AbilityEnum.SANDFORCE,AbilityEnum.NONE,true,108,112,118,68,72,47));
        pokeList.add(new Pokemon(Names.Skorupi,451,POISON,BUG,AbilityEnum.BATTLEARMOR,AbilityEnum.SNIPER,AbilityEnum.KEENEYE,true,40,50,90,30,55,65));
        pokeList.add(new Pokemon(Names.Drapion,452,POISON,DARK,AbilityEnum.BATTLEARMOR,AbilityEnum.SNIPER,AbilityEnum.KEENEYE,true,70,90,110,60,75,95));
        pokeList.add(new Pokemon(Names.Croagunk,453,POISON,FIGHTING,AbilityEnum.ANTICIPATION,AbilityEnum.DRYSKIN,AbilityEnum.POISONTOUCH,true,48,61,40,61,40,50));
        pokeList.add(new Pokemon(Names.Toxicroak,454,POISON,FIGHTING,AbilityEnum.ANTICIPATION,AbilityEnum.DRYSKIN,AbilityEnum.POISONTOUCH,true,83,106,65,86,65,85));
        pokeList.add(new Pokemon(Names.Carnivine,455,GRASS,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,74,100,72,90,72,46));
        pokeList.add(new Pokemon(Names.Finneon,456,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.STORMDRAIN,AbilityEnum.WATERVEIL,true,49,49,56,49,61,66));
        pokeList.add(new Pokemon(Names.Lumineon,457,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.STORMDRAIN,AbilityEnum.WATERVEIL,true,69,69,76,69,86,91));
        pokeList.add(new Pokemon(Names.Mantyke,458,WATER,FLYING,AbilityEnum.SWIFTSWIM,AbilityEnum.WATERABSORB,AbilityEnum.WATERVEIL,true,45,20,50,60,120,50));
        pokeList.add(new Pokemon(Names.Snover,459,GRASS,ICE,AbilityEnum.SNOWWARNING,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,true,60,62,50,62,60,40));
        pokeList.add(new Pokemon(Names.Abomasnow,460,GRASS,ICE,AbilityEnum.SNOWWARNING,AbilityEnum.SOUNDPROOF,AbilityEnum.NONE,true,90,92,75,92,85,60));
        pokeList.add(new Pokemon(Names.Abomasnow_Mega,460,GRASS,ICE,AbilityEnum.SNOWWARNING,AbilityEnum.NONE,AbilityEnum.NONE,false,90,132,105,132,105,30));
        pokeList.add(new Pokemon(Names.Weavile,461,DARK,ICE,AbilityEnum.PRESSURE,AbilityEnum.PICKPOCKET,AbilityEnum.NONE,true,70,120,65,45,85,125));
        pokeList.add(new Pokemon(Names.Magnezone,462,ELECTRIC,STEEL,AbilityEnum.MAGNETPULL,AbilityEnum.STURDY,AbilityEnum.ANALYTIC,true,70,70,115,130,90,60));
        pokeList.add(new Pokemon(Names.Lickilicky,463,NORMAL,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.OBLIVIOUS,AbilityEnum.CLOUDNINE,true,110,85,95,80,95,50));
        pokeList.add(new Pokemon(Names.Rhyperior,464,GROUND,ROCK,AbilityEnum.LIGHTNINGROD,AbilityEnum.SOLIDROCK,AbilityEnum.RECKLESS,true,115,140,130,55,55,40));
        pokeList.add(new Pokemon(Names.Tangrowth,465,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.LEAFGUARD,AbilityEnum.REGENERATOR,true,100,100,125,110,50,50));
        pokeList.add(new Pokemon(Names.Electivire,466,ELECTRIC,NONE,AbilityEnum.MOTORDRIVE,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,75,123,67,95,85,95));
        pokeList.add(new Pokemon(Names.Magmortar,467,FIRE,NONE,AbilityEnum.FLAMEBODY,AbilityEnum.VITALSPIRIT,AbilityEnum.NONE,true,75,95,67,125,95,83));
        pokeList.add(new Pokemon(Names.Togekiss,468,FAIRY,FLYING,AbilityEnum.HUSTLE,AbilityEnum.SERENEGRACE,AbilityEnum.SUPERLUCK,true,85,50,95,120,115,80));
        pokeList.add(new Pokemon(Names.Yanmega,469,BUG,FLYING,AbilityEnum.SPEEDBOOST,AbilityEnum.TINTEDLENS,AbilityEnum.FRISK,true,86,76,86,116,56,95));
        pokeList.add(new Pokemon(Names.Leafeon,470,GRASS,NONE,AbilityEnum.LEAFGUARD,AbilityEnum.LEAFGUARD,AbilityEnum.CHLOROPHYLL,true,65,110,130,60,65,95));
        pokeList.add(new Pokemon(Names.Glaceon,471,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.SNOWCLOAK,AbilityEnum.ICEBODY,true,65,60,110,130,95,65));
        pokeList.add(new Pokemon(Names.Gliscor,472,GROUND,FLYING,AbilityEnum.HYPERCUTTER,AbilityEnum.SANDVEIL,AbilityEnum.POISONHEAL,true,75,95,125,45,75,95));
        pokeList.add(new Pokemon(Names.Mamoswine,473,ICE,GROUND,AbilityEnum.OBLIVIOUS,AbilityEnum.SNOWCLOAK,AbilityEnum.THICKFAT,true,110,130,80,70,60,80));
        pokeList.add(new Pokemon(Names.Porygon_Z,474,NORMAL,NONE,AbilityEnum.ADAPTABILITY,AbilityEnum.DOWNLOAD,AbilityEnum.ANALYTIC,true,85,80,70,135,75,90));
        pokeList.add(new Pokemon(Names.Gallade,475,PSYCHIC,FIGHTING,AbilityEnum.STEADFAST,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,true,68,125,65,65,115,80));
        pokeList.add(new Pokemon(Names.Gallade_Mega,475,PSYCHIC,FIGHTING,AbilityEnum.INNERFOCUS,AbilityEnum.NONE,AbilityEnum.NONE,false,68,165,95,65,115,100));
        pokeList.add(new Pokemon(Names.Probopass,476,ROCK,STEEL,AbilityEnum.STURDY,AbilityEnum.MAGNETPULL,AbilityEnum.SANDFORCE,true,60,55,145,75,150,40));
        pokeList.add(new Pokemon(Names.Dusknoir,477,GHOST,NONE,AbilityEnum.PRESSURE,AbilityEnum.FRISK,AbilityEnum.NONE,true,45,100,135,65,135,45));
        pokeList.add(new Pokemon(Names.Froslass,478,ICE,GHOST,AbilityEnum.SNOWCLOAK,AbilityEnum.CURSEDBODY,AbilityEnum.NONE,true,70,80,70,80,70,110));
        pokeList.add(new Pokemon(Names.Rotom,479,ELECTRIC,GHOST,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,50,77,95,77,91));
        pokeList.add(new Pokemon(Names.Rotom_Heat,479,ELECTRIC,FIRE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,65,107,105,107,86));
        pokeList.add(new Pokemon(Names.Rotom_Wash,479,ELECTRIC,WATER,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,65,107,105,107,86));
        pokeList.add(new Pokemon(Names.Rotom_Frost,479,ELECTRIC,ICE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,65,107,105,107,86));
        pokeList.add(new Pokemon(Names.Rotom_Fan,479,ELECTRIC,FLYING,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,65,107,105,107,86));
        pokeList.add(new Pokemon(Names.Rotom_Mow,479,ELECTRIC,GRASS,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,50,65,107,105,107,86));
        pokeList.add(new Pokemon(Names.Uxie,480,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,75,75,130,75,130,95));
        pokeList.add(new Pokemon(Names.Mesprit,481,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,80,105,105,105,105,80));
        pokeList.add(new Pokemon(Names.Azelf,482,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,75,125,70,125,70,115));
        pokeList.add(new Pokemon(Names.Dialga,483,STEEL,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,100,120,120,150,100,90));
        pokeList.add(new Pokemon(Names.Palkia,484,WATER,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,90,120,100,150,120,100));
        pokeList.add(new Pokemon(Names.Heatran,485,FIRE,STEEL,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.NONE,true,91,90,106,130,106,77));
        pokeList.add(new Pokemon(Names.Regigigas,486,NORMAL,NONE,AbilityEnum.SLOWSTART,AbilityEnum.NONE,AbilityEnum.NONE,true,110,160,110,80,110,100));
        pokeList.add(new Pokemon(Names.Giratina,487,GHOST,DRAGON,AbilityEnum.PRESSURE,AbilityEnum.TELEPATHY,AbilityEnum.NONE,true,150,100,120,100,120,90));
        pokeList.add(new Pokemon(Names.Giratina_Origin,487,GHOST,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,150,120,100,120,100,90));
        pokeList.add(new Pokemon(Names.Cresselia,488,PSYCHIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,120,70,120,75,130,85));
        pokeList.add(new Pokemon(Names.Phione,489,WATER,NONE,AbilityEnum.HYDRATION,AbilityEnum.NONE,AbilityEnum.NONE,true,80,80,80,80,80,80));
        pokeList.add(new Pokemon(Names.Manaphy,490,WATER,NONE,AbilityEnum.HYDRATION,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Darkrai,491,DARK,NONE,AbilityEnum.BADDREAMS,AbilityEnum.NONE,AbilityEnum.NONE,true,70,90,90,135,90,125));
        pokeList.add(new Pokemon(Names.Shaymin,492,GRASS,NONE,AbilityEnum.NATURALCURE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Shaymin_Sky,492,GRASS,FLYING,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,103,75,120,75,127));
        pokeList.add(new Pokemon(Names.Arceus,493,NORMAL,NONE,AbilityEnum.MULTITYPE,AbilityEnum.NONE,AbilityEnum.NONE,true,120,120,120,120,120,120));
        pokeList.add(new Pokemon(Names.Victini,494,PSYCHIC,FIRE,AbilityEnum.VICTORYSTAR,AbilityEnum.NONE,AbilityEnum.NONE,true,100,100,100,100,100,100));
        pokeList.add(new Pokemon(Names.Snivy,495,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,true,45,45,55,45,55,63));
        pokeList.add(new Pokemon(Names.Servine,496,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,true,60,60,75,60,75,83));
        pokeList.add(new Pokemon(Names.Serperior,497,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.CONTRARY,AbilityEnum.NONE,true,75,75,95,75,95,113));
        pokeList.add(new Pokemon(Names.Tepig,498,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.THICKFAT,AbilityEnum.NONE,true,65,63,45,45,45,45));
        pokeList.add(new Pokemon(Names.Pignite,499,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.THICKFAT,AbilityEnum.NONE,true,90,93,55,70,55,55));
        pokeList.add(new Pokemon(Names.Emboar,500,FIRE,FIGHTING,AbilityEnum.BLAZE,AbilityEnum.RECKLESS,AbilityEnum.NONE,true,110,123,65,100,65,65));
        pokeList.add(new Pokemon(Names.Oshawott,501,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,55,55,45,63,45,45));
        pokeList.add(new Pokemon(Names.Dewott,502,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,75,75,60,83,60,60));
        pokeList.add(new Pokemon(Names.Samurott,503,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.SHELLARMOR,AbilityEnum.NONE,true,95,100,85,108,70,70));
        pokeList.add(new Pokemon(Names.Patrat,504,NORMAL,NONE,AbilityEnum.RUNAWAY,AbilityEnum.KEENEYE,AbilityEnum.ANALYTIC,true,45,55,39,35,39,42));
        pokeList.add(new Pokemon(Names.Watchog,505,NORMAL,NONE,AbilityEnum.ILLUMINATE,AbilityEnum.KEENEYE,AbilityEnum.ANALYTIC,true,60,85,69,60,69,77));
        pokeList.add(new Pokemon(Names.Lillipup,506,NORMAL,NONE,AbilityEnum.VITALSPIRIT,AbilityEnum.PICKUP,AbilityEnum.RUNAWAY,true,45,60,45,25,45,55));
        pokeList.add(new Pokemon(Names.Herdier,507,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SANDRUSH,AbilityEnum.SCRAPPY,true,65,80,65,35,65,60));
        pokeList.add(new Pokemon(Names.Stoutland,508,NORMAL,NONE,AbilityEnum.INTIMIDATE,AbilityEnum.SANDRUSH,AbilityEnum.SCRAPPY,true,85,110,90,45,90,80));
        pokeList.add(new Pokemon(Names.Purrloin,509,DARK,NONE,AbilityEnum.LIMBER,AbilityEnum.UNBURDEN,AbilityEnum.PRANKSTER,true,41,50,37,50,37,66));
        pokeList.add(new Pokemon(Names.Liepard,510,DARK,NONE,AbilityEnum.LIMBER,AbilityEnum.UNBURDEN,AbilityEnum.PRANKSTER,true,64,88,50,88,50,106));
        pokeList.add(new Pokemon(Names.Pansage,511,GRASS,NONE,AbilityEnum.GLUTTONY,AbilityEnum.OVERGROW,AbilityEnum.NONE,true,50,53,48,53,48,64));
        pokeList.add(new Pokemon(Names.Simisage,512,GRASS,NONE,AbilityEnum.GLUTTONY,AbilityEnum.OVERGROW,AbilityEnum.NONE,true,75,98,63,98,63,101));
        pokeList.add(new Pokemon(Names.Pansear,513,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.BLAZE,AbilityEnum.NONE,true,50,53,48,53,48,64));
        pokeList.add(new Pokemon(Names.Simisear,514,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.BLAZE,AbilityEnum.NONE,true,75,98,63,98,63,101));
        pokeList.add(new Pokemon(Names.Panpour,515,WATER,NONE,AbilityEnum.GLUTTONY,AbilityEnum.TORRENT,AbilityEnum.NONE,true,50,53,48,53,48,64));
        pokeList.add(new Pokemon(Names.Simipour,516,WATER,NONE,AbilityEnum.GLUTTONY,AbilityEnum.TORRENT,AbilityEnum.NONE,true,75,98,63,98,63,101));
        pokeList.add(new Pokemon(Names.Munna,517,PSYCHIC,NONE,AbilityEnum.FOREWARN,AbilityEnum.SYNCHRONIZE,AbilityEnum.TELEPATHY,true,76,25,45,67,55,24));
        pokeList.add(new Pokemon(Names.Musharna,518,PSYCHIC,NONE,AbilityEnum.FOREWARN,AbilityEnum.SYNCHRONIZE,AbilityEnum.TELEPATHY,true,116,55,85,107,95,29));
        pokeList.add(new Pokemon(Names.Pidove,519,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,true,50,55,50,36,30,43));
        pokeList.add(new Pokemon(Names.Tranquill,520,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,true,62,77,62,50,42,65));
        pokeList.add(new Pokemon(Names.Unfezant,521,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.SUPERLUCK,AbilityEnum.RIVALRY,true,80,115,80,65,55,93));
        pokeList.add(new Pokemon(Names.Blitzle,522,ELECTRIC,NONE,AbilityEnum.LIGHTNINGROD,AbilityEnum.MOTORDRIVE,AbilityEnum.SAPSIPPER,true,45,60,32,50,32,76));
        pokeList.add(new Pokemon(Names.Zebstrika,523,ELECTRIC,NONE,AbilityEnum.LIGHTNINGROD,AbilityEnum.MOTORDRIVE,AbilityEnum.SAPSIPPER,true,75,100,63,80,63,116));
        pokeList.add(new Pokemon(Names.Roggenrola,524,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,true,55,75,85,25,25,15));
        pokeList.add(new Pokemon(Names.Boldore,525,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,true,70,105,105,50,40,20));
        pokeList.add(new Pokemon(Names.Gigalith,526,ROCK,NONE,AbilityEnum.STURDY,AbilityEnum.SANDFORCE,AbilityEnum.NONE,true,85,135,130,60,80,25));
        pokeList.add(new Pokemon(Names.Woobat,527,PSYCHIC,FLYING,AbilityEnum.UNAWARE,AbilityEnum.KLUTZ,AbilityEnum.SIMPLE,true,55,45,43,55,43,72));
        pokeList.add(new Pokemon(Names.Swoobat,528,PSYCHIC,FLYING,AbilityEnum.UNAWARE,AbilityEnum.KLUTZ,AbilityEnum.SIMPLE,true,67,57,55,77,55,114));
        pokeList.add(new Pokemon(Names.Drilbur,529,GROUND,NONE,AbilityEnum.SANDRUSH,AbilityEnum.SANDFORCE,AbilityEnum.MOLDBREAKER,true,60,85,40,30,45,68));
        pokeList.add(new Pokemon(Names.Excadrill,530,GROUND,STEEL,AbilityEnum.SANDRUSH,AbilityEnum.SANDFORCE,AbilityEnum.MOLDBREAKER,true,110,135,60,50,65,88));
        pokeList.add(new Pokemon(Names.Audino,531,NORMAL,NONE,AbilityEnum.HEALER,AbilityEnum.REGENERATOR,AbilityEnum.KLUTZ,true,103,60,86,60,86,50));
        pokeList.add(new Pokemon(Names.Audino,531,NORMAL,FAIRY,AbilityEnum.HEALER,AbilityEnum.NONE,AbilityEnum.NONE,false,103,60,126,80,126,50));
        pokeList.add(new Pokemon(Names.Timburr,532,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,true,75,80,55,25,35,35));
        pokeList.add(new Pokemon(Names.Gurdurr,533,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,true,85,105,85,40,50,40));
        pokeList.add(new Pokemon(Names.Conkeldurr,534,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.SHEERFORCE,AbilityEnum.IRONFIST,true,105,140,95,55,65,45));
        pokeList.add(new Pokemon(Names.Tympole,535,WATER,NONE,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.WATERABSORB,true,50,50,40,50,40,64));
        pokeList.add(new Pokemon(Names.Palpitoad,536,WATER,GROUND,AbilityEnum.SWIFTSWIM,AbilityEnum.HYDRATION,AbilityEnum.WATERABSORB,true,75,65,55,65,55,69));
        pokeList.add(new Pokemon(Names.Seismitoad,537,WATER,GROUND,AbilityEnum.SWIFTSWIM,AbilityEnum.POISONTOUCH,AbilityEnum.WATERABSORB,true,105,95,75,85,75,74));
        pokeList.add(new Pokemon(Names.Throh,538,FIGHTING,NONE,AbilityEnum.GUTS,AbilityEnum.INNERFOCUS,AbilityEnum.MOLDBREAKER,true,120,100,85,30,85,45));
        pokeList.add(new Pokemon(Names.Sawk,539,FIGHTING,NONE,AbilityEnum.STURDY,AbilityEnum.INNERFOCUS,AbilityEnum.MOLDBREAKER,true,75,125,75,30,75,85));
        pokeList.add(new Pokemon(Names.Sewaddle,540,BUG,GRASS,AbilityEnum.SWARM,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,true,45,53,70,40,60,42));
        pokeList.add(new Pokemon(Names.Swadloon,541,BUG,GRASS,AbilityEnum.LEAFGUARD,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,true,55,63,90,50,80,42));
        pokeList.add(new Pokemon(Names.Leavanny,542,BUG,GRASS,AbilityEnum.SWARM,AbilityEnum.CHLOROPHYLL,AbilityEnum.OVERCOAT,true,75,103,80,70,80,92));
        pokeList.add(new Pokemon(Names.Venipede,543,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,true,30,45,59,30,39,57));
        pokeList.add(new Pokemon(Names.Whirlipede,544,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,true,40,55,99,40,79,47));
        pokeList.add(new Pokemon(Names.Scolipede,545,BUG,POISON,AbilityEnum.POISONPOINT,AbilityEnum.SWARM,AbilityEnum.SPEEDBOOST,true,60,100,89,55,69,112));
        pokeList.add(new Pokemon(Names.Cottonee,546,GRASS,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.INFILTRATOR,AbilityEnum.CHLOROPHYLL,true,40,27,60,37,50,66));
        pokeList.add(new Pokemon(Names.Whimsicott,547,GRASS,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.INFILTRATOR,AbilityEnum.CHLOROPHYLL,true,60,67,85,77,75,116));
        pokeList.add(new Pokemon(Names.Petilil,548,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.OWNTEMPO,AbilityEnum.LEAFGUARD,true,45,35,50,70,50,30));
        pokeList.add(new Pokemon(Names.Lilligant,549,GRASS,NONE,AbilityEnum.CHLOROPHYLL,AbilityEnum.OWNTEMPO,AbilityEnum.LEAFGUARD,true,70,60,75,110,75,90));
        pokeList.add(new Pokemon(Names.Basculin,550,WATER,NONE,AbilityEnum.RECKLESS,AbilityEnum.ADAPTABILITY,AbilityEnum.MOLDBREAKER,true,70,92,65,80,55,98));
        pokeList.add(new Pokemon(Names.Sandile,551,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,true,50,72,35,35,35,65));
        pokeList.add(new Pokemon(Names.Krokorok,552,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,true,60,82,45,45,45,74));
        pokeList.add(new Pokemon(Names.Krookodile,553,GROUND,DARK,AbilityEnum.INTIMIDATE,AbilityEnum.MOXIE,AbilityEnum.ANGERPOINT,true,95,117,80,65,70,92));
        pokeList.add(new Pokemon(Names.Darumaka,554,FIRE,NONE,AbilityEnum.HUSTLE,AbilityEnum.INNERFOCUS,AbilityEnum.NONE,true,70,90,45,15,45,50));
        pokeList.add(new Pokemon(Names.Darmanitan,555,FIRE,NONE,AbilityEnum.SHEERFORCE,AbilityEnum.ZENMODE,AbilityEnum.NONE,true,105,140,55,30,55,95));
        pokeList.add(new Pokemon(Names.Maractus,556,GRASS,NONE,AbilityEnum.WATERABSORB,AbilityEnum.CHLOROPHYLL,AbilityEnum.STORMDRAIN,true,75,86,67,106,67,60));
        pokeList.add(new Pokemon(Names.Dwebble,557,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,true,50,65,85,35,35,55));
        pokeList.add(new Pokemon(Names.Crustle,558,BUG,ROCK,AbilityEnum.STURDY,AbilityEnum.SHELLARMOR,AbilityEnum.WEAKARMOR,true,70,95,125,65,75,45));
        pokeList.add(new Pokemon(Names.Scraggy,559,DARK,FIGHTING,AbilityEnum.SHEDSKIN,AbilityEnum.MOXIE,AbilityEnum.INTIMIDATE,true,50,75,70,35,70,48));
        pokeList.add(new Pokemon(Names.Scrafty,560,DARK,FIGHTING,AbilityEnum.SHEDSKIN,AbilityEnum.MOXIE,AbilityEnum.INTIMIDATE,true,65,90,115,45,115,58));
        pokeList.add(new Pokemon(Names.Sigilyph,561,PSYCHIC,FLYING,AbilityEnum.WONDERSKIN,AbilityEnum.MAGICGUARD,AbilityEnum.TINTEDLENS,true,72,58,80,103,80,97));
        pokeList.add(new Pokemon(Names.Yamask,562,GHOST,NONE,AbilityEnum.MUMMY,AbilityEnum.NONE,AbilityEnum.NONE,true,38,30,85,55,65,30));
        pokeList.add(new Pokemon(Names.Cofagrigus,563,GHOST,NONE,AbilityEnum.MUMMY,AbilityEnum.NONE,AbilityEnum.NONE,true,58,50,145,95,105,30));
        pokeList.add(new Pokemon(Names.Tirtouga,564,WATER,ROCK,AbilityEnum.SOLIDROCK,AbilityEnum.STURDY,AbilityEnum.SWIFTSWIM,true,54,78,103,53,45,22));
        pokeList.add(new Pokemon(Names.Carracosta,565,WATER,ROCK,AbilityEnum.SOLIDROCK,AbilityEnum.STURDY,AbilityEnum.SWIFTSWIM,true,74,108,133,83,65,32));
        pokeList.add(new Pokemon(Names.Archen,566,ROCK,FLYING,AbilityEnum.DEFEATIST,AbilityEnum.NONE,AbilityEnum.NONE,true,55,112,45,74,45,70));
        pokeList.add(new Pokemon(Names.Archeops,567,ROCK,FLYING,AbilityEnum.DEFEATIST,AbilityEnum.NONE,AbilityEnum.NONE,true,75,140,65,112,65,110));
        pokeList.add(new Pokemon(Names.Trubbish,568,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.STICKYHOLD,AbilityEnum.AFTERMATH,true,50,50,62,40,62,65));
        pokeList.add(new Pokemon(Names.Garbodor,569,POISON,NONE,AbilityEnum.STENCH,AbilityEnum.WEAKARMOR,AbilityEnum.AFTERMATH,true,80,95,82,60,82,75));
        pokeList.add(new Pokemon(Names.Zorua,570,DARK,NONE,AbilityEnum.ILLUSION,AbilityEnum.NONE,AbilityEnum.NONE,true,40,65,40,80,40,65));
        pokeList.add(new Pokemon(Names.Zoroark,571,DARK,NONE,AbilityEnum.ILLUSION,AbilityEnum.NONE,AbilityEnum.NONE,true,60,105,60,120,60,105));
        pokeList.add(new Pokemon(Names.Minccino,572,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.TECHNICIAN,AbilityEnum.SKILLLINK,true,55,50,40,40,40,75));
        pokeList.add(new Pokemon(Names.Cinccino,573,NORMAL,NONE,AbilityEnum.CUTECHARM,AbilityEnum.TECHNICIAN,AbilityEnum.SKILLLINK,true,75,95,60,65,60,115));
        pokeList.add(new Pokemon(Names.Gothita,574,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,true,45,30,50,55,65,45));
        pokeList.add(new Pokemon(Names.Gothorita,575,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,true,60,45,70,75,85,55));
        pokeList.add(new Pokemon(Names.Gothitelle,576,PSYCHIC,NONE,AbilityEnum.FRISK,AbilityEnum.COMPETITIVE,AbilityEnum.SHADOWTAG,true,70,55,95,95,110,65));
        pokeList.add(new Pokemon(Names.Solosis,577,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,true,45,30,40,105,50,20));
        pokeList.add(new Pokemon(Names.Duosion,578,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,true,65,40,50,125,60,30));
        pokeList.add(new Pokemon(Names.Reuniclus,579,PSYCHIC,NONE,AbilityEnum.OVERCOAT,AbilityEnum.MAGICGUARD,AbilityEnum.REGENERATOR,true,110,65,75,125,85,30));
        pokeList.add(new Pokemon(Names.Ducklett,580,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.BIGPECKS,AbilityEnum.HYDRATION,true,62,44,50,44,50,55));
        pokeList.add(new Pokemon(Names.Swanna,581,WATER,FLYING,AbilityEnum.KEENEYE,AbilityEnum.BIGPECKS,AbilityEnum.HYDRATION,true,75,87,63,87,63,98));
        pokeList.add(new Pokemon(Names.Vanillite,582,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,true,36,50,50,65,60,44));
        pokeList.add(new Pokemon(Names.Vanillish,583,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,true,51,65,65,80,75,59));
        pokeList.add(new Pokemon(Names.Vanilluxe,584,ICE,NONE,AbilityEnum.ICEBODY,AbilityEnum.WEAKARMOR,AbilityEnum.NONE,true,71,95,85,110,95,79));
        pokeList.add(new Pokemon(Names.Deerling,585,NORMAL,GRASS,AbilityEnum.CHLOROPHYLL,AbilityEnum.SAPSIPPER,AbilityEnum.SERENEGRACE,true,60,60,50,40,50,75));
        pokeList.add(new Pokemon(Names.Sawsbuck,586,NORMAL,GRASS,AbilityEnum.CHLOROPHYLL,AbilityEnum.SAPSIPPER,AbilityEnum.SERENEGRACE,true,80,100,70,60,70,95));
        pokeList.add(new Pokemon(Names.Emolga,587,ELECTRIC,FLYING,AbilityEnum.STATIC,AbilityEnum.MOTORDRIVE,AbilityEnum.NONE,true,55,75,60,75,60,103));
        pokeList.add(new Pokemon(Names.Karrablast,588,BUG,NONE,AbilityEnum.SWARM,AbilityEnum.SHEDSKIN,AbilityEnum.NOGUARD,true,50,75,45,40,45,60));
        pokeList.add(new Pokemon(Names.Escavalier,589,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.SHELLARMOR,AbilityEnum.OVERCOAT,true,70,135,105,60,105,20));
        pokeList.add(new Pokemon(Names.Foongus,590,GRASS,POISON,AbilityEnum.EFFECTSPORE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,true,69,55,45,55,55,15));
        pokeList.add(new Pokemon(Names.Amoonguss,591,GRASS,POISON,AbilityEnum.EFFECTSPORE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,true,114,85,70,85,80,30));
        pokeList.add(new Pokemon(Names.Frillish,592,WATER,GHOST,AbilityEnum.WATERABSORB,AbilityEnum.CURSEDBODY,AbilityEnum.DAMP,true,55,40,50,65,85,40));
        pokeList.add(new Pokemon(Names.Jellicent,593,WATER,GHOST,AbilityEnum.WATERABSORB,AbilityEnum.CURSEDBODY,AbilityEnum.DAMP,true,100,60,70,85,105,60));
        pokeList.add(new Pokemon(Names.Alomomola,594,WATER,NONE,AbilityEnum.HEALER,AbilityEnum.HYDRATION,AbilityEnum.REGENERATOR,true,165,75,80,40,45,65));
        pokeList.add(new Pokemon(Names.Joltik,595,BUG,ELECTRIC,AbilityEnum.COMPOUNDEYES,AbilityEnum.UNNERVE,AbilityEnum.SWARM,true,50,47,50,57,50,65));
        pokeList.add(new Pokemon(Names.Galvantula,596,BUG,ELECTRIC,AbilityEnum.COMPOUNDEYES,AbilityEnum.UNNERVE,AbilityEnum.SWARM,true,70,77,60,97,60,108));
        pokeList.add(new Pokemon(Names.Ferroseed,597,GRASS,STEEL,AbilityEnum.IRONBARBS,AbilityEnum.NONE,AbilityEnum.NONE,true,44,50,91,24,86,10));
        pokeList.add(new Pokemon(Names.Ferrothorn,598,GRASS,STEEL,AbilityEnum.IRONBARBS,AbilityEnum.ANTICIPATION,AbilityEnum.NONE,true,74,94,131,54,116,20));
        pokeList.add(new Pokemon(Names.Klink,599,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,true,40,55,70,45,60,30));
        pokeList.add(new Pokemon(Names.Klang,600,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,true,60,80,95,70,85,50));
        pokeList.add(new Pokemon(Names.Klinklang,601,STEEL,NONE,AbilityEnum.PLUS,AbilityEnum.MINUS,AbilityEnum.CLEARBODY,true,60,100,115,70,85,90));
        pokeList.add(new Pokemon(Names.Tynamo,602,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,35,55,40,45,40,60));
        pokeList.add(new Pokemon(Names.Eelektrik,603,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,65,85,70,75,70,40));
        pokeList.add(new Pokemon(Names.Eelektross,604,ELECTRIC,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,85,115,80,105,80,50));
        pokeList.add(new Pokemon(Names.Elgyem,605,PSYCHIC,NONE,AbilityEnum.TELEPATHY,AbilityEnum.SYNCHRONIZE,AbilityEnum.ANALYTIC,true,55,55,55,85,55,30));
        pokeList.add(new Pokemon(Names.Beheeyem,606,PSYCHIC,NONE,AbilityEnum.TELEPATHY,AbilityEnum.SYNCHRONIZE,AbilityEnum.ANALYTIC,true,75,75,75,125,95,40));
        pokeList.add(new Pokemon(Names.Litwick,607,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,true,50,30,55,65,55,20));
        pokeList.add(new Pokemon(Names.Lampent,608,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,true,60,40,60,95,60,55));
        pokeList.add(new Pokemon(Names.Chandelure,609,GHOST,FIRE,AbilityEnum.FLASHFIRE,AbilityEnum.FLAMEBODY,AbilityEnum.INFILTRATOR,true,60,55,90,145,90,80));
        pokeList.add(new Pokemon(Names.Axew,610,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,true,46,87,60,30,40,57));
        pokeList.add(new Pokemon(Names.Fraxure,611,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,true,66,117,70,40,50,67));
        pokeList.add(new Pokemon(Names.Haxorus,612,DRAGON,NONE,AbilityEnum.RIVALRY,AbilityEnum.MOLDBREAKER,AbilityEnum.UNNERVE,true,76,147,90,60,70,97));
        pokeList.add(new Pokemon(Names.Cubchoo,613,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.RATTLED,AbilityEnum.NONE,true,55,70,40,60,40,40));
        pokeList.add(new Pokemon(Names.Beartic,614,ICE,NONE,AbilityEnum.SNOWCLOAK,AbilityEnum.SWIFTSWIM,AbilityEnum.NONE,true,95,110,80,70,80,50));
        pokeList.add(new Pokemon(Names.Cryogonal,615,ICE,NONE,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,70,50,30,95,135,105));
        pokeList.add(new Pokemon(Names.Shelmet,616,BUG,NONE,AbilityEnum.HYDRATION,AbilityEnum.SHELLARMOR,AbilityEnum.OVERCOAT,true,50,40,85,40,65,25));
        pokeList.add(new Pokemon(Names.Accelgor,617,BUG,NONE,AbilityEnum.HYDRATION,AbilityEnum.STICKYHOLD,AbilityEnum.UNBURDEN,true,80,70,40,100,60,145));
        pokeList.add(new Pokemon(Names.Stunfisk,618,GROUND,ELECTRIC,AbilityEnum.STATIC,AbilityEnum.LIMBER,AbilityEnum.SANDVEIL,true,109,66,84,81,99,32));
        pokeList.add(new Pokemon(Names.Mienfoo,619,FIGHTING,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.REGENERATOR,AbilityEnum.RECKLESS,true,45,85,50,55,50,65));
        pokeList.add(new Pokemon(Names.Mienshao,620,FIGHTING,NONE,AbilityEnum.INNERFOCUS,AbilityEnum.REGENERATOR,AbilityEnum.RECKLESS,true,65,125,60,95,60,105));
        pokeList.add(new Pokemon(Names.Druddigon,621,DRAGON,NONE,AbilityEnum.ROUGHSKIN,AbilityEnum.SHEERFORCE,AbilityEnum.MOLDBREAKER,true,77,120,90,60,90,48));
        pokeList.add(new Pokemon(Names.Golett,622,GROUND,GHOST,AbilityEnum.IRONFIST,AbilityEnum.KLUTZ,AbilityEnum.NOGUARD,true,59,74,50,35,50,35));
        pokeList.add(new Pokemon(Names.Golurk,623,GROUND,GHOST,AbilityEnum.IRONFIST,AbilityEnum.KLUTZ,AbilityEnum.NOGUARD,true,89,124,80,55,80,55));
        pokeList.add(new Pokemon(Names.Pawniard,624,DARK,STEEL,AbilityEnum.DEFIANT,AbilityEnum.INNERFOCUS,AbilityEnum.PRESSURE,true,45,85,70,40,40,60));
        pokeList.add(new Pokemon(Names.Bisharp,625,DARK,STEEL,AbilityEnum.DEFIANT,AbilityEnum.INNERFOCUS,AbilityEnum.PRESSURE,true,65,125,100,60,70,70));
        pokeList.add(new Pokemon(Names.Bouffalant,626,NORMAL,NONE,AbilityEnum.RECKLESS,AbilityEnum.SAPSIPPER,AbilityEnum.SOUNDPROOF,true,95,110,95,40,95,55));
        pokeList.add(new Pokemon(Names.Rufflet,627,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SHEERFORCE,AbilityEnum.HUSTLE,true,70,83,50,37,50,60));
        pokeList.add(new Pokemon(Names.Braviary,628,NORMAL,FLYING,AbilityEnum.KEENEYE,AbilityEnum.SHEERFORCE,AbilityEnum.DEFIANT,true,100,123,75,57,75,80));
        pokeList.add(new Pokemon(Names.Vullaby,629,DARK,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.OVERCOAT,AbilityEnum.WEAKARMOR,true,70,55,75,45,65,60));
        pokeList.add(new Pokemon(Names.Mandibuzz,630,DARK,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.OVERCOAT,AbilityEnum.WEAKARMOR,true,110,65,105,55,95,80));
        pokeList.add(new Pokemon(Names.Heatmor,631,FIRE,NONE,AbilityEnum.GLUTTONY,AbilityEnum.FLASHFIRE,AbilityEnum.WHITESMOKE,true,85,97,66,105,66,65));
        pokeList.add(new Pokemon(Names.Durant,632,BUG,STEEL,AbilityEnum.SWARM,AbilityEnum.HUSTLE,AbilityEnum.TRUANT,true,58,109,112,48,48,109));
        pokeList.add(new Pokemon(Names.Deino,633,DARK,DRAGON,AbilityEnum.HUSTLE,AbilityEnum.NONE,AbilityEnum.NONE,true,52,65,50,45,50,38));
        pokeList.add(new Pokemon(Names.Zweilous,634,DARK,DRAGON,AbilityEnum.HUSTLE,AbilityEnum.NONE,AbilityEnum.NONE,true,72,85,70,65,70,58));
        pokeList.add(new Pokemon(Names.Hydreigon,635,DARK,DRAGON,AbilityEnum.LEVITATE,AbilityEnum.NONE,AbilityEnum.NONE,true,92,105,90,125,90,98));
        pokeList.add(new Pokemon(Names.Larvesta,636,BUG,FIRE,AbilityEnum.FLAMEBODY,AbilityEnum.SWARM,AbilityEnum.NONE,true,55,85,55,50,55,60));
        pokeList.add(new Pokemon(Names.Volcarona,637,BUG,FIRE,AbilityEnum.FLAMEBODY,AbilityEnum.SWARM,AbilityEnum.NONE,true,85,60,65,135,105,100));
        pokeList.add(new Pokemon(Names.Cobalion,638,STEEL,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,true,91,90,129,90,72,108));
        pokeList.add(new Pokemon(Names.Terrakion,639,ROCK,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,true,91,129,90,72,90,108));
        pokeList.add(new Pokemon(Names.Virizion,640,GRASS,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,true,91,90,72,90,129,108));
        pokeList.add(new Pokemon(Names.Tornadus,641,FLYING,NONE,AbilityEnum.PRANKSTER,AbilityEnum.DEFIANT,AbilityEnum.NONE,true,79,115,70,125,80,111));
        pokeList.add(new Pokemon(Names.Tornadus_Therian,641,FLYING,NONE,AbilityEnum.REGENERATOR,AbilityEnum.NONE,AbilityEnum.NONE,true,79,100,80,110,90,121));
        pokeList.add(new Pokemon(Names.Thundurus,642,ELECTRIC,FLYING,AbilityEnum.PRANKSTER,AbilityEnum.DEFIANT,AbilityEnum.NONE,true,79,115,70,125,80,111));
        pokeList.add(new Pokemon(Names.Thundurus_Therian,642,ELECTRIC,FLYING,AbilityEnum.VOLTABSORB,AbilityEnum.NONE,AbilityEnum.NONE,true,79,105,70,145,80,101));
        pokeList.add(new Pokemon(Names.Reshiram,643,DRAGON,FIRE,AbilityEnum.TURBOBLAZE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,120,100,150,120,90));
        pokeList.add(new Pokemon(Names.Zekrom,644,DRAGON,ELECTRIC,AbilityEnum.TERAVOLT,AbilityEnum.NONE,AbilityEnum.NONE,true,100,150,120,120,100,90));
        pokeList.add(new Pokemon(Names.Landorus,645,GROUND,FLYING,AbilityEnum.SANDFORCE,AbilityEnum.SHEERFORCE,AbilityEnum.NONE,true,89,125,90,115,80,101));
        pokeList.add(new Pokemon(Names.Landorus_Therian,645,GROUND,FLYING,AbilityEnum.INTIMIDATE,AbilityEnum.NONE,AbilityEnum.NONE,true,89,145,90,105,80,91));
        pokeList.add(new Pokemon(Names.Kyurem,646,DRAGON,ICE,AbilityEnum.PRESSURE,AbilityEnum.NONE,AbilityEnum.NONE,true,125,130,90,130,90,95));
        pokeList.add(new Pokemon(Names.Kyurem_Black,646,DRAGON,ICE,AbilityEnum.TERAVOLT,AbilityEnum.NONE,AbilityEnum.NONE,true,125,170,100,120,90,95));
        pokeList.add(new Pokemon(Names.Kyurem_White,646,DRAGON,ICE,AbilityEnum.TURBOBLAZE,AbilityEnum.NONE,AbilityEnum.NONE,true,125,120,90,170,100,95));
        pokeList.add(new Pokemon(Names.Keldeo,647,WATER,FIGHTING,AbilityEnum.JUSTIFIED,AbilityEnum.NONE,AbilityEnum.NONE,true,91,72,90,129,90,108));
        pokeList.add(new Pokemon(Names.Meloetta,648,NORMAL,PSYCHIC,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,77,77,128,128,90));
        pokeList.add(new Pokemon(Names.Meloetta_Pirouette,648,NORMAL,FIGHTING,AbilityEnum.SERENEGRACE,AbilityEnum.NONE,AbilityEnum.NONE,true,100,128,90,77,77,128));
        pokeList.add(new Pokemon(Names.Genesect,649,BUG,STEEL,AbilityEnum.DOWNLOAD,AbilityEnum.NONE,AbilityEnum.NONE,true,71,120,95,120,95,99));
        pokeList.add(new Pokemon(Names.Chespin,650,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,true,56,61,65,48,45,38));
        pokeList.add(new Pokemon(Names.Quilladin,651,GRASS,NONE,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,true,61,78,95,56,58,57));
        pokeList.add(new Pokemon(Names.Chesnaught,652,GRASS,FIGHTING,AbilityEnum.OVERGROW,AbilityEnum.BULLETPROOF,AbilityEnum.NONE,true,88,107,122,74,75,64));
        pokeList.add(new Pokemon(Names.Fennekin,653,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,true,40,45,40,62,60,60));
        pokeList.add(new Pokemon(Names.Braixen,654,FIRE,NONE,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,true,59,59,58,90,70,73));
        pokeList.add(new Pokemon(Names.Delphox,655,FIRE,PSYCHIC,AbilityEnum.BLAZE,AbilityEnum.MAGICIAN,AbilityEnum.NONE,true,75,69,72,114,100,104));
        pokeList.add(new Pokemon(Names.Froakie,656,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,true,41,56,40,62,44,71));
        pokeList.add(new Pokemon(Names.Frogadier,657,WATER,NONE,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,true,54,63,52,83,56,97));
        pokeList.add(new Pokemon(Names.Greninja,658,WATER,DARK,AbilityEnum.TORRENT,AbilityEnum.PROTEAN,AbilityEnum.NONE,true,72,95,67,103,71,122));
        pokeList.add(new Pokemon(Names.Bunnelby,659,NORMAL,NONE,AbilityEnum.PICKUP,AbilityEnum.CHEEKPOUCH,AbilityEnum.HUGEPOWER,true,38,36,38,32,36,57));
        pokeList.add(new Pokemon(Names.Diggersby,660,NORMAL,GROUND,AbilityEnum.PICKUP,AbilityEnum.CHEEKPOUCH,AbilityEnum.HUGEPOWER,true,85,56,77,50,77,78));
        pokeList.add(new Pokemon(Names.Fletchling,661,NORMAL,FLYING,AbilityEnum.BIGPECKS,AbilityEnum.GALEWINGS,AbilityEnum.NONE,true,45,50,43,40,38,62));
        pokeList.add(new Pokemon(Names.Fletchinder,662,FIRE,FLYING,AbilityEnum.FLAMEBODY,AbilityEnum.GALEWINGS,AbilityEnum.NONE,true,62,73,55,56,52,84));
        pokeList.add(new Pokemon(Names.Talonflame,663,FIRE,FLYING,AbilityEnum.FLAMEBODY,AbilityEnum.GALEWINGS,AbilityEnum.NONE,true,78,81,71,74,69,126));
        pokeList.add(new Pokemon(Names.Scatterbug,664,BUG,NONE,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRIENDGUARD,true,38,35,40,27,25,35));
        pokeList.add(new Pokemon(Names.Spewpa,665,BUG,NONE,AbilityEnum.SHEDSKIN,AbilityEnum.FRIENDGUARD,AbilityEnum.NONE,true,45,22,60,27,30,29));
        pokeList.add(new Pokemon(Names.Vivillon,666,BUG,FLYING,AbilityEnum.SHIELDDUST,AbilityEnum.COMPOUNDEYES,AbilityEnum.FRIENDGUARD,true,80,52,50,90,50,89));
        pokeList.add(new Pokemon(Names.Litleo,667,FIRE,NORMAL,AbilityEnum.RIVALRY,AbilityEnum.UNNERVE,AbilityEnum.MOXIE,true,62,50,58,73,54,72));
        pokeList.add(new Pokemon(Names.Pyroar,668,FIRE,NORMAL,AbilityEnum.RIVALRY,AbilityEnum.UNNERVE,AbilityEnum.MOXIE,true,86,68,72,109,66,106));
        pokeList.add(new Pokemon(Names.Flabebe,669,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,true,44,38,39,61,79,42));
        pokeList.add(new Pokemon(Names.Floette,670,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,true,54,45,47,75,98,52));
        pokeList.add(new Pokemon(Names.Florges,671,FAIRY,NONE,AbilityEnum.FLOWERVEIL,AbilityEnum.SYMBIOSIS,AbilityEnum.NONE,true,78,65,68,112,154,75));
        pokeList.add(new Pokemon(Names.Skiddo,672,GRASS,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.GRASSPELT,AbilityEnum.NONE,true,66,65,48,62,57,52));
        pokeList.add(new Pokemon(Names.Gogoat,673,GRASS,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.GRASSPELT,AbilityEnum.NONE,true,123,100,62,97,81,68));
        pokeList.add(new Pokemon(Names.Pancham,674,FIGHTING,NONE,AbilityEnum.IRONFIST,AbilityEnum.MOLDBREAKER,AbilityEnum.SCRAPPY,true,67,82,62,46,48,43));
        pokeList.add(new Pokemon(Names.Pangoro,675,FIGHTING,DARK,AbilityEnum.IRONFIST,AbilityEnum.MOLDBREAKER,AbilityEnum.SCRAPPY,true,95,124,78,69,71,58));
        pokeList.add(new Pokemon(Names.Furfrou,676,NORMAL,NONE,AbilityEnum.FURCOAT,AbilityEnum.NONE,AbilityEnum.NONE,true,75,80,60,65,90,102));
        pokeList.add(new Pokemon(Names.Espurr,677,PSYCHIC,NONE,AbilityEnum.KEENEYE,AbilityEnum.INFILTRATOR,AbilityEnum.OWNTEMPO,true,62,48,54,63,60,68));
        pokeList.add(new Pokemon(Names.Meowstic,678,PSYCHIC,NONE,AbilityEnum.KEENEYE,AbilityEnum.INFILTRATOR,AbilityEnum.PRANKSTER,true,74,48,76,83,81,104));
        pokeList.add(new Pokemon(Names.Honedge,679,STEEL,GHOST,AbilityEnum.NOGUARD,AbilityEnum.NONE,AbilityEnum.NONE,true,45,80,100,35,37,28));
        pokeList.add(new Pokemon(Names.Doublade,680,STEEL,GHOST,AbilityEnum.NOGUARD,AbilityEnum.NONE,AbilityEnum.NONE,true,59,110,150,45,49,35));
        pokeList.add(new Pokemon(Names.Aegislash,681,STEEL,GHOST,AbilityEnum.STANCECHANGE,AbilityEnum.NONE,AbilityEnum.NONE,true,60,50,150,50,150,60));
        pokeList.add(new Pokemon(Names.Spritzee,682,FAIRY,NONE,AbilityEnum.HEALER,AbilityEnum.AROMAVEIL,AbilityEnum.NONE,true,78,52,60,63,65,23));
        pokeList.add(new Pokemon(Names.Aromatisse,683,FAIRY,NONE,AbilityEnum.HEALER,AbilityEnum.AROMAVEIL,AbilityEnum.NONE,true,101,72,72,99,89,29));
        pokeList.add(new Pokemon(Names.Swirlix,684,FAIRY,NONE,AbilityEnum.SWEETVEIL,AbilityEnum.UNBURDEN,AbilityEnum.NONE,true,62,48,66,59,57,49));
        pokeList.add(new Pokemon(Names.Slurpuff,685,FAIRY,NONE,AbilityEnum.SWEETVEIL,AbilityEnum.UNBURDEN,AbilityEnum.NONE,true,82,80,86,85,75,72));
        pokeList.add(new Pokemon(Names.Inkay,686,DARK,PSYCHIC,AbilityEnum.CONTRARY,AbilityEnum.SUCTIONCUPS,AbilityEnum.INFILTRATOR,true,53,54,53,37,46,45));
        pokeList.add(new Pokemon(Names.Malamar,687,DARK,PSYCHIC,AbilityEnum.CONTRARY,AbilityEnum.SUCTIONCUPS,AbilityEnum.INFILTRATOR,true,86,92,88,68,75,73));
        pokeList.add(new Pokemon(Names.Binacle,688,ROCK,WATER,AbilityEnum.TOUGHCLAWS,AbilityEnum.SNIPER,AbilityEnum.PICKPOCKET,true,42,52,67,39,56,50));
        pokeList.add(new Pokemon(Names.Barbaracle,689,ROCK,WATER,AbilityEnum.TOUGHCLAWS,AbilityEnum.SNIPER,AbilityEnum.PICKPOCKET,true,72,105,115,86,68,54));
        pokeList.add(new Pokemon(Names.Skrelp,690,POISON,WATER,AbilityEnum.POISONPOINT,AbilityEnum.POISONTOUCH,AbilityEnum.ADAPTABILITY,true,50,60,60,60,60,30));
        pokeList.add(new Pokemon(Names.Dragalge,691,POISON,DRAGON,AbilityEnum.POISONPOINT,AbilityEnum.POISONTOUCH,AbilityEnum.ADAPTABILITY,true,65,75,90,97,123,44));
        pokeList.add(new Pokemon(Names.Clauncher,692,WATER,NONE,AbilityEnum.MEGALAUNCHER,AbilityEnum.NONE,AbilityEnum.NONE,true,50,53,62,58,63,44));
        pokeList.add(new Pokemon(Names.Clawitzer,693,WATER,NONE,AbilityEnum.MEGALAUNCHER,AbilityEnum.NONE,AbilityEnum.NONE,true,71,73,88,120,89,59));
        pokeList.add(new Pokemon(Names.Helioptile,694,ELECTRIC,NORMAL,AbilityEnum.DRYSKIN,AbilityEnum.SANDVEIL,AbilityEnum.SOLARPOWER,true,44,38,33,61,43,70));
        pokeList.add(new Pokemon(Names.Heliolisk,695,ELECTRIC,NORMAL,AbilityEnum.DRYSKIN,AbilityEnum.SANDVEIL,AbilityEnum.SOLARPOWER,true,62,55,52,109,94,109));
        pokeList.add(new Pokemon(Names.Tyrunt,696,ROCK,DRAGON,AbilityEnum.STRONGJAW,AbilityEnum.STURDY,AbilityEnum.NONE,true,58,89,77,45,45,48));
        pokeList.add(new Pokemon(Names.Tyrantrum,697,ROCK,DRAGON,AbilityEnum.STRONGJAW,AbilityEnum.ROCKHEAD,AbilityEnum.NONE,true,82,121,119,69,59,71));
        pokeList.add(new Pokemon(Names.Amaura,698,ROCK,ICE,AbilityEnum.REFRIGERATE,AbilityEnum.SNOWWARNING,AbilityEnum.NONE,true,77,59,50,67,63,46));
        pokeList.add(new Pokemon(Names.Aurorus,699,ROCK,ICE,AbilityEnum.REFRIGERATE,AbilityEnum.SNOWWARNING,AbilityEnum.NONE,true,123,77,72,99,92,58));
        pokeList.add(new Pokemon(Names.Sylveon,700,FAIRY,NONE,AbilityEnum.CUTECHARM,AbilityEnum.CUTECHARM,AbilityEnum.PIXILATE,true,95,65,65,110,130,60));
        pokeList.add(new Pokemon(Names.Hawlucha,701,FIGHTING,FLYING,AbilityEnum.UNBURDEN,AbilityEnum.LIMBER,AbilityEnum.MOLDBREAKER,true,78,92,75,74,63,118));
        pokeList.add(new Pokemon(Names.Dedenne,702,ELECTRIC,FAIRY,AbilityEnum.CHEEKPOUCH,AbilityEnum.PICKUP,AbilityEnum.PLUS,true,67,58,57,81,67,101));
        pokeList.add(new Pokemon(Names.Carbink,703,ROCK,FAIRY,AbilityEnum.CLEARBODY,AbilityEnum.STURDY,AbilityEnum.NONE,true,50,50,150,50,150,50));
        pokeList.add(new Pokemon(Names.Goomy,704,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,true,45,50,35,55,75,40));
        pokeList.add(new Pokemon(Names.Sliggoo,705,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,true,68,75,53,83,113,60));
        pokeList.add(new Pokemon(Names.Goodra,706,DRAGON,NONE,AbilityEnum.SAPSIPPER,AbilityEnum.HYDRATION,AbilityEnum.GOOEY,true,90,100,70,110,150,80));
        pokeList.add(new Pokemon(Names.Klefki,707,STEEL,FAIRY,AbilityEnum.PRANKSTER,AbilityEnum.MAGICIAN,AbilityEnum.NONE,true,57,80,91,80,87,75));
        pokeList.add(new Pokemon(Names.Phantump,708,GHOST,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.FRISK,AbilityEnum.HARVEST,true,43,70,48,50,60,38));
        pokeList.add(new Pokemon(Names.Trevenant,709,GHOST,GRASS,AbilityEnum.NATURALCURE,AbilityEnum.FRISK,AbilityEnum.HARVEST,true,85,110,76,65,82,56));
        pokeList.add(new Pokemon(Names.Pumpkaboo,710,GHOST,GRASS,AbilityEnum.PICKUP,AbilityEnum.FRISK,AbilityEnum.INSOMNIA,true,59,66,70,44,55,41));
        pokeList.add(new Pokemon(Names.Gourgeist,711,GHOST,GRASS,AbilityEnum.PICKUP,AbilityEnum.FRISK,AbilityEnum.INSOMNIA,true,85,100,122,58,75,54));
        pokeList.add(new Pokemon(Names.Bergmite,712,ICE,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.ICEBODY,AbilityEnum.STURDY,true,55,69,85,32,35,28));
        pokeList.add(new Pokemon(Names.Avalugg,713,ICE,NONE,AbilityEnum.OWNTEMPO,AbilityEnum.ICEBODY,AbilityEnum.STURDY,true,95,117,184,44,46,28));
        pokeList.add(new Pokemon(Names.Noibat,714,FLYING,DRAGON,AbilityEnum.FRISK,AbilityEnum.INFILTRATOR,AbilityEnum.TELEPATHY,true,40,30,35,45,40,55));
        pokeList.add(new Pokemon(Names.Noivern,715,FLYING,DRAGON,AbilityEnum.FRISK,AbilityEnum.INFILTRATOR,AbilityEnum.TELEPATHY,true,85,70,80,97,80,123));
        pokeList.add(new Pokemon(Names.Xerneas,716,FAIRY,NONE,AbilityEnum.FAIRYAURA,AbilityEnum.NONE,AbilityEnum.NONE,true,126,131,95,131,98,99));
        pokeList.add(new Pokemon(Names.Yveltal,717,DARK,FLYING,AbilityEnum.DARKAURA,AbilityEnum.NONE,AbilityEnum.NONE,true,126,131,95,131,98,99));
        pokeList.add(new Pokemon(Names.Zygarde,718,DRAGON,GROUND,AbilityEnum.AURABREAK,AbilityEnum.NONE,AbilityEnum.NONE,true,108,100,121,81,95,95));
        pokeList.add(new Pokemon(Names.Diancie,719,ROCK,FAIRY,AbilityEnum.CLEARBODY,AbilityEnum.NONE,AbilityEnum.NONE,true,50,100,150,100,150,50));
        pokeList.add(new Pokemon(Names.Diancie_Mega,719,ROCK,FAIRY,AbilityEnum.CLEARBODY,AbilityEnum.NONE,AbilityEnum.NONE,true,50,160,110,160,110,110));
        pokeList.add(new Pokemon(Names.Hoopa,720,PSYCHIC,GHOST,AbilityEnum.MAGICIAN,AbilityEnum.NONE,AbilityEnum.NONE,true,80,110,60,150,130,70));
        pokeList.add(new Pokemon(Names.Hoopa_Unbound,720,PSYCHIC,GHOST,AbilityEnum.MAGICIAN,AbilityEnum.NONE,AbilityEnum.NONE,true,80,160,60,170,130,80));
        pokeList.add(new Pokemon(Names.Volcanion,721,FIRE,WATER,AbilityEnum.WATERABSORB,AbilityEnum.NONE,AbilityEnum.NONE,true,80,110,120,130,90,70));
    }
}
