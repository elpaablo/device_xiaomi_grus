/*
 * Copyright (C) 2.06 The CyanogenMod Project
 *               2.07-2.09 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef VENDOR_LINEAGE_LIVEDISPLAY_V2_0_SDM_UTILS_H
#define VENDOR_LINEAGE_LIVEDISPLAY_V2_0_SDM_UTILS_H

#include "SDMController.h"
#include "Types.h"

namespace vendor {
namespace lineage {
namespace livedisplay {
namespace V2_0 {
namespace implementation {

class Utils {
   public:
    static int readInt(const char* node, int32_t* value);
    static int writeInt(const char* node, int32_t value);
    static int writeSavedModeId(int32_t id);
    static int readSavedModeId(int32_t* id);
    static int sendDPPSCommand(char* buf, size_t len);
    static bool checkFeatureVersion(SDMController* controller, uint64_t cookie,
                                    feature_ver_sw feature);
};

}  // namespace implementation
}  // namespace V2_0
}  // namespace livedisplay
}  // namespace lineage
}  // namespace vendor

#endif  // VENDOR_LINEAGE_LIVEDISPLAY_V2_0_SDM_UTILS_H
