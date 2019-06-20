/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.stdlib.crypto.nativeimpl;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.jvm.Strand;
import org.ballerinalang.jvm.values.ArrayValue;
import org.ballerinalang.jvm.values.MapValue;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.stdlib.crypto.Constants;
import org.ballerinalang.stdlib.crypto.CryptoUtils;

import java.security.InvalidKeyException;
import java.security.PrivateKey;

/**
 * Extern function ballerina.crypto:signRsaSha384.
 *
 * @since 0.990.3
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "crypto",
        functionName = "signRsaSha384", isPublic = true)
public class SignRsaSha384 extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {

    }

    public static Object signRsaSha384(Strand strand, ArrayValue inputValue, MapValue<?, ?> privateKey) {
        byte[] input = inputValue.getBytes();
        try {
            PrivateKey key = (PrivateKey) privateKey.getNativeData(Constants.NATIVE_DATA_PRIVATE_KEY);
            return new ArrayValue(CryptoUtils.sign("SHA384withRSA", key, input));
        } catch (InvalidKeyException e) {
            return CryptoUtils.createCryptoError("invalid uninitialized key");
        }
    }
}
