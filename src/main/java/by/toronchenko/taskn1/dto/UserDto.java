package by.toronchenko.taskn1.dto;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.validators.Error;

import java.util.List;

public record UserDto(Long id, String name, String password, Company company, List<Error> errors) {
}
