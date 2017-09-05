/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import javax.swing.ImageIcon;

/**
 *
 * @author fakambi
 */
public class FunctionEntry {
    
   private String value;
   private ImageIcon icon;
   public boolean isAFunction;
  
   public FunctionEntry(String value/*, boolean isAFunction, ImageIcon icon*/) {
      this.value = value;
      this.isAFunction = isAFunction;
      //this.icon = icon;
   }
  
   public String getValue() { //Texte
      return value;
   }
  
   public ImageIcon getIcon() {
      return icon;
   }
  
    @Override
   public String toString() {
      return value;
   }
    
}
