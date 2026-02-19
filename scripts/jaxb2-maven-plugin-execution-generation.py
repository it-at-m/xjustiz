#!/usr/bin/env python3

"""
 Creates the <executions><execution>...</execution></executions>` instructions in the pom.xml of the xjustiz-version-X-X-X project for the ‘jaxb2-maven-plugin’.
 The instructions ensure that the generated classes for each XSD file are output in a separate package.
 The package name corresponds to `<name>.xsd` without underscore and extension. For example xjustiz_0000_grunddatensatz_3_5.xsd --> xjustiz0000grunddatensatz35
 Please note that the xsd directory is “hard-coded.” Call from the 'scripts' directory : python jaxb2-maven-plugin-execution-generation.py
"""

import os
from pathlib import Path

# Quellverzeichnis mit XSDs (relativ zum Projekt)
XSD_DIR = Path("../xjustiz-version-x-x-x/src/main/resources/xsd/xjustiz-x-x-x-xsd")

# Maven-Platzhalter (als Text!)
XSD_BASE = "${project.basedir}/src/main/resources/xsd/xjustiz-x-x-x-xsd"
OUT_BASE = "${project.basedir}/target/generated-sources/jaxb"
PKG_BASE = "de.muenchen.xjustiz.generated"

# Zieldatei
OUT_FILE = Path("executions.xml")

def clean_name(name: str) -> str:
    """
    Entfernt Unterstriche, Bindestriche und Punkte
    für outputDirectory und packageName
    """
    return (
        name.replace("_", "")
            .replace("-", "")
            .replace(".", "")
    )

def execution_id(name: str) -> str:
    """
    Erzeugt eine stabile execution-id
    (Bindestriche -> Unterstriche)
    """
    return name.replace("-", "_")

# Alle XSDs sammeln und sortieren
xsds = sorted(
    p for p in XSD_DIR.iterdir()
    if p.is_file() and p.suffix == ".xsd"
)

if not xsds:
    raise RuntimeError(f"Keine XSD-Dateien in {XSD_DIR} gefunden")

with OUT_FILE.open("w", encoding="utf-8") as f:
    f.write("<executions>\n")

    for xsd in xsds:
        base = xsd.stem
        exec_id = execution_id(base)
        clean = clean_name(base)

        f.write(f"""    <execution>
        <id>{exec_id}</id>
        <goals>
            <goal>xjc</goal>
        </goals>
        <configuration>
            <sources>
                <source>{XSD_BASE}/{xsd.name}</source>
            </sources>
             <arguments>
                <argument>-npa</argument>
             </arguments>
            <outputDirectory>{OUT_BASE}/{clean}</outputDirectory>
            <packageName>{PKG_BASE}.{clean}</packageName>
        </configuration>
    </execution>

""")

    f.write("</executions>\n")

print(f"executions.xml erfolgreich aus '{XSD_DIR}' erzeugt.")
