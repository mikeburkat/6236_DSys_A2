package assignment1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * This class accepts requests for status, and it sends the status of
 * its GameServer back to the requesting client.
 * 
 * @author Mike
 */
public class UDPserver implements Runnable {

	DatagramSocket socket;
	GameServer gameServer;
	int UDPserverPort;

	public UDPserver(GameServer gs, int udp) {
		gameServer = gs;
		UDPserverPort = udp;
	}

	@Override
	public void run() {
		try {

			byte[] buffer = new byte[1000];
			socket = new DatagramSocket(UDPserverPort);
			System.out.println("UDP server is up on port: " + UDPserverPort);

			while (true) {
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(request);

				String s = gameServer.getPlayerStatusString();
				byte[] b = s.getBytes();

				DatagramPacket reply = new DatagramPacket(b, b.length,
						request.getAddress(), request.getPort());
				socket.send(reply);

			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
			System.out.println("crash in server");
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

	}
}
