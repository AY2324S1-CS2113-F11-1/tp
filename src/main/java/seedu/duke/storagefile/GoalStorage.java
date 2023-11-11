package seedu.duke.storagefile;

import seedu.duke.Duke;
import seedu.duke.data.GoalList;
import seedu.duke.ui.TextUi;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GoalStorage extends StorageFile {

    public GoalStorage(String dirName, String textFileName) {
        super(dirName, textFileName);
    }

    public static GoalStorage initializeGoalStorage(String dirName, String textFilePath) {
        return new GoalStorage(dirName, textFilePath);
    }

    /**
     * This method restore any saved goal into goal list at the beginning to start the app.
     * Both directory or save file not found will cause the app to start with an empty goal list
     * if error occurs in loading saved date, the app will start with empty list immediately
     * without loading part of data
     * Note that this method also set up the file writer into overwrite mode
     */
    public void restoreGoalRecord() throws IOException {
        if (dir.exists() && textFile.exists()) {
            try {
                Scanner s = new Scanner(textFile);
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    if (!line.trim().isEmpty()) {
                        textToGoalObject(line);
                    }
                }
                s.close();
            } catch (FileNotFoundException fnf) {
                System.out.println("Saved file cannot be found! FItNus will start with empty goal records.");
                System.out.println(TextUi.buildingFileMsg());
                Duke.goalList = new GoalList();
            } catch (Exception e) {
                System.out.println("Saved goal file is corrupted! FitNus will start with empty goal records.");
                System.out.println(TextUi.buildingFileMsg());
                Duke.goalList = new GoalList(); //start with an empty goal list
            }
        }

        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!textFile.exists()) {
            textFile.createNewFile();
            Duke.goalList = new GoalList(); //start with an empty goal list
        }
        
    }

    /**
     * This method update the content of output Goal File by overwriting the file
     *  with all data saved in the current goalList
     * Note that in the following implementation, the field writeFile in not used
     * Instead, everytime a new file writer is created to update content of file
     * @throws IOException if failed to access file
     */
    public void overwriteGoalToFile() throws IOException {
        String content = TextUi.contentOfGoalList(Duke.goalList);
        if (content == null) {
            return;
        }
        FileWriter fw = new FileWriter(textFile.toPath().toString(), false);
        fw.write(content);
        fw.close();
    }

    private static void textToGoalObject(String goalRecord) throws Exception {
        String[] goalRecordParts = goalRecord.split(" ", 5);
        //example of saved record: Consume 1230 kcal on Nov 11, 2023
        int amount =Integer.parseInt(goalRecordParts[1]);
        String savedDateString = goalRecordParts[4];
        String date = convertDateFormat(savedDateString);
        String restoredCommand = "set " + amount + " on " + date;
        GoalList.addGoal(restoredCommand);
    }


    /**
     * This method is used to convert a date String with format MMM d, yyyy into a date String with format dd/MM/yyyy
     * @param originalDateString date String with format MMM d, yyyy, e.g. 11 Nov, 2023
     * @return date String with format dd/MM/yyyy, e.g. 11/11/2023
     */
    private static String convertDateFormat(String originalDateString) {
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        LocalDate originalDate = LocalDate.parse(originalDateString, originalFormatter);
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        return originalDate.format(newFormatter);
    }


}

