package uk.ac.swansea.codenames;

import java.net.URI;

import io.socket.client.IO;
import io.socket.client.Socket;

public class socketConnection {
    public static Socket socket = IO.socket(URI.create("http://192.168.1.124:3000"));;

    public socketConnection() {
    }
}
