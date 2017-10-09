package com.bam.repository;

import com.bam.bean.BCMKey;
import com.bam.bean.BatchCurrMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Matthew Hill
 * Additional DAO for Batch_curr_master table
 */
@Repository
public interface BCMRepository extends JpaRepository<BatchCurrMaster, BCMKey> {

}
