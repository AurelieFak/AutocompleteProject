/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniautocomplete;

import java.util.List;

/**
 *
 * @author fakambi
 */
public interface ISuggestionProvider {
    
    public List<SuggestionItem> getSuggestions();
    public List<SuggestionItem> getSuggestions(String fileName);
}
