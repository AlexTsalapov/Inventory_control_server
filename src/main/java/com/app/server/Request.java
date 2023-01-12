package com.app.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private RequestType requestType;
    private String requestMessage;

    public enum RequestType {//список действий
        Authorization,
        Registration,
        GetProducts,//получение продуктов
        GetCategory,//получение категория
        CreateStorage,
        UpdateStorage,
        DeleteStorage,
        CreateProduct,
        DeleteProduct,
        UpdateProduct,
        CreateCategory,
        DeleteCategory,
        UpdateCategory,
        Exit
    }
}
