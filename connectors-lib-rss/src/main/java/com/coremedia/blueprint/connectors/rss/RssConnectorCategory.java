package com.coremedia.blueprint.connectors.rss;

import com.coremedia.blueprint.connectors.api.ConnectorCategory;
import com.coremedia.blueprint.connectors.api.ConnectorColumn;
import com.coremedia.blueprint.connectors.api.ConnectorContext;
import com.coremedia.blueprint.connectors.api.ConnectorId;
import com.coremedia.blueprint.connectors.api.ConnectorItem;
import com.coremedia.blueprint.connectors.library.DefaultConnectorColumn;
import com.rometools.rome.feed.synd.SyndFeed;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RssConnectorCategory extends RssConnectorEntity implements ConnectorCategory {
  private List<ConnectorCategory> subCategories = new ArrayList<>();
  private List<ConnectorItem> items = new ArrayList<>();
  private RssConnectorServiceImpl service;

  RssConnectorCategory(RssConnectorServiceImpl service, ConnectorContext context, SyndFeed feed, ConnectorId connectorId) {
    super(null, context, feed, connectorId);
    this.service = service;
  }

  @Override
  public String getType() {
    return "feed";
  }

  @Override
  public Date getLastModified() {
    return getFeed().getPublishedDate();
  }

  @NonNull
  @Override
  public List<ConnectorColumn> getColumns() {
    return Arrays.asList(new DefaultConnectorColumn("author_header", "author", 120, 2));
  }

  @Nullable
  @Override
  public String getManagementUrl() {
    return getFeed().getLink();
  }

  @NonNull
  @Override
  public List<ConnectorCategory> getSubCategories() {
    return subCategories;
  }

  @NonNull
  @Override
  public List<ConnectorItem> getItems() {
    return items;
  }

  @Override
  public boolean isWriteable() {
    return false;
  }

  void setSubCategories(List<ConnectorCategory> subCategories) {
    this.subCategories = subCategories;
  }

  void setItems(List<ConnectorItem> items) {
    this.items = items;
  }

  @Override
  public boolean refresh(@NonNull ConnectorContext context) {
    return service.refresh(context, this);
  }

}
