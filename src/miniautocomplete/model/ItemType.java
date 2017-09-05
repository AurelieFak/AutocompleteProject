/*
 * Copyright (c) 2017 BoxArr Ltd. All Rights Reserved. http://boxarr.com/
 */
package miniautocomplete;

import javax.swing.ImageIcon;

/**
 *
 * @author fakambi
 */
public enum ItemType {

    //Each type is matched to an image
    Function(new ImageIcon("/home/fakambi/Downloads/function.png")),
    Constant(new ImageIcon("/home/fakambi/Downloads/c.jpeg"));
 
   //private Map<ItemType,ImageIcon> typeToIcon;
   private ImageIcon icon;
   
   private ItemType(ImageIcon icon){
       setIcon(icon);
   }

    /**
     * @return the icon
     */
   public ImageIcon getIcon() {
       return icon;
   }

    /**
     * @param icon the icon to set
     */
    public void setIcon(ImageIcon icon) {
       this.icon = icon;
    }
}
