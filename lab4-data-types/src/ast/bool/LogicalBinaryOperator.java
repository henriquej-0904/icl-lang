package ast.bool;

public enum LogicalBinaryOperator
{
    /**
     * Represents a logical AND of 2 boolean values.
     */
    AND("&&"),

    /**
     * Represents a logical OR of 2 boolean values.
     */
    OR("||");


    private String operator;

    private LogicalBinaryOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return this.operator;
    }
}
