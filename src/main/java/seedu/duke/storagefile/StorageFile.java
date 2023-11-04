package seedu.duke.storagefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import seedu.duke.Duke;
import seedu.duke.exerciselog.Log;

public class StorageFile {
    private static File dir;
    private static File textFile;
    private static FileWriter writeFile;

    public StorageFile(String dirName, String textFileName) {
        dir = new File(dirName);
        textFile = new File(textFileName);
    }

    public void checkForTextFile(Log exerciseLog) throws IOException {
        if (dir.exists() && textFile.exists()) {
            try {
                Scanner s = new Scanner(textFile);
                while (s.hasNextLine()) {
                    textToExercise(s.nextLine().split(","), exerciseLog);
                }
                s.close();
            }
            catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!textFile.exists()) {
            textFile.createNewFile();
        }
        writeFile = new FileWriter(textFile.toPath().toString(), true);
    }

    public static void textToExercise(String[] exerciseDetails, Log exerciseLog) {

    }
}
