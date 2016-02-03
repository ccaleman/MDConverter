package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Default;
import org.mdconverter.api.topologystructure.model.impl.PositionRestraint;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class PositionRestraintH extends Convert<PositionRestraint> {

    public PositionRestraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<PositionRestraint>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<PositionRestraint>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3")).build();

    @Override
    protected void conversionImpl(PositionRestraint elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<PositionRestraint>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<PositionRestraint> getImplementedClass() {
        return PositionRestraint.class;
    }
}
