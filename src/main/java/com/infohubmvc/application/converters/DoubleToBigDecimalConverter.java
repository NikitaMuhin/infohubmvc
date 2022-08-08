package com.infohubmvc.application.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.math.BigDecimal;

public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {
    @Override
    public Result<BigDecimal> convertToModel(Double presentation, ValueContext valueContext) {
        if(presentation == null){
            return Result.ok(BigDecimal.valueOf(0));
        } else {
            return Result.ok(BigDecimal.valueOf(presentation));
        }
    }

    @Override
    public Double convertToPresentation(BigDecimal model, ValueContext valueContext) {
        if (model!=null) return model.doubleValue();
        return null;
    }
}