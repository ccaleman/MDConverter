package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Constraint;
import org.mdconverter.api.topologystructure.model.impl.Default;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class ConstraintH extends Convert<Constraint> {

    public ConstraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<Constraint>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<Constraint>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE), "c1"))
            .put(2, generateMap(Lists.newArrayList(FALSE), "c1")).build();

    @Override
    protected void conversionImpl(Constraint elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<Constraint>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<Constraint> getImplementedClass() {
        return Constraint.class;
    }
}
