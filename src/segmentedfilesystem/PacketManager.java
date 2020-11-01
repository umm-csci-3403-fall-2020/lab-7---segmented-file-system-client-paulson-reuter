package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;

public class PacketManager {

    //checks if all packets have been received by checking "Last Packet" bit
    //checks every packet until last packet is found
    //checks it's packet number and compares it to the counter associated with
    //one of the three files
    public boolean allPackagesReceived(DatagramPacket packet){return false;}

    //sticks packet into hashmap using uniqueID + random letter on end to create unique keys
    public void store(PacketStructure packet, HashMap ourGuy){
        int ID = packet.getID();
        int dex = 0;
        String IDstring = Integer.toString(ID);
        String[] toAdd = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

        String key = keyMaker(ID,ourGuy,dex,toAdd,IDstring);
        ourGuy.put(key,packet);
    }

    //recursively makes keys until one is created that isn't in the hashmap
    private String keyMaker(int uniqueID, HashMap ourGuy, int index, String[] add, String IDasString){
        String finalID = " ";
        if(index == add.length - 1){
            index = 0;
        }

        finalID = IDasString + add[index];

        if(ourGuy.containsKey(finalID)){
            keyMaker(uniqueID,ourGuy, index++,add,IDasString);
        }

        return finalID;
    }


    //constructs packet numbers for all data packets
    public int constructPacketNum(PacketStructure packet){
        byte[]  pnumber = packet.getPNumber();
        int x = Byte.toUnsignedInt(pnumber[0]);
        int y = Byte.toUnsignedInt(pnumber[1]);
        int result = 256*x + y;
        return result;
    }

    //takes all three ArrayLists and sorts in
    //get file name from header to use for data
    //grab data from data packets
    public void fileReconstructor(){}

    //checks the type and assigns it as an object accordingly
    //true for data false for header
    public boolean determineType(DatagramPacket packet){
        boolean header = false;
        if (packet.getData()[0]%2 == 0) {
            header = true;
        }
        return header;
    }
}
