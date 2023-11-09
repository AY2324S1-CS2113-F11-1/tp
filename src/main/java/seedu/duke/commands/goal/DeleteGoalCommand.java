package seedu.duke.commands.goal;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.data.GoalList;
import seedu.duke.data.exception.IncorrectFormatException;

public class DeleteGoalCommand extends Command {
    public static final String COMMAND_WORD = "deleteg";
    public String feedbackToUser;

    public DeleteGoalCommand(String cmd) {
        super(cmd);
    }

    /**
     * execute to remove a goal object from global goal list, by indexing
     * @return feedback to user of either success or fail
     * In case of fail, tell user the specific problem.
     */
    @Override
    public CommandResult execute() {
        try {
            feedbackToUser = GoalList.deleteGoal(this.userCommand);

        } catch (NumberFormatException nfe) {
            feedbackToUser = "Please input a valid number for delete index.";
        } catch (IncorrectFormatException ife) {
            feedbackToUser = ife.getMessage();
        }

        return new CommandResult(feedbackToUser);
    }


}