
"""
Python script for visualizing XSD content (similar to mvn dependency:tree).
- Global elements and all nested sub-elements are displayed recursively.
- Display of the namespace and the xsd file per element.
- Global elements can be selected to limit the content of the output.

Example call:
- Show all Elements : python -X utf8 xsd_tree_recursive.py xjustiz_0500_straf_3_6.xsd > xsdTree_xjustiz_0500_straf_3_6_all.txt
- Show selected Type : python -X utf8 xsd_tree_recursive.py xjustiz_0500_straf_3_6.xsd nachricht.straf.owi.verfahrensmitteilung.externAnJustiz.0500010 > xsdTree_xjustiz_0500_straf_3_6_optional.txt
"""

import xmlschema
import sys
from pathlib import Path
from urllib.parse import urlparse

def format_occurs(min_occurs, max_occurs):
    max_str = "*" if max_occurs is None else str(max_occurs)
    if min_occurs == 1 and max_occurs == 1:
        return ""
    return f" ({min_occurs}..{max_str})"

def build_nsmap(schema):
    """URI -> Präfix ('' wird zu 'default')."""
    ns_map = {}
    for prefix, uri in schema.namespaces.items():
        ns_map[uri] = prefix if prefix else "default"
    return ns_map

def schema_filename(schema):
    """Bestimme einen Anzeigenamen für das Schema (Dateiname), robust über mehrere mögliche Felder."""
    # Kandidaten in Prioritätsreihenfolge
    candidates = [
        getattr(schema, 'source', None),            # kann Resource oder str sein
        getattr(schema, 'url', None),
        getattr(schema, 'base_url', None),
    ]
    for c in candidates:
        if not c:
            continue
        # Falls es ein Resource-Objekt ist, versuche dessen .url / .location
        url = None
        if not isinstance(c, str):
            url = getattr(c, 'url', None) or getattr(c, 'location', None) or getattr(c, 'source', None)
        else:
            url = c
        if url:
            parsed = urlparse(url)
            path = parsed.path or url
            try:
                return Path(path).name or "<inline>"
            except Exception:
                # Fallback ohne Path
                return url.split('/')[-1].split('\\')[-1] or "<inline>"
    return "<inline>"

def ns_prefix_of(obj, ns_map):
    ns = getattr(obj, "target_namespace", None)
    if not ns:
        return "none"
    return ns_map.get(ns, ns)  # Fallback: URI, falls kein Präfix bekannt

def source_info(obj, ns_map):
    """Kurze Info mit Namespace-Präfix und Schema-Datei."""
    parts = []
    try:
        parts.append(f"ns={ns_prefix_of(obj, ns_map)}")
    except Exception:
        pass
    try:
        sch = getattr(obj, "schema", None)
        if sch:
            parts.append(f"file={schema_filename(sch)}")
    except Exception:
        pass
    return f" [{' , '.join(parts)}]" if parts else ""

def print_tree(obj, indent="", is_last=True, visited_types=None, ns_map=None):
    """Rekursives Anzeigen von Elementen und complexTypes."""
    if visited_types is None:
        visited_types = set()

    branch = "└── " if is_last else "├── "
    occurs = ""
    type_name = ""
    name = ""

    cls = obj.__class__.__name__

    if cls == "XsdElement":
        name = obj.name or getattr(obj, "local_name", "<element>")
        if hasattr(obj, "min_occurs") and hasattr(obj, "max_occurs"):
            occurs = format_occurs(obj.min_occurs, obj.max_occurs)
        if getattr(obj, "type", None) and obj.type.name:
            type_name = f" : {obj.type.name}"
    elif cls == "XsdComplexType":
        name = obj.name or "[anonymous type]"
    else:
        name = getattr(obj, "name", None) or str(obj)

    print(f"{indent}{branch}{name}{occurs}{type_name}{source_info(obj, ns_map)}")
    indent += "    " if is_last else "│   "

    # Endlosschutz für Typ-Zyklen
    type_id = getattr(obj, "name", None)
    if type_id and type_id in visited_types:
        print(f"{indent}└── [bereits angezeigt]")
        return
    if type_id:
        visited_types.add(type_id)

    # In komplexe Typen hinabsteigen
    if cls == "XsdElement" and getattr(obj, "type", None) and obj.type.is_complex():
        print_type_content(obj.type, indent, visited_types, ns_map)
    elif cls == "XsdComplexType":
        print_type_content(obj, indent, visited_types, ns_map)

