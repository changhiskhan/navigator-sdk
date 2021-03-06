/*
 * Copyright (c) 2015 Cloudera, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.nav.plugin.model.entities;

import com.cloudera.nav.plugin.model.SourceType;
import com.cloudera.nav.plugin.model.annotations.MProperty;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

import org.joda.time.Instant;

/**
 * An abstract base class for creating entities. Concrete sub-classes
 * that need to be persisted should have the @MClass annotation.
 * Only fields with getter methods annotated with @MProperty will be written.
 * Relationships with other Entities should be expressed in the form of a
 * field with a getter method annotated by @MRelation
 *
 * Hadoop component Entities will inherit from Entity while custom application
 * entities should inherit from CustomEntity
 */
public abstract class Entity {

  public static final String MTYPE = "ENTITY"; // type of metadata object
  public static final String USER_DEFINED_PROPERTIES = "properties";
  public static final String TECHNICAL_PROPERTIES = "technicalProperties";
  public static final CharSequence ID_SEPARATOR = "##";

  private String identity;
  private String sourceId;
  private String name;
  private boolean deleted;
  private Long deletionTime;
  private Collection<String> tags;
  private Map<String, String> properties;
  private Instant created;
  private String owner;
  private String description;

  /**
   * @return id for this custom entity
   */
  @MProperty(required=true)
  public String getIdentity() {
    return identity;
  }

  /**
   * @return id for the designated source
   */
  @MProperty
  public String getSourceId() {
    return sourceId;
  }

  /**
   * @return the source type
   */
  @MProperty(required=true)
  public abstract SourceType getSourceType();

  /**
   * @return name of the custom entity
   */
  @MProperty(attribute="originalName")
  public String getName() {
    return name;
  }

  /**
   * @return the type of the custom entity
   */
  @MProperty
  public abstract EntityType getType();

  /**
   * @return true if the custom entity should be marked deleted
   */
  @MProperty
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * @return deletion time in milliseconds since epoch if the custom entity
   *         has been deleted and null otherwise.
   */
  @MProperty
  public Long getDeletionTime() {
    return deletionTime;
  }

  @MProperty
  public Instant getCreated() {
    return created;
  }

  @MProperty
  public String getOwner() {
    return owner;
  }

  /**
   * @return a collection of string tags associated with this entity
   */
  @MProperty
  public Collection<String> getTags() {
    return tags;
  }

  @MProperty
  public Map<String, String> getProperties() {
    return properties;
  }

  @MProperty
  public String getDescription() {
    return description;
  }

  /**
   * Set the name of the custom entity
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the identity for the custom entity
   * @param identity
   */
  public void setIdentity(String identity) {
    this.identity = identity;
  }

  /**
   * Set the source id for the custom entity
   * @param sourceId
   */
  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  /**
   * Set whether the custom entity should be marked as deleted.
   * @param deleted
   */
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Set deletion time for the custom entity
   * @param deletionTime
   */
  public void setDeletionTime(Long deletionTime) {
    this.deletionTime = deletionTime;
  }

  /**
   * Set the tags for this custom entity.
   * @param tags
   */
  public void setTags(Collection<String> tags) {
    this.tags = ImmutableSet.copyOf(tags);
  }

  public void setCreated(Instant creationTime) {
    this.created = created;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = Maps.newHashMap(properties);
  }

  public abstract String generateId();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Entity)) return false;

    Entity entity = (Entity) o;

    // TODO if identity is null?
    if (!identity.equals(entity.identity)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return identity.hashCode();
  }
}
