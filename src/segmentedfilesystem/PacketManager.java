package segmentedfilesystem;

public class PacketManager {
    //buffer is 1024 bits

    //checks if all packets have been received by checking "Last Packet" bit
    public boolean allPackagesReceived(){}

    //Sort by file ID into 3 different ArrayLists
    //use switch to create/sort by file ID
    public void sortFileID(){}

    //constructs packet numbers for all data packets
    public int constructPacketNum(){}

    //takes all three ArrayLists and sorts in
    //get file name from header to use for data
    //grab data from data packets
    public void fileReconstructor(){}

    //checks the type and assigns it as an object accordingly
    public boolean headerOrData(){}
}
