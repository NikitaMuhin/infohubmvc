package com.infohubmvc.application.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateToStringConverter implements Converter<LocalDate, String> {
    @Override
    public Result<String> convertToModel(LocalDate presentation, ValueContext valueContext) {
        if (presentation==null) return Result.error("");
        return Result.ok(DateTimeFormatter.ofPattern("yyyyMMdd").format(presentation));
    }

    @Override
    public LocalDate convertToPresentation(String model, ValueContext valueContext) {
        try {
            return LocalDate.parse(model, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (NullPointerException e) {
            return null;
        }
    }
}