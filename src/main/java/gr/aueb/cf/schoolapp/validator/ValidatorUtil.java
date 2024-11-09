package gr.aueb.cf.schoolapp.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//utility class
public class ValidatorUtil {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * No instances of this class should be available
     */
    private ValidatorUtil() {}

    public static <T> List<String> validateDTO(T dto) {
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                errors.add(violation.getMessage());
                //.getMessage is the message we have given to dtos through @ in case hibernate criteria are not met otherwise there are default messages
            }
        }
        return errors;
    }
}
