package org.mdconverter.ambertr.fileparser;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by miso on 11.02.2016.  <br>
 *     Amber specific definitions
 *     available pointers for amber input format and their position in an array
 */
public enum AmberPointer {
    NATOM(0),
    NTYPES(1),
    NBONH(2),
    MBONA(3),
    NTHETH(4),
    MTHETA(5),
    NPHIH(6),
    MPHIA(7),
    NHPARM(8),
    NPARM(9),
    NNB(10),
    NRES(11),
    NBONA(12),
    NTHETA(13),
    NPHIA(14),
    NUMBND(15),
    NUMANG(16),
    NPTRA(17),
    NATYP(18),
    NPHB(19),
    IFPERT(20),
    NBPER(21),
    NGPER(22),
    NDPER(23),
    MBPER(24),
    MGPER(25),
    MDPER(26),
    IFBOX(27),
    NMXRS(28),
    IFCAP(29),
    NUMEXTRA(30),
    NCOPY(31);

    private int pointerNr;

    private static Map<Integer, AmberPointer> map = Maps.newHashMap();

    static {
        for (AmberPointer amberEnum : AmberPointer.values()) {
            map.put(amberEnum.pointerNr, amberEnum);
        }
    }

    private AmberPointer(final int pn) {
        pointerNr = pn;
    }

    public static AmberPointer valueOf(int pointerNr) {
        return map.get(pointerNr);
    }
}
