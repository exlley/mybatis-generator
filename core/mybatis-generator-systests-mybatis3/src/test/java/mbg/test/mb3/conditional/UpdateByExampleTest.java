/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package mbg.test.mb3.conditional;

import static mbg.test.common.util.TestUtilities.blobsAreEqual;
import static mbg.test.common.util.TestUtilities.generateRandomBlob;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.mb3.generated.conditional.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.conditional.mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.conditional.mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.conditional.mapper.PKBlobsMapper;
import mbg.test.mb3.generated.conditional.mapper.PKFieldsMapper;
import mbg.test.mb3.generated.conditional.mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.conditional.mapper.PKOnlyMapper;
import mbg.test.mb3.generated.conditional.model.AwfulTable;
import mbg.test.mb3.generated.conditional.model.AwfulTableExample;
import mbg.test.mb3.generated.conditional.model.FieldsBlobsExample;
import mbg.test.mb3.generated.conditional.model.FieldsBlobsWithBLOBs;
import mbg.test.mb3.generated.conditional.model.FieldsOnly;
import mbg.test.mb3.generated.conditional.model.FieldsOnlyExample;
import mbg.test.mb3.generated.conditional.model.PKBlobs;
import mbg.test.mb3.generated.conditional.model.PKBlobsExample;
import mbg.test.mb3.generated.conditional.model.PKFields;
import mbg.test.mb3.generated.conditional.model.PKFieldsExample;
import mbg.test.mb3.generated.conditional.model.PKFieldsBlobs;
import mbg.test.mb3.generated.conditional.model.PKFieldsBlobsExample;
import mbg.test.mb3.generated.conditional.model.PKOnlyExample;
import mbg.test.mb3.generated.conditional.model.PKOnlyKey;

/**
 * 
 * @author Jeff Butler
 *
 */
public class UpdateByExampleTest extends AbstractConditionalTest {

