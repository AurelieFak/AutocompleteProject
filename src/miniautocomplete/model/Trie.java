/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fakambi
 * The trie is a text processing datastructure suitable for autocomplete 
 * 
 */
public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode(' ');
    }

    public void addWord(String word) {   // given a word, add it to the Trie.
        char[] cArray = word.toCharArray();
        TrieNode temp = root;
        TrieNode tn = null;
        int index = 0;

        do {
            if ('a'<= cArray[index] && cArray[index]<= 'z')
                tn = temp.children[cArray[index] - 'A'-6];
           else if ('A'<= cArray[index] && cArray[index]<= 'Z')   
                tn = temp.children[cArray[index] - 'A'];
            if (tn != null) {
                temp = tn;
                index++;
                // if the word got over, then it is already in the trie.
                if (index >= word.length()) {
                    temp.terminal = true;
                    temp.word = word;
                    return;
                }
            }
        } while (tn != null);

        // temp is the last node and the word to add is lengthier
        for (; index < cArray.length; index++) {
             if ('a'<= cArray[index] && cArray[index]<= 'z')
            temp.children[cArray[index] -'A'-6] = new TrieNode(cArray[index]);
             else if ('A'<= cArray[index] && cArray[index]<= 'Z')
             temp.children[cArray[index] -'A'] = new TrieNode(cArray[index]);
             
              if ('a'<= cArray[index] && cArray[index]<= 'z')
            temp = temp.children[cArray[index] - 'A'-6];
             else if ('A'<= cArray[index] && cArray[index]<= 'Z')
                  temp = temp.children[cArray[index] - 'A'];
        }

        temp.terminal = true;
        temp.word = word;
    }

    public String[] wordsByPrefix(String prefix) { //given a prefix, return the list of words starting from the prefix.
        char[] cArray = prefix.toCharArray();
        TrieNode temp = root;
        TrieNode tn = null;
        int index = 0;

        //identifier un noeud en particulier ?
        do {
            if ('a'<= cArray[index] && cArray[index]<= 'z')
                tn = temp.children[cArray[index] - 'A'-6]; //ca ca permet de trouver une lettre en particulier
            else if ('A'<= cArray[index] && cArray[index]<= 'Z')
                 tn = temp.children[cArray[index] - 'A'];
            // if you reached the end of the string, then no words with this prefix
            if (tn == null) {
                return null;
            }

            index++;
            temp = tn;
        } while (index < cArray.length);

        // temp is at the node representing the last char of the prefix
        // do a traversal for all paths below this.
        List<String> words = new ArrayList<String>();
        Deque<TrieNode> DQ = new ArrayDeque<TrieNode>();
        DQ.addLast(temp);
        while (!DQ.isEmpty()) {
            TrieNode first = DQ.removeFirst();
            if (first.terminal) {
                words.add(first.word);
            }

            for (TrieNode n : first.children) {
                if (n != null) {
                    DQ.add(n);
                }
            }
        }

       //solution : tout mettre en lower/upper case et retourner la function correspondante de la liste originale
        return words.toArray(new String[0]);
    }

    public boolean wordsByPrefix2(String prefix) { //given a prefix, return the list of words starting from the prefix.
        char[] cArray = prefix.toCharArray();
        TrieNode temp = root;
        TrieNode tn = null;
        int index = 0;
        //ne rien faire si caractere == ( ou ,
        do {
            System.out.println("B");
             if ('a'<= cArray[index] && cArray[index]<= 'z')
            tn = temp.children[cArray[index] -'A'-6];
             else if ('A'<= cArray[index] && cArray[index]<= 'Z')
                 tn = temp.children[cArray[index] - 'A'];
            System.out.println("B");
            // if you reached the end of the string, then no words with this prefix
            if (tn == null) {
                return false;
            }

            index++;
            temp = tn;
        } while (index < cArray.length);

        // temp is at the node representing the last char of the prefix
        // do a traversal for all paths below this.
        List<String> words = new ArrayList<String>();
        Deque<TrieNode> DQ = new ArrayDeque<TrieNode>();
        DQ.addLast(temp);
        while (!DQ.isEmpty()) {
            TrieNode first = DQ.removeFirst();
            if (first.terminal) {
                words.add(first.word);
            }

            for (TrieNode n : first.children) {
                if (n != null) {
                    DQ.add(n);
                }
            }
        }

        if (words.toArray(new String[0]).length == 0) {
            return false;
        } else {
            return true;

        }
    }

}
