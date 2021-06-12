package com.company;

import java.io.IOException;
import java.util.List;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;


public class ScanThread implements Runnable{
    private List<Result> results;
    private String host;
    private String port;

    /**
     * Construcnor for creating a separate thread
     * @param host
     * @param port
     * @param results
     */

    public ScanThread(String host, String port, List<Result> results){
        this.host = host;
        this.port = port;
        this.results = results;
    }

    public void run(){
        boolean status = false;
        try(Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(host, Integer.parseInt(port)), 100);
            status = true;
        } catch (IOException ex){
           status = false;
        } finally {
            //System.out.println(this.host+":"+this.port+" ["+status+"]");
            this.results.add(new Result(this.host,this.port, status));
        }
    }
}
