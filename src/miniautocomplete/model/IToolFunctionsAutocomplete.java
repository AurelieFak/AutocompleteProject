/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import java.util.List;

/**
 *
 * @author fakambi
 */
public interface IToolFunctionsAutocomplete {
    
    public String[] addParentheses(String[] data);
    public String addParameters(int i, List<SuggestionItem> listItems);
    public String[] addParameters(String[] data);
    public int subParentheses(String word);
    
}
