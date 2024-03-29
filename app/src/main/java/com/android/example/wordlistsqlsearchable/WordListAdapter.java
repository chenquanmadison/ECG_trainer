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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implements a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    /**
     *  Custom view holder with a text view and two buttons.
     */
    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        Button delete_button;
        Button edit_button;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            delete_button = (Button)itemView.findViewById(R.id.delete_button);
            edit_button = (Button)itemView.findViewById(R.id.edit_button);
        }
    }

    private static final String TAG = WordListAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_WORD = "WORD";
    public static final String EXTRA_RATE = "RATE";
    public static final String EXTRA_PAMP = "PAMP";
    public static final String EXTRA_QAMP = "QAMP";
    public static final String EXTRA_RAMP = "RAMP";
    public static final String EXTRA_SAMP = "SAMP";
    public static final String EXTRA_TAMP = "TAMP";
    public static final String EXTRA_PDUR = "PDUR";
    public static final String EXTRA_QDUR = "QDUR";
    public static final String EXTRA_RDUR = "RDUR";
    public static final String EXTRA_SDUR = "SDUR";
    public static final String EXTRA_TDUR = "TDUR";
    public static final String EXTRA_STSEG= "STSEG";
    public static final String EXTRA_PRSEG= "PRSEG";



    public static final String EXTRA_POSITION = "POSITION";

    private final LayoutInflater mInflater;
    WordListOpenHelper mDB;
    Context mContext;

    public WordListAdapter(Context context, WordListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        // Keep a reference to the view holder for the click listener
        final WordViewHolder h = holder; // needs to be final for use in callback

        WordItem current = mDB.query(position);
        holder.wordItemView.setText(current.getWord());


        // Attach a click listener to the DELETE button.
        holder.delete_button.setOnClickListener(new MyButtonOnClickListener(
                current.getId(), null, null,null,null,null,null,null,null,null,null,null,null,null,null)  {

            @Override
            public void onClick(View v ) {
                // Remove from the database.
                if (id>4) {
                    int deleted = mDB.delete(id);
                    if (deleted >= 0) {
                        // Redisplay the view.
                        notifyItemRemoved(h.getAdapterPosition());
                    }
                }
                else
                {
                    Toast.makeText(mContext, "Default preset is not removable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Attach a click listener to the EDIT button.
        holder.edit_button.setOnClickListener(new MyButtonOnClickListener(
                current.getId(), current.getWord(), current.getRate(),current.getPamp(),current.getQamp(),current.getRamp(),current.getSamp(),current.getTamp(),current.getPdur(),current.getQdur(),current.getRdur(),current.getSdur(),current.getTdur(),current.getPRseg(),current.getSTseg()) {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditWordActivity.class);

                intent.putExtra(EXTRA_ID, id);
                intent.putExtra(EXTRA_POSITION, h.getAdapterPosition());
                intent.putExtra(EXTRA_WORD, word);
                intent.putExtra(EXTRA_RATE, rate);
                intent.putExtra(EXTRA_PAMP, pamp);
                intent.putExtra(EXTRA_QAMP, qamp);
                intent.putExtra(EXTRA_RAMP, ramp);
                intent.putExtra(EXTRA_SAMP, samp);
                intent.putExtra(EXTRA_TAMP, tamp);
                intent.putExtra(EXTRA_PDUR, pdur);
                intent.putExtra(EXTRA_QDUR, qdur);
                intent.putExtra(EXTRA_RDUR, rdur);
                intent.putExtra(EXTRA_SDUR, sdur);
                intent.putExtra(EXTRA_TDUR, tdur);
                intent.putExtra(EXTRA_STSEG, stseg);
                intent.putExtra(EXTRA_PRSEG, prseg);


                // Start an empty edit activity.
                ((Activity) mContext).startActivityForResult(intent, MainActivity.WORD_EDIT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }
}


