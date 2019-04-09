package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;


import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {

    private final String NAME = "Name";

    private InvoiceFactory invoiceFactory;
    private InvoiceRequest invoiceRequest;
    private BookKeeper bookKeeper;

    @Mock
    private TaxPolicy taxPolicy;

    @Before
    public void setup(){
        invoiceFactory = new InvoiceFactory();
        bookKeeper = new BookKeeper(invoiceFactory);
    }

    @Test
    public void emptyInvoiceRequestShouldGiveEmptyInvoice() {
        ClientData clientData = new ClientData(id(), NAME);
        invoiceRequest = new InvoiceRequest(clientData);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
        assertThat(invoice.getItems(), empty());

    }

    private Id id() {
        return Id.generate();
    }
}
