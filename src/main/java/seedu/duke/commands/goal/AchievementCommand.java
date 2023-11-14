package seedu.duke.commands.goal;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.data.exception.IncorrectFormatException;
import seedu.duke.ui.TextUi;

public class AchievementCommand extends Command {
    public static final String COMMAND_WORD = "achievement";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show all your achievements\n"
            + "\tExample: " + COMMAND_WORD;
    public String feedbackToUser;

    public AchievementCommand(String cmd) {
        super(cmd);
    }
}
