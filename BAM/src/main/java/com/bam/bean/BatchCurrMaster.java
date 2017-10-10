package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * Author: Matthew Hill
 * Class designed to represent the relationship
 * between a particular batch and its chosen master version of a curriculum
 */
@Entity
@Table(name = "BATCH_CURR_MASTER")
public class BatchCurrMaster implements Serializable {

    @EmbeddedId
    private BCMKey bcmkey;


    private Integer master_Version;

    public BCMKey getBcmkey() {
        return bcmkey;
    }

    public void setBcmkey(BCMKey bcmkey) {
        this.bcmkey = bcmkey;
    }

    @Column(name = "MASTER_VERSION")
    public Integer getMv() {
        return master_Version;
    }

    public void setMv(Integer master_Version) {
        this.master_Version = master_Version;
    }


}