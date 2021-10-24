package unit;

import model.Phonebook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class ReadAndWrite {

    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeFileToCSV(String path, ArrayList<Phonebook> listPhonebook) {
        FileWriter fileWriter = null;
        try {
            File fileName = new File(path);
            fileWriter = new FileWriter(fileName);
            for (Phonebook phonebook : listPhonebook) {
                fileWriter.append(phonebook.getNumberPhone());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(phonebook.getGroup());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(phonebook.getFullname());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(phonebook.getGender());
                fileWriter.append(COMMA_DELIMITER);
                String add = phonebook.getAddress().replace(",",";");
                fileWriter.append(add);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(phonebook.getDateOfBirth());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(phonebook.getEmail());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.close();
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        }
    }

    public static ArrayList<Phonebook> readFileToCsv(String path) {
        ArrayList<Phonebook> listPhonebook = new ArrayList<>();
        try {
            File file = new File(path);
            if (!file.exists()) {
                File fileName = new File(path);
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] arrInfor = line.split(",");
                Phonebook phonebook = new Phonebook(arrInfor[0], arrInfor[1], arrInfor[2], arrInfor[3], arrInfor[4], arrInfor[5], arrInfor[6]);
                listPhonebook.add(phonebook);
            }
        } catch (Exception e) {
            System.out.println();
        }
        return listPhonebook;
    }

    public static void copyFolder(File sourceFolder, File targetFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!targetFolder.exists()) {
                targetFolder.mkdir();
            }
            String files[] = sourceFolder.list();
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File tarFile = new File(targetFolder, file);
                copyFolder(srcFile, tarFile);
            }
        } else {
            Files.copy(sourceFolder.toPath(), targetFolder.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File da duoc save " + targetFolder);
        }
    }
}

