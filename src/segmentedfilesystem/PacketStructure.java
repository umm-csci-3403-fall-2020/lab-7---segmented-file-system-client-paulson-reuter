package segmentedfilesystem;
import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketStructure {
    private byte status;
    private byte ID;
    private byte[] pnumber;
    private byte[] data;
    
    PacketManager packetManager = new PacketManager();
    
    public PacketStructure(DatagramPacket packet){
        status = packet.getData()[0];
        ID = packet.getData()[1];
        int length = packet.getLength();
        if(packetManager.determineType(packet)) {
            pnumber[0] = packet.getData()[2];
            pnumber[1] = packet.getData()[3];
            data = Arrays.copyOfRange(packet.getData(), 4, length);
        }
        else{
            data = Arrays.copyOfRange(packet.getData(),2,length);
        }
    }

    public Byte getStatus (Byte packet) {
        this.status = packet;
        return status;
    }
    
    public Byte getFileID (Byte packet) {
        this.ID = packet;
        return ID;
    }
}
