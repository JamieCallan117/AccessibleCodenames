package uk.ac.swansea.codenames

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI

object SocketConnection {
    @JvmField
    //var socket: Socket = IO.socket(URI.create("http://192.168.1.124:8080")) //For home
    //var socket: Socket = IO.socket(URI.create("http://10.4.15.38:8080")) //For Uni accommodation
    var socket: Socket = IO.socket(URI.create("https://codenamesserver.appspot.com"))
}