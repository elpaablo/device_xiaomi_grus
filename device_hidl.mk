#
# Copyright (C) 2018 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Bluetooth
PRODUCT_PACKAGES += \
    android.hardware.bluetooth@1.1.vendor \
    android.hardware.bluetooth.audio@2.1.vendor \
    vendor.qti.hardware.bluetooth_audio@2.0.vendor

# CODEC 2
PRODUCT_PACKAGES += \
    libcodec2_vndk.vendor \
    libcodec2_hidl@1.0.vendor

# Keymaster
PRODUCT_PACKAGES += \
    android.hardware.gatekeeper@1.0.vendor \
    android.hardware.keymaster@4.1.vendor

# RIL
PRODUCT_PACKAGES += \
    android.hardware.radio.deprecated@1.0.vendor \
    android.hardware.radio@1.2.vendor \
    android.hardware.radio@1.5.vendor \
    android.hardware.radio.config@1.1.vendor \
    android.hardware.radio.config@1.2.vendor

# Secure Element
 PRODUCT_PACKAGES += \
    android.hardware.secure_element@1.1.vendor \
    android.hardware.secure_element@1.2.vendor

# Sensors
PRODUCT_PACKAGES += \
    android.frameworks.sensorservice@1.0.vendor

# Wifi
PRODUCT_PACKAGES += \
    vendor.qti.hardware.wifi.hostapd@1.0.vendor \
    vendor.qti.hardware.wifi.supplicant@1.0.vendor \
    vendor.qti.hardware.wifi.supplicant@2.0.vendor
