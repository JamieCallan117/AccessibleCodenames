package uk.ac.swansea.codenames

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI

object SocketConnection {
    @JvmField
    //var socket: Socket = IO.socket(URI.create("https://codenamesserver-340711.nw.r.appspot.com"))
    var socket: Socket = IO.socket(URI.create("http://10.4.15.61:8080"))
}