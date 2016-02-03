package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Bond;
import org.mdconverter.api.topologystructure.model.impl.Default;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class BondH extends Convert<Bond> {
    public BondH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<Bond>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<Bond>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(3, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3"))
            .put(4, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3"))
            .put(6, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(7, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(8, generateMap(Lists.newArrayList(FALSE), "c2"))
            .put(9, generateMap(Lists.newArrayList(FALSE), "c2"))
            .put(10, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4")).build();

    @Override
    protected void conversionImpl(Bond elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<Bond>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<Bond> getImplementedClass() {
        return Bond.class;
    }
}
