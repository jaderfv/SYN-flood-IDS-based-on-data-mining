package detectorddos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvController {
    
String arquivoCSV = "/var/log/snort/alerts.csv";
String arquivoLog = "/var/log/snort/logAtaque";
BufferedReader br = null;
    
public ArrayList<String> readCsv(){
    
    String line = "";
    ArrayList<String> lines= new ArrayList<String>();

    try {
        
        Thread.sleep(2000);
        br = new BufferedReader(new FileReader(arquivoCSV));
      
        while ((line = br.readLine()) != null) {
                lines.add(line);
        }        
        
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo não encontrado");
    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException ex) {
        Logger.getLogger(CsvController.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (br != null) {
            try {
                br.close();
                eraseCsv();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return lines;
}
public void eraseCsv() {

    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoCSV));
        bw.flush();
    } catch (IOException ex) {
        Logger.getLogger(CsvController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void eraseLog() {

    try {
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(arquivoLog));
        bw2.flush();
    } catch (IOException ex) {
        Logger.getLogger(CsvController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public void gerarLog(String log){

    try {
            BufferedWriter out = new BufferedWriter(new FileWriter(arquivoLog, true));
            out.write(log);
            out.newLine();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

}

public void alteraArquivoConf(String _ipMonitorado, int algorithmIndex){
    BufferedWriter writer = null;
    try {
        String arquivo = "/etc/snort/snort.conf";
        String arquivoTmp = "/etc/snort/snort-tmp.conf";
        writer = new BufferedWriter(new FileWriter(arquivoTmp));
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.contains("ipvar HOME_NET")) {
                linha = linha.replace(linha, "ipvar HOME_NET "+_ipMonitorado+"/24");
            }
            
            if (linha.contains("include $RULE_PATH/j48.rules") && algorithmIndex == 2) {
                linha = linha.replace(linha,"include $RULE_PATH/randomtree.rules");
            }else if(linha.contains("include $RULE_PATH/randomTree.rules") && algorithmIndex == 1){
                linha = linha.replace(linha,"include $RULE_PATH/j48.rules");
            }
            writer.write(linha + "\n");
        }   writer.close();
        reader.close();
        new File(arquivo).delete();
        new File(arquivoTmp).renameTo(new File(arquivo));
    } catch (IOException ex) {
        Logger.getLogger(CsvController.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CsvController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



}
