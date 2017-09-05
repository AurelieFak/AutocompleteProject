/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

/**
 *
 * @author fakambi
 */
public class TrieNode {
    
  TrieNode[] children;
  char label;
  boolean terminal;
  String word;
 
private static int ALPHABET_SIZE = 100;//26 //52, upper and lowercase alphabet ?
 
  public TrieNode() {
    this.children = new TrieNode[ALPHABET_SIZE];
  }
 
  public TrieNode(char l) {
    this();
    this.label = l;
  }
    
}
