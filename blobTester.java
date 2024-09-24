import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

public class blobTester {
    public static void main (String [] args) throws NoSuchAlgorithmException, IOException {
        String yn = "";
        do {
        Git myGit = new Git(false);

        boolean one = true;
        boolean two = true;
        boolean three = true;

        String input1 = JOptionPane.showInputDialog("What is your favorite ice cream flavor? Why? ");
        if (input1 == null || input1.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input was provided!", "Error", JOptionPane.ERROR_MESSAGE);
            one = false;
        }
        String input2 = JOptionPane.showInputDialog("Do you have a favorite student? Why? ");
        if (input2 == null || input2.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input was provided!", "Error", JOptionPane.ERROR_MESSAGE);
            two = false;
        }
        String input3 = JOptionPane.showInputDialog("Will you give me an A? Why? ");
        if (input3 == null || input3.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No input was provided!", "Error", JOptionPane.ERROR_MESSAGE);
            three = false;
        }

        if (one == false && two == false && three == false) {
            JOptionPane.showMessageDialog(null, "You provided no answers to any of my questions...I guess you hate me!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            File file1 = new File("file1.txt");
            File file2 = new File ("file2.txt");
            File file3 = new File ("file3.txt");
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(file1));
            bw1.write(input1);
            bw1.close();
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2));
            bw2.write(input2);
            bw2.close();
            BufferedWriter bw3 = new BufferedWriter(new FileWriter(file3));
            bw3.write(input3);
            bw3.close();

            myGit.makeBLOB("file1.txt");
            myGit.makeBLOB("file2.txt");
            myGit.makeBLOB("file3.txt");

            System.out.println("Validating Hash: ");
            File hardCodedFile = new File ("hardCoded.txt");
            BufferedWriter bwH = new BufferedWriter(new FileWriter(hardCodedFile));
            bwH.write("This file is hard coded!");
            bwH.close();
            System.out.println("Expected Hash: d460c4f03aca92411f7b7d7e9018647c5589a4d9");
            System.out.print("Generated Hash: ");
            System.out.println(myGit.hashingFunction("hardCoded.txt"));
            System.out.println ("\n");
            
            System.out.println("Validating File Contents: ");
            BufferedReader brH = new BufferedReader(new FileReader("hardCoded.txt"));
            System.out.println("Expected: This file is hard coded!");
            System.out.print("Actual File Contents: ");
            System.out.println(brH.readLine());
            System.out.println("\n");
            brH.close();

            myGit.makeBLOB("hardCoded.txt");

            System.out.println("Objects Folder Check: ");
            File hCF = new File ("git/objects/d460c4f03aca92411f7b7d7e9018647c5589a4d9");
            if (hCF.exists()) {
                System.out.println("File IS in objects folder!");
            }
            else {
                System.out.println("File IS NOT in objects folder!");
            }
            System.out.println("\n");

            System.out.println("Index File Check: ");
            BufferedReader indexFileChecker = new BufferedReader (new FileReader ("git/index"));
            System.out.println("Expected Entry: d460c4f03aca92411f7b7d7e9018647c5589a4d9 hardCoded.txt");
            System.out.print("Actual Entry: ");
            while (indexFileChecker.ready()) {
                if (indexFileChecker.readLine().equals("d460c4f03aca92411f7b7d7e9018647c5589a4d9 hardCoded.txt")) {
                    System.out.println ("d460c4f03aca92411f7b7d7e9018647c5589a4d9 hardCoded.txt");
                }
            }
            indexFileChecker.close();

        }
        yn = JOptionPane.showInputDialog("Do you want to add more answers? (Y/N): ");

    } while (yn.equals ("Y"));   
    }

}
