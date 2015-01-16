package com.airsystem.greendao.sample.generator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public interface ISchemaGenerator {
    void createTables(Schema schema);

    interface IEntities {
        Entity createUsers(Schema schema);

        Entity createUserDetails(Schema schema);
    }
}