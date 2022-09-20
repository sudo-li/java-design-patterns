# Java设计模式（149种）

## 1.创建型、建筑型、创造型（Architectural）

### 1.API网关（API Gateway）

即将微服务的调用聚合到单个位置，即API网关。用户只需要调用API网关，API网关就会调用每个相关的微服务

> 使用微服务模式，客户端可能需要来自多个不同微服务的数据。如果客户端直接调用每个微服务，这可能会导致加载时间更长，因为客户端必须为每个调用的微服务发出网络请求。此外，让客户端调用每个微服务直接将客户端与该微服务联系起来——如果微服务的内部实现发生变化（例如，如果两个微服务在未来某个时间合并）或者如果微服务的位置（主机和端口）更改，则必须更新使用这些微服务的每个客户端。
>
> API 网关模式的目的是缓解其中一些问题。在 API 网关模式中，在客户端和微服务之间放置了一个额外的实体（API 网关）。 API 网关的工作是聚合对微服务的调用。客户端不是单独调用每个微服务，而是一次调用 API 网关。 API 网关然后调用客户端需要的每个微服务。

简单的说就是

> 对于使用微服务架构实现的系统，API 网关是聚合对各个微服务的调用的单一入口点。

程序示例

> 这个实现展示了电子商务网站的 API 网关模式可能是什么样的。 ApiGateway 分别使用 ImageClientImpl 和 PriceClientImpl 调用 Image 和 Price 微服务。 在桌面设备上查看网站的客户可以看到价格信息和产品图片，因此 ApiGateway 调用这两个微服务并在 DesktopProduct 模型中聚合数据。 但是，移动用户只能看到价格信息； 他们看不到产品图片。 对于移动用户，ApiGateway 仅检索价格信息，用于填充 MobileProduct。

下面是图片微服务的实现

```java
public interface ImageClient {
    String getImageUrl();
}

@Service
public class ImageClientImpl implements ImageClient{
    @Override
    public String getImageUrl() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9999/image-url"))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

下面是价格微服务的实现

```java
public interface PriceClient {
    String getPrice();
}

@Service
public class PriceClientImpl implements PriceClient{
    @Override
    public String getPrice() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9999/price"))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

在这里我们可以看到API网关如何将请求映射到微服务

```java
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
```

类图

![](C:\Users\LYX\Pictures\Snipaste_2022-09-20_16-55-19.jpg)

您可以在以下的情况你可以使用API网关模式

* 您使用的是微服务体系结构，并需要为微服务调用提供单点聚合。

引用

> [API Gateway (java-design-patterns.com)](https://java-design-patterns.com/patterns/api-gateway/)

## 结构型（Structural）

### 2. 抽象文档（Abstract Document）

使用动态属性实现无类型语言的灵活性，同时保证了类型的安全

> 抽象文档模式可以处理额外的非静态属性。 此模式使用特征概念来实现类型安全并将不同类的属性分离到一组接口中。

简单的说就是

> 抽象文档模式允许在对象不知道的情况下将属性附加到对象。

程序示例

让我们首先定义基类 Document 和 AbstractDocument。 它们使对象包含一个属性映射和任意数量的子对象。

```java
public interface Document {
    void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {
    private final Map<String, Object> properties;

    protected AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties map must not null");
        this.properties = properties;
    }

    @Override
    public void put(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        return Stream.ofNullable(get(key))
                .filter(Objects::nonNull)
                .map(el -> (List<Map<String, Object>>) el)
                .findAny()
                .stream()
                .flatMap(Collection::stream)
                .map(constructor);

    }
}
```

接下来，我们为类型、价格、模型和部件定义一个枚举属性和一组接口。这允许我们为Car类创建静态外观的接口。

```java
public enum Property {

  PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

  default Optional<String> getType() {
    return Optional.ofNullable((String) get(Property.TYPE.toString()));
  }
}

public interface HasPrice extends Document {

  default Optional<Number> getPrice() {
    return Optional.ofNullable((Number) get(Property.PRICE.toString()));
  }
}
public interface HasModel extends Document {

  default Optional<String> getModel() {
    return Optional.ofNullable((String) get(Property.MODEL.toString()));
  }
}

public interface HasParts extends Document {

  default Stream<Part> getParts() {
    return children(Property.PARTS.toString(), Part::new);
  }
}
```

现在我们来创建车(需要什么就是先什么接口，通过接口实现类型安全)

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

最后，我们看一下构建一辆完成车（Car）的示例

```java
@Test
    void shouldConstructCar() {
        var wheelProperties = Map.of(
                Property.TYPE.toString(), "wheel",
                Property.MODEL.toString(), "15C",
                Property.PRICE.toString(), 100L);

        var doorProperties = Map.of(
                Property.TYPE.toString(), "door",
                Property.MODEL.toString(), "Lambo",
                Property.PRICE.toString(), 300L);

        var carProperties = Map.of(
                Property.MODEL.toString(), "300SL",
                Property.PRICE.toString(), 10000L,
                Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

        var car = new Car(carProperties);

        System.out.println("car.getModel().orElseThrow() = " + car.getModel().orElseThrow());
        System.out.println("car.getPrice().orElseThrow() = " + car.getPrice().orElseThrow());

        car.getParts().forEach(p -> {
            System.out.println("p.getType().orElse(null) = " + p.getType().orElse(null));
            System.out.println("p.getModel().orElse(null) = " + p.getModel().orElse(null));
            System.out.println("p.getPrice().orElse(null) = " + p.getPrice().orElse(null));
            }
        );
    }

/*
    car.getModel().orElseThrow() = 300SL
    car.getPrice().orElseThrow() = 10000
    p.getType().orElse(null) = wheel
    p.getModel().orElse(null) = 15C
    p.getPrice().orElse(null) = 100
    p.getType().orElse(null) = door
    p.getModel().orElse(null) = Lambo
    p.getPrice().orElse(null) = 300
*/

```

类图

![](C:\Users\LYX\Pictures\Snipaste_2022-09-20_20-08-18.jpg)

您可以在以下的情况你可以使用抽象文档模式

* 需要动态添加新属性
* 需要一种灵活的方式来组织树状结构
* 您想要一个松耦合的系统

引用

> [Abstract Document (java-design-patterns.com)](https://java-design-patterns.com/patterns/abstract-document/)





