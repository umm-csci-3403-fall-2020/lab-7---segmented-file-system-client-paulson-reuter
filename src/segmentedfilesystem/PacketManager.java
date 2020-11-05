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
            file1.add(packet);
        }
        else if(file2.datapackets.isEmpty()){
            file2.add(packet);
        }
        else if(file3.datapackets.isEmpty()){
            file3.add(packet);
        }
        else{
            if(packet.getID() == file1.fileID){
                file1.add(packet);
            }
            else if(packet.getID() == file2.fileID){
                file2.add(packet);
            }
            else if(packet.getID() == file3.fileID){
                file3.add(packet);
            }
        }
    }

    public void fileGenerator() throws IOException {
        PacketStructure packet;

        ArrayList<Integer> sortedKeys1 = new ArrayList<>(file1.datapackets.keySet());
        Collections.sort(sortedKeys1);
        FileOutputStream fos1 = new FileOutputStream(file1.filename);
        for(int index : sortedKeys1) {
                packet = file1.datapackets.get(index);
                fos1.write(packet.getData());
        }

        ArrayList<Integer> sortedKeys2 = new ArrayList<>(file2.datapackets.keySet());
        Collections.sort(sortedKeys2);
        FileOutputStream fos2 = new FileOutputStream(file2.filename);
        for(int index : sortedKeys2) {
            packet = file2.datapackets.get(index);
            fos2.write(packet.getData());
        }

        ArrayList<Integer> sortedKeys3 = new ArrayList<>(file3.datapackets.keySet());
        Collections.sort(sortedKeys3);
        FileOutputStream fos3 = new FileOutputStream(file3.filename);
        for(int index : sortedKeys3) {
            packet = file3.datapackets.get(index);
            fos3.write(packet.getData());
        }
    }

}
