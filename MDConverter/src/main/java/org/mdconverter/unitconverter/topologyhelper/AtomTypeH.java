package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.impl.AtomTypeImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 07.01.2016.
 */
public class AtomTypeH extends Convert<AtomTypeImpl> {

    public AtomTypeH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<AtomTypeImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<AtomTypeImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c1", "c2", "c3", "c4")).build();

    @Override
    protected void conversionImpl(AtomTypeImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<AtomTypeImpl>> functions = funcMap.get(1);
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<AtomTypeImpl> getImplementedClass() {
        return AtomTypeImpl.class;
    }
}