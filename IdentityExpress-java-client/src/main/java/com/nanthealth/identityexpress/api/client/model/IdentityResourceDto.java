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
 * IdentityResourceDto
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-24T03:48:05.487Z")
public class IdentityResourceDto {
  @SerializedName("id")
  private String id = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("displayName")
  private String displayName = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("enabled")
  private Boolean enabled = null;

  @SerializedName("required")
  private Boolean required = null;

  @SerializedName("emphasize")
  private Boolean emphasize = null;

  @SerializedName("showInDiscoveryDocument")
  private Boolean showInDiscoveryDocument = null;

  @SerializedName("allowedClaims")
  private List<String> allowedClaims = null;

  @SerializedName("nonEditable")
  private Boolean nonEditable = null;

  public IdentityResourceDto id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public IdentityResourceDto name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IdentityResourceDto displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * Get displayName
   * @return displayName
  **/
  @ApiModelProperty(value = "")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public IdentityResourceDto description(String description) {
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

  public IdentityResourceDto enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

   /**
   * Get enabled
   * @return enabled
  **/
  @ApiModelProperty(value = "")
  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public IdentityResourceDto required(Boolean required) {
    this.required = required;
    return this;
  }

   /**
   * Get required
   * @return required
  **/
  @ApiModelProperty(value = "")
  public Boolean isRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public IdentityResourceDto emphasize(Boolean emphasize) {
    this.emphasize = emphasize;
    return this;
  }

   /**
   * Get emphasize
   * @return emphasize
  **/
  @ApiModelProperty(value = "")
  public Boolean isEmphasize() {
    return emphasize;
  }

  public void setEmphasize(Boolean emphasize) {
    this.emphasize = emphasize;
  }

  public IdentityResourceDto showInDiscoveryDocument(Boolean showInDiscoveryDocument) {
    this.showInDiscoveryDocument = showInDiscoveryDocument;
    return this;
  }

   /**
   * Get showInDiscoveryDocument
   * @return showInDiscoveryDocument
  **/
  @ApiModelProperty(value = "")
  public Boolean isShowInDiscoveryDocument() {
    return showInDiscoveryDocument;
  }

  public void setShowInDiscoveryDocument(Boolean showInDiscoveryDocument) {
    this.showInDiscoveryDocument = showInDiscoveryDocument;
  }

  public IdentityResourceDto allowedClaims(List<String> allowedClaims) {
    this.allowedClaims = allowedClaims;
    return this;
  }

  public IdentityResourceDto addAllowedClaimsItem(String allowedClaimsItem) {
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

  public IdentityResourceDto nonEditable(Boolean nonEditable) {
    this.nonEditable = nonEditable;
    return this;
  }

   /**
   * Get nonEditable
   * @return nonEditable
  **/
  @ApiModelProperty(value = "")
  public Boolean isNonEditable() {
    return nonEditable;
  }

  public void setNonEditable(Boolean nonEditable) {
    this.nonEditable = nonEditable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdentityResourceDto identityResourceDto = (IdentityResourceDto) o;
    return Objects.equals(this.id, identityResourceDto.id) &&
        Objects.equals(this.name, identityResourceDto.name) &&
        Objects.equals(this.displayName, identityResourceDto.displayName) &&
        Objects.equals(this.description, identityResourceDto.description) &&
        Objects.equals(this.enabled, identityResourceDto.enabled) &&
        Objects.equals(this.required, identityResourceDto.required) &&
        Objects.equals(this.emphasize, identityResourceDto.emphasize) &&
        Objects.equals(this.showInDiscoveryDocument, identityResourceDto.showInDiscoveryDocument) &&
        Objects.equals(this.allowedClaims, identityResourceDto.allowedClaims) &&
        Objects.equals(this.nonEditable, identityResourceDto.nonEditable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, displayName, description, enabled, required, emphasize, showInDiscoveryDocument, allowedClaims, nonEditable);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdentityResourceDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    required: ").append(toIndentedString(required)).append("\n");
    sb.append("    emphasize: ").append(toIndentedString(emphasize)).append("\n");
    sb.append("    showInDiscoveryDocument: ").append(toIndentedString(showInDiscoveryDocument)).append("\n");
    sb.append("    allowedClaims: ").append(toIndentedString(allowedClaims)).append("\n");
    sb.append("    nonEditable: ").append(toIndentedString(nonEditable)).append("\n");
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
