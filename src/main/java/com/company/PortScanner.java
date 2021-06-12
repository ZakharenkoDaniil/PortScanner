package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PortScanner{

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PortScanner.class);

    private int PoolSize = 1;
    private int TimeOut = 100;
    private List<Result> results;

    void start(String[] hosts, String[] ports, int otherPoolSize, List<Result> results){
        logger.info("Start scaning...");

        PoolSize = otherPoolSize;
        ArrayList<Runnable> arrayOfThreads = new ArrayList<>();

        for(String host:hosts){
            for (String port:ports){
                if(port.contains("-")){
                    int port1 = Integer.parseInt(port.substring(0,port.indexOf("-")));
                    int port2 = Integer.parseInt(port.substring(port.lastIndexOf("-")+1,port.length()));
                    for (int i = port1; i<=port2; ++i){
                        arrayOfThreads.add(new ScanThread(host, String.valueOf(i), results));
                    }
                }
                else{
                    arrayOfThreads.add(new ScanThread(host, port, results));
                }
            }
        }
        Collections.shuffle(arrayOfThreads);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(arrayOfThreads);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(PoolSize, PoolSize, TimeOut, TimeUnit.MILLISECONDS, queue);
        executor.prestartAllCoreThreads();
        while(executor.getTaskCount()!=executor.getCompletedTaskCount()){

        }
        logger.info("Completed task count: "+executor.getCompletedTaskCount());
        executor.shutdown();
        this.results = results;
        logger.info("Scaning sucessfull!");
    }

    void saveAsJson(String filePath) throws IOException {
        File f = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        for(Result result : results)
        {
            mapper.writeValue(f, result);
        }
        System.out.println("json created!");
    }
}
