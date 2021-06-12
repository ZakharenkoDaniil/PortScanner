package com.company;

public class Result {
    private String host;
    private String port;
    private boolean status;

    public Result(String host, String port, boolean status){
        this.host = host;
        this.port = port;
        this.status = status;
    }

    public String getHost() {
        return host;
    }
    public String getPort(){
        return port;
    }
    public boolean getStatus(){
        return status;
    }

    public void setPort(String port){
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    @Override
    public String toString() {
        return new String(this.host+":"+this.port+" ["+status+"]");
    }
}
