package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.util.ArrayList;

public class Server implements Runnable {

    private String ip;
    private int port;
    private int id;
    private ArrayList<ServerThread> serverThreads;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        id = 0;
        serverThreads = new ArrayList<ServerThread>();
    }

    @Override
    public void run() {
        ServerSocketHints hints = new ServerSocketHints();
        ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, port, hints);
        // Wait for the next client connection
        while (true) {
            try {
                Socket client = server.accept(null);

                if(client.isConnected()) {
                    Gdx.app.log("SERVER", "Client connected");
                    serverThreads.add(new ServerThread(this, client, id++));
                    new Thread(serverThreads.get(serverThreads.size()-1)).start();
                }
            }catch (Exception e){
                Gdx.app.log("SERVER", "An error occured", e);
            }
        }
    }

    public synchronized void updateClients(int id){
        for(int i = 0; i < serverThreads.size(); i++){
            if(id != serverThreads.get(i).getId()){
                // Update everyone else but not the client that occured a change
            }
        }
    }

    public int getThreadSize() {
        return serverThreads.size();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
