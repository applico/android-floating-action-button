android-floating-action-button
==============================
Library to use the Floating Action Button (FAB) from Android L, currently for use only on Android L devices.  Other implementations of the code used drawables.  The goal for this was to create a library that used a custom view in order to allow the developer for more flexbility on implementation.  I pariculary did not like being stuck using MINI and NORMAL modes for the fab size, while I understand that those sizes are recommended by Google I found a need on one of my projects to customize the FAB.

###Instructions
==============================

1.) Place the FAB in your relative, linear or frame layout

```
<applico.android_floating_action_button.views.FabView
android:id="@+id/fab_view"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_alignParentBottom="true"
android:layout_alignParentRight="true"
android:layout_marginRight="16dp"
android:layout_marginBottom="20dp"
fab:cFillColor="@color/fabColor"
fab:cStrokeColor="@color/fabStrokeColor"
fab:cStrokeWidth="2"
fab:cSize="NORMAL"
fab:cRadius="100"
fab:cDrawable="@drawable/icn_fab_favorite"
/>
```

2.) Initialize your fab in your code

Fab f = (Fab)findViewById(R.id.fab);

3.) Have fun!

###Notes
This FAB has full attribute and getter setter support.  The supported attributes are:
* cRadius - this overrides the cSize attribute for custom sizes
* cSize - this is an enumeration. MINI and NORMAL are supported to enforce Material Design standards
* cFillColor - this is the fill color of the FAB
* cStrokeColor - this is the stroke color of the FAB
* cStrokeWidth - this is the stroke width of the FAB
* cDrawable - this is the drawable of the FAB, centered.
* cElevationOffsetX - this is not currently supported
* cElevationOffsetY - this is not currently supported

###License
Copyright 2014 Applico, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


###Screenshots
![alt tag](https://raw.githubusercontent.com/applico/android-floating-action-button/master/screenshots/screenshot_gangnam.png?token=2132637__eyJzY29wZSI6IlJhd0Jsb2I6YXBwbGljby9hbmRyb2lkLWZsb2F0aW5nLWFjdGlvbi1idXR0b24vbWFzdGVyL3NjcmVlbnNob3RzL3NjcmVlbnNob3RfZ2FuZ25hbS5wbmciLCJleHBpcmVzIjoxNDA5NDI4MjY1fQ%3D%3D--5f23bbabd099470e917241018ced7a6d943ec49a)
