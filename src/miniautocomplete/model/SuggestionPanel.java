/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniautocomplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author fakambi
 *
 * Autocomplete Engine Contains all the functions allowing the autocomplete to
 * work
 * 
 * Controller ?
 */
public class SuggestionPanel {

    private final JTextArea taFormula;
    private String subWord;
    private Point location;
    private AutocompleteController ac; //bound a suggestionPanel to a specific ac 
    //public MenuKeyListener formulaKeyListener;
    private int insertionPosition;
    private ItemList list;
    private JPopupMenu popupMenu;
    private JScrollPane scrollPane;

    public SuggestionPanel(AutocompleteController ac, JTextArea taFormula, String subWord, int position, Point location, Trie newTrie, List<SuggestionItem> listItems, List<String> listFunctionNames) {
        //possible de reduire le nombre de parametres ?       
        this.ac = ac;
        this.taFormula = taFormula;
        this.subWord = subWord;
        this.insertionPosition = position;

        //setFocusable(true);
        //this.list=list;
        popupMenu = new JPopupMenu();
        popupMenu.removeAll();

        //vole le focus a taformula, run dans le background
        popupMenu.setVisible(true);
        popupMenu.requestFocusInWindow();

        popupMenu.setOpaque(false);
        popupMenu.setBorder(null);

        //popupMenu.addMenuKeyListener(formulaKeyListener);
        list = createSuggestionList(subWord, newTrie, listItems, listFunctionNames/*,this*/);
        if (list.getModel().getSize() > 7) { //JScrollPane only created when the list contains more than 7 items
            scrollPane = new JScrollPane(getList());

            // Bind actions to the scroll pane so the user can scroll with arrow keys.

           /* JScrollBar vertical = scrollPane.getVerticalScrollBar();
            InputMap verticalMap = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            verticalMap.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
            verticalMap.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");*/
           
            scrollPane.setViewportView(list);
            popupMenu.add(scrollPane, BorderLayout.CENTER);
        } else {
            popupMenu.add(list, BorderLayout.CENTER);
        }

        popupMenu.show(taFormula, location.x, taFormula.getBaseline(0, 0) + location.y);
        //taFormula.setComponentPopupMenu(popupMenu);

    }

    //SETTERS AND GETTERS
    /**
     * @return the popupMenu
     */
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    /**
     * @return the list
     */
    public ItemList getList() {
        return list;
    }

    /**
     * @return the subWord
     */
    public String getSubWord() {
        return subWord;
    }

    //test : possible de verifier la taille ?
    public ItemList createSuggestionList(final String subWord, Trie newTrie, List<SuggestionItem> listItems, List<String> listFunctionNames/*,SuggestionPanel suggestionPanel*/) {

        String subWordLc = subWord.toLowerCase();

        String[] data = {""};

        DefaultListModel listModel = new DefaultListModel();
        data = newTrie.wordsByPrefix(subWordLc);
        ArrayList<String> myList = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < listFunctionNames.size(); j++) {
                if (data[i].equalsIgnoreCase(listFunctionNames.get(j))) {
                    myList.add(listFunctionNames.get(j));
                    break;
                }
            }
        }

        Collections.sort(myList); //sorting by Type : Constant/Function and by alphabetic order
        for (int i = 0; i < myList.size(); i++) {
            data[i] = myList.get(i);
        }

        data = ToolFunctionsAutocomplete.addParameters(data, listItems); //adding Parameters to the result of the trie

        if (data != null) {

            ItemList list = new ItemList(listModel);
            list.setCellRenderer(new FunctionEntryCellRenderer()); //icons

            for (int i = 0; i < data.length; i++) {

                listModel.addElement(new FunctionEntry(data[i])); 

            }

            list.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);           
            //list.setAutoscrolls(true);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { //Double click
                        insertSelection();
                        //hideSuggestion();
                    }
                }
            });

            return list;
        }
        return null;
    }

    /**
     * Inserts the selected value in the Jtextarea after double clicking or pressing ENTER
     * @return 
     */
    public boolean insertSelection() {
        int start = 0;
        int end = 0;
        String chaine = "";
        if (this.getList().getSelectedValue() != null) {
            start = taFormula.getCaretPosition() - getSubWord().length();
            end = start + getSubWord().length();

            String selectedValue = (String) getList().getSelectedValue().toString();
            chaine = ToolFunctionsAutocomplete.toUsableArguments(selectedValue); 

            taFormula.replaceRange(chaine, start, end);
            //taFormula.setCaretPosition(subWord.length());
            //popupMenu.setVisible(false);
            hideSuggestion();

            return true;

        }

        return false;
    }

    public void hide() {
        if (this.getPopupMenu() != null) {
            this.getPopupMenu().setVisible(false);
        }

        if (ac.getSuggestionPanel() == this) {
            ac.setSuggestionPanel(null);
        }
    }

    public void hideSuggestion() {

        if (ac.getSuggestionPanel() != null) { //la il ne le retrouve pas
            hide();
        } 
    }
    //move up in the list of words
    public void moveUp() {
        int index = Math.min(this.getList().getSelectedIndex() - 1, 0);
        selectIndex(index);

    }

    //move down in the list of words
    public void moveDown() {
        if (this.getList() != null) {
            int index = Math.min(this.getList().getSelectedIndex() + 1, this.getList().getModel().getSize() - 1);
            selectIndex(index);
        }
    }

    private void selectIndex(int index) { //?

        final int position = taFormula.getCaretPosition();
        this.getList().setSelectedIndex(index);
        this.getList().ensureIndexIsVisible(list.getSelectedIndex()); //ca doit etre fait en continu ?
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                taFormula.setCaretPosition(position);

            }
        ;
    }

    );
        }

    private void setFocusable(boolean b) {
        this.getPopupMenu().setVisible(true);
    }

}
