package co.istad.dealkh.validator.category;

public class SlugFormatter {

    public static String formatSlug(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // Convert to lower case
        name = name.toLowerCase();
        // Remove all invalid characters except for letters, digits, spaces, and hyphens
        name = name.replaceAll("[^a-z0-9\\s-]", "");
        // Replace sequences of spaces or hyphens with a single hyphen
        name = name.replaceAll("[\\s-]+", "-");
        // Remove any leading or trailing hyphens
        name = name.replaceAll("(^-+|-+$)", "");
        return name;
    }
}
