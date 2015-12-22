package org.mdconverter.api.topologystructure.model;

/**
 * Created by miso on 04.12.2015.
 */
public class DihedralRestraint {

    private Integer ai;
    private Integer aj;
    private Integer ak;
    private Integer al;
    private int funcType;
    private int label;
    private Integer phi;
    private Integer dphi;
    private Integer kfac;
    private Integer power;

    public DihedralRestraint() {
    }

    public DihedralRestraint(Integer ai, Integer aj, Integer ak, Integer al, int funcType, int label, Integer phi, Integer dphi, Integer kfac, Integer power) {
        this.ai = ai;
        this.aj = aj;
        this.ak = ak;
        this.al = al;
        this.funcType = funcType;
        this.label = label;
        this.phi = phi;
        this.dphi = dphi;
        this.kfac = kfac;
        this.power = power;
    }

    public Integer getAi() {
        return ai;
    }

    public void setAi(Integer ai) {
        this.ai = ai;
    }

    public Integer getAj() {
        return aj;
    }

    public void setAj(Integer aj) {
        this.aj = aj;
    }

    public Integer getAk() {
        return ak;
    }

    public void setAk(Integer ak) {
        this.ak = ak;
    }

    public Integer getAl() {
        return al;
    }

    public void setAl(Integer al) {
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

    public Integer getPhi() {
        return phi;
    }

    public void setPhi(Integer phi) {
        this.phi = phi;
    }

    public Integer getDphi() {
        return dphi;
    }

    public void setDphi(Integer dphi) {
        this.dphi = dphi;
    }

    public Integer getKfac() {
        return kfac;
    }

    public void setKfac(Integer kfac) {
        this.kfac = kfac;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
