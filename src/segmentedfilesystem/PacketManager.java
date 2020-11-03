package segmentedfilesystem;

import java.io.*;
import java.net.DatagramPacket;
import java.util.HashMap;

public class PacketManager {

    //things that every method needs that I dont want to have to find every stinking time
    private int file1Total;
    private int file2Total;
    private int file3Total;
    private int file1ID;
    private int file2ID;
    private int file3ID;
    private HashMap<String,PacketStructure> theGuy;
    public File file1;
    public File file2;
    public File file3;

    //checks if all packets have been received by checking "Last Packet" bit
    //saves all three last packets, and compares them to counters associated with
    //three files
    public boolean allPackagesReceived(PacketStructure packet){
        int file1Counter = 0;
        int file2Counter = 0;
        int file3Counter = 0;
        boolean status = false;

        for( Object key : theGuy.keySet()){
            //goes through every key, sets fileID's once then counts the ID's, then compares counters to totals to update boolean
            String stringID = key.toString().substring(0,key.toString().length()-2);
            int ID = Integer.parseInt(stringID);
            if(ID == file1ID || file1ID == -1) {
                //if ID equals fileID increase count or if fileID is empty set ID and increase count
                if ((packet.getStatus() % 4) == 3) {
                    //save packetnum as total if its last packet
                    file1Total = constructPacketNum(packet) + 2; //accounts for starting at 0 and header packet
                }
                    if (file1ID == -1) {
                        //only if ID is empty (basecase)
                        file1ID = ID;
                        file1Counter++;
                    } else {
                        file1Counter++;
                    }
            }
            else if(ID == file2ID || file2ID == -1){
                //if ID equals fileID increase count or if fileID is empty set ID and increase count
                if ((packet.getStatus() % 4) == 3) {
                    //save packetnum as total if its last packet
                    file2Total = constructPacketNum(packet) + 2;
                }
                if (file2ID == -1) {
                    //only if ID is empty (basecase)
                    file2ID = ID;
                    file2Counter++;
                } else {
                    file2Counter++;
                }
            }
            else if(ID == file3ID || file3ID == -1){
                //if ID equals fileID increase count or if fileID is empty set ID and increase count
                if ((packet.getStatus() % 4) == 3) {
                    //save packetnum as total if its last packet
                    file3Total = constructPacketNum(packet) + 2;
                }
                if (file3ID == -1) {
                    //only if ID is empty (basecase)
                    file3ID = ID;
                    file3Counter++;
                } else {
                    file3Counter++;
                }
            }
        }//end for loop

        if(file1Counter == file1Total && file2Counter == file2Total && file3Counter == file3Total){
            //does the check and is true only if all files are accounted for
            status = true;
        }
        return status;
    }//end

    //sticks packet into hashmap using uniqueID + random letter on end to create unique keys
    public void store(PacketStructure packet){
        int ID = packet.getID();
        int dex = 0;
        String IDstring = Integer.toString(ID);
        String[] toAdd = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

        String key = keyMaker(ID,dex,toAdd,IDstring);
        theGuy.put(key,packet);
    }

    //recursively makes keys until one is created that isn't in the hashmap
    private String keyMaker(int uniqueID, int index, String[] add, String IDasString){
        String finalID = " ";
        if(index == add.length - 1){
            index = 0;
        }

        finalID = IDasString + add[index];

        if(theGuy.containsKey(finalID)){
            keyMaker(uniqueID,index++,add,IDasString);
        }

        return finalID;
    }


    //constructs packet numbers for all data packets
    private int constructPacketNum(PacketStructure packet){
        int x = Byte.toUnsignedInt(packet.getPnumber()[0]);
        int y = Byte.toUnsignedInt(packet.getPnumber()[1]);
        int packetnum = x*10+y;
        return packetnum;
    }


    public void fileGenerator() throws IOException {
        file1 = fileCreator(file1ID);
        file2 = fileCreator(file2ID);
        file3 = fileCreator(file3ID);

        FileOutputStream fos1 = new FileOutputStream(file1);
        DataOutputStream dos1 = new DataOutputStream(fos1);
        int index = 0;
        int packetnum;

        while(index != file1Total) {
            //loops until all of file1's packets have been written to the file
            for (String key : theGuy.keySet()) {
                //this scans through the hashmap looking at every value
                PacketStructure data = theGuy.get(key);
                if (data.getID() == file1ID) {
                    //this is to select only file1's packets
                    packetnum = constructPacketNum(data);
                    if (packetnum == index) {
                        //this is to make sure it writes in order from packet 0 to ...
                        dos1.write(data.getData());
                        index++;
                    }
                }
            }
        }//now just do this two more times

        FileOutputStream fos2 = new FileOutputStream(file2);
        DataOutputStream dos2 = new DataOutputStream(fos2);
        index = 0;

        while(index != file2Total) {
            //loops until all of file1's packets have been written to the file
            for (String key : theGuy.keySet()) {
                //this scans through the hashmap looking at every value
                PacketStructure data = theGuy.get(key);
                if (data.getID() == file2ID) {
                    //this is to select only file1's packets
                    packetnum = constructPacketNum(data);
                    if (packetnum == index) {
                        //this is to make sure it writes in order from packet 0 to ...
                        dos2.write(data.getData());
                        index++;
                    }
                }
            }
        }

        FileOutputStream fos3 = new FileOutputStream(file3);
        DataOutputStream dos3 = new DataOutputStream(fos3);

        while(index != file3Total) {
            //loops until all of file1's packets have been written to the file
            for (String key : theGuy.keySet()) {
                //this scans through the hashmap looking at every value
                PacketStructure data = theGuy.get(key);
                if (data.getID() == file3ID) {
                    //this is to select only file1's packets
                    packetnum = constructPacketNum(data);
                    if (packetnum == index) {
                        //this is to make sure it writes in order from packet 0 to ...
                        dos3.write(data.getData());
                        index++;
                    }
                }
            }
        }

    }

    private File fileCreator(int fileID){
        String name = " ";

        for( String key : theGuy.keySet()){
            PacketStructure data = theGuy.get(key);
            if(data.getID() == fileID) {
                //only checks packets that have given ID so we only find one header packet
                if (data.getPnumber()[0] == -1) {
                    name = new String(data.getData(), 0, data.getData().length);
                }
            }
        }
        File thefile = new File(name);
        return thefile;
    }

    //checks the type and assigns it as an object accordingly
    public boolean determineType(DatagramPacket packet){
        boolean header = false;
        if (packet.getData()[0]%2 == 0) {
            header = true;
        }
        return header;
    }
}
