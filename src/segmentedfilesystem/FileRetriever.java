package segmentedfilesystem;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class FileRetriever {
	private String server;
	private int port;

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

		ArrayList<PacketStructure> file1 = new ArrayList<>();
		ArrayList<PacketStructure> file2 = new ArrayList<>();
		ArrayList<PacketStructure> file3 = new ArrayList<>();
		packet = new DatagramPacket(buf, buf.length);
		while(!allPackagesRecieved(socket.receive(packet))){
			PacketStructure copy = new PacketStructure(packet);
			sortFileID(copy,file1,file2,file3);
		}
	}

}
