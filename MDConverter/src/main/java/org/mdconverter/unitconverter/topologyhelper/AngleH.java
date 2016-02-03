package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.AngleImpl;
import org.mdconverter.api.topologystructure.model.impl.Default;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class AngleH extends Convert<AngleImpl> {

    public AngleH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<AngleImpl>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<AngleImpl>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(2, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2"))
            .put(3, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE), "c1", "c2", "c3"))
            .put(4, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4"))
            .put(5, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4"))
            .put(6, generateMap(Lists.newArrayList(FALSE, FALSE, FALSE, FALSE, FALSE, FALSE), "c1", "c2", "c3", "c4", "c5", "c6"))
            .put(8, generateMap(Lists.newArrayList(FALSE), "c2"))
            .put(10, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2")).build();

    @Override
    protected void conversionImpl(AngleImpl elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<AngleImpl>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<AngleImpl> getImplementedClass() {
        return AngleImpl.class;
    }
}
