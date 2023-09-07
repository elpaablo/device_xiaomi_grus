#
# Copyright (C) 2018 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

DEVICE_PATH := device/xiaomi/grus
TARGET_APPS_ARCH := arm64

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

$(call inherit-product, device/xiaomi/grus/device.mk)

# Inherit some common Aosp stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Device identifier. This must come after all inclusions.
PRODUCT_NAME := lineage_grus
PRODUCT_DEVICE := grus
PRODUCT_BRAND := Xiaomi
PRODUCT_MODEL := Mi 9 SE
PRODUCT_MANUFACTURER := Xiaomi

WITH_GAPPS := false
TARGET_CORE_GAPPS := false
TARGET_OPTOUT_GOOGLE_TELEPHONY := false

# Alpha internal properties
TARGET_HAS_UDFPS := true
TARGET_ENABLE_BLUR := true
TARGET_INCLUDE_MATLOG := false
TARGET_USE_GRAPHENE_CAMERA := false
TARGET_USE_PIXEL_LAUNCHER := false
TARGET_EXCLUDES_AUDIOFX := true
TARGET_FACE_UNLOCK_SUPPORTED := true

ALPHA_BUILD_TYPE := Official
ALPHA_MAINTAINER := elpaablo

### out ###
#BUILD_FINGERPRINT := "google/raven/raven:13/TP1A.221005.002/9012097:user/release-keys" \

#PRODUCT_BUILD_PROP_OVERRIDES += \
#    PRIVATE_BUILD_DESC="raven-user 13 TP1A.221005.002 9012097 release-keys" \
#    PRODUCT_NAME="grus" \
#    TARGET_DEVICE="grus"

# PRODUCT_PROPERTY_OVERRIDES += \
#    ro.build.fingerprint=$(BUILD_FINGERPRINT) \
#    ro.build.description=$(PRIVATE_BUILD_DESC)

PRODUCT_ENFORCE_VINTF_MANIFEST := false

PRODUCT_GAPPS_CLIENTID_BASE := android-xiaomi
PRODUCT_PACKAGES_DEBUG := false
PRODUCT_PACKAGES_DEBUG_ASAN := false

# Mark build as not debuggable
PRODUCT_PROPERTY_OVERRIDES += \
    ro.debuggable=0

# Fling Sysprops
PRODUCT_SYSTEM_EXT_PROPERTIES += \
    ro.min.fling_velocity=50 \
    ro.max.fling_velocity=16000
