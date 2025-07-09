package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.luispiquinrey.KnotCommerce.Entities.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Inheritance(strategy = jakarta.persistence.InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = jakarta.persistence.DiscriminatorType.STRING)
@Entity
@Table(name = "Product", uniqueConstraints = {
        @UniqueConstraint(columnNames = "description")
})
@JsonSubTypes({
        @JsonSubTypes.Type(value = NoPerishableProduct.class, name = "noPerishable"),
        @JsonSubTypes.Type(value = PerishableProduct.class, name = "perishable")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@PropertySource("KnotCommerce/src/main/resources/validationProduct.yml")
@Schema(name = "Product", description = "Representation of items", example = """
            {
                "id_Category": 1,
                "name": "Dairy",
                "description": "Products derived from milk"
            }
        """)
public abstract class Product implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Product.class);

    @Schema(description = "Indicates if the product is available for purchase", example = "true")
    @Column(name = "available", columnDefinition = "BIT")
    @JsonProperty("available")
    private boolean available;

    @Schema(description = "Unique identifier of the product", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Product")
    @JsonProperty("id_Product")
    private Long id_Product;

    @Schema(description = "Product name (5-20 characters)", example = "Fresh Milk")
    @Column(name = "name")
    @JsonProperty("name")
    @Length(min = 5, max = 20, message = "{product.length.name}")
    private String name;

    @Schema(description = "Price of the product (positive value)", example = "2.5")
    @Column(name = "price")
    @JsonProperty("price")
    @Positive(message = "{product.positive.price}")
    private double price;

    @Schema(description = "Short description of the product (5-100 characters)", example = "1L organic milk")
    @Column(name = "description")
    @JsonProperty("description")
    @Length(min = 5, max = 100, message = "{product.length.description}")
    private String description;

    @Schema(description = "Number of items available in stock (min 0)", example = "100")
    @Column(name = "stock")
    @JsonProperty("stock")
    @Min(value = 0, message = "{product.min.stock}")
    private Integer stock;

    @Column(name = "code_user")
    @JsonProperty("code_user")
    @Positive
    private Long code_User;

    @Version
    @Column(name = "version")
    @JsonProperty("version")
    private Integer version;

    private String imageName;

    private String imageType;

    @Lob
    private byte[] imageContent;

    @Schema(description = "Categories to which the product belongs", example = """
                    [
                        {
                            "id_Category": 1,
                            "name": "Dairy",
                            "description": "Products derived from milk"
                        },
                        {
                            "id_Category": 2,
                            "name": "Organic",
                            "description": "Natural and pesticide-free items"
                        }
                    ]
            """)
    @ManyToMany
    @OrderBy("name")
    @JoinTable(name = "Product_Category", joinColumns = @JoinColumn(name = "id_Product"), inverseJoinColumns = @JoinColumn(name = "id_Category"))
    private List<Category> categories;

    public Product() {
    }

    public Product(boolean available, Long id_Product,
            @Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Length(min = 5, max = 100, message = "{product.length.description}") String description,
            @Min(value = 0, message = "{product.min.stock}") Integer stock, @Positive Long code_User,
            List<Category> categories) {
        this.available = available;
        this.id_Product = id_Product;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.code_User = code_User;
        this.categories = categories;
    }

    public Product(boolean available,
            @Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Length(min = 5, max = 100, message = "{product.length.description}") String description,
            @Min(value = 0, message = "{product.min.stock}") Integer stock,
            List<Category> categories) {
        this.available = available;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.categories = categories;
    }

    public Product(@Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Length(min = 5, max = 100, message = "{product.length.description}") String description,
            @Min(value = 0, message = "{product.min.stock}") Integer stock, @Positive Long code_User) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.code_User = code_User;
    }

    public Product(@Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Min(value = 0, message = "{product.min.stock}") Integer stock, @Positive Long code_User) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.code_User = code_User;
    }

    public Product(@Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Min(value = 0, message = "{product.min.stock}") Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCode_User() {
        return code_User;
    }

    public void setCode_User(Long code_User) {
        this.code_User = code_User;
    }
    
    public Long getId_Product() {
        return id_Product;
    }

    protected void setIdProduct(Long idProduct) {
        this.id_Product = idProduct;
    }

    

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public String productToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String json = writer.writeValueAsString(this);
        log.info("""
                \u001b[32m\ud83d\uded2 Product JSON formatted:
                """ + json + "\u001B[0m");
        return json;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @PostConstruct
    public void generateQR() {
        log.info("Generating QRCode");
        try {
            MultiFormatWriter writerQR = new MultiFormatWriter();
            BitMatrix matrix = writerQR.encode(this.productToJson(), BarcodeFormat.QR_CODE, 250, 250);
            String dirName = "qrcodes";
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dirName));

            String fileName = dirName + "/product-" + (id_Product != null ? id_Product : "new") + ".png";
            Path path = FileSystems.getDefault().getPath(fileName);

            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            log.info("QR Code generated at: " + path.toAbsolutePath());
        } catch (WriterException | JsonProcessingException ex) {
            log.error("Error to encode the product or to parse the product to Json", ex);
        } catch (Exception ex) {
            log.error("Error writing QR code to file", ex);
        }
    }
}