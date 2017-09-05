/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author fakambi
 */
public class FunctionEntryCellRenderer extends JLabel implements ListCellRenderer {

    private JLabel label;
    List<SuggestionItem> listItems = new ArrayList<>();
    ISuggestionProvider sp = new ReadFunctionsCSV2();
  
    public FunctionEntryCellRenderer(){
        listItems = sp.getSuggestions();
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected,
            boolean cellHasFocus) {

        FunctionEntry entry = (FunctionEntry) value;
        //get the icon according to the SuggestionItem type
     
        String originalWord = ToolFunctionsAutocomplete.wordWithoutBracket(entry.toString());
           for (int j = 0; j < listItems.size(); j++) {

                    if (entry.toString().equals(listItems.get(j).getName()) && listItems.get(j).getItemType() == ItemType.Constant) {
                         setIcon(listItems.get(j).getItemType().getIcon());
    
                    } else if (originalWord.equals(listItems.get(j).getName()) && listItems.get(j).getItemType() == ItemType.Function) {
                        setIcon(listItems.get(j).getItemType().getIcon());
                    }
                }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setText(value.toString());

        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);

        return this;
    }

}
