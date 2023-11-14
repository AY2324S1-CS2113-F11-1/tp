package seedu.duke.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.Command;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.IncorrectCommand;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void parse_emptyInput_returnsIncorrect() throws Exception {
        final String[] emptyInputs = { "", "  ", "\n  \n" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    /**
     * Asserts that parsing the given inputs will return IncorrectCommand with the
     * given feedback message.
     * 
     * @throws Exception
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) throws Exception {
        for (String input : inputs) {
            final IncorrectCommand result = parseAndAssertCommandType(input, IncorrectCommand.class);
            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }
}
