package com.coremedia.blueprint.connectors.upload;

import com.coremedia.blueprint.connectors.api.ConnectorCategory;
import com.coremedia.blueprint.connectors.api.ConnectorContext;
import com.coremedia.blueprint.connectors.impl.Connectors;
import com.coremedia.cap.content.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 */
public class ConnectorContentUploadService {
  private static final Logger LOG = LoggerFactory.getLogger(ConnectorContentUploadService.class);

  private ExecutorService service = Executors.newCachedThreadPool();

  private Connectors connectors;

  public void upload(@NonNull ConnectorContext context, ConnectorCategory category, @NonNull List<Content> contents, Boolean defaultAction) {
    try {
      ConnectorContentUploadCallable callable = new ConnectorContentUploadCallable(context, category, contents, defaultAction);
      Future<Void> submit = service.submit(callable);
      submit.get();
      category.refresh(context);
      this.connectors.notifyCategoryChange(context, category);
    } catch (Exception e) {
      LOG.error("Error submitting connector content callable for " + category + ": " + e.getMessage(), e);
    }
  }

  @Required
  public void setConnectors(Connectors connectors) {
    this.connectors = connectors;
  }
}
