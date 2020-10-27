package segmentedfilesystem;
import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacketStructure {
    private byte status;
    private byte ID;
    private byte[] pnumber;
    private byte[] data;

    public DataPacketStructure(DatagramPacket packet){
        status = packet.getData()[0];
        ID = packet.getData()[1];
        pnumber[0] = packet.getData()[2];
        pnumber[1] = packet.getData()[3];
        int length = packet.getLength();
        data = Arrays.copyOfRange(packet.getData(),4, length);
    }
}
