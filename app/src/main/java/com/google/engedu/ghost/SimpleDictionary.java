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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    //int i;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);//whether word is in dictionary or not
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
    //binary search

        if(prefix.equals(""))
            return words.get(new Random().nextInt(words.size()-1));
        else{
            String midString="";
            int mid,start=0,end=words.size()-1;

            // int res;
            //mid=(start+end)/2;
            while(start<end){
                mid=(start+end)/2;
                midString=words.get(mid);
                if(midString.compareToIgnoreCase(prefix)>0)
                 end=mid-1;
                if(midString.compareToIgnoreCase(prefix)<0)
                    start=mid+1;
                if(midString.startsWith(prefix))
                    return midString;
            }
        }
        return null;
    }


    @Override
    public String getGoodWordStartingWith(String prefix) {
        ArrayList<String> arrayList= new ArrayList<>();
        if(prefix.equals(""))
            return words.get(new Random().nextInt(words.size()-1));
        else{
            String midString="";
            String check="";

            int mid,start=0,end = words.size()-1;
            while(start<end){
                //Log.e("in while loop", "start "+start+" end "+end );
                mid=(start+end)/2;
                midString=words.get(mid);
                if(midString.compareToIgnoreCase(prefix)>0)
                    end=mid-1;
                if(midString.compareToIgnoreCase(prefix)<0)
                    start=mid+1;



                if(midString.startsWith(prefix)){

                    arrayList.add(midString);

                     for(int i = mid-1 ; i>start ; i--){
                         check = words.get(i);
                         if(check.startsWith(prefix)){

                             arrayList.add(check);}
                         else break;
                     }
                     for (int i = mid+1 ; i>end ; i++){
                         check = words.get(i);
                         if(check.startsWith(prefix)){

                             arrayList.add(check);}
                         else break;
                     }
                     int n = new Random().nextInt(arrayList.size());

                     return arrayList.get(n);
                }
            }
        }
        return null;
    }
}