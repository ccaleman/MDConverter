package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jscience.mathematics.number.Real;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;

import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by miso on 07.01.2016.
 */
public abstract class Convert<T extends ValueHolder> {

    private final Pattern REFL = Pattern.compile("'([\\d\\w]+?)'");
    private final double amu = 1.6605402E-27;
    private final double cal = 4.1868;
    private Map<String, Map<String, Map<String, String>>> readerUnits;
    private Map<String, Map<String, Map<String, String>>> writerUnits;
    private Default def;

    public Convert(Map<String, Map<String, Map<String, String>>> readerUnits,
                   Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        this.readerUnits = readerUnits;
        this.writerUnits = writerUnits;
        this.def = def;
    }

    protected interface SetterFunction<T> {
        void apply(T elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits, boolean checkDefault);
    }

    protected static class MapContainer<T> {
        private boolean checkDefault;
        private SetterFunction<T> setterFunction;

        public MapContainer(boolean checkDefault, SetterFunction<T> setterFunction) {
            this.checkDefault = checkDefault;
            this.setterFunction = setterFunction;
        }

        public boolean isCheckDefault() {
            return checkDefault;
        }

        public SetterFunction<T> getSetterFunction() {
            return setterFunction;
        }
    }

    protected abstract void conversionImpl(T elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits);

    protected abstract Class<T> getImplementedClass();

    public List<T> convert(List<T> elem) {
        if (elem.size() > 0) {
            Class clazz = elem.get(0).getClass();
            if (!checkFuncTypeExists()) {
                final Map<String, Object> readerUnits = getUnitsForClass(clazz, "none", PluginType.READER);
                final Map<String, Object> writerUnits = getUnitsForClass(clazz, "none", PluginType.WRITER);
                elem.forEach(t -> conversionImpl(t, readerUnits, writerUnits));
            } else {
                for (T t : elem) {
                    FuncType ft = (FuncType) t;
                    final Map<String, Object> readerUnits = getUnitsForClass(clazz, ft.getFuncType().toString(), PluginType.READER);
                    final Map<String, Object> writerUnits = getUnitsForClass(clazz, ft.getFuncType().toString(), PluginType.WRITER);
                    conversionImpl(t, readerUnits, writerUnits);
                }
            }
        }
        return null;
    }

    private boolean checkFuncTypeExists() {
        return FuncType.class.isAssignableFrom(getImplementedClass());
    }

    protected Unit<? extends Quantity> getUnitForName(String name, Map<String, Object> units, T obj) {
        Unit<? extends Quantity> from;
        Object o = units.get(name);
        if (o != null) {
            if (o instanceof Unit) {
                from = (Unit<? extends Quantity>) o;
            } else {
                from = replaceReflectionCall(o.toString(), obj);
            }
            if (from == null) {
                throw new RuntimeException(String.format("Couldn't find unit for name: %s in Class: %s)",
                        name, obj.getClass().getSimpleName()));
            } else {
                return from;
            }
        } else {
            throw new RuntimeException(String.format("Unit %s isn't available for %s.", name, obj.getClass()));
        }
    }

