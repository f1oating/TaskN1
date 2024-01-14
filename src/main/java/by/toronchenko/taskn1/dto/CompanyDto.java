package by.toronchenko.taskn1.dto;

import by.toronchenko.taskn1.validators.Error;

import java.util.List;

public record CompanyDto(Long id, String name, List<Error> errors) {
}
