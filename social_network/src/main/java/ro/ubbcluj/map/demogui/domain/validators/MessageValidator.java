package ro.ubbcluj.map.demogui.domain.validators;

import ro.ubbcluj.map.demogui.domain.Message;

import java.util.Objects;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException{
        if(entity.getFrom()<0)
            throw new ValidationException("Id invalid!");
        entity.getTo().forEach(x->{
            if(x<0)
                throw new ValidationException("Id invalid!");
        });
        if(Objects.equals(entity.getMessage(), ""))
            throw new ValidationException("Mesaj invalid!");
    }
}
