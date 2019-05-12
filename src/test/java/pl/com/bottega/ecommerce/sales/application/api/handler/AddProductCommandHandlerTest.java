package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;
import pl.com.bottega.ecommerce.system.application.SystemUser;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AddProductCommandHandlerTest {

    ReservationRepository reservationRepository = mock(ReservationRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    SuggestionService suggestionService = mock(SuggestionService.class);
    ClientRepository clientRepository = mock(ClientRepository.class);
    SystemContext systemContext = mock(SystemContext.class);
    Reservation reservation = mock(Reservation.class);
    SystemUser systemUser = mock(SystemUser.class);

    Id id;
    Client client;
    Product product;
    Product equivalentProduct;

    AddProductCommandHandler addProductCommandHandler;

    @BeforeEach
    public void init(){
        id = Id.generate();
        client = new Client();

        product = new ProductBuilder()
                .setId(Id.generate())
                .setMoney(new Money(1))
                .setName("product")
                .setProductType(ProductType.FOOD)
                .build();

        equivalentProduct = new ProductBuilder()
                .setId(Id.generate())
                .setMoney(new Money(2))
                .setName("equivalentProduct")
                .setProductType(ProductType.FOOD)
                .build();

        Mockito.when(reservationRepository.load(any()))
                .thenReturn(reservation);
        Mockito.when(productRepository.load(any()))
                .thenReturn(product);
        Mockito.when(suggestionService.suggestEquivalent(eq(product), any(Client.class)))
                .thenReturn(equivalentProduct);
        Mockito.when(systemContext.getSystemUser()).thenReturn(systemUser);
        Mockito.when(systemUser.getClientId()).thenReturn(id);
        Mockito.when(clientRepository.load(id)).thenReturn(client);

        addProductCommandHandler =
                new AddProductCommandHandler(reservationRepository, productRepository, suggestionService,
                        clientRepository, systemContext);
    }

    @Test
    public void reservationOneCall(){
        AddProductCommand command1 = new AddProductCommand(new Id("1"), new Id("1"), 1);
        addProductCommandHandler.handle(command1);
        Mockito.verify(reservation, times(1)).add(product, 1);
    }

    @Test
    public void reservationAddEquivalentProduct(){
        product.markAsRemoved();
        AddProductCommand command1 = new AddProductCommand(new Id("1"), new Id("1"), 1);
        addProductCommandHandler.handle(command1);
        Mockito.verify(reservation, times(1)).add(equivalentProduct, 1);
    }

}