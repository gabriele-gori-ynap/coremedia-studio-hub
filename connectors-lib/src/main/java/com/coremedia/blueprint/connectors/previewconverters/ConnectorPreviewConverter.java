package com.coremedia.blueprint.connectors.previewconverters;

import com.coremedia.blueprint.connectors.api.ConnectorContext;
import com.coremedia.blueprint.connectors.api.ConnectorItem;
import com.coremedia.common.util.Predicate;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.File;

/**
 * Interface that can be implemented when item data should be converted
 * before previewing it.
 */
public interface ConnectorPreviewConverter extends Predicate<ConnectorItem> {

  /**
   * Returns the preview HTML for the given connectorItem or null if
   * the connectorItem did not support the operation
   *
   * @param context the connection context
   * @param connectorItem the connectorItem to generate the previe for
   * @param itemTempFile the temp file that has been generated for generating the preview
   * @return HTML used for the preview
   */
  @Nullable
  PreviewConversionResult convert(@NonNull ConnectorContext context, @NonNull ConnectorItem connectorItem, @NonNull File itemTempFile);
}
