package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Default;
import org.mdconverter.api.topologystructure.model.impl.PairType;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 08.01.2016.
 */
public class PairTypeH extends Convert<PairType> {

    public PairTypeH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<PairType>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<PairType>>>builder()
            .put(1, generateMap(Lists.newArrayList(TRUE, TRUE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c2", "c3", "c4", "c5")).build();

    @Override
    protected void conversionImpl(PairType elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<PairType>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<PairType> getImplementedClass() {
        return PairType.class;
    }
}