    @Test
    public void testFieldsOnlyUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(99d);
            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);
            
            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(5);
            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 11.22, 0.001);
            assertEquals(record.getFloatfield(), 33.44, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 5);
            
            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(8);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 99d, 0.001);
            assertEquals(record.getFloatfield(), 66.77, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 8);
            
            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(9);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 99d, 0.001);
            assertEquals(record.getFloatfield(), 100.111, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 9);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlyUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setIntegerfield(22);
            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldEqualTo(5);
            
            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(22);
            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertNull(record.getDoublefield());
            assertNull(record.getFloatfield());
            assertEquals(record.getIntegerfield().intValue(), 22);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey();
            key.setId(1);
            key.setSeqNum(3);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setId(5);
            key.setSeqNum(6);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setId(7);
            key.setSeqNum(8);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setSeqNum(3);
            PKOnlyExample example = new PKOnlyExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleSelective(key, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria()
                .andIdEqualTo(5)
                .andSeqNumEqualTo(3);
            
            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
            
            example.clear();
            example.createCriteria()
                .andIdEqualTo(7)
                .andSeqNumEqualTo(3);
            
            returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey();
            key.setId(1);
            key.setSeqNum(3);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setId(5);
            key.setSeqNum(6);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setId(7);
            key.setSeqNum(8);
            mapper.insert(key);

            key = new PKOnlyKey();
            key.setSeqNum(3);
            key.setId(22);
            PKOnlyExample example = new PKOnlyExample();
            example.createCriteria()
                .andIdEqualTo(7);
            int rows = mapper.updateByExample(key, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria()
                .andIdEqualTo(22)
                .andSeqNumEqualTo(3);
            
            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);
    
            record = new PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);
    
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Fred");
            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andLastnameLike("J%");
            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(1, rows);
            
            example.clear();
            example.createCriteria()
                .andFirstnameEqualTo("Fred")
                .andLastnameEqualTo("Jones")
                .andId1EqualTo(3)
                .andId2EqualTo(4);
    
            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);
    
            record = new PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);
    
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Fred");
            record.setId1(3);
            record.setId2(4);
            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria()
                .andId1EqualTo(3)
                .andId2EqualTo(4);
            
            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);
            
            example.clear();
            example.createCriteria()
                .andFirstnameEqualTo("Fred")
                .andLastnameIsNull()
                .andId1EqualTo(3)
                .andId2EqualTo(4);
    
            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobs record = new PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            PKBlobs newRecord = new PKBlobs();
            newRecord.setBlob1(generateRandomBlob());
            
            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);
            
            List<PKBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKBlobs returnedRecord = answer.get(0);
            
            assertEquals(6, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(newRecord.getBlob1(), returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobs record = new PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            PKBlobs newRecord = new PKBlobs();
            newRecord.setId(8);
            
            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);
            
            List<PKBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKBlobs returnedRecord = answer.get(0);
            
            assertEquals(8, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobs record = new PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            PKBlobs newRecord = new PKBlobs();
            newRecord.setId(8);
            
            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);
            
            List<PKBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKBlobs returnedRecord = answer.get(0);
            
            assertEquals(8, returnedRecord.getId().intValue());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobs record = new PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobs newRecord = new PKFieldsBlobs();
            newRecord.setFirstname("Fred");
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1NotEqualTo(3);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);
    
            List<PKFieldsBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKFieldsBlobs returnedRecord = answer.get(0);
            
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(), returnedRecord.getFirstname());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobs record = new PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobs newRecord = new PKFieldsBlobs();
            newRecord.setId1(5);
            newRecord.setId2(8);
            newRecord.setFirstname("Fred");
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1EqualTo(5);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);
    
            List<PKFieldsBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKFieldsBlobs returnedRecord = answer.get(0);
            
            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(), returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobs record = new PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);
    
            record = new PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobs newRecord = new PKFieldsBlobs();
            newRecord.setId1(3);
            newRecord.setId2(8);
            newRecord.setFirstname("Fred");
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1EqualTo(3);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);
    
            List<PKFieldsBlobs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            PKFieldsBlobs returnedRecord = answer.get(0);
            
            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(), returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            FieldsBlobsWithBLOBs newRecord = new FieldsBlobsWithBLOBs();
            newRecord.setLastname("Doe");
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);
            
            List<FieldsBlobsWithBLOBs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);
            
            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            FieldsBlobsWithBLOBs newRecord = new FieldsBlobsWithBLOBs();
            newRecord.setFirstname("Scott");
            newRecord.setLastname("Doe");
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);
            
            List<FieldsBlobsWithBLOBs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);
            
            assertEquals(newRecord.getFirstname(), returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);
    
            record = new FieldsBlobsWithBLOBs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            FieldsBlobsWithBLOBs newRecord = new FieldsBlobsWithBLOBs();
            newRecord.setFirstname("Scott");
            newRecord.setLastname("Doe");
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);
            
            List<FieldsBlobsWithBLOBs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());
            
            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);
            
            assertEquals(newRecord.getFirstname(), returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
    
            mapper.insert(record);
    
            record = new AwfulTable();
            record.seteMail("fred2@fred.com");
            record.setEmailaddress("alsofred2@fred.com");
            record.setFirstFirstName("fred11");
            record.setFrom("from from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("fred22");
            record.setThirdFirstName("fred33");
    
            mapper.insert(record);
    
            AwfulTable newRecord = new AwfulTable();
            newRecord.setFirstFirstName("Alonzo");
            AwfulTableExample example = new AwfulTableExample();
            example.createCriteria().andEMailLike("fred2@%");
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);
    
            List<AwfulTable> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            AwfulTable returnedRecord = answer.get(0);
            
            assertEquals(record.getCustomerId(), returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord.getEmailaddress());
            assertEquals(newRecord.getFirstFirstName(), returnedRecord.getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord.getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord.getThirdFirstName());
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
    
        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
    
            mapper.insert(record);
    
            record = new AwfulTable();
            record.seteMail("fred2@fred.com");
            record.setEmailaddress("alsofred2@fred.com");
            record.setFirstFirstName("fred11");
            record.setFrom("from from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("fred22");
            record.setThirdFirstName("fred33");
    
            mapper.insert(record);
    
            AwfulTable newRecord = new AwfulTable();
            newRecord.setFirstFirstName("Alonzo");
            newRecord.setCustomerId(58);
            newRecord.setId1(111);
            newRecord.setId2(222);
            newRecord.setId5(555);
            newRecord.setId6(666);
            newRecord.setId7(777);
            
            AwfulTableExample example = new AwfulTableExample();
            example.createCriteria().andEMailLike("fred2@%");
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andCustomerIdEqualTo(58);
            List<AwfulTable> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            AwfulTable returnedRecord = answer.get(0);
            
            assertEquals(newRecord.getCustomerId(), returnedRecord.getCustomerId());
            assertNull(returnedRecord.geteMail());
            assertNull(returnedRecord.getEmailaddress());
            assertEquals(newRecord.getFirstFirstName(), returnedRecord.getFirstFirstName());
            assertNull(returnedRecord.getFrom());
            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getId5(), returnedRecord.getId5());
            assertEquals(newRecord.getId6(), returnedRecord.getId6());
            assertEquals(newRecord.getId7(), returnedRecord.getId7());
            assertNull(returnedRecord.getSecondFirstName());
            assertNull(returnedRecord.getThirdFirstName());
        } finally {
            sqlSession.close();
        }
    }
}
