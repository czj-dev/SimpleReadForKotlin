<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:tools="http://schemas.android.com/tools">
    
    <data>
    <#if (needModel)>
      <variable
                name="viewModel"
                type="${modelPackageName}.${viewModelClass}"/>
    </#if>

    </data>
  
    <LinearLayout 
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
 
  
    </LinearLayout>
</layout>