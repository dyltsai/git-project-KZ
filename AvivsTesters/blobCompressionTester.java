import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class blobCompressionTester {
    public static void main (String [] args) throws IOException, NoSuchAlgorithmException {
        Git compressedGit = new Git (true);
        File toCompress = new File ("compressMe.txt");
        BufferedWriter bC = new BufferedWriter(new FileWriter(toCompress));
        bC.write("Compress me, godammit!");
        bC.close();
        compressedGit.makeBLOB("compressMe.txt");

        System.out.print("Hash on Compressed Data: ");

        Git nonCompressedGit = new Git (false); //need to create this because I want to run the hash on the compressed data. If I use the same git object, it will re-compress the already compressed data!
        String hash = nonCompressedGit.hashingFunction("compressedFile.zip");
        System.out.println(hash);
        System.out.println("Hash on Non-Compressed Data: 63e553d8acd7b79466411e2e695ee1d0f7e9e9fd");
        System.out.println("These two hashes should not be the same.");
        System.out.println("\n");

        System.out.println("Validating Index: ");
        BufferedReader indexReader = new BufferedReader(new FileReader("git/index"));
        if (indexReader.readLine().equals(hash + " " + "compressMe.txt")) {
            System.out.println("Correct hash + fileName in index file!");
        }
        else {
            System.out.println("Index file was not updated.");
        }
        indexReader.close();
        System.out.println("\n");

        System.out.println("Veryfing File Name in Objects: ");
        System.out.print("Expected: ");
        System.out.println(hash);
        File verifyExistence = new File ("git/objects/ae0cbe38d57d3b376836d72ec31a506a61666635");
        if (verifyExistence.exists()) {
            System.out.println("The file exists and is correctly named in objects!");
        }
        else {
            System.out.println("The file is not correctly named and/or doesn't exist in objects!");
        }
        System.out.println("\n");

        System.out.println("Validate File Contents in Direcotry in Objects: ");
        System.out.println("Expected: Compress me, godammit!");
        System.out.print("Actual: ");
        BufferedReader bA = new BufferedReader(new FileReader("git/objects/ae0cbe38d57d3b376836d72ec31a506a61666635"));
        System.out.println(bA.readLine());
    }
}
