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
        }
        if (isHeader(packet)) {
            headerPacket = packet;
            filename = new String(packet.getData(), 0, packet.getData().length);
            datapackets.put(Byte.toUnsignedInt(packet.getID()),packet);
        }
        else {
            datapackets.put(constructPacketNum(packet),packet);
        }

    }

    private boolean isHeader(PacketStructure packet){
        boolean header = false;
        if ((packet.getID() % 2) == 0) {
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
        if(numPackets == null){
        return false;
        }
        return numPackets.equals(datapackets.size()) && headerPacket != null;
    }

    private int constructPacketNum(PacketStructure packet){
        int x = Byte.toUnsignedInt(packet.getPnumber()[0]);
        int y = Byte.toUnsignedInt(packet.getPnumber()[1]);
        return x*10+y;
    }
}
