package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class File {
    PacketStructure headerPacket;
    PacketStructure lastPacket;
    String filename = "Empty";
    Integer numPackets = -1;
    int fileID;
    HashMap<Integer, PacketStructure> datapackets = new HashMap<>();

    public void add(PacketStructure packet) {
        //checks to update variables
        if(datapackets.isEmpty()) {
            fileID = Byte.toUnsignedInt(packet.getID());
        }

        if (isLastPacket(packet)) {
            lastPacket = packet;
            numPackets = constructPacketNum(packet) + 1;
	    datapackets.put(constructPacketNum(packet),packet);
        }
        else if (isHeader(packet)) {
            headerPacket = packet;
            filename = new String(packet.getData(),2,packet.getLength()-2);
        }
        else {
            datapackets.put(constructPacketNum(packet),packet);
        }

    }

    private boolean isHeader(PacketStructure packet){
        boolean header = false;
        if ((packet.getStatus() % 2) == 0) {
            header = true;
        }
        return header;
    }

    private boolean isLastPacket(PacketStructure packet){
        boolean last = false;
        if((packet.getStatus() % 4) == 3){
            last = true;
        }
        return last;
    }

    public boolean fileComplete() {
    	boolean complete = false;
        if(numPackets == datapackets.size()){
	complete = true;
	}
	return complete;
    }

    private int constructPacketNum(PacketStructure packet){
        int x = Byte.toUnsignedInt(packet.getPnumber()[0]);
        int y = Byte.toUnsignedInt(packet.getPnumber()[1]);
        return x*256+y;
    }
}
