package com.company;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        CommandLineArgParser cp = new CommandLineArgParser();
        try{
            cp.parse(args);
        }
        catch(IllegalArgumentException ex){
            logger.error(ex.getMessage());
            logger.info("Main finished with problem with arguments");
            return;
        }

        String[] hosts = cp.getHosts();
        String[] ports = cp.getPorts();
        int threadsCount = cp.getThreadsCount();
        logger.info("Arguments parsing successful");

        List<Result> results = new LinkedList<Result>();
        PortScanner ps = new PortScanner();

        ps.start(hosts, ports, threadsCount, results);

        try {
            ps.saveAsJson("save.json");
        }catch(IOException e){
            logger.error(e.getMessage());
        }
        for(Result res : results){
            System.out.println(res);
        }
        logger.info("Main finished without problems");
    }
}
