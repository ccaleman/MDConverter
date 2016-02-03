package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.PairImpl;
import org.mdconverter.api.topologystructure.model.impl.PairNB;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by miso on 08.01.2016.
 */
public class PairH extends Convert<PairImpl> {

    public PairH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<PairImpl>>> funcMapPairNB = ImmutableMap.<Integer, Map<String, MapContainer<PairImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c1", "c2", "c3", "c4")).build();

    private final Map<Integer, Map<String, MapContainer<PairImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<PairImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(TRUE, TRUE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c2", "c3", "c4", "c5")).build();

    @Override
    protected void conversionImpl(PairImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<PairImpl>> functions = null;
        if (elem instanceof PairNB) {
            functions = funcMapPairNB.get(elem.getFuncType());
        } else {
            functions = funcMap.get(elem.getFuncType());
        }
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<PairImpl> getImplementedClass() {
        return PairImpl.class;
    }
}
