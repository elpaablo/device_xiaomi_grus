LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES := camera.shim.c
LOCAL_MODULE := camera.shim
LOCAL_MODULE_TAGS := optional
LOCAL_VENDOR_MODULE := true
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_SHARED_LIBRARIES := libmedia
LOCAL_SRC_FILES := libmedia_jni.cpp
LOCAL_MODULE := libmedia_jni_shim
LOCAL_MODULE_TAGS := optional
include $(BUILD_SHARED_LIBRARY)
