package com.bam.logging;

import java.util.HashMap;
import java.util.Map;
import com.splunk.Service;

public class SplunkManager {

  private Map<String, Object> connectionArgs;

  public SplunkManager() {
    connectionArgs = new HashMap<>();
    connectionArgs.put("host", "https://prd-p-9hp2hbcv2cnv.cloud.splunk.com");
    connectionArgs.put("username", "Tkk");
    connectionArgs.put("password", "RevaBam2017#");
    connectionArgs.put("port", 8089);
    connectionArgs.put("scheme", "https");
  }

  public Service getService() {
    return Service.connect(connectionArgs);
  }
}
