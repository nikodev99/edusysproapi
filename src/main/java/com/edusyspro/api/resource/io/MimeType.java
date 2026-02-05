package com.edusyspro.api.resource.io;

import java.util.*;
import java.util.stream.Collectors;

public enum MimeType {
    PDF("application/pdf", "templates"),

    IMAGE_JPEG("image/jpeg", "jpg", "jpeg"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_SVG_XML("image/svg+xml", "svg"),
    IMAGE_WEBP("image/webp", "webp"),

    TEXT_PLAIN("text/plain", "txt"),
    HTML("text/html", "html", "htm"),
    CSV("text/csv", "csv"),

    JSON("application/json", "json"),
    XML("application/xml", "xml"),

    ZIP("application/zip", "zip"),
    X_TAR("application/x-tar", "tar"),
    GZIP("application/gzip", "gz"),
    OCTET_STREAM("application/octet-stream"),

    AUDIO_MPEG("audio/mpeg", "mp3"),
    VIDEO_MP4("video/mp4", "mp4"),

    MS_WORD("application/msword", "doc"),
    OPENXML_WORD("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),
    MS_EXCEL("application/vnd.ms-excel", "xls"),
    OPENXML_EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
    MS_POWERPOINT("application/vnd.ms-powerpoint", "ppt"),
    OPENXML_PPT("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx"),

    MULTIPART_FORM_DATA("multipart/form-data", "form-data");

    private final String mimeType;
    private final List<String> extensions;

    // Lookup maps
    private static final Map<String, MimeType> MIME_TO_ENUM;
    private static final Map<String, MimeType> EXT_TO_ENUM;

    static {
        MIME_TO_ENUM = Collections.unmodifiableMap(
                Arrays.stream(values())
                        .collect(Collectors.toMap(m -> m.mimeType, m -> m, (a, b) -> a, LinkedHashMap::new))
        );

        var extMap = new LinkedHashMap<String, MimeType>();
        for (var m : values()) {
            for (var ext : m.extensions) {
                extMap.put(ext.toLowerCase(Locale.ROOT), m);
            }
        }
        EXT_TO_ENUM = Collections.unmodifiableMap(extMap);
    }

    MimeType(String mimeType, String... extension) {
        this.mimeType = Objects.requireNonNull(mimeType, "mimeType");
        this.extensions = Arrays.stream(extension == null ? new String[0] : extension)
                .filter(Objects::nonNull)
                .map(s -> s.toLowerCase(Locale.ROOT)).toList();
    }

    /**
     * Retrieves the MIME type associated with this instance.
     *
     * @return the MIME type as a string.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Retrieves the list of file extensions associated with the MIME type.
     *
     * @return a list of strings representing the file extensions.
     *         If no extensions are available, the list will be empty.
     */
    public List<String> getExtensions() {
        return extensions;
    }

    /**
     * Retrieves the primary file extension associated with the MIME type.
     *
     * @return an {@code Optional} containing the primary file extension if available,
     *         or {@code Optional.empty()} if no extensions are associated.
     */
    public Optional<String> getPrimaryExtension() {
        return extensions.isEmpty() ? Optional.empty() : Optional.of(extensions.get(0));
    }

    /**
     * Retrieves an {@code Optional} containing the {@code MimeType} corresponding
     * to the provided MIME type string. The lookup is case-insensitive.
     *
     * @param mime the MIME type string to look up. If null, an empty {@code Optional} is returned.
     * @return an {@code Optional} containing the matched {@code MimeType} if found;
     *         otherwise, an empty {@code Optional}.
     */
    public static Optional<MimeType> fromMimeType(String mime) {
        if (mime == null) return Optional.empty();
        var exact = MIME_TO_ENUM.get(mime);
        if (exact != null) return Optional.of(exact);
        // try case-insensitive
        return MIME_TO_ENUM.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(mime))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    /**
     * Retrieves an {@code Optional} containing the {@code MimeType} corresponding to the provided file extension.
     * The lookup is case-insensitive and will ignore a leading dot (.) in the extension if present.
     *
     * @param extension the file extension to look up. If the extension is null, blank, or not found,
     *                  an empty {@code Optional} is returned.
     * @return an {@code Optional} containing the matched {@code MimeType} if found; otherwise,
     *         an empty {@code Optional}.
     */
    public static Optional<MimeType> fromExtension(String extension) {
        if (extension == null || extension.isBlank()) return Optional.empty();
        var ext = extension.startsWith(".") ? extension.substring(1) : extension;
        return Optional.ofNullable(EXT_TO_ENUM.get(ext.toLowerCase(Locale.ROOT)));
    }

    /**
     * Attempts to determine the {@code MimeType} based on the provided file name.
     * The method extracts the file extension from the name and uses it to look up the corresponding MIME type.
     *
     * @param fileName the name of the file, including the extension. If the file name is null, blank,
     *                 or does not contain a valid extension, an empty {@code Optional} is returned.
     * @return an {@code Optional} containing the matched {@code MimeType} if a corresponding MIME type
     *         is found; otherwise, an empty {@code Optional}.
     */
    public static Optional<MimeType> fromFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) return Optional.empty();
        var idx = fileName.lastIndexOf('.');
        if (idx < 0 || idx == fileName.length() - 1) return Optional.empty();
        return fromExtension(fileName.substring(idx + 1));
    }

    @Override
    public String toString() {
        return mimeType;
    }
}
