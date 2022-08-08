package com.infohubmvc.application.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.math.BigDecimal;

public class IntegerToLongConverter implements Converter<Integer, Long> {
    @Override
    public Result<Long> convertToModel(Integer presentation, ValueContext valueContext) {
        if (presentation == null) {
            return Result.ok(0L);
        } else {
            return Result.ok(Long.valueOf(presentation));
        }
    }

    @Override
    public Integer convertToPresentation(Long model, ValueContext valueContext) {
        if (model != null) return model.intValue();
        return null;
    }
}