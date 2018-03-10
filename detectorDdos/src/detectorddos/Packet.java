
package detectorddos;

public class Packet {
    
private String port,msg,ipSrc, ipDst, protocol;


    public void setPort(String _port){
        port = _port;     
    }
    public String getPort(){
        return port;
    }
    public void setMsg(String _msg){
        _msg = _msg.replace("\"","");
        msg = _msg;
    }
    public String getMsg(){
        return msg;
    }
    
    public void setIpSrc(String _ipSrc){
        ipSrc = _ipSrc;
    }
    public String getIpSrc(){
        return ipSrc;
    }
    public void setIpDst(String _ipDst){
        ipDst = _ipDst;
    }
    public String getIpDst(){
        return ipDst;
    }    
    public void setProtocol(String _protocol){
        protocol = _protocol;
    }

}
