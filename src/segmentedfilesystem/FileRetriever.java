package segmentedfilesystem;
import java.net.*;
import java.io.*;

public class FileRetriever {
	private String server;
	private int port;

	PacketManager manager = new PacketManager();
	public FileRetriever(String s, int p) {
		server = s;
		port = p;
	}

	public void downloadFiles() throws IOException {

		DatagramSocket socket = new DatagramSocket();
		byte[] buf = new byte[1028];
		InetAddress address = InetAddress.getByName(server);
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address,port);
		socket.send(packet);

		packet = new DatagramPacket(buf, buf.length);

		boolean loop = true;
		while(loop){
			socket.receive(packet);
			PacketStructure copy = new PacketStructure(packet);
			manager.store(copy);

			if(manager.allPackagesReceived(copy)){
				loop = false;
			}
		}
	}

}
