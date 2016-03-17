/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
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
 *
 */

package com.jaredrummler.truetype.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jaredrummler.fontreader.truetype.TTFFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View view) {
        showFontInfo("font.ttf");
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  private void showFontInfo(String asset) {
    new AsyncTask<String, Void, List<String[]>>() {

      @Override protected List<String[]> doInBackground(String... params) {
        List<String[]> properties = new ArrayList<>();

        try {
          TTFFile ttfFile = TTFFile.open(getAssets().open(params[0]));

          properties.add(new String[]{"NAME", ttfFile.getFullName()});
          properties.add(new String[]{"POST SCRIPT NAME", ttfFile.getPostScriptName()});
          properties.add(new String[]{"FAMILIES", Arrays.toString(ttfFile.getFamilyNames().toArray())});
          properties.add(new String[]{"SUB FAMILY", ttfFile.getSubFamilyName()});
          properties.add(new String[]{"WEIGHT", String.valueOf(ttfFile.getWeightClass())});
          properties.add(new String[]{"NOTICE", ttfFile.getCopyrightNotice()});

        } catch (IOException e) {
          e.printStackTrace();
        }

        return properties;
      }

      @Override protected void onPostExecute(List<String[]> properties) {
        if (isFinishing()) {
          return;
        }
        StringBuilder html = new StringBuilder();
        for (String[] property : properties) {
          html.append("<strong>")
              .append(property[0])
              .append(":</strong>")
              .append(' ')
              .append(property[1])
              .append("<br><br>");
        }
        new AlertDialog.Builder(MainActivity.this)
            .setTitle("Font properties")
            .setMessage(Html.fromHtml(html.toString()))
            .setPositiveButton(android.R.string.ok, null)
            .show();
      }

    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, asset);
  }

}
