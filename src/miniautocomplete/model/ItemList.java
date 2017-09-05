/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ToolTipManager;

/**
 *
 * @author fakambi
 * Model
 */
public class ItemList extends JList<FunctionEntry> {

    //public JToolTip tooltip = new JToolTip();
   final int dismissDelayMinutes = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    public ItemList() {

    }

    //Initialiser une fois pour toute
    /*ReadFunctionsCSV2 rf= new ReadFunctionsCSV2(); 
    List<Item> listItems = rf.getParam();*/
    public ItemList(DefaultListModel listModel) {
        super(listModel);
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setDismissDelay(dismissDelayMinutes);
        listItems = suggestionProvider.getSuggestions(); //lu une seule fois ?
    }

    /* @Override
    public JToolTip createToolTip() 
    {
        JToolTip tooltip = new JToolTip();
        tooltip.
        return tooltip;
    
    }*/
    /**
     * Function to restructure/manipulate a tip in case its too long to be
     * displayed entirely -> Mutltiline tooltip
     *
     * @param tip
     * @return
     */
    public String resizeTip(String tip) {
        String resizedTip = "";

        int length = tip.toCharArray().length; //nb Caracteres dans la description
        int nbCharactersPerLine = 70; // Nb characters per tooltip line
        int nbLoops = length / nbCharactersPerLine;
        int nbCharactersLeft = length;
        int start = 0;
        resizedTip += "<html>";
        //pour les tres longs textes de description;
        if (nbLoops > 0) {
            for (int i = 0; i < nbLoops; i++) {
                resizedTip += tip.substring(i * nbCharactersPerLine, (i + 1) * nbCharactersPerLine) + "<br>";
            }
            //pour les caracteres restants
            resizedTip += tip.substring(nbLoops * nbCharactersPerLine, length);

        } else { //it'a small description

            resizedTip += tip;
        }

        resizedTip += "</html>";

        return resizedTip;
    }

    private ISuggestionProvider suggestionProvider = new ReadFunctionsCSV2(); 
    private List<SuggestionItem> listItems;
    public void setSuggestionProvider(ISuggestionProvider suggestionProvider) {
        this.suggestionProvider = suggestionProvider;
    }
    
    
    //si je fais list.getToolTipText(Mouse event) ca ne marche pas donc je ne peux pas l'appeler avec une fonction en parametre
    @Override
    public String getToolTipText(MouseEvent event) {
        //List<SuggestionItem> listItems = suggestionProvider.getSuggestions();
        Point p = event.getPoint();
        int location = locationToIndex(p);
        FunctionEntry fe = null;
        Object element = getModel().getElementAt(location);
        if (element instanceof FunctionEntry) {
            fe = (FunctionEntry) element;
        }

        String tip = "";
        String originalWord = ToolFunctionsAutocomplete.wordWithoutBracket(fe.getValue());

        for (int i = 0; i < listItems.size(); i++) {
            
            //pour les constantes car seule la premiere ligne contient la description   
            //pour les constantes no parenthese donc fe.getValue = au meme nom dans la liste d'origine
            if (fe.getValue().equals(listItems.get(i).getName()) && listItems.get(i).getDescription()!=null ) {
                tip = listItems.get(i).getDescription(); // il peut y avoir AOBE
                break;
            }

            //function without doublons et +
            if (originalWord.equalsIgnoreCase(listItems.get(i).getName())
                    && listItems.get(i).getNbParameters() == 0) {
                tip = listItems.get(i).getDescription(); // il peut y avoir AOBE
                break;
            }


            //combinaison "unique" :  nom + nbParameters, utilse pour les doublons et +
            else  if (originalWord.equalsIgnoreCase(listItems.get(i).getName())
                    && (ToolFunctionsAutocomplete.nbComas(fe.getValue()) + 1 == listItems.get(i).getNbParameters())
                    && listItems.get(i).getNbParameters()>0) {
                tip = listItems.get(i).getDescription();

            }
        }
        tip = resizeTip(tip);
        return tip;
    }
    
    /*
label.addMouseListener(new MouseAdapter() {
    final int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
    final int dismissDelayMinutes = (int) TimeUnit.MINUTES.toMillis(10); // 10 minutes
    @Override
    public void mouseEntered(MouseEvent me) {
        ToolTipManager.sharedInstance().setDismissDelay(dismissDelayMinutes);
    }
 
    @Override
    public void mouseExited(MouseEvent me) {
        ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);
    }
});*/

}
