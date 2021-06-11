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
$(call inherit-product, $(SRC_TARGET_DIR)/product/product_launched_with_p.mk)

# Inherit some common stuff
$(call inherit-product, vendor/fluid/config/common_full_phone.mk)

# Inherit from land device
$(call inherit-product, $(DEVICE_PATH)/device.mk)

# Device identifier. This must come after all inclusions.
PRODUCT_NAME := fluid_grus
PRODUCT_DEVICE := grus
PRODUCT_BRAND := Xiaomi
PRODUCT_MODEL := MI 9 SE
PRODUCT_MANUFACTURER := Xiaomi

BUILD_FINGERPRINT := "google/redfin/redfin:11/RQ3A.210605.005/7349499:user/release-keys"

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="redfin-user 11 RQ3A.210605.005 7349499 release-keys" \
    PRODUCT_NAME="grus" \
    TARGET_DEVICE="grus"

MY_BUILD_TYPE := VANILLA-CUSTOM
TARGET_INCLUDE_GAPPS := false
TARGET_INCLUDE_MIUI_CAMERA := false

PRODUCT_ENFORCE_VINTF_MANIFEST := false

PRODUCT_PROPERTY_OVERRIDES += \
    ro.build.fingerprint=$(BUILD_FINGERPRINT) \
    ro.build.description=$(PRIVATE_BUILD_DESC)


PRODUCT_PROPERTY_OVERRIDES += \
    persist.device_config.runtime_native.usap_pool_enabled=true \
    ro.debuggable=0

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
PRODUCT_ART_TARGET_INCLUDE_DEBUG_BUILD := false
PRODUCT_MINIMIZE_JAVA_DEBUG_INFO := true
PRODUCT_PACKAGES_DEBUG := false
PRODUCT_PACKAGES_DEBUG_ASAN := false
PRODUCT_DEXPREOPT_SPEED_APPS += SystemUI

