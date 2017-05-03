package com.github.aconsultinggmbh.socket;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;

public class Client implements Runnable{

    private static int id = 0;

    public Client() {
        id++;
    }

    @Override
    public void run() {
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 4000; //4 Sec time to connect
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 10000 ,socketHints);

        Gdx.app.log("DEBUG","CLIENT: Client on " + socket.getRemoteAddress() + " created and connected ");
        try {
            while (true) {
                if(socket.isConnected()) {
                    Gdx.app.log("DEBUG","CLIENT: Client send something " + id);
                    socket.getOutputStream().write("Hello".getBytes()); //if connected, send message
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
