package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.PairImpl;
import org.mdconverter.api.topologystructure.model.impl.PairNB;
import org.mdconverter.unitconverter.Convert;

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

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     * defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     * eg. funcType 1 only has four values and alternative units for c3 and c4 will be considered
     */
    private final Map<Integer, Map<String, MapContainer<PairImpl>>> funcMapPairNB = ImmutableMap.<Integer, Map<String, MapContainer<PairImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE, TRUE, TRUE), "c1", "c2", "c3", "c4")).build();

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     *     defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     *     eg. funcType 1 only has two values and alternative units will be considered
     */
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
