package com.coremedia.blueprint.studio.connectors.model {
import com.coremedia.cap.undoc.content.Content;
import com.coremedia.ui.data.impl.RemoteServiceMethod;
import com.coremedia.ui.data.impl.RemoteServiceMethodResponse;

import ext.JSON;

[RestResource(uriTemplate="connector/category/{externalId:.+}")]
public class ConnectorCategoryImpl extends ConnectorEntityImpl implements ConnectorCategory {
  public function ConnectorCategoryImpl(uri:String, vars:Object) {
    super(uri);
    // set immediate vars
    setImmediateProperty(ConnectorPropertyNames.EXTERNAL_ID, vars.externalId);
  }

  public function getChildren():Array {
    return get(ConnectorPropertyNames.CHILDREN);
  }

  public function getSubCategories():Array {
    return get(ConnectorPropertyNames.SUB_CATEGORIES);
  }

  public function getItems():Array {
    return get(ConnectorPropertyNames.ITEMS);
  }

  public function getChildrenByName():Object {
    return get(ConnectorPropertyNames.CHILDREN_BY_NAME);
  }

  private function getRefreshUri():String {
    return get(ConnectorPropertyNames.REFRESH_URI);
  }

  public function isWriteable():Boolean {
    return get(ConnectorPropertyNames.WRITEABLE);
  }

  public function getColumns():Array {
    return get(ConnectorPropertyNames.COLUMNS);
  }

  public function getType():String {
    return get(ConnectorPropertyNames.TYPE);
  }

  public function isContentUploadEnabled():Boolean {
    return get(ConnectorPropertyNames.CONTENT_UPLOAD_ENABLED);
  }

  public function dropContents(contents:Array, defaultAction:Boolean, callback:Function = undefined) {
    var method:RemoteServiceMethod = new RemoteServiceMethod(getContentDropUri(), 'POST');
    var contentIds:Array = [];
    for each(var c:Content in contents) {
      contentIds.push(c.getNumericId());
    }
    var data:Object = {
      'contentIds': contentIds.join(","),
      'defaultAction': defaultAction
    };

    method.request(data,
            function (response:RemoteServiceMethodResponse):void {
              if(callback) {
                callback();
              }

            },
            function (response:RemoteServiceMethodResponse):void {
              if(callback) {
                callback(response.getError());
              }
            }
    );
  }

  override public function preview(callback:Function):void {
    var method:RemoteServiceMethod = new RemoteServiceMethod(getPreviewUri(), 'GET');
    method.request(
            {},
            function (response:RemoteServiceMethodResponse):void {
              var result:String = response.response.responseText;
              if (result) {
                var previewRepresentation:Object = JSON.decode(result);
                var html:String = previewRepresentation.html;
              }
              callback(html, previewRepresentation[ConnectorPropertyNames.META_DATA]);
            },
            function (response:RemoteServiceMethodResponse):void {
              callback(response.getError());
            }
    );
  }

  public function refresh(callback:Function = null):void {
    var method:RemoteServiceMethod = new RemoteServiceMethod(getRefreshUri(), 'GET');
    method.request({},
            function (response:RemoteServiceMethodResponse):void {
              invalidate(callback);
            },
            function (response:RemoteServiceMethodResponse):void {
              invalidate(callback);
            }
    );
  }


  public function getContentDropUri():String {
    return get(ConnectorPropertyNames.CONTENT_DROP_URI);
  }

  public function getUploadUri():String {
    return get(ConnectorPropertyNames.UPLOAD_URI);
  }
}
}
