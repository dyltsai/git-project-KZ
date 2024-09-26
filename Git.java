import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import java.math.BigInteger;

public class Git {
    File gitDirectory = new File("git");
    File objectsDirectory = new File("git/objects");
    String indexFileName = "index";
    File indexFile = new File(gitDirectory, indexFileName);
    boolean toZip;

    // blame Aviv if everything looks messy >:(
    // I had to manually add in all the comments - Kyara

    public Git(boolean toZip) throws IOException {
        this.toZip = toZip;
        if (!gitDirectory.exists()) {
            gitDirectory.mkdir();
            objectsDirectory.mkdirs();
            indexFile.createNewFile();
        } else if (objectsDirectory.exists() && !indexFile.exists()) {
            indexFile.createNewFile();
        } else if (indexFile.exists() && !objectsDirectory.exists()) {
            objectsDirectory.mkdirs();
        } else if (!indexFile.exists() && !objectsDirectory.exists()) {
            indexFile.createNewFile();
            objectsDirectory.mkdirs();
        } else {
            System.out.println("Git Repository already exists");
        }

    }

    public void compressData(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        FileOutputStream fos = new FileOutputStream("compressedFile.zip");
        DeflaterOutputStream dos = new DeflaterOutputStream(fos);
        int data;
        while ((data = fis.read()) != -1) {
            dos.write(data);
        }
        fis.close();
        dos.close();
    }

    // creates a blob.
    // fileName is actually the file Path
    // returns the hash of the blob
    public String makeBLOB(String fileName) throws IOException, NoSuchAlgorithmException {
        File temp = new File(fileName);
        StringBuilder fileContent = new StringBuilder();
        String hash;
        // if not exist, then throw exceptions
        if (!temp.exists()) {
            throw new FileNotFoundException("the file doesn't exist");
        } else if (temp.isDirectory()) {
            // if is directory, then first list out the file, create a blob for each
            // content, append a small index into a string, and hash that for the tree
            File[] files = temp.listFiles();
            StringBuilder sb = new StringBuilder();
            for (File currFile : files) {
                // for each currFile, make a blob
                String hashed = makeBLOB(currFile.getPath()); // makes the blob and returns the hash
                if (currFile.isDirectory()) {
                    sb.append("tree " + hashed + " " + currFile.getName() + "\n");
                } else {
                    sb.append("blob " + hashed + " " + currFile.getName() + "\n");
                }
            }
            hash = hashTree(sb);
            // creates file for the tree
            File file = new File("git/objects", hash);
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.append(sb.toString());
            fileContent.append("tree " + hash + " " + new File(fileName).getPath());
            bw.close();
        } else {
            // hashes a blob
            hash = hashingFunction(fileName);
            File file = new File("git/objects", hash);
            file.createNewFile();
            fileWriter(fileName, file);
            fileContent.append("blob " + hash + " " + new File(fileName).getPath());
        }

        // adds a line into the index file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexFile, true))) {
            bw.append(fileContent.toString());
            bw.newLine();
        }
        return hash;

    }

    // hashes the small list of files for trees
    private String hashTree(StringBuilder sb) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        String content = sb.toString();
        digest.update(content.getBytes("UTF-8"));
        BigInteger temp = new BigInteger(1, digest.digest());
        return temp.toString(16);
    }

    // generates the hashed name using file content (blob only)
    public String hashingFunction(String fileName) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        if (toZip) {
            compressData(fileName);
            fileName = "compressedFile.zip";
        }
        File file = new File(fileName);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] byteArray = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesRead);
            }
        }

        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // creates a file with given content(blob only)
    public void fileWriter(String toRead, File toWrite) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(toWrite));
        BufferedReader br = new BufferedReader(new FileReader(toRead));
        while (br.ready()) {
            bw.append((char) br.read());
        }
        br.close();
        bw.close();
    }
}