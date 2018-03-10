
package detectorddos;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Rules {
    int countTotal, countZero, sizeService, countServices, src_bytes, indexAlgorithm, indexRule;
    boolean returnAtack;
    ArrayList<Host> hosts;
    CsvController log;
    Ids time;

    public Rules(int _indexAlgorithm,int _indexRule, ArrayList<Host> _hosts ) {
        indexAlgorithm = _indexAlgorithm;
        indexRule = _indexRule;
        hosts = _hosts;
        log = new CsvController();
        time = new Ids();
    } 
    public boolean selectAlgorithmRule(){
        switch(indexAlgorithm){
            case 1:{             
                switch(indexRule){
                    case 0:{
                        returnAtack = ruleJ481();break;
                    }
                    case 1:{
                        returnAtack = ruleJ482();break;
                    }
                    case 2:{
                        returnAtack = ruleJ483();break;
                    }                    
                    default:{
                        JOptionPane.showMessageDialog(null,"No rule selected", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            }
            case 2:{
                switch(indexRule){
                    case 0:{
                        returnAtack = ruleRandomTree1();break;
                    }
                    case 1:{
                        returnAtack = ruleRandomTree2();break;
                    }
                    case 2:{
                        returnAtack = ruleRandomTree3();break;
                    }                    
                    default:{
                        JOptionPane.showMessageDialog(null,"No rule selected", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            }
            default:{
                JOptionPane.showMessageDialog(null,"No algorithm selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return returnAtack;
    }
    public boolean ruleJ481(){

        for(int x =0;x<hosts.size();x++){
          
           countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
           countZero = hosts.get(x).getCountConnectionZero();
           
           if (countTotal <= 54){
                if(countZero > 2 && countZero <= 18){
                    for(int v=0;v<hosts.get(x).sizeListServices(); v++){
                        if(hosts.get(x).getCountSrvCount(v)<= 12){        
                            log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                            return true;
                        }
                    }
                }
           }
        }    
        return false;       
    } 
    public boolean ruleJ482(){
        
        for(int x =0;x<hosts.size();x++){
           countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
           countZero = hosts.get(x).getCountConnectionZero();
           
            if (countTotal <= 54){
                if(countZero > 18){
                    for(int v=0;v<hosts.get(x).sizeListServices(); v++){
                        if(hosts.get(x).getCountSrvCount(v) <= 18){
                            log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                            return true;
                        }

                    }
                }
            }
        }
        return false;  
    }
    public boolean ruleJ483(){
        
        for(int x =0;x<hosts.size();x++){
            countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
            countZero = hosts.get(x).getCountConnectionZero();
            if (countTotal > 54){
                log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                return true;
            }
        }
        return false;
    }
    public boolean ruleRandomTree1(){
        
        for(int x =0;x<hosts.size();x++){
            countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
            countZero = hosts.get(x).getCountConnectionZero();
            
            if(countTotal < 54.5){
                if(countZero >= 6.5){
                    for(int v=0;v<hosts.get(x).sizeListServices(); v++){
                        if(hosts.get(x).getCountSrvCount(v)  >= 3.5 && hosts.get(x).getCountSrvCount(v) < 6.5){
                            log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                            return true;
                        }
                    }
                }
            }   
        }
        return false;
    }
    public boolean ruleRandomTree2(){

        for(int x =0;x<hosts.size();x++){
            countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
            countZero = hosts.get(x).getCountConnectionZero();
            
            if(countTotal < 54.5){
                if(countZero >= 11.5){
                    for(int v=0;v<hosts.get(x).sizeListServices(); v++){
                           if(hosts.get(x).getCountSrvCount(v) >= 6.5 && hosts.get(x).getCountSrvCount(v) < 12.5){
                               log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                               return true;
                           }
                    }
                }
            }
        }
        return false;
    }
    public boolean ruleRandomTree3(){
        
        for(int x =0;x<hosts.size();x++){
            countTotal = hosts.get(x).getCountConnectionZero()+hosts.get(x).getcountConnectionSmallerSix();
            countZero = hosts.get(x).getCountConnectionZero();
            if (countTotal >= 54.5){
                log.gerarLog(time.getHora(1)+" "+time.getHora(0)+" "+hosts.get(x).getIp()+" "+countTotal+" "+"Attack Neptune detected");
                return true;
            }
        }
        return false;
    }
}
