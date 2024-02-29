package ro.ubbcluj.map.demogui.domain.validators;

import ro.ubbcluj.map.demogui.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if(entity.getId().getLeft()<0)
            throw new ValidationException("Id invalid!");
        if(entity.getId().getRight()<0)
            throw new ValidationException("Id invalid!");
    }
}
