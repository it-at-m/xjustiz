package de.muenchen.xjustiz.xjustiz0500straf.config;

import de.muenchen.xjustiz.xjustiz0500straf.XJustizException;
import lombok.Data;

import java.util.Map;

@Data
public class CodelistenProperty {

        private String currentVersion;
        private String kennung;
        private Map<String, Map<String, String> > codelistVersions;

        public String currentCodelistValueWithKey(String key) {
         try {
                 return codelistVersions.entrySet().stream().filter(entry -> entry.getKey().endsWith(getKeyCurrentVersion()))
                         .map(Map.Entry::getValue).toList().getFirst().entrySet().stream().filter(entry -> entry.getKey().equals(key)).map(Map.Entry::getValue).findFirst().get();

         } catch (Exception e) {
                 throw new XJustizException(String.format("XOEV XRepository 'codelist.value' not found : %s.%s (https://www.xrepository.de/details/%s).", this.codelistVersions.keySet().toArray()[0], key, this.kennung), e);
         }
        }

        private String getKeyCurrentVersion() {
                return currentVersion.replace(".", "-");
        }
}
