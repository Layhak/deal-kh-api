package co.istad.dealkh.validator.category;

public class NameFormatter {

    public static String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // Convert to lower case, trim, and normalize spaces and hyphens
        name = name.toLowerCase();
        // Remove all invalid characters except for letters, digits, spaces, and hyphens
        name = name.replaceAll("[^a-zA-Z0-9\\s-]", "");
        // Replace sequences of spaces or hyphens with a single space
        name = name.replaceAll("[\\s-]+", " ");
        // Capitalize each word
        String[] words = name.split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        // Trim the final string to remove any trailing spaces
        return formattedName.toString().trim();
    }
}
