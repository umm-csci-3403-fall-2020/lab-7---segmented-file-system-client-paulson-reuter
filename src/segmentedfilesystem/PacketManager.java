package segmentedfilesystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PacketManager {

    File file1 = new File();
    File file2 = new File();
    File file3 = new File();

    public boolean allPackagesReceived(){
        return file1.fileComplete() && file2.fileComplete() && file3.fileComplete();
    }

    public void store(PacketStructure packet){
        if(file1.datapackets.isEmpty()){
	System.out.println("Adding initial to file1");
            file1.add(packet);
        }
        else if(file2.datapackets.isEmpty()){
	System.out.println("Adding initial to file2");
            file2.add(packet);
        }
        else if(file3.datapackets.isEmpty()){
	System.out.println("Adding initial to file3");
            file3.add(packet);
        }
        else{
            if(packet.getID() == file1.fileID){
	    System.out.println("Adding to file1");
                file1.add(packet);
            }
            else if(packet.getID() == file2.fileID){
	    System.out.println("Adding to file2");
                file2.add(packet);
            }
            else if(packet.getID() == file3.fileID){
	    System.out.println("Adding to file3");
                file3.add(packet);
            }
        }

	System.out.println(file1.numPackets);
	System.out.println(file1.datapackets.size());

	System.out.println(file2.numPackets);
	System.out.println(file2.datapackets.size());

	System.out.println(file3.numPackets);
	System.out.println(file3.datapackets.size());
    }

    public void fileGenerator() throws IOException {
        PacketStructure packet;

        ArrayList<Integer> sortedKeys1 = new ArrayList<>(file1.datapackets.keySet());
        Collections.sort(sortedKeys1);
	System.out.println(file1.filename);
        FileOutputStream fos1 = new FileOutputStream(file1.filename);
        for(int index : sortedKeys1) {
                packet = file1.datapackets.get(index);
                fos1.write(packet.getData());
        }

        ArrayList<Integer> sortedKeys2 = new ArrayList<>(file2.datapackets.keySet());
        Collections.sort(sortedKeys2);
	System.out.println(file2.filename);
        FileOutputStream fos2 = new FileOutputStream(file2.filename);
        for(int index : sortedKeys2) {
            packet = file2.datapackets.get(index);
            fos2.write(packet.getData());
        }

        ArrayList<Integer> sortedKeys3 = new ArrayList<>(file3.datapackets.keySet());
        Collections.sort(sortedKeys3);
	System.out.println(file3.filename);
        FileOutputStream fos3 = new FileOutputStream(file3.filename);
        for(int index : sortedKeys3) {
            packet = file3.datapackets.get(index);
            fos3.write(packet.getData());
        }
    }

}
