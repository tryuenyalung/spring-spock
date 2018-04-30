package com.yuen.springapi.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//Child Class
@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "name")

public final class Name {

    @Id
    @JsonIgnore
    private Long id;

    @NotBlank(message = "first name is required")
    @JsonProperty(value = "first_name")
    private String firstName;

    @NotBlank(message = "middle name is required")
    @JsonProperty(value = "middle_name")
    private String middleName ;

    @NotBlank(message = "last name is required")
    @JsonProperty(value = "last_name")
    private String lastName;

    private String suffix;

    /**
     * @OnetoOne relationship
     * FetchType.Lazy = get only data when you need it
     * @MapsId = unidirectional
     * @JsonIgnore =  ignore field to prevent stackoverflow
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Users users;

    //private constructor for jackson static factory
    private Name(@NotBlank String firstName, @NotBlank String middleName, @NotBlank String lastName, String suffix) {
        this.firstName = firstName; this.middleName = middleName;
        this.lastName = lastName; this.suffix = suffix;
    }

    //jackson static factory for request parsing
    @JsonCreator
    public static Name jacksonRequestParser(
            @JsonProperty(value = "first_name") String firstName,
            @JsonProperty(value = "middle_name") String middleName,
            @JsonProperty(value = "last_name") String lastName,
            @JsonProperty(value = "suffix") String suffix
    ){
        if(suffix == null) suffix ="none";
        return new Name(firstName,middleName,lastName,suffix);
    }

}
