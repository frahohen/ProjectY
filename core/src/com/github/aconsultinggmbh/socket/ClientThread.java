package com.github.aconsultinggmbh.socket;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientThread implements Runnable{
    protected Socket socket;
    private String message;

    public ClientThread(Socket socket) {
        this.socket = socket;
        Gdx.app.log("DEBUG","SERVER: Client Thread created!");
    }

    @Override
    public void run() {
        Gdx.app.log("DEBUG","SERVER: Streams created");

        try {
            while(true){
                Gdx.app.log("DEBUG","SERVER: Execute Loop on Socket: " + socket.getRemoteAddress());
                message = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                Gdx.app.log("DEBUG", "SERVER: Line send from Client: " + message);
                //outputStream.writeBytes(line);
                //outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
