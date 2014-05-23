package assignment1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPclient {

	DatagramSocket socket;
	DatagramPacket request;
	int udpPort;
	InetAddress host;

	public UDPclient(int udp) {
		socket = null;
		udpPort = udp;
//		try {
//			String s = "status";
//			byte[] a = s.getBytes();
//			socket = new DatagramSocket();
//			host = InetAddress.getByName("localhost");
//			request = new DatagramPacket(a, s.length(), host, udpPort);
//
//		} catch (SocketException e) {
//			System.out.println("Socket: " + e.getMessage());
//		} catch (IOException e) {
//			System.out.println("IO: " + e.getMessage());
//		} finally {
//			if (socket != null)
//				socket.close();
//		}
	}

	public String getStatus() {
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
			status = status.trim();
			System.out.println("Reply: " + status);
			return status;
		} catch (IOException e) {
			System.out.println("crash in client call");
			e.printStackTrace();
		} finally { 
			if (socket != null) {
				System.out.println("socket closed in client: "+ udpPort);
				socket.close();
			}
		}
		return "failed";

	}

}
