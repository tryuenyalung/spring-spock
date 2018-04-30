package com.yuen.springapi.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "address")
public final class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @NotNull(message = "zip code is required")
    @JsonProperty(value = "zip_code")
    private Integer zipCode;

    /**
     * @ManyToOne bidirectional relationship
     * @JoinColumn = column name in database table
     * @JsonIgnore =  ignore field to prevent stackoverflow
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users users;

    //private constructor for jackson static factory
    private Address(@NotBlank String street, @NotBlank String city, @NotBlank String state, @NotNull Integer zipCode) {
        this.street = street; this.city = city;
        this.state = state; this.zipCode = zipCode;
    }

    //jackson static factory for request parsing
    @JsonCreator
    public static Address jacksonRequestParser(
            @JsonProperty(value = "street")String street,
            @JsonProperty(value = "city")String city,
            @JsonProperty(value = "state")String state,
            @JsonProperty(value = "zip_code")Integer zipCode
    ){
        return new Address(street,city,state,zipCode);
    }
}
