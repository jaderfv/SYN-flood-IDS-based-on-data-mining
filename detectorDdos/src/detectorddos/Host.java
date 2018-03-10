
package detectorddos;

import java.util.ArrayList;


public class Host {
    String ip;
    int countConnectionZero;
    int countConnectionSmallerSix;

    ArrayList<Service> service = new ArrayList<>();
    
    public void countConnectionZero(){
        countConnectionZero++;
    }
    public int getCountConnectionZero(){
        return countConnectionZero;
    }
    public void countConnectionSmallerSix(){
        countConnectionSmallerSix++;
    }
    public int getcountConnectionSmallerSix(){
        return countConnectionSmallerSix;
    }
    public void setIp(String _ip){
        ip = _ip;
    }
    public String getIp(){
        return ip;
    }
    public void addServiceHost(Service _service){
        service.add(_service);
    }
    
    
    
    public void hostCountSrvCount(int _indexService){
        service.get(_indexService).countSrvCount();
    }
    public int getCountSrvCount(int _indexService){
        return service.get(_indexService).getCountSrvCount();
    }
    public void setPort(int _indexService, String _port){
        service.get(_indexService).setPort(_port); 
    }
    public String getPort(int _indexService){
        return service.get(_indexService).getPort();
    }
    public int sizeListServices(){
        return service.size();
    }
    public void addService(Service _service){
        service.add(_service);
    }
    
}
