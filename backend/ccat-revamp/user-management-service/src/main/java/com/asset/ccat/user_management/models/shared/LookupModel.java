/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.shared;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Mahmoud Shehab
 */
public class LookupModel implements Serializable, Comparable, Cloneable {

    protected int id;
    protected int seqId;
    protected String name;
    protected String value;
    protected long longid;

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public LookupModel() {
    }

    public LookupModel(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getLongid() {
        return longid;
    }

    public void setLongid(long longid) {
        this.longid = longid;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.id;
        hash = 43 * hash + this.seqId;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.value);
        hash = 43 * hash + (int) (this.longid ^ (this.longid >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LookupModel other = (LookupModel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.seqId != other.seqId) {
            return false;
        }
        if (this.longid != other.longid) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "LookupModel{" + "id=" + id + ", seqId=" + seqId + ", name=" + name + ", value=" + value + ", longid=" + longid + '}';
    }

    @Override
    public int compareTo(Object arg0) {
        LookupModel model = (LookupModel) arg0;
        if (model.getId() > this.id) {
            return -1;
        } else if (model.getId() == this.id) {
            return 0;
        } else {
            return 1;
        }
    }

}
