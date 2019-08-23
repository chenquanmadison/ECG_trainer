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

import android.view.View;

/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
public class MyButtonOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    int id;
    String word;
    String rate;
    String pamp;
    String qamp;
    String ramp;
    String samp;
    String tamp;
    String pdur;
    String qdur;
    String rdur;
    String sdur;
    String tdur;
    String prseg;
    String stseg;



    public MyButtonOnClickListener(int id, String word, String rate,String pamp, String qamp, String ramp, String samp, String tamp,String pdur, String qdur, String rdur, String sdur, String tdur,String prseg,String stseg) {
        this.id = id;
        this.word = word;
        this.rate = rate;
        this.pamp = pamp;
        this.qamp = qamp;
        this.ramp = ramp;
        this.samp = samp;
        this.tamp = tamp;
        this.pdur = pdur;
        this.qdur = qdur;
        this.rdur = rdur;
        this.sdur = sdur;
        this.tdur = tdur;
        this.stseg = stseg;
        this.prseg = prseg;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }

}
