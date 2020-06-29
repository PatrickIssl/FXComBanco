/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius Teider
 */
public class Log {

    
    public static void gravar(Class classe,  String texto){
        
        Date date = new Date();        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");        
        String nomeArquivo = dateFormat.format(date)+".log";
        
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");        
        String horario = dateFormat.format(date);
        
        FileWriter arquivo;
        
        try {
            arquivo = new FileWriter(nomeArquivo,true);
            
            PrintWriter escritor = new PrintWriter(arquivo);
        
            escritor.append(horario + " ------> " + classe.getName() + " ------- " + texto + "\n");

            arquivo.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
