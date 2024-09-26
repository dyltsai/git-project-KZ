import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class simpleTester {
    // I AM SO TIRED OF AVIV"S ANNOYING AF TESTERS - Kyara
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        
        // testing the mk blob with trees
        Git myGit = new Git(false);
        mkTesters();
        //myGit.makeBLOB("./hm"); // testing file not found exception
        myGit.makeBLOB("./testingFolder");

    }


    // creates testers for testing
    private static void mkTesters() throws IOException, NoSuchAlgorithmException {
        File testingFolder = new File("./testingFolder");
        testingFolder.mkdir();
        File indexFile = new File(testingFolder, "item");
        BufferedWriter tf = new BufferedWriter(new FileWriter(indexFile));
        tf.write("why is this so confusing");
        File testingFolder2 = new File(testingFolder, "testingFolder2");
        testingFolder2.mkdir();
        // File test2 = new File(testingFolder2, "item2");
        // BufferedWriter tf2 = new BufferedWriter(new FileWriter(test2));
        // tf2.write("cry");
        tf.close();
        //tf2.close();

        // File file1 = new File("file1.txt");
        // File file2 = new File("file2.txt");
        // File file3 = new File("file3.txt");
        // BufferedWriter bw1 = new BufferedWriter(new FileWriter(file1));
        // bw1.write("test1");
        // bw1.close();
        // BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2));
        // bw2.write("test2");
        // bw2.close();
        // BufferedWriter bw3 = new BufferedWriter(new FileWriter(file3));
        // bw3.write("test3");
        // bw3.close();
    }

}
