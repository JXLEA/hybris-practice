package com.epam.training.core.decorator;

import de.hybris.platform.impex.jalo.header.AbstractImpExCSVCellDecorator;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Objects;

public class ProductCodeDecorator extends AbstractImpExCSVCellDecorator {

    @Override
    public String decorate(int i, Map<Integer, String> map) {
        var code = map.get(i);
        Objects.requireNonNull(code);
        return StringUtils.deleteWhitespace(code.toLowerCase());
    }
}
