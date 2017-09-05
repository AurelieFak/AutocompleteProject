/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.util.List;

/**
 *
 * @author fakambi
 * 
 * This class describes an Item of the dictionary : function / constant
 */

public class SuggestionItem {
    
    //@NotNull
    private String name;
    //@NotNull
    private ItemType itemType;
    private int nbParameters;
    private int nbDoublons;
    private String description; //d'apres la doc
    //private List<String> parameters; // @todo change to Parameter
    private List<SuggestionParameter> parametersSplit;
    //icon

    /**
     * @return the functionName
     */
    public String getName() {
        return name;
    }

    /**
     * @return the itemType
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * @return the nbParameters
     */
    public int getNbParameters() {
        return nbParameters;
    }

    /**
     * @return the nbDoublons
     */
    public int getNbDoublons() {
        return nbDoublons;
    }

    /**
     * @return the parameters
     */
    /*public List<String> getParameters() {
        return parameters;
    }*/

    /**
     * @param functionName the functionName to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * @param nbParameters the nbParameters to set
     */
    public void setNbParameters(int nbParameters) {
        this.nbParameters = nbParameters;
    }

    /**
     * @param nbDoublons the nbDoublons to set
     */
    public void setNbDoublons(int nbDoublons) {
        this.nbDoublons = nbDoublons;
    }

    /**
     * @param parameters the parameters to set
     */
   /* public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }*/

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the parametersSplit
     */
    public List<SuggestionParameter> getParametersSplit() {
        return parametersSplit;
    }

    /**
     * @param parametersSplit the parametersSplit to set
     */
    public void setParametersSplit(List<SuggestionParameter> parametersSplit) {
        this.parametersSplit = parametersSplit;
    }
    

}
