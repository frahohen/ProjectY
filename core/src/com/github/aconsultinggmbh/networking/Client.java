package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;

public class Client implements Runnable {
    private int id;
    private String ip;
    private int port;

    public Client(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, hints);

        String clientMessage = "I am Client " + id + "\n";
        while (true) {
            try {
                client.getOutputStream().write(clientMessage.getBytes());
            } catch (IOException e) {
                Gdx.app.log("CLIENT", "An error occured", e);
            }
        }

    }
}
