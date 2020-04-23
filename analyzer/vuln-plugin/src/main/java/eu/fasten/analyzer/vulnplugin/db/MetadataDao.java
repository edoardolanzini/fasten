package eu.fasten.analyzer.vulnplugin.db;

import eu.fasten.core.data.metadatadb.codegen.tables.Modules;
import eu.fasten.core.data.metadatadb.codegen.tables.Packages;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.Query;
import org.json.JSONObject;

import java.sql.Timestamp;

public class MetadataDao {

    private DSLContext context;

    public MetadataDao(DSLContext context) {
        this.context = context;
    }

    public DSLContext getContext() {
        return context;
    }

    public void setContext(DSLContext context) {
        this.context = context;
    }

    /**
     * Inserts vulnerability information in the Metadata DB at the package-level.
     * @param vulnerability
     */
    public void insertVulnerability(Vulnerability vulnerability) {
        System.out.println("TODO: Insert the vulnerability in the DB");
    }

    /**
     * Searches 'packages' table for certain package record.
     *
     * @param packageName Name of the package
     * @param forge       Forge of the package
     * @return ID of the record found or -1 otherwise
     */
    public long findPackage(String packageName, String forge) {
        var resultRecords = context.selectFrom(Packages.PACKAGES)
                .where(Packages.PACKAGES.PACKAGE_NAME.eq(packageName))
                .and(Packages.PACKAGES.FORGE.eq(forge)).fetch();
        if (resultRecords == null || resultRecords.isEmpty()) {
            return -1L;
        } else {
            return resultRecords.getValues(Packages.PACKAGES.ID).get(0);
        }
    }

    /**
     * Updates SHA256, timestamp and metadata of certain module record.
     *
     * @param moduleId  ID of the module record
     * @param sha256    New SHA256 for the module
     * @param timestamp New timestamp for the module
     * @param metadata  New metadata for the module
     */
    public void updateModule(long moduleId, byte[] sha256, Timestamp timestamp, JSONB metadata) {
        context.update(Modules.MODULES)
                .set(Modules.MODULES.SHA256, sha256)
                .set(Modules.MODULES.CREATED_AT, timestamp)
                .set(Modules.MODULES.METADATA, metadata)
                .where(Modules.MODULES.ID.eq(moduleId)).execute();
    }
}
