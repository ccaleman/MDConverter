package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.impl.DefaultImpl;
import org.mdconverter.api.topologystructure.model.impl.DihedralImpl;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class DihedralH extends Convert<DihedralImpl> {

    public DihedralH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, DefaultImpl def) {
        super(readerUnits, writerUnits, def);
    }

    /**
     * generate a map for all existing {@link FuncType} for this class and <br>
     * defines if a alternative unit can be defined if comb_Rule in {@link Default} is 1 <br>
     * eg. funcType 1 only has two values and no alternative unit will be considered
     */
    private final Map<Integer, Map<String, MapContainer<DihedralImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<DihedralImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(3, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4", "c5", "c6"))
            .put(4, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(5, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4"))
            .put(8, generateMap(Lists.newArrayList(FALSE), "c2"))
            .put(9, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(10, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(11, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2")).build();

    @Override
    protected void conversionImpl(DihedralImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<DihedralImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<DihedralImpl> getImplementedClass() {
        return DihedralImpl.class;
    }
}
