package com.siemens.hrsync.service;
import com.siemens.hrsync.external.HrClient;
import com.siemens.hrsync.model.Employee;
import com.siemens.hrsync.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;

// BUG: nenhum @Bulkhead no HrClient.fetchEmployees(). Se o HR legado ficar
// lento, TODAS as threads do Tomcat disponiveis ficam presas a espera dessa
// chamada - esgota a app inteira, ate pedidos que nada tem a ver com sync.
@Service
public class EmployeeSyncService {
  private final HrClient hrClient;
  private final EmployeeRepository repository;
  public EmployeeSyncService(HrClient hrClient, EmployeeRepository repository) {
    this.hrClient = hrClient; this.repository = repository;
  }

  public void sync() {
    hrClient.fetchEmployees().forEach(e -> {
      Employee emp = new Employee();
      emp.setExternalId(e.id()); emp.setName(e.name()); emp.setEmail(e.email()); emp.setLastSyncedAt(Instant.now());
      repository.save(emp);
    });
  }
}
