package de.metro.code.warehouse.orderservice.persistence.model;

import java.util.Objects;
import java.util.UUID;

import com.google.common.base.MoreObjects;

public class Article {

    private String id;
    private String name;
    private StorageArea storageArea;
    private String ean;

    protected Article() {
    }

    public Article(final Article article) {
        this.id = article.id;
        this.name = article.name;
        this.storageArea = article.storageArea;
        this.ean = article.ean;
    }

    private Article(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.storageArea = builder.storageArea;
        this.ean = builder.ean;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public StorageArea getStorageArea() {
        return storageArea;
    }

    public void setStorageArea(final StorageArea storageArea) {
        this.storageArea = storageArea;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(final String ean) {
        this.ean = ean;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, storageArea, ean);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Article)) {
            return false;
        }

        final Article other = (Article) obj;
        return Objects.equals(id, other.id) &&
            Objects.equals(name, other.name) &&
            Objects.equals(storageArea, other.storageArea) &&
            Objects.equals(ean, other.ean);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("name", name)
            .add("storageArea", storageArea)
            .add("ean", ean)
            .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id = UUID.randomUUID().toString();
        private String name;
        private StorageArea storageArea;
        private String ean;

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withStorageArea(final StorageArea storageArea) {
            this.storageArea = storageArea;
            return this;
        }

        public Builder withEan(final String ean) {
            this.ean = ean;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
