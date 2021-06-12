package com.company;

import java.util.ArrayList;

public class CommandLineArgParser {
    private String[] hosts;
    private String[] ports;
    private int threadsCount;

    CommandLineArgParser(){
        hosts = null;
        ports = null;
        threadsCount = 1;
    }

    void parse(String[] args) {
        if(args.length<2 || args.length>3) {
            throw new IllegalArgumentException("Wrong format of arguments! The right format is " +
                    "\"-hosts=host1,host2... -ports=port1,port2-port3... -threads=1(not obligatory)\"");
        }
        if(args[0].startsWith("-hosts=") && args[0].length()>7){
            String unParsedHosts = args[0].substring(7);
            this.hosts = unParsedHosts.split(",");
        }
        else{
            throw new IllegalArgumentException("No hosts specified!");
        }
        if(args[1].startsWith("-ports=") && args[1].length()>7){
            String unParsedPorts = args[1].substring(7);
            this.ports = unParsedPorts.split(",");
        }
        else{
            throw new IllegalArgumentException("No ports specified!");
        }
        if(args.length==3){
            if(args[2].startsWith("-threads=") && args[2].length()>9){
                this.threadsCount = Integer.parseInt(args[2].substring(9));
            }
            else{
                throw new IllegalArgumentException("Te number of threads is not specified!");
            }
        }

    }

    String[] getHosts(){ return hosts; }
    String[] getPorts(){ return ports; }
    int getThreadsCount(){ return threadsCount; }
}
