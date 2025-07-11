# Dokumentation
Der xJustizStarter implementiert den [XJustiz](https://xjustiz.justiz.de/index.php) Standard zur Justiz Behördenkommunikation.
Die vorliegende Version implementiert nicht den kompletten Standard, sondern nur die 
_NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010_ aus _xjustiz_0500_straf_3_6.xsd_ in der Version XJustiz 3.6.2.
Er ist aber erweiterbar.

Über eine Eingabe Schnittstelle werden die Fachdaten übermittelt und der xJustizStarter liefert ein um Fachdaten angereichertes XML im xJustiz Standard zurück. 

Die aktuellen XJustiz Versionen können bei [XJustiz](https://xjustiz.justiz.de/index.php) oder [XÖV-Standards und Codelisten](https://www.xrepository.de/) bezogen werden.
Alle im [XJustiz](https://xjustiz.justiz.de/index.php) verwendeten Codelisten sind im [XÖV-Standards und Codelisten](https://www.xrepository.de/) veröffentlicht.

## Architektur
Die Fachdaten werden über die Klassen im Package _de.muenchen.xjustiz.xjustiz0500straf.content_ übergeben.
Siehe die Beispiele in _ExternAnJustiz0500010TestEnvironment_.

Die weiteren Verarbeitungsschritte sind _XJustizDocumentRouteBuilder_ abgebildet.
Mit den Builder Klassen im Package _de.muenchen.xjustiz.xjustiz0500straf.builder_ wird das gewünschte Document generiert.
Anschließend wird das erstellte Dokument per XML Marshalling konvertiert und mit der XSD des xJustiz Documents validiert.

Kann die Erstellung des gewünschten Dokuments erfolgreich durchlaufen werden, wird das generierte Dokument im XML Format an den Aufrufer zurück gegeben.

Der XJustiz Standard bedient sich in seinem XSD verschiedener Codelisten aus dem [XÖV-Standards und Codelisten](https://www.xrepository.de/).
Die erforderlichen Werte müssen konfiguriert werden bevor sie über die Klassen im Package _de.muenchen.xjustiz.xjustiz0500straf.config_ den Builder Klassen zur Verfügung gestellt werden können.

## XJustiz XÖV Codelisten
Da nicht alle Werte aller Codelisten tatsächlich gebraucht werden und noch kein automatisches Einbinden der XÖV Codelisten implementiert ist, müssen diese bis auf weiteres manuel konfiguriert werden.
Sie werden in der _application.yml_ spezifiziert und über die Klassen im Package _de.muenchen.xjustiz.xjustiz0500straf.config_ eingelesen.
Pro Codeliste wird ein Eintrag angelegt. Der _Codelist Name_ ist wie die _Attribut Bezeichner_ frei wählbar, aus Gründen Zuordenbarkeit sind im Beispiel die Bezeichner der XÖV Codelisten übernommen.

Die [XÖV](https://www.xrepository.de/) Codelisten können versioniert und aktualisiert werden. Das wird unter den jeweiligen _codelist-versions:_ abgebildet.

```
xjustiz:
  xjustiz0500straf:
    codelisten:
      [XÖV Name der Codeliste wg. Zuordnung]
        current-version: [Version mit '.' wie in XÖV]
        codelist-versions:
          [Name frei wählbar][Version mit '-']
             [XÖV Attributname wg Zuordnung]          
```

Daher ist es möglich in der Konfiguration zur Dokumentation "alten" Codelisten beizubehaltem und neue hinzuzufügen.
Siehe das Beispiel der _gds-ereignis_ mit den Versionen 1-10 und 1-11.
In den Buildern wird die Codelisten Version verwendet, die als _current-version_ eingetragen ist. Im Beispiel der _gds-ereignis_ also die Version 1.11.
Beim Hinzufügen einer neuen Codelisten Version unter _codelist-versions:_ ist darauf zu achten, das mindestens ein Codeliste Name angelegt ist, _der mit der Version der **current-version:** endet_ . Also z.Bsp. abc-1-11,
Sonst kann keine gültige Codeliste gefunden werden.

Im Source Code werden die Werte über die vergebenen Namen der _codelisten:_ ermittelt. 
Eine Änderung bereits vergebener Bezeichner muss im Source Code aktualisiert werden.

```
...
  ereignis.setCode(nachrichtenProperty.getCodelisten().get("gds-ereignis").currentCodelistValueWithKey("neueingang-e-haft"));
...
```

```
xjustiz:
  xjustiz0500straf:
    codelisten:
      gds-rollenbezeichnung:
        current-version: 3.5
        kennung: urn:xoev-de:xjustiz:codeliste:gds.rollenbezeichnung
        codelist-versions:
          gds-rollenbezeichnung-3-5:
            betroffener: '040'
            antragsteller: '016'
      gds-gerichte:
        current-version: 3.6
        kennung: urn:xoev-de:xjustiz:codeliste:gds.gerichte
        codelist-versions:
          gds-gericht-3-6:
            amtsgericht-muenchen: D2601
      gds-ereignis:
        current-version: 1.11
        kennung: urn:xoev-de:xjustiz:codeliste:gds.ereignis
        codelist-versions:
          gds-ereignis-1-11:
            neueingang-e-haft: 117
          gds-ereignis-1-10:
            neueingang-e-haft: 117
   ...
 
```

## Einbindung des xJustizStarter
Der xJustizStarter kann über eine Apache Camel Route direkt oder in einer Spring-Boot Componente aufgerufen werden.
Die Fachdaten für das zu erstellende xJsutiz Dokument müssen in der _ContentContainer.class_ übergeben werden. 

### Direkte Apache Camel Einbindung
In einer Apache Camel Anwendung kann die XJustiz Document Generierung in die eigene Camel Routen Konfiguration eingebunden werden :
```
xjustiz:
  document:
    processor: direct:xjustiz-document-processor  
```
Dokumentation [Apache Camel Direct](https://camel.apache.org/components/4.10.x/direct-component.html).

### Einbindung aus Spring Kontext
Beispiele für eine Anbindung ohne Apache Camel Kontext finden sich in den _Testfällen_.
Dazu muss ein Apache Camel Exchange mit den gewünschten Fachdaten im Exchange Body erstellt und dieser per Camel Producer versendet werden.

```
@Component
public class CamelCallExample {

  @Produce("direct:xjustiz-document-processor")
  private ProducerTemplate xjustizDocumentProducer;

   @Autowired
   private CamelContext camelContext;

  public String xjustizExampleCall() {
  
        Exchange request = ExchangeBuilder.anExchange(camelContext).withBody(new ContentContainer(..., new GrunddatenContent(...))).build();
        var response  = xjustizDocumentProducer.send request);
        var xml = response.getMessage().getBody(String.class);
        var exception = response.getException();
        ...
  }
}
```
Dokumentation [Apache Camel Producer](https://camel.apache.org/manual/producertemplate.html).

### Error Handling
Das Error Handling der verarbeitenden Camel Route im XJustizDocumentRouteBuilder fängt alle Exceptions und gibt diese an den Aufrufer zurück, 
wenn die _xjustiz.document.processor_ Route, wie im Beispiel oben, mit meinem _Camel Exchange aufgerufen_ wird.

Sie auch die Beispiele im _ErrorHandlingTest_.

## Maven Artefakt dem eigenen Projekt hinzufügen

```
<dependency>
   <groupId>...</groupId>
   <artifactId>xjustiz-starter</artifactId>
   <version>0.0.1</version>
</dependency>
```

## Source Code holen

Sourcen auf den lokalen Rechner holen

    git clone https://github.com/it-at-m/xjustiz.git

- Das Projekt _xjustiz-converter_ enthält die Sourcen.
- Das Projekt _xjustiz-starter_ enthält die Starter Klassen.