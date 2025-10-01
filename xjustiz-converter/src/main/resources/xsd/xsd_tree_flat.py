
"""
Python script for visualizing XSD content (similar to mvn dependency:tree).
The output shows global and complex types only (without nested sub elements) for the simpler optional selection with the script xsd_tree_recursive.py.
Example call: python -X utf8 xsd_tree_flat.py xjustiz_0500_straf_3_6.xsd > xsdTreeSimple_xjustiz_0500_straf_3_6.txt
"""

import xmlschema
import sys
from pathlib import Path

def format_occurs(min_occurs, max_occurs):
    """Formatiert die Kardinalität als (min..max)."""
    max_str = "*" if max_occurs is None else str(max_occurs)
    if min_occurs == 1 and max_occurs == 1:
        return ""
    return f" ({min_occurs}..{max_str})"

def print_tree(obj, indent="", is_last=True, visited_types=None):
    """Gibt die Struktur eines Elements oder complexTypes rekursiv aus."""
    if visited_types is None:
        visited_types = set()

    branch = "└── " if is_last else "├── "
    occurs = ""
    type_name = ""

    # Wenn es ein Element ist
    if obj.__class__.__name__ == "XsdElement":
        if hasattr(obj, "min_occurs") and hasattr(obj, "max_occurs"):
            occurs = format_occurs(obj.min_occurs, obj.max_occurs)
        if obj.type and obj.type.name:
            type_name = f" : {obj.type.name}"
        name = obj.name or obj.local_name
    # Wenn es ein ComplexType ist
    elif obj.__class__.__name__ == "XsdComplexType":
        name = obj.name or "[anonymous type]"
    else:
        name = str(obj)

    print(f"{indent}{branch}{name}{occurs}{type_name}")

    indent += "    " if is_last else "│   "

    # Rekursive Typverarbeitung mit Endlosschutz
    type_id = getattr(obj, "name", None)
    if type_id and type_id in visited_types:
        print(f"{indent}└── [bereits angezeigt]")
        return
    if type_id:
        visited_types.add(type_id)

    # Kinder holen
    if hasattr(obj, "content") and obj.content:
        children = list(obj.content.iter_elements())
        for i, child in enumerate(children):
            print_tree(child, indent, i == len(children) - 1, visited_types)

def main(xsd_path):
    schema = xmlschema.XMLSchema(xsd_path)
    print(f"Schema: {xsd_path}\n")

    print("Globale Elemente:")
    elements = list(schema.elements.values())
    for i, elem in enumerate(elements):
        print_tree(elem, "", i == len(elements) - 1)

    print("\nGlobale complexTypes:")
    types = [t for t in schema.types.values() if t.is_complex() and t.name]
    for ctype in types:
        print(f"{ctype.name}")
        print_tree(ctype, "", True)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Benutzung: python xsd_tree.py schema.xsd")
        sys.exit(1)

    xsd_file = Path(sys.argv[1])
    if not xsd_file.exists():
        print(f"Fehler: Datei {xsd_file} nicht gefunden.")
        sys.exit(1)

    main(xsd_file)
