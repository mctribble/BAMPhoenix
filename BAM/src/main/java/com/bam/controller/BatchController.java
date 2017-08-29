package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
 
@RestController
@RequestMapping(value = "/api/v1/Batches/")
public class BatchController {

  private static final String EMAIL = "email";

  @Autowired
  BatchService batchService;

  @Autowired
  BamUserService bamUserService;

  @RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Batch> getBatchAll() {
    return batchService.getBatchAll();
  }

  @RequestMapping(value = "Past", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Batch> getPastBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> pastBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).after(b.getEndDate())) {
        pastBatches.add(b);
      }
    }
    return pastBatches;
  }

  @RequestMapping(value = "Future", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Batch> getFutureBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> futureBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).before(b.getStartDate())) {
        futureBatches.add(b);
      }
    }
    return futureBatches;
  }

  @RequestMapping(value = "InProgress", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public Batch getBatchInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    Batch batchInProgress = null;
    Timestamp t = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (t.after(b.getStartDate()) && t.before(b.getEndDate())) {
        batchInProgress = b;
        break;
      }
    }
    return batchInProgress;
  }

  @RequestMapping(value = "AllInProgress", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Batch> getAllBatchesInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> batchesInProgress = new ArrayList<>();
    Timestamp time = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (time.after(b.getStartDate()) && time.before(b.getEndDate())) {
        batchesInProgress.add(b);
      }
    }
    return batchesInProgress;
  }

  @RequestMapping(value = "Edit", method = RequestMethod.POST, produces = "application/json")
  public void updateUser(@RequestBody String jsonObject) {
    Batch currentBatch = null;
    try {
      currentBatch = new ObjectMapper().readValue(jsonObject, Batch.class);
    } catch (IOException e) {
      LogManager.getRootLogger().error(e);
    }

    batchService.addOrUpdateBatch(currentBatch);
  }

  @RequestMapping(value = "ById", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public Batch getBatchById(HttpServletRequest request) {
    return batchService.getBatchById(Integer.parseInt(request.getParameter("batchId")));
  }

  @RequestMapping(value = "UpdateBatch", method = RequestMethod.POST)
  public void updateBatch(@RequestBody Batch batch) {
    batchService.addOrUpdateBatch(batch);
  }

  @RequestMapping(value = "BatchTypes", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<BatchType> getAllBatchTypes() {
    return batchService.getAllBatchTypes();
  }

}
