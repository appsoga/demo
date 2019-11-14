package com.example.demo.data.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.demo.data.MemberGroup;

import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class MemberGroupConverter implements AttributeConverter<MemberGroup, String> {

    @Override
    public String convertToDatabaseColumn(MemberGroup attribute) {
        return attribute.getValue();
    }

    @Override
    public MemberGroup convertToEntityAttribute(String dbData) {
        return MemberGroup.of(dbData);
    }

}