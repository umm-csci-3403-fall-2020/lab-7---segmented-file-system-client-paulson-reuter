package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacketStructure {
    private byte status;
    private byte ID;
    private byte[] data;

    public HeaderPacketStructure(DatagramPacket packet){
        status = packet.getData()[0];
        ID = packet.getData()[1];
        data = Arrays.copyOfRange(packet.getData(),4,packet.getLength());
    }
}
