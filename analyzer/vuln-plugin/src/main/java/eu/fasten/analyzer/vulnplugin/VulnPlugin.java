package eu.fasten.analyzer.vulnplugin;

import eu.fasten.analyzer.vulnplugin.db.MetadataDao;
import eu.fasten.analyzer.vulnplugin.db.Vulnerability;
import eu.fasten.analyzer.vulnplugin.utils.Scraper;
import eu.fasten.core.plugins.DBConnector;
import eu.fasten.core.plugins.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jooq.DSLContext;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VulnPlugin extends Plugin {

    public VulnPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class VulnPluginExtension implements KafkaConsumer<String>, DBConnector {

        private String topic = "maven.packages";
        private static DSLContext dslContext;
        private boolean processedRecord = false;
        private String pluginError = "";

        @Override
        public void setDBConnection(DSLContext dslContext) {
            this.dslContext = dslContext;
        }

        @Override
        public List<String> consumerTopics() {
            return new ArrayList<>(Collections.singletonList(topic));
        }

        @Override
        public void setTopic(String topicName) {
            this.topic = topicName;
        }

        @Override
        public void consume(String topic, ConsumerRecord<String, String> record) {
            // TODO: Consume maven coordinates and save to DB
            // Step 1: Consumer maven coordinates

            // Step 2: Retrieve vulnerability information

            // Step 3: Save the information to the database
        }

        public Vulnerability getVulnerabilityInfo(String group, String packageName, String version) throws IOException, InterruptedException {
            Scraper scraper = new Scraper();
            String rawInfo = scraper.sendGET(group, packageName, version);

            // TODO: Process the raw information and return a Vulnerability Object
            return null;
        }

        public long saveToDatabase(Vulnerability vulnerability, MetadataDao metadataDao) {
            // TODO: Connect to DB and save the records
            return 0L;
        }

        @Override
        public boolean recordProcessSuccessful() {
            return false;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public String description() {
            return null;
        }

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

        @Override
        public void setPluginError(Throwable throwable) {

        }

        @Override
        public String getPluginError() {
            return null;
        }

        @Override
        public void freeResource() {

        }
    }
}
