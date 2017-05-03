package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;

public class Client implements Runnable {
    private int id;

    public Client(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 9999, hints);

        String clientMessage = "I am Client " + id + "\n";
        while (true) {
            try {
                client.getOutputStream().write(clientMessage.getBytes());
                //String serverMessage = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                //Gdx.app.log("CLIENT", serverMessage);
            } catch (IOException e) {
                Gdx.app.log("CLIENT", "an error occured", e);
            }
        }
    }
}