    protected void checkValues(T elem, Map<String, MapContainer<T>> functions) {
        try {
            if (elem.getC1() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c1");
        }
        try {
            if (elem.getC2() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c2");
        }
        try {
            if (elem.getC3() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c3");
        }
        try {
            if (elem.getC4() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c4");
        }
        try {
            if (elem.getC5() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c5");
        }
        try {
            if (elem.getC6() == null) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            functions.remove("c6");
        }
    }

    protected Map<String, MapContainer<T>> generateMap(List<Boolean> combRule, String... args) {
        Map<String, MapContainer<T>> map = Maps.newHashMap();
        if (combRule.size() == args.length) {
            for (int i = 0; i < combRule.size(); i++) {
                map.put(args[i], new MapContainer<>(combRule.get(i), createSetter(args[i])));
            }
        } else {
            throw new RuntimeException("error in generateMap in UnitConverter");
        }
        return map;
    }

    private SetterFunction<T> createSetter(String cType) {
        switch (cType) {
            case "c1":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC1(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC1().toString()),
                                    getUnitForName("c1_1", readerUnits, elem), getUnitForName("c1_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC1(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC1().toString()),
                            getUnitForName("c1", readerUnits, elem), getUnitForName("c1", writerUnits, elem)).toString()));
                };
            case "c2":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC2(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC2().toString()),
                                    getUnitForName("c2_1", readerUnits, elem), getUnitForName("c2_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC2(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC2().toString()),
                            getUnitForName("c2", readerUnits, elem), getUnitForName("c2", writerUnits, elem)).toString()));
                };
            case "c3":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC3(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC3().toString()),
                                    getUnitForName("c3_1", readerUnits, elem), getUnitForName("c3_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC3(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC3().toString()),
                            getUnitForName("c3", readerUnits, elem), getUnitForName("c3", writerUnits, elem)).toString()));
                };
            case "c4":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC4(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC4().toString()),
                                    getUnitForName("c4_1", readerUnits, elem), getUnitForName("c4_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC4(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC4().toString()),
                            getUnitForName("c4", readerUnits, elem), getUnitForName("c4", writerUnits, elem)).toString()));
                };
            case "c5":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC5(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC5().toString()),
                                    getUnitForName("c5_1", readerUnits, elem), getUnitForName("c5_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC5(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC5().toString()),
                            getUnitForName("c5", readerUnits, elem), getUnitForName("c5", writerUnits, elem)).toString()));
                };
            case "c6":
                return (elem, readerUnits, writerUnits, checkDefault) -> {
                    if (checkDefault) {
                        if (def.getCombRule().equals(1)) {
                            elem.setC6(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC6().toString()),
                                    getUnitForName("c6_1", readerUnits, elem), getUnitForName("c6_1", writerUnits, elem)).toString()));
                            return;
                        }
                    }
                    elem.setC6(new BigDecimal(convertFromXInY(Real.valueOf(elem.getC6().toString()),
                            getUnitForName("c6", readerUnits, elem), getUnitForName("c6", writerUnits, elem)).toString()));
                };
            default:
                throw new RuntimeException(String.format("create setter in %s failed", getImplementedClass()));
        }
    }

    private Unit<? extends Quantity> replaceReflectionCall(String call, T obj) {
        java.lang.reflect.Method method;
        String[] s = StringUtils.substringsBetween(call, "'", "'");
        for (String methodName : s) {
            try {
                method = obj.getClass().getMethod(methodName);
                Object invoke = method.invoke(obj);
                call = call.replace("'" + methodName + "'", invoke.toString());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(String.format("Method \"%s()\" couldn't be found defined in manifest.json for class \"%s\""
                        , methodName, obj.getClass().getSimpleName()));
            }
        }
        try {
            return Unit.valueOf(call);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("Defined unit \"%s\" is unknown to the system!", call));

        }
    }

    private Map<String, Object> getUnitsForClass(Class clazz, String funcType, PluginType type) {
        String keyForClassname = getKeyForClassname(clazz.getSimpleName());
        Map<String, Object> units = Maps.newHashMap();
        Map<String, String> map;
        if (type.equals(PluginType.READER)) {
            map = readerUnits.get(keyForClassname).get(funcType);
        } else {
            map = writerUnits.get(keyForClassname).get(funcType);
        }
        if (map != null) {
            map.forEach((s, s2) -> units.put(s, checkSpecialUnits(s2)));
            return units;
        } else {
            throw new RuntimeException(String.format("No measurement units defined for: %s", keyForClassname));
        }
    }

    private Object checkSpecialUnits(String s2) {
        if (s2.equals("amu")) {
            return Unit.valueOf("kg").times(amu);
        } else if (REFL.matcher(s2).find()) {
            return s2;
        } else if (s2.contains("cal")) {
            String replace = s2.replace("cal", "J");
            return Unit.valueOf(replace).times(cal);
        } else {
            return Unit.valueOf(s2);
        }
    }

    public static Real convertFromXInY(Real length, Unit<? extends Quantity> X, Unit<? extends Quantity> Y) {
        javax.measure.converter.UnitConverter converter = X.getConverterTo(Y);
        double convert = converter.convert(length.doubleValue());
        return Real.valueOf(convert);
    }

    private String getKeyForClassname(String name) {
        name = name.toLowerCase();
        if (name.endsWith("impl")) {
            name = name.substring(0, name.length() - 4);
        }
        if ("atom/atomtypes".contains(name)) {
            return "atom/atomTypes";
        } else if ("bond/bondtypes".contains(name)) {
            return "bond/bondTypes";
        } else if ("pair/pairtypes".contains(name)) {
            return "pair/pairTypes";
        } else if ("pairNB".contains(name)) {
            return "pairNB";
        } else if ("angle/angletypes".contains(name)) {
            return "angle/angleTypes";
        } else if ("dihedral/dihedraltypes".contains(name)) {
            return "dihedral/dihedralTypes";
        } else if ("constraint/constrainttypes".contains(name)) {
            return "constraint/constraintTypes";
        } else if ("nonbond_params".contains(name)) {
            return "nonbond_params";
        } else if ("settle".contains(name)) {
            return "settle";
        } else if ("positionrestraint".contains(name)) {
            return "positionRestraint";
        } else if ("distancerestraint".contains(name)) {
            return "distanceRestraint";
        } else if ("dihedralrestraints".contains(name)) {
            return "dihedralRestraints";
        } else if ("orientationRestraint".contains(name)) {
            return "orientationRestraint";
        } else if ("anglerestraint".contains(name)) {
            return "angleRestraint";
        } else if ("anglerestraintz".contains(name)) {
            return "angleRestraintZ";
        } else {
            throw new RuntimeException(String.format("Defined TopologyStructureClass couldn't be found: %s", name));
        }
    }
}
