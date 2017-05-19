package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.github.aconsultinggmbh.networking.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Client implements Runnable {
    private String ip;
    private int port;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, hints);

        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());

            String message = (String) objectInputStream.readObject();
            // erstelle den Spieler mit dem String in message
            //....
            ArrayList<String> list = (ArrayList<String>) objectInputStream.readObject();
            // erstelle die Gameobjekte mit den Namen in der Arraylist
            //....
            //while (true) {

                list = (ArrayList<String>) objectInputStream.readObject();
                // erstelle die neuen Gameobjekte (Gegner) falls vorhanden
                //....
            //}
        } catch (IOException e) {
            Gdx.app.log("CLIENT", "An error occured", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
