package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.NonBondParamImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 07.01.2016.
 */
public class NonBondParamH extends Convert<NonBondParamImpl> {

    public NonBondParamH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<NonBondParamImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<NonBondParamImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(TRUE, TRUE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3")).build();

    @Override
    protected void conversionImpl(NonBondParamImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<NonBondParamImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<NonBondParamImpl> getImplementedClass() {
        return NonBondParamImpl.class;
    }
}
