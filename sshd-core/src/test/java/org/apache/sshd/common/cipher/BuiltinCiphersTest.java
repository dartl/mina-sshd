/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.sshd.common.cipher;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Set;

import org.apache.sshd.common.Cipher;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.util.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:dev@mina.apache.org">Apache MINA SSHD Project</a>
 */
public class BuiltinCiphersTest extends BaseTest {
    public BuiltinCiphersTest() {
        super();
    }

    @Test
    public void testFromEnumName() {
        for (BuiltinCiphers expected : BuiltinCiphers.VALUES) {
            String  name=expected.name();

            for (int index=0; index < name.length(); index++) {
                BuiltinCiphers  actual=BuiltinCiphers.fromString(name);
                Assert.assertSame(name + " - mismatched enum values", expected, actual);
                name = shuffleCase(name);   // prepare for next time
            }
        }
    }

    @Test
    public void testFromFactoryName() {
        for (BuiltinCiphers expected : BuiltinCiphers.VALUES) {
            String  name=expected.getName();
            
            for (int index=0; index < name.length(); index++) {
                BuiltinCiphers  actual=BuiltinCiphers.fromFactoryName(name);
                Assert.assertSame(name + " - mismatched enum values", expected, actual);
                name = shuffleCase(name);   // prepare for next time
            }
        }
    }

    @Test
    public void testFromFactory() {
        for (BuiltinCiphers expected : BuiltinCiphers.VALUES) {
            if (!expected.isSupported()) {
                System.out.append("Skip unsupported cipher: ").println(expected);
                continue;
            }
            
            NamedFactory<Cipher>    factory=expected;
            Assert.assertEquals(expected.name() + " - mismatched factory names", expected.getName(), factory.getName());

            BuiltinCiphers  actual=BuiltinCiphers.fromFactory(factory);
            Assert.assertSame(expected.getName() + " - mismatched enum values", expected, actual);
        }
    }

    @Test
    public void testAllConstantsCovered() throws Exception {
        Set<BuiltinCiphers> avail=EnumSet.noneOf(BuiltinCiphers.class);
        Field[]             fields=BuiltinCiphers.Constants.class.getFields();
        for (Field f : fields) {
            String          name=(String) f.get(null);
            BuiltinCiphers  value=BuiltinCiphers.fromFactoryName(name);
            Assert.assertNotNull("No match found for " + name, value);
            Assert.assertTrue(name + " re-specified", avail.add(value));
        }
        
        Assert.assertEquals("Incomplete coverage", BuiltinCiphers.VALUES, avail);
    }

    @Test
    public void testSupportedCipher() throws Exception {
        for (BuiltinCiphers expected : BuiltinCiphers.VALUES) {
            if (!expected.isSupported()) {
                System.out.append("Skip unsupported cipher: ").println(expected);
                continue;
            }
            Cipher cipher = expected.create();
            byte[] key = new byte[cipher.getBlockSize()];
            byte[] iv = new byte[cipher.getIVSize()];
            cipher.init(Cipher.Mode.Encrypt, key, iv);
        }
    }
}
