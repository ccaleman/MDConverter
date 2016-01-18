package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.ConstraintType;
import org.mdconverter.api.topologystructure.model.Default;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 07.01.2016.
 */
public class ConstraintTypeH extends Convert<ConstraintType> {

    public ConstraintTypeH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<ConstraintType>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<ConstraintType>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE), "c1"))
            .put(2, generateMap(Lists.newArrayList(FALSE), "c1")).build();

    @Override
    protected void conversionImpl(ConstraintType elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<ConstraintType>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<ConstraintType> getImplementedClass() {
        return ConstraintType.class;
    }
}
