package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.jupiter.api.BeforeEach;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.system.application.SystemContext;

import static org.mockito.Mockito.mock;

class AddProductCommandHandlerTest {

    ReservationRepository reservationRepository = mock(ReservationRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    SuggestionService suggestionService = mock(SuggestionService.class);
    ClientRepository clientRepository = mock(ClientRepository.class);
    SystemContext systemContext = mock(SystemContext.class);

    AddProductCommandHandler addProductCommandHandler;

    @BeforeEach
    public void init(){
        addProductCommandHandler =
                new AddProductCommandHandler(reservationRepository, productRepository, suggestionService,
                        clientRepository, systemContext);
    }

}