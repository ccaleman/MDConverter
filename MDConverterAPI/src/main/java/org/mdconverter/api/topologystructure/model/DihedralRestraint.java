package org.mdconverter.api.topologystructure.model;

/**
 * Created by miso on 04.12.2015.
 */
public class DihedralRestraint {

    private String ai;
    private String aj;
    private String ak;
    private String al;
    private int funcType;
    private int label;
    private Integer c1;
    private Integer c2;
    private Integer c3;
    private Integer c4;

    public DihedralRestraint() {
    }

    public DihedralRestraint(String ai, String aj, String ak, String al, int funcType, int label, Integer c1,
                             Integer c2, Integer c3, Integer c4) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.label = label;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    public DihedralRestraint(String ai, String aj, String ak, String al, int funcType, Integer c1,
                             Integer c2) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public String getAj() {
        return aj;
    }

    public void setAj(String aj) {
        this.aj = aj;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public int getFuncType() {
        return funcType;
    }

    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC3() {
        return c3;
    }

    public void setC3(Integer c3) {
        this.c3 = c3;
    }

    public Integer getC4() {
        return c4;
    }

    public void setC4(Integer c4) {
        this.c4 = c4;
    }
}
