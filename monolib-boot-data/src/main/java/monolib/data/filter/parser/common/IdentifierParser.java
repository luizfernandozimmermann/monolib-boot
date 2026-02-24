package monolib.data.filter.parser.common;

import monolib.data.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdentifierParser {

    public static String parse(ParseValue parseValue) {
        parseValue.skipWhiteSpace();
        int start = parseValue.getPos();
        while (parseValue.getPos() < parseValue.getInput().length() &&
                (Character.isLetterOrDigit(parseValue.getInput().charAt(parseValue.getPos())) ||
                        parseValue.getInput().charAt(parseValue.getPos()) == '.' ||
                        parseValue.getInput().charAt(parseValue.getPos()) == '_')) {
            parseValue.increment();
        }
        return parseValue.getInput().substring(start, parseValue.getPos());
    }

}
