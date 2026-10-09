package com.devoteam.payments.solid;
import org.springframework.stereotype.Service;
// BUG (SRP): uma classe, 4 responsabilidades (validar, calcular taxa,
// gravar, notificar). Qualquer alteracao a uma delas obriga a mexer aqui -
// e a testar as outras 3 de novo.
@Service
public class PaymentService {
  private final PaymentServiceNotification notificationService;
  private final PaymentServicePersistence persistenceService;
  private final CardVerificationService cardVerification;
  private final PaymentCalculationService paymentCalculationService;

  public PaymentService(PaymentServiceNotification notificationService, PaymentServicePersistence persistenceService, 
    CardVerificationService cardVerification, PaymentCalculationService paymentCalculationService) {
    this.notificationService = notificationService;
    this.persistenceService = persistenceService;
    this.cardVerification = cardVerification;
    this.paymentCalculationService = paymentCalculationService;
  }

  public void process(String cardNumber, double amount) {
    if (!this.cardVerification.isValid(cardNumber)) throw new IllegalArgumentException("cartao invalido");
    double fee = this.paymentCalculationService.calculateFee(amount);
    this.persistenceService.savePayment(cardNumber, amount + fee);
    this.notificationService.sendConfirmationEmail();
  }
}
