/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 *
 */

package org.ballerinalang.net.jms.nativeimpl.message;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.AbstractBlockingAction;
import org.ballerinalang.net.jms.JmsConstants;
import org.ballerinalang.net.jms.utils.BallerinaAdapter;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Get a float property in the JMS Message.
 */
@BallerinaFunction(
        orgName = "ballerina",
        packageName = "jms",
        functionName = "getType",
        receiver = @Receiver(type = TypeKind.OBJECT, structType = "Message", structPackage = "ballerina/jms"),
        returnType = { @ReturnType(type = TypeKind.STRING)},
        isPublic = true
)
public class GetType extends AbstractBlockingAction {

    @Override
    public void execute(Context context, CallableUnitCallback callableUnitCallback) {

        Struct messageStruct = BallerinaAdapter.getReceiverObject(context);
        Message message = BallerinaAdapter.getNativeObject(messageStruct,
                                                           JmsConstants.JMS_MESSAGE_OBJECT,
                                                           Message.class,
                                                           context);
        try {
            String messageType = message.getJMSType();
            context.setReturnValues(new BString(messageType));
        } catch (JMSException e) {
            BallerinaAdapter.returnError("Error when retrieving float property", context, e);
        }
    }
}
