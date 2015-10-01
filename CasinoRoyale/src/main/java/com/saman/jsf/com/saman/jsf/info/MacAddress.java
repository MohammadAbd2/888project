package com.saman.jsf.com.saman.jsf.info;

/**
 * Created by Saman on 5/19/2015.
 */
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
public class MacAddress {
    public String getMacaddr() {
        return Macaddr;
    }

    public void setMacaddr(String macaddr) {
        Macaddr = macaddr;
    }

    public String getIpaddr() {
        return Ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        Ipaddr = ipaddr;
    }

    private String Macaddr;
    private String Ipaddr;
  //  public static void main(String[] args){
public void getMac(){
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            setIpaddr(ip.getHostAddress());
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            setMacaddr(sb.toString());
            System.out.println(sb.toString());

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e){

            e.printStackTrace();

        }

   }
}
