package com.dogmeeting.catalogservice.service;

import com.dogmeeting.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
