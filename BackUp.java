import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BackUp {
    public void backup(String file) {
        String location = "backups/" + file;

        File currentFile = new File("files/" + file);
        File backUpFile = new File(location);

        try {
            Files.copy(currentFile.toPath(), backUpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created successfully");
        } catch (IOException e) {
            System.out.println("Backup cannot be created");
        }
    }

    public Boolean hasBackup(String file) {
        FileOps f = new FileOps();

        File[] list = f.listFiles("backups");

        for (File element: list) {
            if (file.equals(element.getName())) {
                return true;
            }
        }

        return false;
    }

    public String generateHash(File file) throws NoSuchAlgorithmException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            byte[] fileHashBytes;

            MessageDigest digest = MessageDigest.getInstance("MD5");

            while ((bytesRead = raf.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            fileHashBytes = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : fileHashBytes) {
                hexString.append(String.format("%02x", b));
            }
    
            return hexString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public Boolean checkFiles(String file, String backUp) {
        File real = new File("files/" + file);
        File backUpFile = new File(backUp);

        try {
            if (generateHash(real).equals(generateHash(backUpFile))) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Failed to compare two files");
        }

        return false;
    }

    public void removeBackUp(String file) {
        File f = new File("backups/" + file);

        if (!f.delete()) {
            System.out.println("Failed to delete backup");
        }
    }
}
