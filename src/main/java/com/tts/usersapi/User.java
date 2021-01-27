package com.tts.usersapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The ID of the user")
    private Long id; // Id

    @ApiModelProperty(notes = "The user first name")
    @Length(max = 20)
    private String fname;// First name

    @ApiModelProperty(notes = "The user last name")
    @Length(min = 2)
    private String lname;// Last name

    @ApiModelProperty(notes = "The user state of residence")
    @Length(min = 4, max = 20)
    private String state;// State (of residence)
}