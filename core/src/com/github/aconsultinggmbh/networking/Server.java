package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class Server implements Runnable {
    @Override
    public void run() {
        ServerSocketHints hints = new ServerSocketHints();
        ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, 9999, hints);
        // wait for the next client connection
        while (true) {
            Socket client = server.accept(null);
            // read message and send it back
            Gdx.app.log("SERVER","Client connected");
            new Thread(new ServerThread(client)).start();
        }
    }
}
