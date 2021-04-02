#!/bin/bash
find $1 -type f -readable -writable -exec sed -i "s/org\.lineageos\.settings/com.xiaomi.parts/g" {} \;
