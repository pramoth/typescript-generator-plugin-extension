package th.co.geniustree.typescript;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class MyBean {

    @Size(min = 5, max = 10)
    @NotBlank(message = "Name is required")
    @NotEmpty
    private String name;

    @Max(10)
    @Min(1)
    @NotNull
    private Integer value;

    @Digits(integer = 2,fraction = 2)
    private BigDecimal decimal;

    @Email(message = "xxx")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }
}
