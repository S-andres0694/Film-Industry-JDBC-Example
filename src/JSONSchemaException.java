/**
 *
 * Custom exception used when one of the files used in the parsing does pass the validation process of the JSONTester Class
 *
 * @author 230018374
 *
 */

public class JSONSchemaException extends Exception{

    public JSONSchemaException(String message){

        super(message);

    }

}
