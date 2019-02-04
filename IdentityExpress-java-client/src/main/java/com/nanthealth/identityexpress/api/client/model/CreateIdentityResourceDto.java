/*
 * IdentityExpress Management API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.nanthealth.identityexpress.api.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateIdentityResourceDto
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-24T03:48:05.487Z")
public class CreateIdentityResourceDto {
  @SerializedName("name")
  private String name = null;

  @SerializedName("displayName")
  private String displayName = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("allowedClaims")
  private List<String> allowedClaims = null;

  public CreateIdentityResourceDto name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateIdentityResourceDto displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * Get displayName
   * @return displayName
  **/
  @ApiModelProperty(required = true, value = "")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public CreateIdentityResourceDto description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CreateIdentityResourceDto allowedClaims(List<String> allowedClaims) {
    this.allowedClaims = allowedClaims;
    return this;
  }

  public CreateIdentityResourceDto addAllowedClaimsItem(String allowedClaimsItem) {
    if (this.allowedClaims == null) {
      this.allowedClaims = new ArrayList<String>();
    }
    this.allowedClaims.add(allowedClaimsItem);
    return this;
  }

   /**
   * Get allowedClaims
   * @return allowedClaims
  **/
  @ApiModelProperty(value = "")
  public List<String> getAllowedClaims() {
    return allowedClaims;
  }

  public void setAllowedClaims(List<String> allowedClaims) {
    this.allowedClaims = allowedClaims;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateIdentityResourceDto createIdentityResourceDto = (CreateIdentityResourceDto) o;
    return Objects.equals(this.name, createIdentityResourceDto.name) &&
        Objects.equals(this.displayName, createIdentityResourceDto.displayName) &&
        Objects.equals(this.description, createIdentityResourceDto.description) &&
        Objects.equals(this.allowedClaims, createIdentityResourceDto.allowedClaims);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName, description, allowedClaims);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateIdentityResourceDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    allowedClaims: ").append(toIndentedString(allowedClaims)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

