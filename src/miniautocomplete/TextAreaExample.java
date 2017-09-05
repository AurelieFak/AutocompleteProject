/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniautocomplete;

/**
 *
 * @author fakambi
 */
    import javax.swing.*;  
    public class TextAreaExample  
    {  
         TextAreaExample(){  
            JFrame f= new JFrame();  
            JTextArea area=new JTextArea("Welcome to javatpoint");  
            area.setBounds(10,30, 500,500);  
            f.add(area);  
            f.setSize(500,500);  
            f.setLayout(null);  
            f.setVisible(true);  

            AutocompleteController autocompleteController = new AutocompleteController(area);
         }  
    public static void main(String args[])  
        {  
             TextAreaExample textAreaExample = new TextAreaExample();

        }}  
