package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Default;
import org.mdconverter.api.topologystructure.model.impl.Pair;
import org.mdconverter.api.topologystructure.model.impl.PairNB;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 08.01.2016.
 */
public class PairH extends Convert<Pair> {

    public PairH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<Pair>>> funcMapPairNB = ImmutableMap.<Integer, Map<String, MapContainer<Pair>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c1", "c2", "c3", "c4")).build();

    private final Map<Integer, Map<String, MapContainer<Pair>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<Pair>>>builder()
            .put(1, generateMap(Lists.newArrayList(TRUE, TRUE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c2", "c3", "c4", "c5")).build();

    @Override
    protected void conversionImpl(Pair elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<Pair>> functions = null;
        if (elem instanceof PairNB) {
            functions = funcMapPairNB.get(elem.getFuncType());
        } else {
            functions = funcMap.get(elem.getFuncType());
        }
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<Pair> getImplementedClass() {
        return Pair.class;
    }
}
