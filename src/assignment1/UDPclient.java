package assignment1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPclient {

	DatagramSocket socket;
	DatagramPacket request;
	int udpPort;
	InetAddress host;

	public UDPclient(int udp) {
		socket = null;
		udpPort = udp;
	}

	public String getStatus() {
		String out = "";
		try {	
			String s = "status";
			byte[] a = s.getBytes();
			socket = new DatagramSocket();
			host = InetAddress.getByName("localhost");
			request = new DatagramPacket(a, s.length(), host, udpPort);
			
			socket.send(request);
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(reply);
			String status = new String(reply.getData());
			out = status.trim();
			
		} catch (IOException e) {
			out = "crash in client call";
			e.printStackTrace();
		} finally { 
			if (socket != null) {
//				System.out.println("socket closed in client: "+ udpPort);
				socket.close();
			}
		}
		return out;
	}

}
