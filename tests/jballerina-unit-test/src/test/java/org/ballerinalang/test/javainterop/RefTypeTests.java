/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.test.javainterop;

import org.ballerinalang.jvm.scheduling.Scheduler;
import org.ballerinalang.jvm.types.BTypes;
import org.ballerinalang.jvm.values.FPValue;
import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.jvm.values.TypedescValue;
import org.ballerinalang.jvm.values.XMLItem;
import org.ballerinalang.jvm.values.XMLValue;
import org.ballerinalang.model.types.BTypeDesc;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BHandleValue;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BTypeDescValue;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.model.values.BValueArray;
import org.ballerinalang.model.values.BValueType;
import org.ballerinalang.model.values.BXML;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test cases for java interop with ballerina ref types.
 *
 * @since 1.0.0
 */
public class RefTypeTests {

    private CompileResult result;

    @BeforeClass
    public void setup() {
        result = BCompileUtil.compile("test-src/javainterop/ballerina_types_as_interop_types.bal");
    }

    @Test(description = "Test interoperability with ballerina array value as return and map as a param")
    public void testInteropWithBalArrayAndMap() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithArrayAndMap");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BValueArray);
        BValueArray arr = (BValueArray) returns[0];
        Assert.assertEquals(arr.stringValue(), "[1, 8]");
    }

    @Test(description = "Test interoperability with ballerina service type value as a param")
    public void testInteropWithServiceTypesAndStringReturn() {
        BValue[] returns = BRunUtil.invoke(result, "acceptServiceAndBooleanReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        BBoolean bool = (BBoolean) returns[0];
        Assert.assertTrue(bool.booleanValue());
    }

    @Test(description = "Test interoperability with ballerina ref types as params and map return")
    public void testInteropWithRefTypesAndMapReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithRefTypesAndMapReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BMap);
        BMap bMap = (BMap) returns[0];
        Assert.assertEquals(bMap.stringValue(), "{\"a\":{}, \"b\":[5, \"hello\", {}]," +
                " \"c\":{name:\"sameera\"}, \"e\":{}, \"f\":83, \"g\":{name:\"sample\"}}");
    }

    @Test(description = "Test interoperability with ballerina error return")
    public void testInteropWithErrorReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithErrorReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BString);
        Assert.assertEquals(returns[0].stringValue(), "example error with given reason");
    }

    @Test(description = "Test interoperability with ballerina union return")
    public void testInteropWithUnionReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithUnionReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        Assert.assertTrue(((BBoolean) returns[0]).booleanValue());
    }

    @Test(description = "Test interoperability with ballerina object return")
    public void testInteropWithObjectReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithObjectReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        Assert.assertTrue(((BBoolean) returns[0]).booleanValue());
    }

    @Test(description = "Test interoperability with ballerina record return")
    public void testInteropWithRecordReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithRecordReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        Assert.assertTrue(((BBoolean) returns[0]).booleanValue());
    }

    @Test(description = "Test interoperability with ballerina any return")
    public void testInteropWithAnyReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithAnyReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        Assert.assertTrue(((BBoolean) returns[0]).booleanValue());
    }

    @Test(description = "Test interoperability with ballerina anydata return")
    public void testInteropWithAnydataReturn() {
        BValue[] returns = BRunUtil.invoke(result, "interopWithAnydataReturn");
        Assert.assertEquals(returns.length, 1);

        Assert.assertTrue(returns[0] instanceof BBoolean);
        Assert.assertTrue(((BBoolean) returns[0]).booleanValue());
    }

    @Test(description = "Test interoperability with ballerina json return")
    public void testInteropWithJsonReturns() {
        BValue[] returns = BRunUtil.invoke(result, "testJsonReturns");
        Assert.assertEquals(returns.length, 5);

        Assert.assertTrue(returns[0] instanceof BMap);
        Assert.assertEquals(returns[0].toString(), "{\"name\":\"John\"}");

        Assert.assertTrue(returns[1] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[1]).intValue(), 4);

        Assert.assertTrue(returns[2] instanceof BMap);
        Assert.assertEquals(returns[2].toString(), "{\"name\":\"Doe\"}");

        Assert.assertTrue(returns[3] instanceof BValueArray);
        Assert.assertEquals(returns[3].toString(), "[\"John\"]");

        Assert.assertNull(returns[4]);
    }

    @Test(description = "Test interoperability with ballerina json params")
    public void testInteropWithJsonParams() {
        BValue[] returns = BRunUtil.invoke(result, "testJsonParams");
        Assert.assertTrue(returns[0] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[0]).intValue(), 7);
    }

    @Test
    public void testGetXML() {
        BValue[] returns = BRunUtil.invoke(result, "getXML");
        Assert.assertTrue(returns[0] instanceof BXML);
        Assert.assertEquals(returns[0].stringValue(), "<hello></hello>");
    }

    @Test
    public void testPassXML() {
        BValue[] returns = BRunUtil.invoke(result, "testPassingXML");
        Assert.assertTrue(returns[0] instanceof BString);
        Assert.assertEquals(returns[0].stringValue(), "<foo></foo>");
    }

    @Test
    public void testGetAllInts() {
        BValue[] returns = BRunUtil.invoke(result, "getAllInts");
        Assert.assertTrue(returns[0] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[0]).intValue(), 3);
    }

    @Test
    public void testAcceptAllInts() {
        BValue[] returns = BRunUtil.invoke(result, "testAcceptAllInts");
        Assert.assertTrue(returns[0] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[0]).intValue(), 4);
        Assert.assertTrue(returns[1] instanceof BFloat);
        Assert.assertEquals(((BFloat) returns[1]).floatValue(), 4.0);
        Assert.assertTrue(returns[2] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[2]).intValue(), 4);
    }

    @Test
    public void testGetMixType() {
        BValue[] returns = BRunUtil.invoke(result, "getMixType");
        Assert.assertTrue(returns[0] instanceof BValueType);
        Assert.assertEquals(((BValueType) returns[0]).intValue(), 5);
    }

    @Test
    public void testGetIntegersAsMixType() {
        BValue[] returns = BRunUtil.invoke(result, "getIntegersAsMixType");
        Assert.assertTrue(returns[0] instanceof BValueType);
        Assert.assertEquals(((BValueType) returns[0]).intValue(), 3);
    }

    @Test
    public void testAcceptMixType() {
        BValue[] returns = BRunUtil.invoke(result, "testAcceptMixTypes");
        Assert.assertTrue(returns[0] instanceof BInteger);
        Assert.assertEquals(((BInteger) returns[0]).intValue(), 2);
        Assert.assertTrue(returns[1] instanceof BHandleValue);
        Assert.assertEquals(((BHandleValue) returns[1]).getValue(), "hello");
        Assert.assertTrue(returns[2] instanceof BBoolean);
        Assert.assertEquals(((BBoolean) returns[2]).booleanValue(), false);
    }

    @Test
    public void testUseFunctionPointer() {
        BValue[] returns = BRunUtil.invoke(result, "testUseFunctionPointer");
        Assert.assertTrue(returns[0] instanceof BValueType);
        Assert.assertEquals(((BValueType) returns[0]).intValue(), 7);
    }

    @Test
    public void testGetFunctionPointer() {
        BValue[] returns = BRunUtil.invoke(result, "testGetFunctionPointer");
        Assert.assertTrue(returns[0] instanceof BValueType);
        Assert.assertEquals(((BValueType) returns[0]).intValue(), 10);
    }

    @Test
    public void testUseTypeDesc() {
        BValue[] returns = BRunUtil.invoke(result, "testUseTypeDesc");
        Assert.assertTrue(returns[0] instanceof BHandleValue);
        Assert.assertEquals(((BHandleValue) returns[0]).getValue(), "typedesc json");
    }

    @Test
    public void testGetTypeDesc() {
        BValue[] returns = BRunUtil.invoke(result, "testGetTypeDesc");
        Assert.assertTrue(returns[0] instanceof BTypeDescValue);
        Assert.assertEquals(((BTypeDescValue) returns[0]).stringValue(), "xml");
    }

    // static methods

    public static XMLValue getXML() {
        return new XMLItem("<hello/>");
    }

    public static String getStringFromXML(XMLValue x) {
        return x.toString();
    }

    public static int getAllInts() {
        return 3;
    }

    public static int acceptAllInts(int x) {
        return x;
    }

    public static float getAllFloats() {
        return (float) 3.54;
    }

    public static float acceptAllFloats(float x) {
        return x;
    }

    public static Object getAny() {
        return 5;
    }

    public static Object acceptAny(Object x) {
        return x;
    }

    public static int useFunctionPointer(FPValue fp) {
        return ((Long) fp.call(new Object[] { Scheduler.getStrand(), 3, true, 4, true })).intValue();
    }

    public static FPValue getFunctionPointer(Object fp) {
        return (FPValue) fp;
    }

    public static String useTypeDesc(TypedescValue type) {
        return type.stringValue();
    }

    public static TypedescValue getTypeDesc() {
        return new TypedescValue(BTypes.typeXML);
    }
}
