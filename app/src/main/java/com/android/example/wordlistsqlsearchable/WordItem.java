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

/**
 * Data model for one word list item.
 */
public class WordItem {

    private int mId;
    private String mWord;
    private String mRate;
    private String mPamp;
    private String mQamp;
    private String mRamp;
    private String mSamp;
    private String mTamp;
    private String mPdur;
    private String mQdur;
    private String mRdur;
    private String mSdur;
    private String mTdur;
    private String mSTseg;
    private String mPRseg;

    public WordItem() {}

    public int getId() {
        return this.mId;
    }
    public String getWord() {
        return this.mWord;
    }
    public String getRate() {
        return this.mRate;
    }
    public String getPamp() {
        return this.mPamp;
    }
    public String getQamp() {
        return this.mQamp;
    }
    public String getRamp() {
        return this.mRamp;
    }
    public String getSamp() {
        return this.mSamp;
    }
    public String getTamp() {
        return this.mTamp;
    }

    public String getPdur() {
        return this.mPdur;
    }
    public String getQdur() {
        return this.mQdur;
    }
    public String getRdur() {
        return this.mRdur;
    }
    public String getSdur() {
        return this.mSdur;
    }
    public String getTdur() {
        return this.mTdur;
    }
    public String getSTseg() { return this.mSTseg;}
    public String getPRseg() { return this.mPRseg;}





    public void setId(int id) {
        this.mId = id;
    }
    public void setWord(String word) {
        this.mWord = word;
    }
    public void setRate(String rate) {
        this.mRate = rate;
    }
    public void setPamp(String Pamp) {
        this.mPamp = Pamp;
    }
    public void setQamp(String Qamp) {
        this.mQamp = Qamp;
    }
    public void setRamp(String Ramp) {
        this.mRamp = Ramp;
    }
    public void setSamp(String Samp) {
        this.mSamp = Samp;
    }
    public void setTamp(String Tamp) {
        this.mTamp = Tamp;
    }

    public void setPdur(String Pdur) {
        this.mPdur = Pdur;
    }
    public void setQdur(String Qdur) {
        this.mQdur = Qdur;
    }
    public void setRdur(String Rdur) {
        this.mRdur = Rdur;
    }
    public void setSdur(String Sdur) {
        this.mSdur = Sdur;
    }
    public void setTdur(String Tdur) {
        this.mTdur = Tdur;
    }

    public void setSTseg(String STseg) {
        this.mSTseg = STseg;
    }
    public void setPRseg(String PRseg) {
        this.mPRseg = PRseg;
    }
}