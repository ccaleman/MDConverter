package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.impl.AtomImpl;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 07.01.2016.
 */
public class AtomH extends Convert<AtomImpl> {

    public AtomH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     * defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     * eg. funcType 1 only has four values and c3 and c4 may use alternative units c3_1 and c4_1 <br>
     * IMPORTANT: no implementation of {@link FuncType} --> 1 eguals none in manifest.json
     */
    private final Map<Integer, Map<String, MapContainer<AtomImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<AtomImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c1", "c2", "c3", "c4")).build();

    @Override
    protected void conversionImpl(AtomImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<AtomImpl>> functions = funcMap.get(1);
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<AtomImpl> getImplementedClass() {
        return AtomImpl.class;
    }
}
