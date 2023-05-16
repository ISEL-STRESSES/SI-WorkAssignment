package pt.isel.model.views;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;

@Converter(autoApply = true)
public class Email implements AttributeConverter<pt.isel.model.types.Email, String> {

    @Override
    public String convertToDatabaseColumn(pt.isel.model.types.Email email) {
        return null;
    }

    @Override
    public pt.isel.model.types.Email convertToEntityAttribute(String string) {
        return null;
    }
}
