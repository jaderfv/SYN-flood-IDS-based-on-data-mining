package detectorddos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Ids {   
    //---------Host em que está rodando o IDS-----------------
    String localhost;
    //--------Atributos detecção Neptune----------------------
    int countZero, countSmallerSix, countTotal, src_bytes;
    int count, srv_count;
    //ArrayList<Service> service = new ArrayList<>();
    ArrayList<Host> host = new ArrayList<>();
    int indexIp;
    int indexPort;
    int qtdPackets;
    int flagLocalHost;
    //--------------------------------------------------------    
    public void openPackets(int _indexAlgorithm, int _indexRules, ArrayList<Packet> _packets, String _localhost){
        qtdPackets = 0;
        countZero = 0;
        countSmallerSix = 0;
        flagLocalHost = 0;
        qtdPackets = _packets.size();
        localhost = _localhost;
           
            for(int x=0;x< qtdPackets;x++){

                if(_packets.get(x).getIpDst().equals(localhost)){
                    flagLocalHost = 1;
                    if(existsHost(_packets.get(x).getIpSrc()) == true){

                        if(_packets.get(x).getMsg().equals("zero")){
                            host.get(indexIp).countConnectionZero();
                        }else{
                            host.get(indexIp).countConnectionSmallerSix();
                        }

                        if(existsService(_packets.get(x).getPort()) == true){
                            host.get(indexIp).hostCountSrvCount(indexPort);
                        }else{
                            Service serv = new Service();
                            serv.setPort(_packets.get(x).getPort());
                            serv.countSrvCount();
                            host.get(indexIp).addService(serv);
                        }

                }
                else{
                    Host _host = new Host();
                    _host.setIp(_packets.get(x).getIpSrc());
                    
                    if(_packets.get(x).getMsg().equals("zero")){
                        _host.countConnectionZero();
                    }else{
                        _host.countConnectionSmallerSix();
                    
                    }
                    Service serv = new Service();
                    serv.setPort(_packets.get(x).getPort());
                    serv.countSrvCount();
                    _host.addService(serv);
                    host.add(_host);

                }
            }

        }
        
        Rules rules = new Rules(_indexAlgorithm, _indexRules, host);
        if(rules.selectAlgorithmRule() == true){
            System.out.println("Intervalo de monitoramento:");
            System.out.println(getHora(1));
            System.out.println(getHora(0));
            System.err.println("Ataque Neptune detectado");
            System.err.println("\n");
        }
        else if(rules.selectAlgorithmRule() == false && flagLocalHost == 1){
            System.out.println("Intervalo de monitoramento:");
            System.out.println(getHora(1));
            System.out.println(getHora(0));
            System.out.println("Tráfego Normal");
            System.out.println("\n");
        }
    }    
    
    public boolean existsService(String _port){
        for(int y=0;y< host.get(indexIp).sizeListServices();y++){
            if(host.get(indexIp).getPort(y).equals(_port)){
                indexPort = y;
                return true;
            }
        }
        return false;
    }
    public boolean existsHost(String _ip){
        for(int y=0;y< host.size();y++){
            if( host.get(y).getIp().equals(_ip)){
                indexIp = y;
                return true;
            }
        }
        return false;
    }
    public String getHora(int _flag) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        //System.out.println(sdf.format(gc.getTime()));
        if(_flag == 1){
            gc.add(Calendar.SECOND,-2);
        }
        return sdf.format(gc.getTime());
    }
}
