package com.smrc.javadesignpatterns.architectural.apigateway;

import com.smrc.javadesignpatterns.architectural.apigateway.client.ImageClient;
import com.smrc.javadesignpatterns.architectural.apigateway.client.PriceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiGateway {
    private final ImageClient imageClient;

    private final PriceClient priceClient;

    @RequestMapping(value = "/desktop", method = RequestMethod.GET)
    public DesktopProduct getProductDesktop() {
        DesktopProduct desktopProduct = new DesktopProduct();
        desktopProduct.setImageUrl(imageClient.getImageUrl());
        desktopProduct.setPrice(priceClient.getPrice());
        return desktopProduct;
    }
    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    public MobileProduct getMobileProduct() {
        MobileProduct mobileProduct = new MobileProduct();
        mobileProduct.setPrice(priceClient.getPrice());
        return mobileProduct;
    }
}
