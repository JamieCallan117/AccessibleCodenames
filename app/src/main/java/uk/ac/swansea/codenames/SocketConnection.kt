package uk.ac.swansea.codenames

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI

/**
 * Connects the client to the server.
 */
object SocketConnection {
    @JvmField
    //The server host I use.
    var socket: Socket = IO.socket(URI.create("https://codenamesserver-340711.nw.r.appspot.com"))
}