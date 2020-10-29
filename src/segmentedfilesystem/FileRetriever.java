package segmentedfilesystem;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
		byte[] buf = new byte[1024];
		InetAddress address = InetAddress.getByName(server);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);

		HashMap<String,PacketStructure> packetChest = new HashMap<>();
		packet = new DatagramPacket(buf, buf.length);

		//generate array of numbers 0-9, create key, check if its already in the hashmap, if true remake key, check until key isn't in hashmap
		while(manager.allPackagesReceived(packet)){
			socket.receive(packet);
			PacketStructure copy = new PacketStructure(packet);
			manager.store(copy,packetChest);

		}
	}

}
