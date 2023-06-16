package br.com.project.wish.constant;

public class CommonConstant {
  public static final class baseMocksConstant {
    public static final String CUSTOMER_NAME_01 = "João Teste 01";
    public static final String CUSTOMER_EMAIL_01 = "joao.teste.01@mail.com";
    public static final String CUSTOMER_NAME_02 = "Pedro Teste 02";
    public static final String CUSTOMER_EMAIL_02 = "pedro.teste.01@mail.com";
    public static final String CUSTOMER_NAME_03 = "Maria Teste 03";
    public static final String CUSTOMER_EMAIL_03 = "maria.teste.01@mail.com";

    public static final String PRODUCT_NAME_01 = "Tenis marca teste";
    public static final String PRODUCT_DESCRIPTION_01 = "Tenis de corrido confortável";
    public static final Double PRODUCT_PRICE_01 = 251.01;
    public static final String PRODUCT_NAME_02 = "geladeira X litros";
    public static final String PRODUCT_DESCRIPTION_02 = "geladeira frost free";
    public static final Double PRODUCT_PRICE_02 = 560.54;
    public static final String PRODUCT_NAME_03 = "jogo de lençois king";
    public static final String PRODUCT_DESCRIPTION_03 = "jogo de lençois brancos de 800 linhas";
    public static final Double PRODUCT_PRICE_03 = 32.90;
    public static final String PRODUCT_NAME_04 = "Pacote de viagens";
    public static final String PRODUCT_DESCRIPTION_04 = "Pacote de viagens com tudo incluso, translado, refeições e hospedagem";
    public static final Double PRODUCT_PRICE_04 = 32.90;
  }
  public static final class logConstant {
    public static final String PRODUCT_ID = " productId: ";

    public static final String PAYLOAD = " payload: ";
    public static final String QUERY = " query: ";
    public static final String ERROR = " error: ";
    public static final String UPDATE = " update: ";

    public static final String ERROR_QUERY_WISH_BY_CUSTOMER_ID = "Error querying wish by id, wishId: ";
    public static final String ERROR_QUERY_WISH_BY_CUSTOMER_ID_LOG = "Error querying wish by id, wishId: {} error: {}";
    public static final String ERROR_UPDATE_PRODUCT_LIST_CUSTOMER_ID = "Error updating product list. payload: ";
    public static final String ERROR_UPDATE_PRODUCT_LIST_CUSTOMER_ID_LOG = "Error updating product list. payload: {} query: {} update: {} error: {}";
    public static final String ERROR_PRODUCT_NOT_FOUND_BY_CUSTOMERID = "Error locating wish list by customer and product. vishId: ";

    public static final String INFO_PRODUCT_LIST_IS_EMPTY_BY_CUSTOMER_ID = "Product list is empty. customerId: ";
    public static final String INFO_PRODUCT_IS_NULL = "Product is null";
    public static final String INFO_PRODUCT_ALREADY_EXIST_IN_PRODUCT_LIST = "Product already exists in the list. customerID: ";
    public static final String INFO_PRODUCT_NOT_ON_LIST = "Product is not on the list. customerId: ";
    public static final String INFO_WISH_LIST_NOT_FOUND = "Wish list not found";
    public static final String INFO_PRODUCT_NOT_FOUND_BY_CUSTOMERID = "Product not found in customer list. wishId: ";
    public static final String INFO_PRODUCT_NOT_FOUND_BY_PRODUCTID = "Product not found. productId: ";
    public static final String INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID = "Client not found. customerId: ";
    public static final String INFO_CUSTOMER_NOT_FOUND_BY_CUSTOMERID_LOG = "Client not found. customerId: {}";
    public static final String INFO_CANNOT_BE_NULL = "cannot be null";
    public static final String INFO_CANNOT_BE_NULL_OR_BLANK = "cannot be null or blank";
    public static final String INFO_MUST_BE_CHARACTERS = "Must be 3 to 250 characters";
    public static final String INFO_CANNOT_ADD_ITEM_TO_LIST = "Cannot add more items to wishlist";
  }
  public static final class queryConstant {
    public static final String PRODUCTS = "products";
    public static final String PRODUCTS_ID = "products.id";
    public static final String ID = "id";
  }
}