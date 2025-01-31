package org.intellij.plugins.markdown.settings;

import consulo.logging.Logger;
import consulo.util.xml.serializer.annotation.Attribute;
import jakarta.annotation.Nonnull;

import java.net.URISyntaxException;
import java.net.URL;

public final class MarkdownCssSettings {
  public static final MarkdownCssSettings DEFAULT = new MarkdownCssSettings(false);
  public static final MarkdownCssSettings DARCULA = new MarkdownCssSettings(true);

  @Attribute("UriEnabled")
  private boolean myUriEnabled;
  @Attribute("StylesheetUri")
  @Nonnull
  private String myStylesheetUri;
  @Attribute("TextEnabled")
  private boolean myTextEnabled;
  @Attribute("StylesheetText")
  @Nonnull
  private String myStylesheetText;

  private MarkdownCssSettings() {
    this(true, getPredefinedCssURI(false), false, "");
  }

  private MarkdownCssSettings(boolean isDarcula) {
    this(true, getPredefinedCssURI(isDarcula), false, "");
  }

  public MarkdownCssSettings(boolean uriEnabled, @Nonnull String stylesheetUri, boolean textEnabled, @Nonnull String stylesheetText) {
    myUriEnabled = uriEnabled;
    myStylesheetUri = stylesheetUri;
    myTextEnabled = textEnabled;
    myStylesheetText = stylesheetText;
  }

  public boolean isUriEnabled() {
    return myUriEnabled;
  }

  @Nonnull
  public String getStylesheetUri() {
    return myStylesheetUri;
  }

  public boolean isTextEnabled() {
    return myTextEnabled;
  }

  @Nonnull
  public String getStylesheetText() {
    return myStylesheetText;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MarkdownCssSettings settings = (MarkdownCssSettings)o;

    if (myUriEnabled != settings.myUriEnabled) return false;
    if (myTextEnabled != settings.myTextEnabled) return false;
    if (!myStylesheetUri.equals(settings.myStylesheetUri)) return false;
    if (!myStylesheetText.equals(settings.myStylesheetText)) return false;

    return true;
  }

  @Nonnull
  public static MarkdownCssSettings getDefaultCssSettings(boolean isDarcula) {
    return isDarcula ? DARCULA : DEFAULT;
  }

  @Nonnull
  private static String getPredefinedCssURI(boolean isDarcula) {
    final String fileName = isDarcula ? "darcula.css" : "default.css";
    try {
      final URL resource = MarkdownCssSettings.class.getResource(fileName);
      return resource != null ? resource.toURI().toString() : "";
    }
    catch (URISyntaxException e) {
      Logger.getInstance(MarkdownCssSettings.class).error(e);
      return "";
    }
  }

  @Override
  public int hashCode() {
    int result = (myUriEnabled ? 1 : 0);
    result = 31 * result + myStylesheetUri.hashCode();
    result = 31 * result + (myTextEnabled ? 1 : 0);
    result = 31 * result + myStylesheetText.hashCode();
    return result;
  }

  public interface Holder {
    void setMarkdownCssSettings(@Nonnull MarkdownCssSettings settings);

    @Nonnull
    MarkdownCssSettings getMarkdownCssSettings();
  }
}
