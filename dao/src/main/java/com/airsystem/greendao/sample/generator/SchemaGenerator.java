package com.airsystem.greendao.sample.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Budi Oktaviyan Suryanto (budi.oktaviyan@icloud.com)
 */
public class SchemaGenerator implements ISchemaGenerator, ISchemaGenerator.IEntities {
    public static void main(String[] args) {
        new SchemaGenerator();
    }

    public SchemaGenerator() {
        Schema schema = new Schema(1, "com.airsystem.greendao.sample.dao");
        createTables(schema);

        try {
            generateAll(schema, Configs.OUTPUT_DIR);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void createTables(Schema schema) {
        Entity users = createUsers(schema);
        Entity userDetails = createUserDetails(schema);

        Property usersIdForUserDetails = userDetails.addLongProperty("usersid").notNull().getProperty();
        Property userDetailsIdForUsers = users.addLongProperty("userdetailsid").notNull().getProperty();

        userDetails.addToOne(users, usersIdForUserDetails, "fkusers");
        users.addToOne(userDetails, userDetailsIdForUsers, "fkuserdetails");
    }

    @Override
    public Entity createUsers(Schema schema) {
        Entity users = schema.addEntity("Users");
        users.addIdProperty().primaryKey().autoincrement();
        users.addStringProperty("username").notNull();
        users.addStringProperty("password").notNull();

        return users;
    }

    @Override
    public Entity createUserDetails(Schema schema) {
        Entity userDetails = schema.addEntity("UserDetails");
        userDetails.addIdProperty().primaryKey().autoincrement();
        userDetails.addStringProperty("firstname");
        userDetails.addStringProperty("lastname");
        userDetails.addStringProperty("role").notNull();
        userDetails.addStringProperty("gender").notNull();

        return userDetails;
    }

    private void generateAll(Schema schema, String output) throws Exception {
        new DaoGenerator().generateAll(schema, output);
    }
}