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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

/**
 * Activity for entering a new word or editing an existing word.
 */
public class EditWordActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private static final int NO_ID = -99;
    private static final String NO_WORD = "";

    private int Pamp, Pdur, Qamp, Qdur, Ramp, Rdur, Samp, Sdur, Tamp, Tdur, RRint, PRseg, STseg, RPdur, RPamp,STbase,Pperiod,princ,RR;
    private LineGraphSeries<DataPoint> series;
    public int pratio=3;
    String errorString="RR interval duration should be larger than sum of P,QRS,T, PR,ST duration !";
    private int lastX=0, miss=0;
    private int type=0;
    private String savenum;
    private static final Random RANDOM = new Random();


    private EditText mEditWordView;
    private EditText mEditRateView;
    private EditText mEditPampView;
    private EditText mEditQampView;
    private EditText mEditRampView;
    private EditText mEditSampView;
    private EditText mEditTampView;

    private EditText mEditPdurView;
    private EditText mEditQdurView;
    private EditText mEditRdurView;
    private EditText mEditSdurView;
    private EditText mEditTdurView;
    private EditText mEditSTsegView;
    private EditText mEditPRsegView;

    private SeekBar sbRate;
    private SeekBar sbPamp;
    private SeekBar sbQamp;
    private SeekBar sbRamp;
    private SeekBar sbSamp;
    private SeekBar sbTamp;
    private SeekBar sbPdur;
    private SeekBar sbQdur;
    private SeekBar sbRdur;
    private SeekBar sbSdur;
    private SeekBar sbTdur;
    private SeekBar sbSTseg;
    private SeekBar sbPRseg;




    // Unique tag for the intent reply.
    public static final String EXTRA_REPLYWORD = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLYRATE = "rate";
    public static final String EXTRA_REPLYPAMP = "pamp";
    public static final String EXTRA_REPLYQAMP = "qamp";
    public static final String EXTRA_REPLYRAMP = "ramp";
    public static final String EXTRA_REPLYSAMP = "samp";
    public static final String EXTRA_REPLYTAMP = "tamp";

    public static final String EXTRA_REPLYPDUR = "pdur";
    public static final String EXTRA_REPLYQDUR = "qdur";
    public static final String EXTRA_REPLYRDUR = "rdur";
    public static final String EXTRA_REPLYSDUR = "sdur";
    public static final String EXTRA_REPLYTDUR = "tdur";

    public static final String EXTRA_REPLYSTSEG = "stseg";
    public static final String EXTRA_REPLYPRSEG = "prseg";





    int mId = MainActivity.WORD_ADD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        mEditWordView = (EditText) findViewById(R.id.edit_word);
        mEditRateView = (EditText) findViewById(R.id.edit_rate);
        mEditPampView = (EditText) findViewById(R.id.edit_pamp);
        mEditQampView = (EditText) findViewById(R.id.edit_qamp);
        mEditRampView = (EditText) findViewById(R.id.edit_ramp);
        mEditSampView = (EditText) findViewById(R.id.edit_samp);
        mEditTampView = (EditText) findViewById(R.id.edit_tamp);
        mEditPdurView = (EditText) findViewById(R.id.edit_pdur);
        mEditQdurView = (EditText) findViewById(R.id.edit_qdur);
        mEditRdurView = (EditText) findViewById(R.id.edit_rdur);
        mEditSdurView = (EditText) findViewById(R.id.edit_sdur);
        mEditTdurView = (EditText) findViewById(R.id.edit_tdur);
        mEditSTsegView = (EditText) findViewById(R.id.edit_stseg);
        mEditPRsegView = (EditText) findViewById(R.id.edit_prseg);
        sbRate = (SeekBar) findViewById(R.id.seekBarRate);
        sbPamp = (SeekBar) findViewById(R.id.seekBarPamp);
        sbQamp = (SeekBar) findViewById(R.id.seekBarQamp);
        sbRamp = (SeekBar) findViewById(R.id.seekBarRamp);
        sbSamp = (SeekBar) findViewById(R.id.seekBarSamp);
        sbTamp = (SeekBar) findViewById(R.id.seekBarTamp);
        sbPdur = (SeekBar) findViewById(R.id.seekBarPdur);
        sbQdur = (SeekBar) findViewById(R.id.seekBarQdur);
        sbRdur = (SeekBar) findViewById(R.id.seekBarRdur);
        sbSdur = (SeekBar) findViewById(R.id.seekBarSdur);
        sbTdur = (SeekBar) findViewById(R.id.seekBarTdur);
        sbSTseg = (SeekBar) findViewById(R.id.seekBarSTseg);
        sbPRseg = (SeekBar) findViewById(R.id.seekBarPRseg);



        sbRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditRateView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();

            }
        });
        sbPamp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditPampView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbQamp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditQampView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbRamp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditRampView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbSamp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditSampView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbTamp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditTampView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbPdur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditPdurView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbQdur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditQdurView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbRdur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditRdurView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbSdur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditSdurView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbTdur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditTdurView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbSTseg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditSTsegView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });
        sbPRseg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEditPRsegView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateplot();
            }
        });

        mEditRateView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditRateView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
        mEditPampView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditPampView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditQampView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditQampView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditRampView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditRampView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditSampView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditSampView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditTampView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditTampView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        mEditPdurView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditPdurView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditQdurView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditQdurView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditRdurView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditRdurView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditSdurView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditSdurView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditTdurView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditTdurView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        mEditSTsegView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditSTsegView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        mEditPRsegView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditPRsegView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    updateplot();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });




        // Get data sent from calling activity.
        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            int id = extras.getInt(WordListAdapter.EXTRA_ID, NO_ID);
            String word = extras.getString(WordListAdapter.EXTRA_WORD, NO_WORD);
            String rate = extras.getString(WordListAdapter.EXTRA_RATE, NO_WORD);
            String pamp = extras.getString(WordListAdapter.EXTRA_PAMP, NO_WORD);
            String qamp = extras.getString(WordListAdapter.EXTRA_QAMP, NO_WORD);
            String ramp = extras.getString(WordListAdapter.EXTRA_RAMP, NO_WORD);
            String samp = extras.getString(WordListAdapter.EXTRA_SAMP, NO_WORD);
            String tamp = extras.getString(WordListAdapter.EXTRA_TAMP, NO_WORD);
            String pdur = extras.getString(WordListAdapter.EXTRA_PDUR, NO_WORD);
            String qdur = extras.getString(WordListAdapter.EXTRA_QDUR, NO_WORD);
            String rdur = extras.getString(WordListAdapter.EXTRA_RDUR, NO_WORD);
            String sdur = extras.getString(WordListAdapter.EXTRA_SDUR, NO_WORD);
            String tdur = extras.getString(WordListAdapter.EXTRA_TDUR, NO_WORD);
            String stseg = extras.getString(WordListAdapter.EXTRA_STSEG, NO_WORD);
            String prseg = extras.getString(WordListAdapter.EXTRA_PRSEG, NO_WORD);

            myplot(Integer.valueOf(rate),Integer.valueOf(pamp),Integer.valueOf(qamp),Integer.valueOf(ramp),Integer.valueOf(samp),Integer.valueOf(tamp),Integer.valueOf(pdur),Integer.valueOf(qdur),Integer.valueOf(rdur),Integer.valueOf(sdur),Integer.valueOf(tdur),Integer.valueOf(prseg),Integer.valueOf(stseg));




            if (id != NO_ID && word != NO_WORD) {
                mId = id;
                mEditWordView.setText(word);
                mEditRateView.setText(rate);
                mEditPampView.setText(pamp);
                mEditQampView.setText(qamp);
                mEditRampView.setText(ramp);
                mEditSampView.setText(samp);
                mEditTampView.setText(tamp);
                mEditPdurView.setText(pdur);
                mEditQdurView.setText(qdur);
                mEditRdurView.setText(rdur);
                mEditSdurView.setText(sdur);
                mEditTdurView.setText(tdur);
                mEditSTsegView.setText(stseg);
                mEditPRsegView.setText(prseg);

                sbRate.setProgress(Integer.valueOf(rate));
                sbPamp.setProgress(Integer.valueOf(pamp));
                sbQamp.setProgress(Integer.valueOf(qamp));
                sbRamp.setProgress(Integer.valueOf(ramp));
                sbSamp.setProgress(Integer.valueOf(samp));
                sbTamp.setProgress(Integer.valueOf(tamp));
                sbPdur.setProgress(Integer.valueOf(pdur));
                sbQdur.setProgress(Integer.valueOf(qdur));
                sbRdur.setProgress(Integer.valueOf(rdur));
                sbSdur.setProgress(Integer.valueOf(sdur));
                sbTdur.setProgress(Integer.valueOf(tdur));
                sbSTseg.setProgress(Integer.valueOf(stseg));
                sbPRseg.setProgress(Integer.valueOf(prseg));

            }
        } // Otherwise, start with empty fields.
    }

    /* *
     * Click handler for the Save button.
     *  Creates a new intent for the reply, adds the reply message to it as an extra,
     *  sets the intent result, and closes the activity.
     */
    public void returnReply(View view) {
        String word = ((EditText) findViewById(R.id.edit_word)).getText().toString();
        String rate = ((EditText) findViewById(R.id.edit_rate)).getText().toString();
        String pamp = ((EditText) findViewById(R.id.edit_pamp)).getText().toString();
        String qamp = ((EditText) findViewById(R.id.edit_qamp)).getText().toString();
        String ramp = ((EditText) findViewById(R.id.edit_ramp)).getText().toString();
        String samp = ((EditText) findViewById(R.id.edit_samp)).getText().toString();
        String tamp = ((EditText) findViewById(R.id.edit_tamp)).getText().toString();
        String pdur = ((EditText) findViewById(R.id.edit_pdur)).getText().toString();
        String qdur = ((EditText) findViewById(R.id.edit_qdur)).getText().toString();
        String rdur = ((EditText) findViewById(R.id.edit_rdur)).getText().toString();
        String sdur = ((EditText) findViewById(R.id.edit_sdur)).getText().toString();
        String tdur = ((EditText) findViewById(R.id.edit_tdur)).getText().toString();
        String stseg = ((EditText) findViewById(R.id.edit_stseg)).getText().toString();
        String prseg = ((EditText) findViewById(R.id.edit_prseg)).getText().toString();



        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLYWORD, word);
        replyIntent.putExtra(EXTRA_REPLYRATE, rate);
        replyIntent.putExtra(EXTRA_REPLYPAMP, pamp);
        replyIntent.putExtra(EXTRA_REPLYQAMP, qamp);
        replyIntent.putExtra(EXTRA_REPLYRAMP, ramp);
        replyIntent.putExtra(EXTRA_REPLYSAMP, samp);
        replyIntent.putExtra(EXTRA_REPLYTAMP, tamp);
        replyIntent.putExtra(EXTRA_REPLYPDUR, pdur);
        replyIntent.putExtra(EXTRA_REPLYQDUR, qdur);
        replyIntent.putExtra(EXTRA_REPLYRDUR, rdur);
        replyIntent.putExtra(EXTRA_REPLYSDUR, sdur);
        replyIntent.putExtra(EXTRA_REPLYTDUR, tdur);
        replyIntent.putExtra(EXTRA_REPLYSTSEG, stseg);
        replyIntent.putExtra(EXTRA_REPLYPRSEG, prseg);






        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId);
        if(mId>4){
        setResult(RESULT_OK, replyIntent);
        finish();}
        else
        {
            Context context = getApplicationContext();
            Toast.makeText(context, "Default preset is not changable", Toast.LENGTH_SHORT).show();
        finish();}
    }
    //save as a copy
    public void returnReply2(View view) {

        String word = ((EditText) findViewById(R.id.edit_word)).getText().toString();
        String rate = ((EditText) findViewById(R.id.edit_rate)).getText().toString();
        String pamp = ((EditText) findViewById(R.id.edit_pamp)).getText().toString();
        String qamp = ((EditText) findViewById(R.id.edit_qamp)).getText().toString();
        String ramp = ((EditText) findViewById(R.id.edit_ramp)).getText().toString();
        String samp = ((EditText) findViewById(R.id.edit_samp)).getText().toString();
        String tamp = ((EditText) findViewById(R.id.edit_tamp)).getText().toString();
        String pdur = ((EditText) findViewById(R.id.edit_pdur)).getText().toString();
        String qdur = ((EditText) findViewById(R.id.edit_qdur)).getText().toString();
        String rdur = ((EditText) findViewById(R.id.edit_rdur)).getText().toString();
        String sdur = ((EditText) findViewById(R.id.edit_sdur)).getText().toString();
        String tdur = ((EditText) findViewById(R.id.edit_tdur)).getText().toString();
        String stseg = ((EditText) findViewById(R.id.edit_stseg)).getText().toString();
        String prseg = ((EditText) findViewById(R.id.edit_prseg)).getText().toString();




        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLYWORD, "Copy of "+ word);
        replyIntent.putExtra(EXTRA_REPLYRATE, rate);
        replyIntent.putExtra(EXTRA_REPLYPAMP, pamp);
        replyIntent.putExtra(EXTRA_REPLYQAMP, qamp);
        replyIntent.putExtra(EXTRA_REPLYRAMP, ramp);
        replyIntent.putExtra(EXTRA_REPLYSAMP, samp);
        replyIntent.putExtra(EXTRA_REPLYTAMP, tamp);
        replyIntent.putExtra(EXTRA_REPLYPDUR, pdur);
        replyIntent.putExtra(EXTRA_REPLYQDUR, qdur);
        replyIntent.putExtra(EXTRA_REPLYRDUR, rdur);
        replyIntent.putExtra(EXTRA_REPLYSDUR, sdur);
        replyIntent.putExtra(EXTRA_REPLYTDUR, tdur);
        replyIntent.putExtra(EXTRA_REPLYSTSEG, stseg);
        replyIntent.putExtra(EXTRA_REPLYPRSEG, prseg);






        replyIntent.putExtra(WordListAdapter.EXTRA_ID, -1);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void updateplot() {
        String word = ((EditText) findViewById(R.id.edit_word)).getText().toString();
        String rate = ((EditText) findViewById(R.id.edit_rate)).getText().toString();
        String pamp = ((EditText) findViewById(R.id.edit_pamp)).getText().toString();
        String qamp = ((EditText) findViewById(R.id.edit_qamp)).getText().toString();
        String ramp = ((EditText) findViewById(R.id.edit_ramp)).getText().toString();
        String samp = ((EditText) findViewById(R.id.edit_samp)).getText().toString();
        String tamp = ((EditText) findViewById(R.id.edit_tamp)).getText().toString();
        String pdur = ((EditText) findViewById(R.id.edit_pdur)).getText().toString();
        String qdur = ((EditText) findViewById(R.id.edit_qdur)).getText().toString();
        String rdur = ((EditText) findViewById(R.id.edit_rdur)).getText().toString();
        String sdur = ((EditText) findViewById(R.id.edit_sdur)).getText().toString();
        String tdur = ((EditText) findViewById(R.id.edit_tdur)).getText().toString();
        String stseg = ((EditText) findViewById(R.id.edit_stseg)).getText().toString();
        String prseg = ((EditText) findViewById(R.id.edit_prseg)).getText().toString();

        sbRate.setProgress(Integer.valueOf(rate));
        sbPamp.setProgress(Integer.valueOf(pamp));
        sbQamp.setProgress(Integer.valueOf(qamp));
        sbRamp.setProgress(Integer.valueOf(ramp));
        sbSamp.setProgress(Integer.valueOf(samp));
        sbTamp.setProgress(Integer.valueOf(tamp));
        sbPdur.setProgress(Integer.valueOf(pdur));
        sbQdur.setProgress(Integer.valueOf(qdur));
        sbRdur.setProgress(Integer.valueOf(rdur));
        sbSdur.setProgress(Integer.valueOf(sdur));
        sbTdur.setProgress(Integer.valueOf(tdur));
        sbSTseg.setProgress(Integer.valueOf(stseg));
        sbPRseg.setProgress(Integer.valueOf(prseg));

        myplot(Integer.valueOf(rate),Integer.valueOf(pamp),Integer.valueOf(qamp),Integer.valueOf(ramp),Integer.valueOf(samp),Integer.valueOf(tamp),Integer.valueOf(pdur),Integer.valueOf(qdur),Integer.valueOf(rdur),Integer.valueOf(sdur),Integer.valueOf(tdur),Integer.valueOf(prseg),Integer.valueOf(stseg));
        Context context = getApplicationContext();
        Toast.makeText(context, "reploting", Toast.LENGTH_SHORT).show();

    }


    void myplot( int vRate, int vPamp,int vQamp, int vRamp, int vSamp, int vTamp,int vPdur,int vQdur, int vRdur, int vSdur, int vTdur,int vPRseg, int vSTseg)
    {
        vRate = (vRate >0) ? vRate:0;
        vPamp = (vPamp < 0 ) ? 500 : vPamp;
        vQamp = (vQamp < 0 ) ? 500 : vQamp;
        vRamp = (vRamp < 0 ) ? 500 : vRamp;
        vSamp = (vSamp < 0 ) ? 500 : vSamp;
        vTamp = (vTamp < 0 ) ? 500 : vTamp;
        vPdur = (vPdur < 0 ) ? 500 : vPdur;
        vQdur = (vQdur < 0 ) ? 500 : vQdur;
        vRdur = (vRdur < 0 ) ? 500 : vRdur;
        vSdur = (vSdur < 0 ) ? 500 : vSdur;
        vTdur = (vTdur < 0 ) ? 500 : vTdur;
        vPRseg = (vPRseg < 0 ) ? 0 : vPRseg;
        vSTseg = (vSTseg < 0 ) ? 0 : vSTseg;




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
            {
                Toast ToastMessage;

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


//                    //if(Pcnt%3==0&&type==1)
//                    // 50 , or use Pperiod/(pratio+1)
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

