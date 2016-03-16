package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.PositionRestraintImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class PositionRestraintH extends Convert<PositionRestraintImpl> {

    public PositionRestraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     * defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     * eg. funcType 1 only has three values and no alternative unit will be considered
     */
    private final Map<Integer, Map<String, MapContainer<PositionRestraintImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<PositionRestraintImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3")).build();

    @Override
    protected void conversionImpl(PositionRestraintImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<PositionRestraintImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<PositionRestraintImpl> getImplementedClass() {
        return PositionRestraintImpl.class;
    }
}
