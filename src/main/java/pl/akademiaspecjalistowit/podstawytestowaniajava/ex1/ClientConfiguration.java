package pl.akademiaspecjalistowit.podstawytestowaniajava.ex1;

import java.util.ArrayList;
import java.util.List;

public class ClientConfiguration {

    private List<PaymentType> supportedPaymentTypes;

    public ClientConfiguration() {
        this.supportedPaymentTypes = new ArrayList<>();
    }

    public void addPaymentType(PaymentType paymentType) {
        supportedPaymentTypes.add(paymentType);
    }

    public List<PaymentType> getSupportedPaymentTypes() {
        return List.copyOf(supportedPaymentTypes);
    }
}
