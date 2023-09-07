LOCAL_PATH := $(call my-dir)

include $(call all-makefiles-under,$(LOCAL_PATH))

PRODUCT_PACKAGES += \
    google_default_allowlist \
    privapp_permissions_turbo \
    sysconfig_power \
    GoogleOverlay

#    privapp_permissions_storage_manager \
