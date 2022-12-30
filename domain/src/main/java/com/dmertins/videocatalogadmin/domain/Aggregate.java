package com.dmertins.videocatalogadmin.domain;

public abstract class Aggregate<ID extends Identifier> extends Entity<ID> {
    protected Aggregate(final ID id) {
        super(id);
    }
}
