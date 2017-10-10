package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Author: Matthew Hill
 * Class designed to represent the primary key of the
 * association table BATCH_CURR_MASTER
 */
@Embeddable
public class BCMKey implements Serializable {

    private int batch_ID;

    private String curriculum_Name;

    public BCMKey(int batch_ID, String curriculum_Name) {
        this.batch_ID = batch_ID;
        this.curriculum_Name = curriculum_Name;
    }

    public BCMKey() {
    }

    @Column(name = "BATCH_ID")
    public int getBid() {
        return batch_ID;
    }

    public void setBid(int batch_ID) {
        this.batch_ID = batch_ID;
    }

    @Column(name = "CURRICULUM_NAME")
    public String getCname() {
        return curriculum_Name;
    }

    public void setCname(String curriculum_Name) {
        this.curriculum_Name = curriculum_Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BCMKey bcmKey = (BCMKey) o;

        if (getBid() != bcmKey.getBid()) return false;
        return getCname() != null ? getCname().equals(bcmKey.getCname()) : bcmKey.getCname() == null;
    }

    @Override
    public int hashCode() {
        int result = getBid();
        result = 31 * result + (getCname() != null ? getCname().hashCode() : 0);
        return result;
    }
}



