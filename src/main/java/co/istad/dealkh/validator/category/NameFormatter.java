package co.istad.dealkh.validator.category;

public class NameFormatter {

    public static String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // Remove all invalid characters except for letters, digits, and spaces
        name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
        // Replace multiple spaces with a single space
        name = name.replaceAll("\\s+", " ").trim();
        return name;
    }
}
