package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerThread implements Runnable{
    private Socket client;
    private String serverMessage;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String clientMessage = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                //Gdx.app.log("SERVER", clientMessage);
                //client.getOutputStream().write("PONG\n".getBytes());
            } catch (IOException e) {
                Gdx.app.log("SERVER", "an error occured", e);
            }
        }
    }
}
