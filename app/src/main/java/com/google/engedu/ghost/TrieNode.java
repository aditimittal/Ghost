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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        //will break a word and keep adding nodes into the trie
        TrieNode currentNode = this;
        //traverse the word
        for(int i = 0 ; i<s.length() ; i++){

            if(!currentNode.children.containsKey(String.valueOf(s.charAt(i)))){
                currentNode.children.put(String.valueOf(s.charAt(i)),new TrieNode());
            }
            currentNode = currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        currentNode.isWord = true;

    }

    //represents the check for end of a word aka identifier
    public boolean isWord(String s) {
      //run a for loop to iterate thru the trie to search a word
      // if the word is successfully found then on the ast character we
      //check for the is word condition
        TrieNode currentNode = this;
        for(int i=0 ; i<s.length() ; i++) {
            if (!currentNode.children.containsKey(String.valueOf(s.charAt(i)))) {
                //if some char does not exist
                return false;
            }
            //to interate thru the trie
            currentNode = currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        return currentNode.isWord;
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode currentNode = this;
        String string = s;
        for(int i=0 ; i<s.length() ; i++) {
            if (!currentNode.children.containsKey(String.valueOf(s.charAt(i)))) {
                return null;
            }
            currentNode = currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        if(currentNode.children.size()==0){
            return null;
        }
        do{
            for (char i = 'a' ; i <= 'z' ; i++ ){
                if(currentNode.children.containsKey(String.valueOf(i))){
                    string +=i;
                    currentNode = currentNode.children.get(String.valueOf(i));
                    break;
                }
            }
        }while (currentNode.isWord==false);
        return string;
    }

    public String getGoodWordStartingWith(String s) {
        TrieNode currentNode = this;
        String string = s;
        ArrayList<String> arrayList= new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (!currentNode.children.containsKey(String.valueOf(s.charAt(i)))) {
                return null;
            }
            currentNode = currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        if (currentNode.children.size() == 0) {
            return null;
        }
        do {
            for (char i = 'a'; i <= 'z'; i++) {
                if (currentNode.children.containsKey(String.valueOf(i))) {
                    arrayList.add(String.valueOf(i));
                    //Log.e("in do while", " >>"+String.valueOf(i));
                    //currentNode = currentNode.children.get(String.valueOf(i));
                }
            }
            int n = new Random().nextInt(arrayList.size());
            string+= arrayList.get(n);
            //Log.e("in...On RANDOM CONCAT", " >>"+string);
            currentNode = currentNode.children.get(arrayList.get(n));
            arrayList.clear();
        } while (currentNode.isWord == false);
        return string;
    }
}