/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.wordlistsqlsearchable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

/**
 * Implements a RecyclerView that displays a list of words from a SQL database.
 * - Clicking the fab button opens a second activity to add a word to the database.
 * - Clicking the Edit button opens an activity to edit the current word in the database.
 * - Clicking the Delete button deletes the current word from the database.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int WORD_EDIT = 1;
    public static final int WORD_ADD = -1;

    private WordListOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    //
    private int Pamp, Pdur, Qamp, Qdur, Ramp, Rdur, Samp, Sdur, Tamp, Tdur, RRint, PRseg, STseg, RPdur, RPamp,STbase,Pperiod,princ,RR;
    private int lastX=0, miss=0;
    private int type=0;
    private String savenum;
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    public int pratio=3;
    String errorString="RR interval duration should be larger than sum of P,QRS,T, PR,ST duration !";

    //



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new WordListOpenHelper(this);

        // Create recycler view.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // Create an mAdapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, /* mDB.getAllEntries(),*/ mDB);
        // Connect the mAdapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add a floating action click handler for creating new entries.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts empty edit activity.
                Intent intent = new Intent(getBaseContext(), EditWordActivity.class);
                startActivityForResult(intent, WORD_EDIT);
            }
        });
        myplot(180,300,400,1500,250,150,75,25,50,25,100,0,0);

    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param   menu    Menu to inflate.
     * @return          Returns true if the menu inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item  Item clicked.
     * @return      Returns true if one of the defined items was clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Starts search activity.
                Intent intent = new Intent(getBaseContext(), com.android.example.wordlistsqlsearchable.SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WORD_EDIT) {
            if (resultCode == RESULT_OK) {

                String word = data.getStringExtra(EditWordActivity.EXTRA_REPLYWORD);
                String rate = data.getStringExtra(EditWordActivity.EXTRA_REPLYRATE);
                String pamp = data.getStringExtra(EditWordActivity.EXTRA_REPLYPAMP);
                String qamp = data.getStringExtra(EditWordActivity.EXTRA_REPLYQAMP);
                String ramp = data.getStringExtra(EditWordActivity.EXTRA_REPLYRAMP);
                String samp = data.getStringExtra(EditWordActivity.EXTRA_REPLYSAMP);
                String tamp = data.getStringExtra(EditWordActivity.EXTRA_REPLYTAMP);
                String pdur = data.getStringExtra(EditWordActivity.EXTRA_REPLYPDUR);
                String qdur = data.getStringExtra(EditWordActivity.EXTRA_REPLYQDUR);
                String rdur = data.getStringExtra(EditWordActivity.EXTRA_REPLYRDUR);
                String sdur = data.getStringExtra(EditWordActivity.EXTRA_REPLYSDUR);
                String tdur = data.getStringExtra(EditWordActivity.EXTRA_REPLYTDUR);
                String prseg = data.getStringExtra(EditWordActivity.EXTRA_REPLYPRSEG);
                String stseg = data.getStringExtra(EditWordActivity.EXTRA_REPLYSTSEG);


                myplot(Integer.valueOf(rate),Integer.valueOf(pamp),Integer.valueOf(qamp),Integer.valueOf(ramp),Integer.valueOf(samp),Integer.valueOf(tamp),Integer.valueOf(pdur),Integer.valueOf(qdur),Integer.valueOf(rdur),Integer.valueOf(sdur),Integer.valueOf(tdur),Integer.valueOf(prseg),Integer.valueOf(stseg));



                // Update the database.
                if (!TextUtils.isEmpty(word)) {
                    int id = data.getIntExtra(WordListAdapter.EXTRA_ID, -99);

                    if (id == WORD_ADD) {
                        mDB.insert(word,rate,pamp,qamp,ramp,samp,tamp,pdur,qdur,rdur,sdur,tdur,stseg,prseg);
                    } else if (id >= 0) {
                        mDB.update(id, word, rate,pamp, qamp,ramp,samp,tamp,pdur,qdur,rdur,sdur,tdur,stseg,prseg);
                    }
                    // Update the UI.
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_word_not_saved,
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     this is the plot function
     */
    void myplot( int vRate, int vPamp,int vQamp, int vRamp, int vSamp, int vTamp,int vPdur,int vQdur, int vRdur, int vSdur, int vTdur,int vPRseg, int vSTseg)
    {
        double y, x;
        x = 0;


        int RRint = 60000/vRate;
        int Pamp = Integer.valueOf(vPamp);
        int Pdur = Integer.valueOf(vPdur); //75
        int Qamp = -1*Integer.valueOf(vQamp);
        int Qdur = Integer.valueOf(vQdur); //25
        int Ramp = Integer.valueOf(vRamp);//1500
        int Rdur = Integer.valueOf(vRdur);//50
        int Samp = -1*Integer.valueOf(vSamp);//-250
        int Sdur = Integer.valueOf(vSdur);//25
        int Tamp = Integer.valueOf(vTamp);//150
        int Tdur = Integer.valueOf(vTdur);//100
        int PRseg = Integer.valueOf(vPRseg);
        int STseg = Integer.valueOf(vSTseg);

        int RPdur = Integer.valueOf(0);
        int RPamp = Integer.valueOf(0);
        int STbase= Integer.valueOf(0);
        int Pperiod=60000/vRate;




        //Pperiod=RRint;
        //nmPperiod.setText(nmRR.getText());

        double a, b, c, d, e, ep, f, g, h, i,yd, ye, yp,yy, yf, yeep, yep,period,pcount,stepsize,Pcnt;
        int blockcount=0;
        y=0;
        yy=0;
        yp=0;
        Pcnt=0;
        stepsize=1;
        yd = 0;
        ye = 0;
        yf = 0;
        yeep = 0;
        yep = 0;
        a = Pperiod-(Pdur+Qdur+Rdur+Sdur+Tdur+PRseg+STseg);
        pcount =0; //period count;
        b = a + Pdur;
        c = b + PRseg;
        d = c + Qdur/2;
        e = d + Qdur/2+Rdur/2;
        ep = e + RPdur;
        f = ep + +Rdur/2+Sdur/2;
        g = f +Sdur/2;
        h = g + STseg;
        i = h + Tdur;
        period=RRint;
        princ=Integer.valueOf(RRint/(pratio*3));
        if(Pdur==0)
        {Pamp=0;}
        if(Qdur==0)
        {Qamp=0; }
        if(Rdur==0)
        {Ramp=0;}
        if(Sdur==0)
        {Samp=0;}
        if(Tdur==0)
        {Tamp=0;}
        if(STbase > 0)
        {Samp=0;}



        GraphView graph = (GraphView) findViewById(R.id.graph);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
// activate horizontal scrolling
        graph.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
        //graph.getViewport().setScalableY(true);

// activate vertical scrolling
        //graph.getViewport().setScrollableY(true);


        graph.removeAllSeries();
        series = new LineGraphSeries<>();
        series.setThickness(6);
        //eries.setDataPointsRadius(0);
        //series.setDrawDataPoints(false);
        //Viewport viewport = graph.getViewport();
        //viewport.setScrollable(true);


        //stepsize=.5;
        for (int I = 0; I < 50000; I++) {
            x = x + stepsize;
            if(RRint-Qdur-Rdur-Sdur-RPdur <0)
            {Toast ToastMessage;

                ToastMessage=Toast.makeText(getApplicationContext(),errorString,Toast.LENGTH_LONG);
                ToastMessage.setGravity(0,0,0);
                ToastMessage.show();

                return;}
            //calculate total period points
            if (x > a+Pcnt*Pperiod && x <= b+Pcnt*Pperiod) {
                yp = Pamp * Math.sin(((x - Pcnt * Pperiod) - a) / (b - a) * 3.14);
            }
            else if (x>b+Pcnt*Pperiod){
                Pcnt=Pcnt+1;

            }
            else{
                yp=0;
            }

            if (RPamp <= 0 || RPdur <= 0) {
                if (x > 0+pcount*period && x <= a+pcount*period) {
                    y = 0;
                }
                else if (x > a+pcount*period && x <= b+pcount*period) {
                    //y = 0; //
                    y=Pamp * Math.sin(((x-pcount*period) - a) / (b - a) * 3.14);
                } else if (x > b+pcount*period && x <= c+pcount*period) {
                    y = 0;
                } else if (x > c+pcount*period && x <= d+pcount*period) {
                    y = Qamp * ((x-pcount*period) - c)/ (d-c) ;
                    yd = y;
                } else if (x > d+pcount*period && x <= e+pcount*period) {
                    y = (Ramp - Qamp) * ((x-pcount*period) - d)/ (e-d) + Qamp;

                } else if (x > e+pcount*period && x <= f+pcount*period) {
                    y = (Samp-Ramp) * ((x-pcount*period) - e)/(f-e) + Ramp+STbase*Math.sin(((x-pcount*period)-e)/(f-e)*3.14/2);

                } else if (x > f+pcount*period && x <= g+pcount*period) {
                    y = -Samp / (g - f) * ((x-pcount*period) - f) + Samp+STbase;
                } else if (x > g+pcount*period && x <= h+pcount*period) {
                    y = 0+STbase*Math.sin(((x-pcount*period)-g)/(i-g)*3.14/2+3.14/2);
                } else if (x > h+pcount*period && x <= i+pcount*period) {
                    y = Tamp * Math.sin(((x-pcount*period) - h) / (i - h) * 3.14)+STbase*Math.sin(((x-pcount*period)-g)/(i-g)*3.14/2+3.14/2);
                }
                else if (x > pcount*period+i ){
                    pcount=pcount+1;


                    //if(Pcnt%3==0&&type==1)
                    // 50 , or use Pperiod/(pratio+1)
//                    if((Pcnt+1)%(pratio+1)==0&&type==1)
//                    {
//                        Qamp=0;
//                        Ramp=0;
//                        Samp=0;
//                        Tamp=0;
//                        PRseg=PRseg-(pratio)*(princ);
//                    }
//                    else{
//                        if(type==1){
//                            PRseg = PRseg + (princ);}
//                        else{PRseg = Integer.valueOf(0);
//                        }
//                        c = b + PRseg;
//                        d = c + Qdur / 2;
//                        e = d + Qdur / 2 + Rdur / 2;
//                        ep = e + RPdur;
//                        f = ep + +Rdur / 2 + Sdur / 2;
//                        g = f + Sdur / 2;
//                        h = g + STseg;
//                        i = h + Tdur;
//                        period = RRint ;//+ blockcount * 5;
//
//                        int random = new Random().nextInt(3);
//
//                        if((Pcnt)%random==0&&type==2&&miss==0)
//                        {   miss=1;
//                            Qamp=0;
//                            Ramp=0;
//                            Samp=0;
//                            Tamp=0;
//                        }
//                        else{miss=0;}
//
//
//                    }




                }

            } else {
                if (x > 0+pcount*period && x <= a+pcount*period) {
                    y = 0;
                }
                else if (x > a+pcount*period && x <= b+pcount*period) {
                    y = Pamp * Math.sin(((x-pcount*period) - a) / (b - a) * 3.14);
                } else if (x > b+pcount*period && x <= c+pcount*period) {
                    y = 0;
                } else if (x > c+pcount*period && x <= d+pcount*period) {
                    y = Qamp  * ((x-pcount*period) - c)/ Qdur;
                    yd = y;
                } else if (x > d+pcount*period && x <= e+pcount*period) {
                    y = (Ramp - Qamp)  * ((x-pcount*period) - d)/ Rdur + yd;
                    ye = y;
                } else if (x > e+pcount*period && x <= (e + ep) * 0.5+pcount*period) {
                    y = (-RPamp + Samp)  * ((x-pcount*period) - e)/ Sdur + ye;
                    yeep = y;
                } else if (x > (e + ep) * 0.5+pcount*period && x <= ep+pcount*period) {
                    y = (RPamp - yeep)  * ((x-pcount*period) - (e + ep) * 0.5)/ (RPdur * 0.5) + yeep;
                    yep = y;
                } else if (x > ep+pcount*period && x <= f+pcount*period) {
                    y = (-RPamp + Samp) * ((x-pcount*period) - ep) / Sdur + yep;
                    yf = y;
                } else if (x > f+pcount*period && x <= g+pcount*period) {
                    y = -Samp / (g - f) * ((x-pcount*period) - f) + yf;
                } else if (x > g+pcount*period && x <= h+pcount*period) {
                    y = 0;
                } else if (x > h+pcount*period && x <= i+pcount*period) {
                    y = Tamp * Math.sin(((x-pcount*period) - h) / (i - h) * 3.14);
                }
                else if (x > pcount*period+i ){
                    pcount=pcount+1;
                }
            }
            series.setColor(Color.BLACK);
            //yy=yp+y;
            series.appendData(new DataPoint(x, y), true, 50000); // number of total point should be equal to for loop cmd

        }

        /*for(int i2 =0; i2<5; i2++) {
            x = x + 500;
            y = Math.sin(x)*500;
            series.appendData(new DataPoint(x, y), true, 100);
        }*/

        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(3000);
        graph.getViewport().setMinY(-2000);
        graph.getViewport().setMaxY(2000);
        graph.getGridLabelRenderer().setNumHorizontalLabels(17);
        graph.getGridLabelRenderer().setNumVerticalLabels(9);
        graph.getGridLabelRenderer().setGridColor(Color.RED);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);



        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        double y2,x2;
        x2 = -1.0;
        for(int i2 =0; i2<5; i2++) {
            x2 = x2 + 1;
            y2 = Math.sin(x2);
            series2.appendData(new DataPoint(x2, y2), true, 100);
        }
        graph.addSeries(series2);




    }

}