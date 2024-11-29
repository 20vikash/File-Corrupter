import java.util.Scanner;

public class FileCorrupter {

    public static void main(String[] args) {
        FileOps m = new FileOps();
        BackUp b = new BackUp();

        Scanner s = new Scanner(System.in);

        String fileName;
        int choice;

        System.out.println("Welcome to File corrupter..");
        System.out.println("Press 0 to corrupt files");
        System.out.println("Press 1 to recover files");

        System.out.print("Your choice: ");

        choice = s.nextInt();

        switch (choice) {
            case 0:
                System.out.println("List of files in the directory.");
                m.listFiles("files");

                System.out.print("Enter the file name to be corrupted: ");
            
                s.nextLine();
                fileName = s.nextLine();
        
                if (!b.hasBackup(fileName)) {
                    System.out.println("Creating backup for the file you are about to corrupt..");
                    b.backup(fileName);
                    m.corruptRecoverFile(fileName);
                } else {
                    System.out.println("The file is already corrupted.. Try another file..");
                }

                break;
            
            case 1:
                System.out.println("List of files to be recovered.");

                m.listFiles("backups");

                System.out.print("Enter the file name to be recovered: ");

                s.nextLine();
                fileName = s.nextLine();

                m.corruptRecoverFile(fileName);
                b.removeBackUp(fileName);
                break;
        
            default:
                System.out.println("Invalid choice");
                break;
        }

        s.close();
    }
}
