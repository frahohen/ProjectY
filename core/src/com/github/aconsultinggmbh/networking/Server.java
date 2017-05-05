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
        // wait for the next client connection
        while (true) {
            try {
                Socket client = server.accept(null);

                // read message and send it back
                if(client.isConnected()) {
                    Gdx.app.log("SERVER", "Client connected");
                    serverThreads.add(new ServerThread(client, id++));
                    new Thread(serverThreads.get(serverThreads.size()-1)).start();
                }
            }catch (Exception e){
                Gdx.app.log("SERVER", "An error occured", e);
            }
        }
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