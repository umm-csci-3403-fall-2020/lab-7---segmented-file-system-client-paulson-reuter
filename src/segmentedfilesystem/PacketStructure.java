package segmentedfilesystem;
import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketStructure {
    private byte status;
    private byte ID;
    private byte[] pnumber = new byte[2];
    private byte[] data;
    private int length;

    PacketManager manager = new PacketManager();
    public PacketStructure(DatagramPacket packet){
        status = packet.getData()[0];
        ID = packet.getData()[1];
        length = packet.getLength();
        if(determineType(packet)) {
            data = packet.getData();
        }
        else{
            pnumber[0] = packet.getData()[2];
            pnumber[1] = packet.getData()[3];
            data = Arrays.copyOfRange(packet.getData(),4,packet.getData().length-4);
        }
    }

    private boolean determineType(DatagramPacket packet){
        boolean header = false;
        if (packet.getData()[0]%2 == 0) {
            header = true;
        }
        return header;
    }

    public byte getStatus(){return status;}

    public byte getID(){return ID;}

    public byte[] getPnumber(){return pnumber;}

    public byte[] getData() {return data;}

    public int getLength() {return length;}
    
}
