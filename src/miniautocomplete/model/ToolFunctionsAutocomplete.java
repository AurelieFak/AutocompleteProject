/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fakambi
 *
 * Functions to manipulate the words resulting from the tries and make them
 * displayable in the suggestionpanel
 */
public class ToolFunctionsAutocomplete /* implements IToolFunctionsAutocomplete */ {

    //@Override
    public static String[] addParentheses(String[] data) { //renvoie une liste de mots avec des parentheses en + , pour les fonctions mathematiques //liste de tous les mots
        // mettre des parentheses a tous ceux qui sont dans la liste de suggestions
        String[] list = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            list[i] = data[i] + "()";
        }

        return list;
    }

    //@Override
    public static String addParameters(int i, List<SuggestionItem> listItems) { //i represents a line in the list of all items

        String chaine = "";
        chaine += "(";
        int nbParameters = -1;
        nbParameters = listItems.get(i).getNbParameters();
        int nbDoublons = 0; //3eme colonne

        for (int k = 0; k < nbParameters; k++) {
            if (k == nbParameters - 1) //no coma after the last parameter
            {
                chaine += listItems.get(i).getParametersSplit().get(k).getType() + " " + listItems.get(i).getParametersSplit().get(k).getName();
            } else {
                chaine += listItems.get(i).getParametersSplit().get(k).getType() + " " + listItems.get(i).getParametersSplit().get(k).getName() + ",";
            }
        }

        chaine += ")";
        return chaine;
    }

    //@Override
    /**
     * Detects an opening bracket (this function is used by the
     * wordWithoutBracket function)
     *
     * @param word
     * @return the position of an opening bracket
     */
    public static int positionOpeningBracket(String word) {

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == '(') {
                return i;
            }
        }
        return 0;

    }

    /**
     * From a FunctionEntry value (word+parametres) to the original word,
     * extracts the originalWord ex : add(x1,x2,x3,..) -> add
     *
     * @param word
     * @return
     */
    public static String wordWithoutBracket(String word) {
        return word.substring(0, positionOpeningBracket(word));
    }

    /**
     * This function adds parameters (or not) to the words resulting from the
     * trie
     *
     * @param data
     * @return a String array : items names + the parameters
     */
    public static String[] addParameters(String[] data, List<SuggestionItem> listItems) {

        ArrayList<String> myList = new ArrayList<String>();
        int nbDoublons = 0;

        if (data != null) {
            //i definit le mot de data   
            //j me permet de parcourir la liste originale avec les fonctions differentes
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < listItems.size(); j++) {//faire correspondre la fonction et le parametre

                    if (data[i].equals(listItems.get(j).getName())
                            && listItems.get(j).getNbDoublons() == 0
                            && listItems.get(j).getItemType() == ItemType.Function) //c'est peut-etre trop couteux
                    {
                        myList.add(i, data[i] + ToolFunctionsAutocomplete.addParameters(j, listItems));
                    } else if (data[i].equals(listItems.get(j).getName()) //si le mot a son equivalent et a besoin d'un double
                            && listItems.get(j).getNbDoublons() > 0
                            && listItems.get(j).getItemType() == ItemType.Function) //on verifie qu'il y a un doublon en parcourant les parametres iligne, j colonne
                    {
                        nbDoublons = listItems.get(j).getNbDoublons();
                        myList.add(i, data[i] + ToolFunctionsAutocomplete.addParameters(j, listItems));

                    } else if (data[i].equals(listItems.get(j).getName())
                            && listItems.get(j).getItemType() == ItemType.Constant) //it's a constant
                    {
                        myList.add(i, data[i]); //no bracket for a constant
                    }
                }
            }

            String[] list = new String[myList.size()];
            for (int i = 0; i < myList.size(); i++) {
                list[i] = myList.get(i);
            }

            return list;

        } else {
            return null;
        }
    }

    //public boolean searchItemInList
    public static int nbComas(String entry) {
        int nbComas = 0;

        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == ',') {
                nbComas++;
            }
        }
        return nbComas;
    }

    //tout se passe dans insertSelection 
    //Shrink to the parametername
    public static String onlySelectFunctionName() {

        String param = " ";

        return param;
    }

    public static String toUsableArguments(String selectedValue) {

        //resize the parameters to usuable arguments 
        //le nom de la fonction se trouve avant la parenthse ouvrante
        //ONLY S' IL Y A DES PARAMETRES, placer ailleurs
        //A GERER
        //- 1 parametre OK
        //- constante
        String chaine = "";

        if (selectedValue.contains(" ")) { //implicitement ca veut dire qu'il y aura des parametres
            String getName[] = selectedValue.split("\\(");

            chaine += getName[0];
            chaine += "(";

            String listParams;
            listParams = getName[1].substring(0, getName[1].length() - 1);// delete the last bracket
            String[] param = listParams.split(",");
            int paramLength = param.length;
            String[] secondSplit;
            for (int i = 0; i < paramLength; i++) {
                secondSplit = param[i].split(" ");
                if (i == paramLength - 1) { //the last param doesn't need a bracket
                    chaine += secondSplit[1];
                } else {
                    chaine += secondSplit[1] + ",";
                }
            }
            chaine += ")";
        } else {
            chaine += selectedValue;
        }

        return chaine;
    }
    
    //Not take space into account for the trie pour que l'ordre alphabetique soit calcule
    //en gros il faut donner le mot sans espace au trie mais retablir l'espace pour le nom de la fonction en elle-meme
    //plus tard, au cas ou l'autocomplete serait utilise pour d'autres trucs
    //String wordWithoutSpace="";
    //if(subWord.contains(" "))
        //delete l'espace
}
