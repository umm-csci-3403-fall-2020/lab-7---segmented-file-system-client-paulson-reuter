package segmentedfilesystem;

public class PacketManager {
    //buffer is 1024 bits

    //checks if all packets have been received by checking "Last Packet" bit
    //checks every packet until last packet is found
    //checks it's packet number and compares it to the counter associated with
    //one of the three files
    public boolean allPackagesReceived(){return false;}

    //Sort by file ID into 3 different ArrayLists
    //Take advice from Nic and use a map because then you do not have to worry about 
    // having many if statements and it can just take the fileID as an input and it will find the correct bucket to put the data into.
    public void sortFileID(){}

    //constructs packet numbers for all data packets
    public int constructPacketNum(){return 0;}

    //takes all three ArrayLists and sorts in
    //get file name from header to use for data
    //grab data from data packets
    public void fileReconstructor(){}

    //checks the type and assigns it as an object accordingly
    public boolean determineType(packet) {
        boolean header = false;
        PacketStructure packet = new PacketStructure(packet);
        if (packet.getStatus%2 == 0) {
            header = true;
        }
        return header;
    }
}
