package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;

import java.util.Map;

@Data
public class CodelistenProperty {

        private String currentVersion;
        private Map<String, Map<String, String> > codelistVersions;

        public String currentCodelistValueWithKey(String key) {
           return codelistVersions.entrySet().stream().filter(entry -> entry.getKey().endsWith(getKeyCurrentVersion()))
                    .map(Map.Entry::getValue).toList().getFirst().entrySet().stream().filter(entry -> entry.getKey().equals(key)).map(Map.Entry::getValue).findFirst().get();

        }

        private String getKeyCurrentVersion() {
                return currentVersion.replace(".", "-");
        }
}
