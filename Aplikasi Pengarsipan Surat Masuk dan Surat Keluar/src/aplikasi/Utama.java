/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aplikasi;
import aplikasi.Form_login;
import javax.swing.UIManager;

/**
 *
 * @author NUBE
 */
public class Utama {
    
    public static void main(String []args){
         try{
        UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        Form_login a=new Form_login();
        a.setVisible(true);
    }catch(Exception e){
      e.printStackTrace();
    }
    }
    
}