def print_type_content(ctype, indent, visited_types, ns_map):
    """Gibt die Inhalte eines complexType aus: Sub-Elemente und Attribute."""
    # Attribute
    if hasattr(ctype, "attributes"):
        attrs = list(ctype.attributes.values())
        for i, attr in enumerate(attrs):
            branch = "└── " if (i == len(attrs) - 1 and not getattr(ctype, "content", None)) else "├── "
            occurs = " (0..1)" if getattr(attr, "use", None) == "optional" else ""
            print(f"{indent}{branch}@{attr.name}{occurs}{source_info(attr, ns_map)}")

    # Sub-Elemente
    if getattr(ctype, "content", None):
        try:
            elements = list(ctype.content.iter_elements())
        except Exception:
            elements = []
        for i, child in enumerate(elements):
            print_tree(child, indent, i == len(elements) - 1, visited_types, ns_map)

def print_schema_tree(schema, ns_map, filter_element=None, is_last=True, indent="", visited_schemas=None):
    """Zeigt ein gesamtes Schema (inkl. globaler Elemente) an und listet includes/imports als Unterknoten."""
    if visited_schemas is None:
        visited_schemas = set()

    # Endlosschutz auf Schema-Ebene
    sid = id(schema)
    if sid in visited_schemas:
        branch = "└── " if is_last else "├── "
        print(f"{indent}{branch}[Schema bereits angezeigt]")
        return
    visited_schemas.add(sid)

    schema_name = schema_filename(schema)
    branch = "└── " if is_last else "├── "
    print(f"{indent}{branch}Schema {schema_name} [ns={ns_prefix_of(schema, ns_map)}]")

    indent_child = "    " if is_last else "│   "
    indent = indent + indent_child

    # Elemente für dieses Schema
    elements = list(schema.elements.values())

    if filter_element:
        elem = schema.elements.get(filter_element)
        if elem:
            print_tree(elem, indent, True, ns_map=ns_map)
    else:
        for i, elem in enumerate(elements):
            print_tree(elem, indent, i == len(elements) - 1, ns_map=ns_map)

    # Eingebundene Schemas
    imports_objs = list(getattr(schema, 'imports', {}).values())
    includes_objs = list(getattr(schema, 'includes', []))
    sub_schemas = []

    # imports: dict von namespace->XsdImport; includes: Liste XsdInclude
    for imp in imports_objs:
        sch = getattr(imp, 'schema', None)
        if sch:
            sub_schemas.append(sch)
    for inc in includes_objs:
        sch = getattr(inc, 'schema', None)
        if sch:
            sub_schemas.append(sch)

    # Doppelte vermeiden
    seen_ids = set()
    uniq = []
    for sch in sub_schemas:
        if id(sch) not in seen_ids:
            uniq.append(sch)
            seen_ids.add(id(sch))

    for i, subschema in enumerate(uniq):
        print_schema_tree(subschema, ns_map, None, i == len(uniq) - 1, indent, visited_schemas)

def main(xsd_path, filter_element=None):
    schema = xmlschema.XMLSchema(xsd_path)
    ns_map = build_nsmap(schema)

    print(f"Schema-Übersicht für: {xsd_path}\n")
    print_schema_tree(schema, ns_map, filter_element)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Benutzung: python xsd_tree.py schema.xsd [GlobalElementName]")
        sys.exit(1)

    xsd_file = Path(sys.argv[1])
    if not xsd_file.exists():
        print(f"Fehler: Datei {xsd_file} nicht gefunden.")
        sys.exit(1)

    filter_element = sys.argv[2] if len(sys.argv) > 2 else None
    main(xsd_file, filter_element)
