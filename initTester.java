import java.io.File;
import java.io.IOException;

public class initTester {
    public static void deleteCurrent () {
        File gitDirectory = new File ("git");
        File objectsDirectory = new File ("git/objects");
        String indexFileName = "index";
        File indexFile = new File (gitDirectory, indexFileName);
        if (gitDirectory.exists() && objectsDirectory.exists() && indexFile.exists()) {
            gitDirectory.delete();
            System.out.println("Git Directory, Objects Directory, and Index File ALL EXISTED.");
        }
        else if (gitDirectory.exists() && objectsDirectory.exists() && !indexFile.exists()) {
            objectsDirectory.delete();
            gitDirectory.delete();
            System.out.println("Git Directory, and Objects Directory EXISTED. Index File DID NOT EXIST.");
        }
        else if (gitDirectory.exists() && !objectsDirectory.exists() && indexFile.exists()) {
            gitDirectory.delete();
            System.out.println("Git Directory, and Index File EXISTED. Objects Directory DID NOT EXIST.");
        }
        else if (gitDirectory.exists() && !objectsDirectory.exists() && !indexFile.exists()) {
            gitDirectory.delete();
            System.out.println("Git Directory EXISTED. Objects Directory and Index File DID NOT EXIST.");
        }
        else {
            System.out.println("Git Directory, Objects Directory, and Index File ALL DID NOT EXIST");
        }

    }
    public static void main (String [] args) throws IOException {
        File gitDirectory = new File ("git");
        File objectsDirectory = new File ("git/objects");
        String indexFileName = "index";
        File indexFile = new File (gitDirectory, indexFileName);
        Git myGit1 = new Git(false);

        System.out.println("Case 1: Has Everything");
        System.out.println("Expected Output: Git Directory, Objects Directory, and Index File ALL EXISTED.");
        System.out.print("Tester Output: ");
        deleteCurrent();
        System.out.println("");

        System.out.println("Case 2: Has Git Directory with Nothing Inside");
        Git myGit2 = new Git(false);
        objectsDirectory.delete();
        indexFile.delete();
        System.out.println("Expected Output: Git Directory EXISTED. Objects Directory and Index File DID NOT EXIST.");
        System.out.print("Tester Output: ");
        deleteCurrent();
        System.out.println("");

        System.out.println("Case 3: Has Git Directory with No Objects Directory");
        Git myGit3 = new Git(false);
        objectsDirectory.delete();
        System.out.println("Expected Output: Git Directory, and Index File EXISTED. Objects Directory DID NOT EXIST.");
        System.out.print("Tester Output: ");
        deleteCurrent();
        System.out.println("");

        System.out.println("Case 4: Has Git Directory with No Index File");
        Git myGit4 = new Git(false);
        indexFile.delete();
        System.out.println("Expected Output: Git Directory, and Objects Directory EXISTED. Index File DID NOT EXIST.");
        System.out.print("Tester Output: ");
        deleteCurrent();
        System.out.println("");

        System.out.println("Case 5: Has Nothing");
        System.out.println("Expected Output: Git Directory, Objects Directory, and Index File ALL DID NOT EXIST");
        System.out.print("Tester Output: ");
        deleteCurrent();
        System.out.println("");

    }
}
