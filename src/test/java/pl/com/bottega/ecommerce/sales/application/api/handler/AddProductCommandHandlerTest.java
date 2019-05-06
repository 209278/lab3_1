package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AddProductCommandHandlerTest {

    ReservationRepository reservationRepository = mock(ReservationRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    SuggestionService suggestionService = mock(SuggestionService.class);
    ClientRepository clientRepository = mock(ClientRepository.class);
    SystemContext systemContext = mock(SystemContext.class);
    Reservation reservation = mock(Reservation.class);

    Product product = mock(Product.class);
//    Id id;
//    Money money = new Money(1);
//    String name = "test";
//    ProductType productType = ProductType.FOOD;

    AddProductCommandHandler addProductCommandHandler;

    @BeforeEach
    public void init(){
//        id = Id.generate();
//        product = new Product(id, money, name, productType);

        Mockito.when(reservationRepository.load(any()))
                .thenReturn(reservation);
        Mockito.when(productRepository.load(any()))
                .thenReturn(product);

        addProductCommandHandler =
                new AddProductCommandHandler(reservationRepository, productRepository, suggestionService,
                        clientRepository, systemContext);
    }

    @Test
    public void reservationOneCall(){
        Mockito.when(product.isAvailable()).thenReturn(true);
        AddProductCommand command1 = new AddProductCommand(new Id("1"), new Id("1"), 1);
        addProductCommandHandler.handle(command1);
        Mockito.verify(reservation, times(1)).add(product, 1);
    }

}