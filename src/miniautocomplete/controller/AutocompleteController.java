/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 *
 * @author fakambi
 *
 * Bridge between the user inputs in the Jtextarea and the autocomplete Engine
 * Provides the autocomplete functionnality to any Jtext area
 * Primirarly for functions
 */
public class AutocompleteController {

    private final JTextArea taFormula;
    private SuggestionPanel suggestionPanel;
    //public MenuKeyListener formulaKeyListener;
    
    //DATA SOURCE
    public ISuggestionProvider rf = new ReadFunctionsCSV2();
    public List<SuggestionItem> listItems = new ArrayList<>();
    public List<String> listFunctionNames = new ArrayList();
    public Trie newTrie = new Trie();

    //called only once
    public AutocompleteController(JTextArea taFormula) {
        init();
        this.taFormula = taFormula;

        //this.suggestionPanel=suggestionPanel;
        //formulaKeyListener = new FormulaKeyListener();
        //this.taFormula.addKeyListener(formulaKeyListener);
        this.taFormula.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyChar() == KeyEvent.VK_ENTER) { //endroit different de up and down 
                    if (getSuggestionPanel() != null) {
                        if (getSuggestionPanel().insertSelection()) {
                            e.consume();
                            final int position = taFormula.getCaretPosition();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Kreleased");
                if (e.getKeyCode() == KeyEvent.VK_DOWN && getSuggestionPanel() != null) {
                    getSuggestionPanel().moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP && getSuggestionPanel() != null) {
                    getSuggestionPanel().moveUp();
                } else if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    showSuggestionLater();
                } else if (Character.isWhitespace(e.getKeyChar()) && getSuggestionPanel() != null ) {
                    getSuggestionPanel().hideSuggestion();
                }
            }

        });
    }
    //Getters and setters
  /**
     * @return the suggestionPanel
     */
    public SuggestionPanel getSuggestionPanel() {
        return suggestionPanel;
    }

    /**
     * @param suggestionPanel the suggestionPanel to set
     */
    public void setSuggestionPanel(SuggestionPanel suggestionPanel) {
        this.suggestionPanel = suggestionPanel;
    }
    public final void init() {

        /*rf = new ReadFunctionsCSV2();
        listItems = new ArrayList<>();
        listFunctionNames = new ArrayList();
        newTrie = new Trie();*/
        //fill the list with all items
        listItems = rf.getSuggestions();

        //fill a list with only the item names
        for (int i = 0; i < listItems.size(); i++) {
            listFunctionNames.add(listItems.get(i).getName());
        }

        //fill the trie, onve for all
        for (String word : listFunctionNames) {
            newTrie.addWord(word.toLowerCase());
            System.out.println("Word in the trie :" + word);
        }
    }

    protected void showSuggestion() throws BadLocationException {

        if (getSuggestionPanel() != null) {
            getSuggestionPanel().hideSuggestion();
        }
        final int position = taFormula.getCaretPosition();//position the suggestion panel where the cursor is
        Point location;
        location = taFormula.modelToView(position).getLocation();
        String text = taFormula.getText();
        int start = Math.max(0, position - 1);
        while (start > 0) {
            start--;
            if (text.charAt(start) == '(' || text.charAt(start) == ',') { // a word start after an opening bracket or after a comma
                start++;
                break;
            } else if (Character.isWhitespace(text.charAt(start))) {
                start++;
                break;
            }

        }
        if (start > position) {
            return;
        }

        final String subWord = (text.substring(start, position)).toLowerCase();

        System.out.println("Le subword " + subWord + subWord.length());

        if (subWord.length() < 2) { //we start looking for similar words from 2 letters types by the user
            return;
        }

        if (newTrie.wordsByPrefix2(subWord)) {

            setSuggestionPanel(new SuggestionPanel(this,taFormula, subWord, position, location, newTrie, listItems, listFunctionNames));
            getSuggestionPanel().getPopupMenu().requestFocusInWindow();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //popupMenu.requestFocusInWindow();
                    taFormula.requestFocusInWindow(); //pour pouvoir continuer a ecrire meme quand la popup est ouverte BOF...
                }
            });
        } else {
            ;
        }

        return;

    }

    protected void showSuggestionLater() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    showSuggestion();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

        });
    }

  

}
