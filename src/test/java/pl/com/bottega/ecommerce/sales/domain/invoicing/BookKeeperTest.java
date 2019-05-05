package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookKeeperTest {
    BookKeeper bookKeeper;

    InvoiceFactory invoiceFactory;
    InvoiceRequest invoiceRequest;
    ClientData clientData;

    TaxPolicy taxPolicy = mock(TaxPolicy.class);

    ProductData productData = mock(ProductData.class);

    @BeforeEach
    public void init(){
        clientData = new ClientData(Id.generate(), "name");
        invoiceFactory = new InvoiceFactory();
        invoiceRequest = new InvoiceRequest(clientData);

        bookKeeper = new BookKeeper(invoiceFactory);
    }

    @Test
    public void invoiceRequestWithOneProduct() {

        Money money = new Money(1);

        RequestItem requestItem = new RequestItem(productData, 1, money);
        invoiceRequest.add(requestItem);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(new Money(1), "a"));

        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
        MatcherAssert.assertThat(invoice.getItems().size(), Matchers.is(1));
    }

    @Test
    public void calculateTaxCallCountForOneProduct() {

        Money money = new Money(1);

        when(taxPolicy.calculateTax(ProductType.FOOD, money))
                .thenReturn(new Tax(new Money(1), "a"));

        ProductData productData1 = mock(ProductData.class);
        when(productData1.getType()).thenReturn(ProductType.FOOD);

        RequestItem requestItem = new RequestItem(productData1, 1, money);
        invoiceRequest.add(requestItem);

        bookKeeper.issuance(invoiceRequest, taxPolicy);

        Mockito.verify(taxPolicy, Mockito.times(1))
                .calculateTax(ProductType.FOOD, money);
    }

    @Test
    public void invoiceRequestWithTwoProducts() {

        Money money = new Money(1);

        RequestItem requestItem1 = new RequestItem(productData, 1, money);
        RequestItem requestItem2 = new RequestItem(productData, 1, money);
        invoiceRequest.add(requestItem1);
        invoiceRequest.add(requestItem2);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(new Money(1), "a"));

        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
        MatcherAssert.assertThat(invoice.getItems().size(), Matchers.is(2));
    }

}