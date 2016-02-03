package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.mdconverter.api.topologystructure.model.impl.Default;
import org.mdconverter.api.topologystructure.model.impl.DihedralRestraint;

import java.util.Map;

import static java.lang.Boolean.FALSE;

/**
 * Created by miso on 08.01.2016.
 */
public class DihedralRestraintH extends Convert<DihedralRestraint> {

    public DihedralRestraintH(Map<String, Map<String, Map<String, String>>> readerUnits, Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        super(readerUnits, writerUnits, def);
    }

    private final Map<Integer, Map<String, MapContainer<DihedralRestraint>>> funcMap = ImmutableMap.<Integer, Map<String, MapContainer<DihedralRestraint>>>builder()
            .put(1, generateMap(Lists.newArrayList(FALSE, FALSE), "c1", "c2")).build();

    @Override
    protected void conversionImpl(DihedralRestraint elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits) {
        Map<String, MapContainer<DihedralRestraint>> functions = funcMap.get(elem.getFuncType());
        checkValues(elem, functions);
        functions.forEach((key, container) -> container.getSetterFunction().apply(elem, readerUnits, writerUnits, container.isCheckDefault()));
    }

    @Override
    protected Class<DihedralRestraint> getImplementedClass() {
        return DihedralRestraint.class;
    }
}
