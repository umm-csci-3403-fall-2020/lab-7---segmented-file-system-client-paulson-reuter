package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class File {
    PacketStructure headerPacket;
    PacketStructure lastPacket;
    String filename;
    Integer numPackets;


    HashMap<Integer, PacketStructure> datapackets = new HashMap<>();

    public void write() {
        System.out.println("Writing file" + filename);

        ArrayList<Byte> outputBuffer = new ArrayList<>();

        // Add all datapackets to the outputBuffer
        for (int i = 0; i < numPackets; i++) {
            outputBuffer.addAll(datapackets.get(i).getPacketData());
        }

        // Create ByteArray to be able to later write the array to the FileOutputStream
        byte[] ByteArray = new byte[outputBuffer.size()];
        for (int i = 0; i < numPackets; i++) {
            ByteArray[i] = (byte) outputBuffer.get(i);
        }

        try {
            FileOutputStream file = new FileOutputStream(filename);
            file.write(ByteArray);
        } catch (IOException e) {
            System.err.println("Error attempting to write file" + filename);
            System.err.println(e);
        }
    }
    
    public void add(PacketStructure packet) {
        // packet.constructPacketNum();
        // datapackets.put(packet.,packet);
        // if (packet.lastPacket){
        //   numPackets = numPackets + 1;
        //   lastPacket = packet;
        // }
        if (fileComplete()){
          write();
        }
    }

    public boolean fileComplete() {
        if(numPackets == null){
        return false;
        }
        return numPackets.equals(datapackets.size()) && headerPacket != null;
    }
}
