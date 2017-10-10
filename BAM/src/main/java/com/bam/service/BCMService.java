package com.bam.service;

import com.bam.bean.BCMKey;
import com.bam.bean.BatchCurrMaster;
import com.bam.repository.BCMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service class created to interact with the Batch_curr_master table
 */
@Service
public class BCMService {

    @Autowired
    BCMRepository bcmRepository;

    public void setMaster(int bid, String cname, int vers)
    {


        BCMKey b = new BCMKey(bid, cname);
        BatchCurrMaster bcm = new BatchCurrMaster();
        bcm.setBcmkey(b);
        bcm.setMv(vers);
        bcmRepository.save(bcm);

    }

    public Integer getMaster(int bid, String cname)
    {
        BCMKey b = new BCMKey(bid, cname);
        BatchCurrMaster bcm = bcmRepository.findOne(b);
        return bcm.getMv();

    }

}
