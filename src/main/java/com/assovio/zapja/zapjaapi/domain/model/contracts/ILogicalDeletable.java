package com.assovio.zapja.zapjaapi.domain.model.contracts;

import java.time.OffsetDateTime;

public interface ILogicalDeletable {
    void setDeletedAt(OffsetDateTime deletedAt);
}
