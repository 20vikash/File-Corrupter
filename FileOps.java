import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOps {
    public File[] listFiles(String folder) {
        String directoryPath = folder;
        File[] list = {};

        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] fileList = directory.listFiles();

            if (fileList != null && fileList.length > 0) {
                for (File file: fileList) {
                    if (file.isFile()) {
                        System.out.println("File: " + file.getName());
                    } else if (file.isDirectory()) {
                        System.out.println("Directory name: " + file.getName());
                    }
                }
            }

            return fileList;
        } else {
            System.out.println(directoryPath + " is not a valid directory");
            return list;
        }
    }

    public void corruptRecoverFile(String fileName) {
        String filePath = "files/" + fileName;

        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = raf.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer[i] = (byte) ~buffer[i];
                }

                raf.seek(raf.getFilePointer() - bytesRead);
                
                raf.write(buffer, 0, bytesRead);
            }

            System.out.println("File modified in place successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
