package seedu.duke.data;

import seedu.duke.Duke;
import seedu.duke.data.exception.IllegalValueException;
import seedu.duke.data.exception.IncorrectFormatException;
import seedu.duke.ui.TextUi;

import java.util.ArrayList;

public class GoalList extends ArrayList<Goal> {
    private static final String GOALKEYWORD = "set";
    private static final String DATEKEYWORD = "on";
    private static final String DELETEGOALKEYWORD = "deleteg";
    private static final String VIEWGOALKEYWORD = "viewq";

    private ArrayList<Goal> goals;
    private int goalCount;

    public GoalList() {
        goals = new ArrayList<>();
        this.goalCount = 0;
    }

    public GoalList(ArrayList<Goal> goals) {
        this.goals = goals;
        this.goalCount = goals.size();
    }

    public Goal getGoal(int index) {
        return goals.get(index);
    }

    public int getGoalCount() {
        return goalCount;
    }

    /**
     * This method removes a goal object from the global field goals list by indexing
     * It also decrements goalCount by 1
     * @param cmd Raw user Command
     * @return message of succeeding to delete goal and tell user the updated total number of goals
     */
    public static String deleteGoal(String cmd) throws IncorrectFormatException, NumberFormatException {
        verifyDeleteGoalInput(cmd);
        String[] cmdSplit = cmd.toLowerCase().trim().split(" ");
        int index = Integer.parseInt(cmdSplit[1]);
        Goal targetGoal = Duke.goals.remove(index);
        Duke.goals.goalCount--;
        return TextUi.deleteGoalMsg(targetGoal) + TextUi.noOfGoalMsg(Duke.goals.goalCount);
    }

    public static String achieveGoal(String cmd) throws IncorrectFormatException, NumberFormatException {
        verifyAchieveGoalInput(cmd);
        String[] cmdSplit = cmd.split(" ");
        int index = Integer.parseInt(cmdSplit[1]);
        Goal achievedGoal = Duke.goals.remove(index);
        Duke.goals.goalCount--;
        Duke.achievedGoals.add(achievedGoal);
        return "Congratulation! You have achieved one goal!\n"
                + "[Finished]" + achievedGoal + "✔✔✔";
    }

    /**
     * Begins to format user input by change to small letter, remove leading and
     * checks if the user Input is valid by:
     * 1. check if the length of the command equals 4
     * 2. detect keywords "set", "on", etc.
     * 3. check if user inputs a calories number at valid range
     * The userCmd should be like: set 1234 on Date
     * @param userCmd represents the raw userInput
     * @throws IncorrectFormatException
     */
    private static void verifyGoalInput(String userCmd) throws IncorrectFormatException, NumberFormatException {

        String[] cmdSplit = userCmd.split(" ");
        if (cmdSplit.length != 4) {
            throw new IncorrectFormatException("Oops! The goal instruction is in wrong format.");
        }

        if (!cmdSplit[0].equals(GOALKEYWORD) ) {
            throw new IncorrectFormatException("Sorry. I cannot detect the [" + GOALKEYWORD + "] keyword." );
        }

        if (!cmdSplit[2].equals(DATEKEYWORD)) {
            throw new IncorrectFormatException("Sorry. I cannot detect the [" + DATEKEYWORD + "] keyword." );
        }

        int calories = Integer.parseInt(cmdSplit[1]); //throws number exception if not a number string
        if (calories <= 0 ){
            throw new IllegalValueException("Please input a positive value.");
        }

    }

    /**
     * Possible exceptions:
     * 1. Missing target index or index is invalid, includes wrong range or even not a number
     * 2. Command length not equals to 2
     * @param cmd Raw User Command
     * @throws IncorrectFormatException if the input command is in incorrect format,
     */
    private static void verifyDeleteGoalInput(String cmd) throws IncorrectFormatException, NumberFormatException {
        String[] cmdSplit = cmd.split(" ");
        if (cmdSplit.length == 1) {
            throw new IncorrectFormatException("Oops! Please provide the target index.");
        }

        if (cmdSplit.length != 2) {
            throw new IncorrectFormatException("Oops! Wrong format of delete goal instruction.");
        }

        int index = Integer.parseInt(cmdSplit[1]); //throws number format exception if not a number string
        if (index <= 0 || index > Duke.goals.goalCount){
            throw new IllegalValueException("Please input a valid index by referring to your goals list.");
        }
    }

    /**
     * This method is similar to verifyDeleteGoalInput method as the input of each command is similar
     * @param cmd Raw User Command
     * @throws IncorrectFormatException Either missing the target index or command contains more than 2 words
     * @throws NumberFormatException if an invalid number string is input, e.g. achieve one
     */
    private static void verifyAchieveGoalInput(String cmd) throws IncorrectFormatException, NumberFormatException {
        String[] cmdSplit = cmd.split(" ");
        if (cmdSplit.length == 1) {
            throw new IncorrectFormatException("Oops! Please tell me the target index.");
        }

        if (cmdSplit.length != 2) {
            throw new IncorrectFormatException("Oops! Wrong format of achieve goal instruction.");
        }

        int index = Integer.parseInt(cmdSplit[1]); //throws number format exception if not a number string
        if (index <= 0 || index > Duke.goals.goalCount){
            throw new IllegalValueException("Please input a valid index by referring to your goals list.");
        }
    }

    /**
     * This method will first check if the raw user input is in the correct format.
     * If not, terminate the method and throws error message.
     * If yes, continue to add a new goal object into the goals list.
     * @param userCmd represents raw user input
     * @throws IncorrectFormatException if user input is in wrong format
     * @throws NumberFormatException if the user does not input a valid number
     */
    public static String addGoal(String userCmd) throws IncorrectFormatException, NumberFormatException {
        verifyGoalInput(userCmd); //if invalid, exceptions is thrown

        String[] cmdSplit = userCmd.split(" ");
        int calories = Integer.parseInt(cmdSplit[1]);
        String date = cmdSplit[3];

        Duke.goals.add(new Goal(calories, date));
        Duke.goals.goalCount++;

        return TextUi.addGoalSuccessMessage();
    }

    /**
     * Exception appears if the length of view goal command does not equal to 1
     * i.e. containing extra information
     * @param cmd Raw user command
     * @throws IncorrectFormatException
     */
    public static void verifyViewGoalInput(String cmd) throws IncorrectFormatException {
        String[] cmdSplit = cmd.split(" ");
        if (cmdSplit.length != 1){
            throw new IncorrectFormatException("Use single word [viewG] to view your goal list.");
        }
    }

}
