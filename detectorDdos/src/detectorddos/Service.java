
package detectorddos;

public class Service {
    
    int srvCount;
    String port;
    
    public void countSrvCount(){
        srvCount++;
    }
    public int getCountSrvCount(){
        return srvCount;
    }
    public void setPort(String _port){
        port = _port;
    }
    public String getPort(){
        return port;
    }
    
}
