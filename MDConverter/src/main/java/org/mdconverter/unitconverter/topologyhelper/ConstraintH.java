package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.ConstraintImpl;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class ConstraintH extends Convert<ConstraintImpl> {

    public ConstraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<ConstraintImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<ConstraintImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE), "c1"))
            .put(2, generateMap(Lists.newArrayList(FALSE), "c1")).build();

    @Override
    protected void conversionImpl(ConstraintImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<ConstraintImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<ConstraintImpl> getImplementedClass() {
        return ConstraintImpl.class;
    }
}
