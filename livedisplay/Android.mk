# Copyright (C) 2019 The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := vendor.lineage.livedisplay@2.0-service.xiaomi
LOCAL_INIT_RC := vendor.lineage.livedisplay@2.0-service.xiaomi.rc
LOCAL_VENDOR_MODULE := true
LOCAL_MODULE_RELATIVE_PATH := hw
LOCAL_HEADER_LIBRARIES := generated_kernel_headers
LOCAL_REQUIRED_MODULES := privapp-permissions-livedisplay.xml

LOCAL_SRC_FILES := \
    SunlightEnhancement.cpp \
    DisplayModes.cpp \
    DisplayModesSDM.cpp \
    PictureAdjustment.cpp \
    SDMController.cpp \
    Utils.cpp \
    service.cpp \
#    ColorEnhancement.cpp \
#    AdaptiveBacklight.cpp \

LOCAL_SHARED_LIBRARIES := \
    libbase \
    libbinder \
    libcutils \
    libdl \
    libhidlbase \
    libhidltransport \
    liblog \
    libutils \
    vendor.lineage.livedisplay@2.0

include $(BUILD_EXECUTABLE)

include $(CLEAR_VARS)
LOCAL_MODULE := privapp-permissions-livedisplay.xml
LOCAL_MODULE_CLASS := ETC
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_PATH := $(TARGET_OUT_SYSTEM_EXT_ETC)/permissions
LOCAL_PRODUCT_MODULE := true
LOCAL_SRC_FILES := $(LOCAL_MODULE)
include $(BUILD_PREBUILT)
