/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    TextView text,label;
    String wordFragment="",currentWord="";
    //String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        try{
            InputStream dictionaryWords=getAssets().open("words.txt");
            dictionary=new SimpleDictionary(dictionaryWords);//dictionary is object of ghost dictionary
        }catch (IOException e){
            e.printStackTrace();
        }
        text=(TextView)findViewById(R.id.ghostText);
        label=(TextView)findViewById(R.id.gameStatus);
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        text.setText("");
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {

        // Do computer turn stuff then make it the user's turn again
        if(wordFragment.length()>=4 && dictionary.isWord(wordFragment))
        {
            label.setText("Computer Win...");
        }
        else{
            String wordFromDictionary=dictionary.getGoodWordStartingWith(wordFragment);
            if(wordFromDictionary!=null)
            {
                wordFragment=wordFragment.concat(wordFromDictionary.substring(wordFragment.length(),wordFragment.length()+1));
                text.setText(wordFragment);
                currentWord=wordFragment;
                userTurn=true;
                label.setText(USER_TURN);
            }
            else{
                text.setText("Match Draw...");
                label.setText("No word can be formed from this prefix");
            }
        }

    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
       if(keyCode>=29 && keyCode<=54){
           char c=(char)event.getUnicodeChar();
           wordFragment=(String)text.getText();
           wordFragment=currentWord+Character.toString(c);
           text.setText(wordFragment);
           computerTurn();
           return true;
       }
        else return super.onKeyUp(keyCode, event);
    }

    public void challenge(View view) {
        //check the word fragment word in dictionary
        //if word is in dictionary -you win esle you lose
        String str=(String)text.getText();
        if(dictionary.isWord(str))
        {
            label.setText("YOU WIN");
        }
        else
        {
            label.setText("YOU LOSE");
        }

    }

    public void reset(View view) {
        userTurn=random.nextBoolean();
        text.setText("");
        wordFragment="";
        currentWord="";
        if(userTurn)
        {
            label.setText(USER_TURN);
        }
        else{
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
    }
}
