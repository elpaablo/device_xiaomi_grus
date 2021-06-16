/*
 * Copyright (C) 2.09 The LineageOS Project
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

#include <dlfcn.h>

#define LOG_TAG "lineage.livedisplay@2.1-service.xiaomi"

#include <android-base/logging.h>
#include <binder/ProcessState.h>
#include <hidl/HidlTransportSupport.h>
//#include "AntiFlicker.h"
#include "DisplayModes.h"
#include "DisplayModesSDM.h"
#include "PictureAdjustment.h"
#include "SDMController.h"

using android::OK;
using android::sp;
using android::status_t;
using android::hardware::configureRpcThreadpool;
using android::hardware::joinRpcThreadpool;

//using ::vendor::lineage::livedisplay::V2_1::IAntiFlicker;
using ::vendor::lineage::livedisplay::V2_0::IDisplayModes;
using ::vendor::lineage::livedisplay::V2_0::IPictureAdjustment;
//using ::vendor::lineage::livedisplay::V2_1::implementation::AntiFlicker;
using ::vendor::lineage::livedisplay::V2_0::implementation::DisplayModes;
using ::vendor::lineage::livedisplay::V2_0::implementation::DisplayModesSDM;
using ::vendor::lineage::livedisplay::V2_0::implementation::PictureAdjustment;
using ::vendor::lineage::livedisplay::V2_0::implementation::SDMController;

int main() {
    
    android::ProcessState::initWithDriver("/dev/vndbinder");
    
    // Vendor backend
    std::shared_ptr<SDMController> controller;
    uint64_t cookie = 0;

    // HIDL frontend
//    sp<IAntiFlicker> af;
    sp<DisplayModes> dm;
    sp<DisplayModesSDM> dms;
    sp<PictureAdjustment> pa;

    status_t status = OK;

    LOG(INFO) << "LiveDisplay HAL custom service is starting.";

    configureRpcThreadpool(1, true /*callerWillJoin*/);

/*    af = new AntiFlicker();
    if (af->registerAsService() != android::OK) {
        LOG(ERROR) << "Cannot register anti flicker HAL service.";
        return 1;
    }
*/    
    controller = std::make_shared<SDMController>();
    if (controller == nullptr) {
        LOG(ERROR) << "Failed to create SDMController";
        goto shutdown;
    }

    status = controller->init(&cookie, 0);
    if (status != OK) {
        LOG(ERROR) << "Failed to initialize SDMController";
        goto shutdown;
    }

    dm = new DisplayModes();
    if (dm == nullptr) {
        LOG(ERROR) << "Can not create an instance of LiveDisplay HAL DisplayModes Iface, exiting.";
        goto shutdown;
    }

    dms = new DisplayModesSDM(controller, cookie);
    if (dms == nullptr) {
        LOG(ERROR) << "Can not create an instance of LiveDisplay HAL DisplayModesSDM Iface, exiting.";
        goto shutdown;
    }

    pa = new PictureAdjustment(controller, cookie);
    if (pa == nullptr) {
        LOG(ERROR)
            << "Can not create an instance of LiveDisplay HAL PictureAdjustment Iface, exiting.";
        goto shutdown;
    }

    if (!dms->isSupported() && !pa->isSupported()) {
        // SDM backend isn't ready yet, so restart and try again
        goto shutdown;
    }

    configureRpcThreadpool(1, true /*callerWillJoin*/);

    // fallback to SDM impl if kernel display modes isn't supported
    if (dm->isSupported()) {
        status = dm->registerAsService();
        if (status != OK) {
            LOG(ERROR) << "Could not register service for LiveDisplay HAL DisplayModes Iface ("
                       << status << ")";
            goto shutdown;
        }
    } else if (dms->isSupported()) {
        status = dms->registerAsService();
        if (status != OK) {
            LOG(ERROR) << "Could not register service for LiveDisplay HAL DisplayModesSDM Iface ("
                       << status << ")";
            goto shutdown;
        }
    }

    if (pa->isSupported()) {
        status = pa->registerAsService();
        if (status != OK) {
            LOG(ERROR) << "Could not register service for LiveDisplay HAL PictureAdjustment Iface ("
                       << status << ")";
            goto shutdown;
        }
    }

    LOG(INFO) << "LiveDisplay HAL custom service is ready.";
    joinRpcThreadpool();
    // Should not pass this line

shutdown:
    // In normal operation, we don't expect the thread pool to shutdown
    LOG(ERROR) << "LiveDisplay HAL custom service is shutting down.";
    return 1;
}
