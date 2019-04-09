package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.mockito.Matchers.any;
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

    @Test
    public void emptyInvoiceRequestShouldCallTaxPolicyZeroTimes() {
        ClientData clientData = new ClientData(id(), NAME);
        invoiceRequest = new InvoiceRequest(clientData);
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        Mockito.verify(taxPolicy, Mockito.times(0)).calculateTax(any(ProductType.class), any(Money.class));

    }

    private Id id() {
        return Id.generate();
    }
}
