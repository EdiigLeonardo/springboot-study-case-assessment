package com.siemens.hrsync.controller;
import com.siemens.hrsync.service.EmployeeSyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/sync")
public class SyncController {
  private final EmployeeSyncService service;
  public SyncController(EmployeeSyncService service) { this.service = service; }
  @PostMapping public void trigger() { service.sync(); }
}
