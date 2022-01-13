package typeError;

/**
 * Represents an error when trying to apply an operator to illegal arguments.
 */
public class IllegalOperatorException extends TypeErrorException
{
    protected final static String DEFAULT_ERROR_MSG_FORMAT = "Illegal arguments to %s operator. Expected %s and got %s";

    public final String operator;

    /**
     * @param operator
     */
    public IllegalOperatorException(String operator, String typeExpected, String TypeGot)
    {
        super(String.format(DEFAULT_ERROR_MSG_FORMAT, operator, typeExpected, TypeGot));
        this.operator = operator;
    }

    /**
     * @param message
     * @param operator
     */
    public IllegalOperatorException(String message, String operator) {
        super(message);
        this.operator = operator;
    }
}
