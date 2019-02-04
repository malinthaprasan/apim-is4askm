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
import com.nanthealth.identityexpress.api.client.model.ScopeDto;
import com.nanthealth.identityexpress.api.client.model.SecretDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;

/**
 * ProtectedResourceDto
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-01-24T03:48:05.487Z")
public class ProtectedResourceDto {
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

  @SerializedName("secrets")
  private List<SecretDto> secrets = null;

  @SerializedName("scopes")
  private List<ScopeDto> scopes = null;

  @SerializedName("allowedClaims")
  private List<String> allowedClaims = null;

  @SerializedName("created")
  private OffsetDateTime created = null;

  @SerializedName("nonEditable")
  private Boolean nonEditable = null;

  public ProtectedResourceDto id(String id) {
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

  public ProtectedResourceDto name(String name) {
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

  public ProtectedResourceDto displayName(String displayName) {
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

  public ProtectedResourceDto description(String description) {
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

  public ProtectedResourceDto enabled(Boolean enabled) {
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

  public ProtectedResourceDto secrets(List<SecretDto> secrets) {
    this.secrets = secrets;
    return this;
  }

  public ProtectedResourceDto addSecretsItem(SecretDto secretsItem) {
    if (this.secrets == null) {
      this.secrets = new ArrayList<SecretDto>();
    }
    this.secrets.add(secretsItem);
    return this;
  }

   /**
   * Get secrets
   * @return secrets
  **/
  @ApiModelProperty(value = "")
  public List<SecretDto> getSecrets() {
    return secrets;
  }

  public void setSecrets(List<SecretDto> secrets) {
    this.secrets = secrets;
  }

  public ProtectedResourceDto scopes(List<ScopeDto> scopes) {
    this.scopes = scopes;
    return this;
  }

  public ProtectedResourceDto addScopesItem(ScopeDto scopesItem) {
    if (this.scopes == null) {
      this.scopes = new ArrayList<ScopeDto>();
    }
    this.scopes.add(scopesItem);
    return this;
  }

   /**
   * Get scopes
   * @return scopes
  **/
  @ApiModelProperty(value = "")
  public List<ScopeDto> getScopes() {
    return scopes;
  }

  public void setScopes(List<ScopeDto> scopes) {
    this.scopes = scopes;
  }

  public ProtectedResourceDto allowedClaims(List<String> allowedClaims) {
    this.allowedClaims = allowedClaims;
    return this;
  }

  public ProtectedResourceDto addAllowedClaimsItem(String allowedClaimsItem) {
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

  public ProtectedResourceDto created(OffsetDateTime created) {
    this.created = created;
    return this;
  }

   /**
   * Get created
   * @return created
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public ProtectedResourceDto nonEditable(Boolean nonEditable) {
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
    ProtectedResourceDto protectedResourceDto = (ProtectedResourceDto) o;
    return Objects.equals(this.id, protectedResourceDto.id) &&
        Objects.equals(this.name, protectedResourceDto.name) &&
        Objects.equals(this.displayName, protectedResourceDto.displayName) &&
        Objects.equals(this.description, protectedResourceDto.description) &&
        Objects.equals(this.enabled, protectedResourceDto.enabled) &&
        Objects.equals(this.secrets, protectedResourceDto.secrets) &&
        Objects.equals(this.scopes, protectedResourceDto.scopes) &&
        Objects.equals(this.allowedClaims, protectedResourceDto.allowedClaims) &&
        Objects.equals(this.created, protectedResourceDto.created) &&
        Objects.equals(this.nonEditable, protectedResourceDto.nonEditable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, displayName, description, enabled, secrets, scopes, allowedClaims, created, nonEditable);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProtectedResourceDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    secrets: ").append(toIndentedString(secrets)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("    allowedClaims: ").append(toIndentedString(allowedClaims)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
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
