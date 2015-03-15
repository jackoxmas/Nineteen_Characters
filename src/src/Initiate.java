/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.ObjectOutputStream;
import java.net.Socket;
import src.RunController;

/**
 *
 * @author JohnReedLOL
 */
public class Initiate extends Thread {

    public final Socket socket_;
    private final ObjectOutputStream object_output_stream_;

    public Initiate(Socket s, ObjectOutputStream oos) {
        super("Initiate");
        socket_ = s;
        object_output_stream_ = oos;
        System.out.println("Initiate constractor.");
    }

    @Override
    public void run() {
        System.out.println("Initiate about to run.");
        try {
            System.out.println("Initiate run in try catch.");
            object_output_stream_.flush();
            System.out.println("Will write string to map.");
            object_output_stream_.writeObject(RunController.unique_id);
            System.out.println("Wrote string to map");
            object_output_stream_.flush();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
