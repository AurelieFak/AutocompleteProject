/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniautocomplete;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fakambi
 */
public class TrieTest {
    
    public TrieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddWord(){
        Trie newTrie = new Trie();
        newTrie.addWord("Aurelie");
        newTrie.addWord("Aurore");
        newTrie.addWord("Lion");
        newTrie.addWord("Line");  
        newTrie.addWord("Livia"); 
        int number = newTrie.wordsByPrefix("Li").length;
        int number2 = newTrie.wordsByPrefix("Au").length;
        assertEquals(number,3);
        assertEquals(number2,2);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
