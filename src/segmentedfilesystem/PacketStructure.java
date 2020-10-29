package segmentedfilesystem;
import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketStructure {
    private byte status;
    private byte ID;
    private byte[] pnumber;
    private byte[] data;

    PacketManager manager = new PacketManager();
    public PacketStructure(DatagramPacket packet){
        status = packet.getData()[0];
        ID = packet.getData()[1];
        int length = packet.getLength();
        if(manager.determineType(packet)) {
            data = Arrays.copyOfRange(packet.getData(),2,length);
        }
        else{
            pnumber[0] = packet.getData()[2];
            pnumber[1] = packet.getData()[3];
            data = Arrays.copyOfRange(packet.getData(), 4, length);
        }
    }

    public byte getStatus(){return status;}

    public byte getID(){return ID;}

    public byte[] getPnumber(){return pnumber;}

    public byte[] getData() {return data;}
}
