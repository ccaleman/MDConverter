package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.OrientationRestraintImpl;
import org.mdconverter.unitconverter.Convert;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class OrientationRestraintH extends Convert<OrientationRestraintImpl> {

    public OrientationRestraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     * defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     * eg. funcType 1 only has three values and no alternative units will be considered
     */
    private final Map<Integer, Map<String, MapContainer<OrientationRestraintImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<OrientationRestraintImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3")).build();

    @Override
    protected void conversionImpl(OrientationRestraintImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<OrientationRestraintImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<OrientationRestraintImpl> getImplementedClass() {
        return OrientationRestraintImpl.class;
    }
}
