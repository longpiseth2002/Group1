import java.io.File;

public class fileExample {
    public static void main(String[] args) {
// Create a file object
        File file = new File("myFile2.txt");
        try {
// try to create a file
            boolean isFileCreated = file.createNewFile();
            if (isFileCreated){
                System.out.println("The new file is created.");
            }
            else {
                System.out.println("The file already exists.");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
