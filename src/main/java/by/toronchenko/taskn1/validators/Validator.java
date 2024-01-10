package by.toronchenko.taskn1.validators;

public interface Validator<T> {

    ValidationResult isValid(T object);

}
