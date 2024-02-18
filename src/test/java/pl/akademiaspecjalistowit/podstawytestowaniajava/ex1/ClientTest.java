package pl.akademiaspecjalistowit.podstawytestowaniajava.ex1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    @Test
    public void should_successfully_create_the_client() {
        //given
        String clientName = "Magda";

        //when
        Client client = new Client(clientName);

        //then
        assertThat(client.getClientName()).isNotNull();
        assertThat(client.getClientName()).isEqualTo(clientName);
    }

    @Test
    public void should_successfully_create_the_client_configuration() {
        //given
        String clientName = "Magda";

        //when
        Client client = new Client(clientName);

        //then
        assertThat(client.getPaymentConfiguration()).isNotNull();
    }

    @Test
    public void should_successfully_add_the_selected_payment_type() {
        //given
        Client client = new Client("Magda");
        PaymentType paymentType = PaymentType.BLIK;

        //when
        client.addSupportedPaymentType(paymentType);

        //then
        assertThat(client.getPaymentConfiguration().getSupportedPaymentTypes()).isNotNull();
        assertThat(client.getPaymentConfiguration().getSupportedPaymentTypes()).contains(paymentType);
    }
}