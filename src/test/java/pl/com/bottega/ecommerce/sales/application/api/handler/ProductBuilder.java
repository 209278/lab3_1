package pl.com.bottega.ecommerce.sales.application.api.handler;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductBuilder {

    private Id id;
    private Money money;
    private String name;
    private ProductType productType;

    public ProductBuilder setId(Id id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setMoney(Money money) {
        this.money = money;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public Product build(){
        return new Product(Id.generate(), new Money(1), "product", ProductType.FOOD);
    }
}