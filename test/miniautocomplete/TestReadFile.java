/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniautocomplete;

import java.util.ArrayList;
import java.util.List;
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
public class TestReadFile {

    public TestReadFile() {
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

    //checks that the reader fills properly the SuggestionItem list
    @Test
    public void testGetSuggestions() {
        String fileName = "";
        ISuggestionProvider sp = new ReadFunctionsCSV2();
        List<SuggestionItem> listSuggestions = new ArrayList<SuggestionItem>();
        listSuggestions = sp.getSuggestions("/home/fakambi/Downloads/functionSmallTest");
        
        assertEquals(5, listSuggestions.size());
    }
}
