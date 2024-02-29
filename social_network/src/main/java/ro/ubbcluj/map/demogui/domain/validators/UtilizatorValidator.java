package ro.ubbcluj.map.demogui.domain.validators;

import ro.ubbcluj.map.demogui.domain.Utilizator;

import java.util.Objects;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if(Objects.equals(entity.getFirstName(), ""))
            throw new ValidationException("Prenume invalid!");
        if(Objects.equals(entity.getLastName(), ""))
            throw new ValidationException("Nume invalid!");
    }
}

