package org.mdconverter.unitconverter.topologyhelper;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jscience.mathematics.number.Real;
import org.mdconverter.api.plugin.PluginManifest;
import org.mdconverter.api.plugin.type.PluginType;
import org.mdconverter.api.topologystructure.model.TopologyStructure;
import org.mdconverter.api.topologystructure.model.api.Default;
import org.mdconverter.api.topologystructure.model.api.FuncType;
import org.mdconverter.api.topologystructure.model.api.ValueHolder;
import org.mdconverter.api.topologystructure.model.impl.AngleImpl;

import javax.measure.quantity.Quantity;
import javax.measure.unit.NonSI;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static javax.measure.unit.SI.JOULE;
import static javax.measure.unit.SI.KILO;

/**
 * Created by miso on 07.01.2016.
 */
public abstract class Convert<T extends ValueHolder> {

    //Fields
    private final Pattern REFL = Pattern.compile("'([\\d\\w]+?)'");
    private final double cal = 4.1868;
    private Map<String, Map<String, Map<String, String>>> readerUnits;
    private Map<String, Map<String, Map<String, String>>> writerUnits;
    private Default def;

    public Convert(Map<String, Map<String, Map<String, String>>> readerUnits,
                   Map<String, Map<String, Map<String, String>>> writerUnits, Default def) {
        //TODO generate custom units from manifest.json automatically
        UnitFormat.getInstance().label(NonSI.E.divide(18.222615), "efact");
        UnitFormat.getInstance().label(KILO(JOULE.times(cal)), "kcal");
        UnitFormat.getInstance().label(JOULE.times(cal), "cal");
        this.readerUnits = readerUnits;
        this.writerUnits = writerUnits;
        this.def = def;
    }

    /**
     * This is a SAM interface which can be used as a lambda expression in Java 8
     *
     * @param <T>
     */
    protected interface SetterFunction<T> {
        void apply(T elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits, boolean checkDefault);
    }

    /**
     * holds the Function and a boolean if in {@link Default} the comb_Rule should be checked (instead of c1 --> c1_1)
     * @param <T> can be for eg. {@link AngleImpl}
     */
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


    /**
     * starts conversion for the given element
     * @param elem the actual implementation of the {@link TopologyStructure} (eg. {@link AngleImpl})
     * @param readerUnits units for the ReaderPlugin from {@link PluginManifest}
     * @param writerUnits units for the WriterPlugin from {@link PluginManifest}
     */
    protected abstract void conversionImpl(T elem, Map<String, Object> readerUnits, Map<String, Object> writerUnits);

    /**
     * @return which class is handled by this {@link Convert} implementation
     */
    protected abstract Class<T> getImplementedClass();

    /**
     * converts the units from the ReaderPlugin to the WriterPlugin for the given List of Elements
     * @param elem a List of elements of the {@link TopologyStructure} (eg. {@link AngleImpl})
     * @return a List of elements with converted values {@link ValueHolder}
     */
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

    /**
     * checks if the actual implementation of the {@link TopologyStructure} (eg. {@link AngleImpl}) implements the {@link FuncType} interface
     * @return true if {@link FuncType} is implemented
     */
    private boolean checkFuncTypeExists() {
        return FuncType.class.isAssignableFrom(getImplementedClass());
    }

    /**
     * @param name the name of the requested unit from the given map (eg. c1_1)
     * @param units a Map of either Unit or a String with reflection call included
     * @param obj the actual implementation of the {@link TopologyStructure} (eg. {@link AngleImpl})
     * @return a Unit
     */
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

    /**
     * checks the support of {@link ValueHolder} fields for the given implementation of the {@link TopologyStructure} (eg. {@link AngleImpl}) <br>
     *     and remove unsupported functions from the given map
     * @param elem the actual implementation of the {@link TopologyStructure}
     * @param functions a Map with c1 - c6 keys and function values
     */
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

    /**
     * generate the Map for the c1 - c6 keys and functions as values
     * @param combRule a List of boolean for each args entry one
     * @param args a Array of Strings
     * @return a Map which holds the c1 - c6 keys and functions as values
     */
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

    /**
     * returns a function depending on the cType (c1 - c6) --> {@link ValueHolder}
     * @param cType defines which function will be returned
     * @return a function
     */
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

    /**
     * Replaces the reflection call on the given unitString
     * @param call defines a unit with a method name which should be called
     * @param obj the object which contains the method
     * @return a Unit
     */
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

    /**
     * converts the units from the {@link PluginManifest} into a map which could contain String or Unit as value
     * @param clazz the actual implementation of the {@link TopologyStructure} which should be converted
     * @param funcType the defined {@link FuncType} of the actual implementation
     * @param type defines which units should be chosen
     * @return a Map of for example <c1, U·nm^'getAlpha'>
     */
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

    /**
     * checks if the given String matches a Regex and defines a Reflection method call
     *
     * @param unit for example kJ·nm^-2/mol or U·nm^'getAlpha' if a method call is included
     * @return either the already transformed unit or the String if method call is included
     */
    private Object checkSpecialUnits(String unit) {
        if (REFL.matcher(unit).find()) {
            return unit;
        } else {
            return Unit.valueOf(unit);
        }
    }

    /**
     * converts the given value from unit X to unit Y
     *
     * @param value the value to convert
     * @param X     the unit of value
     * @param Y     the unit value should be
     * @return the converted value from X to Y
     */
    public static Real convertFromXInY(Real value, Unit<? extends Quantity> X, Unit<? extends Quantity> Y) {
        javax.measure.converter.UnitConverter converter = X.getConverterTo(Y);
        double convert = converter.convert(value.doubleValue());
        return Real.valueOf(convert);
    }

    /**
     * maps the actual given classname to the manifest.json unit section
     * @param name the classname
     * @return the manifest.json unit section
     */
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
