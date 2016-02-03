package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Default;
import org.mdconverter.api.topologystructure.model.impl.DihedralType;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 07.01.2016.
 */
public class DihedralTypeH extends Convert<DihedralType> {

    public DihedralTypeH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<DihedralType>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<DihedralType>>>builder()
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
    protected void conversionImpl(DihedralType elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<DihedralType>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<DihedralType> getImplementedClass() {
        return DihedralType.class;
    }
}
